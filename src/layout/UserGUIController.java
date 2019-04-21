package layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.util.Pair;
import model.DijkstraResult;
import model.Graph;
import observer.Observable;
import util.BundleDijkstra;
import util.Triple;


public class UserGUIController extends Observable {

    public static final String NOTIFY_RUN_EULER = "NOTIFY_RUN_EULER";

    private TextField numVertices = new TextField();
    private TextField startVertex = new TextField();
    private TextField endVertex = new TextField();

    private Button generateButton = new Button("Tạo mới");
    private Button btnDijkstra = new Button("Chạy Dijkstra");
    private Button btnEuler = new Button("Chạy Euler");
    private Button btnReset = new Button("Quay lại");

    private TableView<model.DijkstraResult> dijkstraTable = new TableView<>();
    private TableColumn<model.DijkstraResult, Integer> srcCol = new TableColumn<>("Điểm nguồn");
    private TableColumn<model.DijkstraResult, Integer> desCol = new TableColumn<>("Điểm đích");
    private TableColumn<model.DijkstraResult, Integer> minDistance = new TableColumn<>("Khoảng cách ngắn nhất");
    private TableColumn<model.DijkstraResult, String> path = new TableColumn<>("Đường đi");

    private GridPane tableAdjacencyMatrix = new GridPane();

    // Contains adjacency matrix and Dijkstra's result table
    private StackPane stackPane = new StackPane();

    private BorderPane root;

    public UserGUIController() {
        numVertices.setTooltip(new Tooltip("Số đỉnh lớn hơn 20 sẽ làm phần đồ thị bị thu hẹp"));
        startVertex.setTooltip(new Tooltip("Nếu số đỉnh không hợp lệ thì sao"));
        endVertex.setTooltip(new Tooltip("Cái ni để làm chi ri @@@@"));

        HBox row1 = new HBox(8, new Label("Số đỉnh đồ thị: "), numVertices, generateButton, btnDijkstra, btnEuler, btnReset);
        row1.setPadding(new Insets(10));
        row1.setAlignment(Pos.CENTER_LEFT);

        HBox row2 = new HBox(8, new Label("Đỉnh bắt đầu: "), startVertex, new Label("Đỉnh kết thúc"), endVertex);
        row2.setPadding(new Insets(10));
        row2.setAlignment(Pos.CENTER_LEFT);

        VBox inputForm = new VBox(0, row1, row2);

        numVertices.setPrefWidth(50);
        startVertex.setPrefWidth(50);
        endVertex.setPrefWidth(50);

        VBox matrix = new VBox(8, new Label("Ma trận kề"), tableAdjacencyMatrix);
        matrix.setPadding(new Insets(10));
        matrix.setVisible(false);

        bindTableToGraphModel(srcCol, desCol, minDistance, path);
        renderDijkstraResult(dijkstraTable);
        //noinspection unchecked
        dijkstraTable.getColumns().addAll(srcCol, desCol, minDistance, path);
        dijkstraTable.setEditable(false);
        VBox table = new VBox(new Label("Kết quả giải thuật Dijkstra"), dijkstraTable);
        table.setPadding(new Insets(10));
        table.setVisible(false); // Hidden this table until press Dijkstra button

        stackPane.getChildren().addAll(table, matrix);

        root = new BorderPane(stackPane);
        root.setTop(inputForm);

        addListeners();
    }

    // TODO: move this to other place
    private void bindTableToGraphModel(TableColumn<model.DijkstraResult, Integer> srcCol,
                                       TableColumn<model.DijkstraResult, Integer> desCol,
                                       TableColumn<model.DijkstraResult, Integer> minDistanceCol,
                                       TableColumn<model.DijkstraResult, String> pathCol) {
        srcCol.setCellValueFactory(new PropertyValueFactory<>("src"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("dest"));
        minDistanceCol.setCellValueFactory(new PropertyValueFactory<>("minDistance"));
        pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));

        desCol.setSortType(TableColumn.SortType.ASCENDING);
    }

    // TODO: move this to other place
    private void renderDijkstraResult(TableView<DijkstraResult> table) {
        ObservableList<DijkstraResult> dijkstraResults = getDijkstraResult();
        table.setItems(dijkstraResults);
    }

    // TODO: move this to other place
    private ObservableList<DijkstraResult> getDijkstraResult() {
        DijkstraResult row1 = new DijkstraResult(1,2,2,"A -> B -> C");
        DijkstraResult row2 = new DijkstraResult(1,2,2,"A -> C -> D -> E -> G");
        DijkstraResult row3 = new DijkstraResult(1,2,2,"A -> E");
        DijkstraResult row4 = new DijkstraResult(1,2,2,"A");

        return FXCollections.observableArrayList(row1, row2, row3, row4);
    }

    private void addListeners() {
        generateButton.setOnAction(e -> {
            // Make the matrix visible
            ObservableList<Node> children = this.stackPane.getChildren();
            int numChildren = children.size();
            children.get(numChildren - 1).setVisible(true);

            // Generate the appropriate matrix based on user's input
            int inputValue = Integer.parseInt(numVertices.getText());
            generateAdjMatrix(inputValue);
            this.notifyAllObservers(inputValue);
        });

        btnDijkstra.setOnAction(e -> {
            if (!hasTypedStartVertex()) {
                showAlertDijkstra();
            }
            handleDijkstra(e);
            showDijkstraTable();
        });

        btnEuler.setOnAction(this::handleEuler);

        btnReset.setOnAction(e -> {
            for (Node element: this.stackPane.getChildren()) {
                element.setVisible(false);
            }
        });
    }

    private void showAlertDijkstra() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Đã có lỗi xảy ra khi thực hiện giải thuật Dijkstra");
        alert.setContentText("Đỉnh bắt đầu không được để trống và phải là số");
        alert.showAndWait();
    }

    private boolean hasTypedStartVertex() {
        return startVertex.getText() != null && startVertex.getText().matches("^[0-9]*[1-9][0-9]*$");
    }

    private void handleDijkstra(ActionEvent event) {
        this.notifyAllObservers(new BundleDijkstra(startVertex.getText(), endVertex.getText()));
    }

    // TODO: move this to other place
    private void showDijkstraTable() {
        ObservableList<Node> children = this.stackPane.getChildren();
        int numChildren = children.size();

        if (numChildren > 1) {
            Node currTopElement = children.get(numChildren - 1); // the adjacency matrix
            Node newTopElement = children.get(numChildren - 2); // the table

            currTopElement.setVisible(false);
            currTopElement.toBack();

            newTopElement.setVisible(true);
        }
    }

    private void handleEuler(ActionEvent event) {
        // TODO
        this.notifyAllObservers(UserGUIController.NOTIFY_RUN_EULER);
    }

    private void generateAdjMatrix(int numVertices) {
        int length = numVertices;
        int width = numVertices;

        for (int y = 0; y < length + 1; y++) {
            for (int x = 0; x < width + 1; x++) {

                // Create a new TextField in each Iteration
                TextField tf = new TextField();
                tf.setPrefHeight(32);
                tf.setPrefWidth(32);
                tf.setAlignment(Pos.CENTER);
                tf.setPadding(new Insets(4));

                if (x == 0 || y == 0) {
                    tf.setText((x + y) + "");
                    tf.setEditable(false);
                    tf.setStyle("-fx-control-inner-background: #caed7b");

                } else if (x == y) {
                    tf.setDisable(true);
                    tf.setStyle("-fx-control-inner-background: #808080");

                } else {
                    tf.setEditable(true);
                    addListener(tf, y, x);
                }

                // Iterate the Index using the loops
                GridPane.setRowIndex(tf, y);
                GridPane.setColumnIndex(tf, x);
                tableAdjacencyMatrix.getChildren().add(tf);

            }
        }
    }

    private void addListener(TextField tf, final int row, final int col) {
        tf.textProperty().addListener(observable -> {
            try {
                int distance = Integer.valueOf(tf.getText());
                if (distance < 0) {
                    // remove edge
                    notifyAllObservers(new Pair<>(row, col));
                } else {
                    // add or update edge
                    notifyAllObservers(new Triple<>(row, col, distance));
                }

            } catch (NumberFormatException e) {
                notifyAllObservers(new Pair<>(row, col));
            }

        });
    }

    public Node getView() {
        return root;
    }

}