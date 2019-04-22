package layout;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import observer.Observable;
import util.AlertError;
import util.BundleDijkstra;
import util.BundleEuler;
import util.Triple;


public class UserGUIController extends Observable {

    private TextField numVertices = new TextField();
    private TextField startVertex = new TextField();
    private TextField endVertex = new TextField();

    private Button generateButton = new Button("Tạo mới");
    private Button btnDijkstra = new Button("Chạy Dijkstra");
    private Button btnEuler = new Button("Chạy Euler");
    private Button btnReset = new Button("Quay lại");

    private DijkstraResultTable dijkstraResultTable = DijkstraResultTable.getInstance();
    private EulerResultLabel eulerResultLabel = EulerResultLabel.getInstance();

    private GridPane tableAdjacencyMatrix = new GridPane();
    private VBox matrix;

    private BorderPane root;

    public UserGUIController() {
        numVertices.setTooltip(new Tooltip("Yêu cầu số đỉnh nhỏ hơn 20"));
        startVertex.setTooltip(new Tooltip("Nhập một đỉnh hợp lệ"));
        endVertex.setTooltip(new Tooltip("Nhập một đỉnh hợp lệ"));

        HBox row1 = new HBox(8, new Label("Số đỉnh đồ thị: "), numVertices, generateButton,
                btnDijkstra, btnEuler, btnReset);
        row1.setPadding(new Insets(10));
        row1.setAlignment(Pos.CENTER_LEFT);

        HBox row2 = new HBox(8, new Label("Đỉnh bắt đầu: "), startVertex, new Label("Đỉnh kết thúc"), endVertex);
        row2.setPadding(new Insets(10));
        row2.setAlignment(Pos.CENTER_LEFT);

        VBox inputForm = new VBox(0, row1, row2);

        numVertices.setPrefWidth(50);
        startVertex.setPrefWidth(50);
        endVertex.setPrefWidth(50);

        matrix = new VBox(8, new Label("Ma trận kề"), tableAdjacencyMatrix);
        matrix.setPadding(new Insets(10));
        matrix.setVisible(false);

        root = new BorderPane(new VBox(5, matrix, dijkstraResultTable, eulerResultLabel));
        root.setTop(inputForm);

        addListeners();
    }

    private void handleGenerate(ActionEvent ev) {
        try {
            int inputValue = Integer.parseInt(numVertices.getText());
            if (inputValue < 0) {
                AlertError.getInstance().show("Số đỉnh không hợp lệ");
            }
            matrix.setVisible(true);
            generateAdjMatrix(inputValue);
            this.notifyAllObservers(inputValue);
        } catch (NumberFormatException ex) {
            AlertError.getInstance().show("Số đỉnh không hợp lệ");
        }
    }

    private void addListeners() {
        generateButton.setOnAction(this::handleGenerate);
        btnDijkstra.setOnAction(this::handleDijkstra);
        btnEuler.setOnAction(this::handleEuler);

        btnReset.setOnAction(event -> {
            dijkstraResultTable.hide();
            eulerResultLabel.hide();
        });
    }

    private boolean hasTypedStartVertex() {
        return startVertex.getText() != null && startVertex.getText().matches("^[0-9]*[1-9][0-9]*$");
    }

    private void handleDijkstra(ActionEvent ev) {
        if (!hasTypedStartVertex()) {
            AlertError.getInstance().show("Đỉnh bắt đầu không được để trống và phải là số");
            return;
        }
        this.notifyAllObservers(new BundleDijkstra(startVertex.getText(), endVertex.getText()));
    }

    private void handleEuler(ActionEvent event) {
        // TODO
        this.notifyAllObservers(new BundleEuler());
    }

    private void generateAdjMatrix(int numVertices) {

        for (int y = 0; y < numVertices + 1; y++) {
            for (int x = 0; x < numVertices + 1; x++) {

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
