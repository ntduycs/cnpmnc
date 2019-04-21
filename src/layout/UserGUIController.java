package layout;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.util.Pair;
import observer.Observable;
import util.Triple;

public class UserGUIController implements Observable {

    public static final String NOTIFY_RUN_DIJKSTRA = "NOTIFY_RUN_DIJKSTRA";
    public static final String NOTIFY_RUN_EULER = "NOTIFY_RUN_EULER";

    private TextField inputField = new TextField();
    private TextField startVertex = new TextField("Nhập đỉnh bắt đầu");
    private TextField endVertex = new TextField("Nhập đỉnh bắt đầu");
    private Button generateButton = new Button("Tạo mới");
    private Button btnDijkstra = new Button("Chạy Dijkstra");
    private Button btnEuler = new Button("Chạy Euler");
    private GridPane tableAdjacencyMatrix = new GridPane();
    private BorderPane root;

    public UserGUIController() {

        HBox inputForm = new HBox(8, new Label("Số đỉnh đồ thị: "), inputField,generateButton, btnDijkstra, btnEuler);
        inputForm.setPadding(new Insets(10));
        inputForm.setAlignment(Pos.CENTER);
        inputField.setPrefWidth(50);

        VBox matrix = new VBox(8, new Label("Ma trận kề"), tableAdjacencyMatrix);
        matrix.setPadding(new Insets(10));

        root = new BorderPane(matrix);
        root.setTop(inputForm);

        addListeners();
    }

    private void addListeners() {
        generateButton.setOnAction(e -> {
            int inputValue = Integer.parseInt(inputField.getText());
            generateAdjMatrix(inputValue);
            this.notifyAllObservers(inputValue);
        });

        btnDijkstra.setOnAction(this::handleDijkstra);
        btnEuler.setOnAction(this::handleEuler);
    }

    private void handleDijkstra(ActionEvent event) {
        // TODO
        this.notifyAllObservers(UserGUIController.NOTIFY_RUN_DIJKSTRA);
    }

    private void handleEuler(ActionEvent event) {
        // TODO
        this.notifyAllObservers(UserGUIController.NOTIFY_RUN_EULER);
    }

    private void generateAdjMatrix(int numVertices) {
        int length = numVertices;
        int width = numVertices;

        for(int y = 0; y < length+1; y++){
            for(int x = 0; x < width+1; x++){

                // Create a new TextField in each Iteration
                TextField tf = new TextField();
                tf.setPrefHeight(40);
                tf.setPrefWidth(40);
                tf.setAlignment(Pos.CENTER);
                tf.setPadding(new Insets(4));

                if (x == 0 || y == 0) {
                    tf.setText((x+y)+"");
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
