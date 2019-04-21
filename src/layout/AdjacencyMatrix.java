package layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import observer.Observable;
import util.Triple;

public class AdjacencyMatrix extends Observable {

    private TextField inputField = new TextField("Nhập số đỉnh đồ thị");
    private TextField startVertex = new TextField("Nhập đỉnh bắt đầu");
    private Button generateButton = new Button("Tạo mới");
    private Button btnDijkstra = new Button("Dijkstra");
    private Button btnEuler = new Button("Euler");
    private GridPane gridPane = new GridPane();
    private BorderPane root;

    public AdjacencyMatrix() {

        HBox inputForm = new HBox(5, inputField,generateButton, btnDijkstra, btnEuler);
        inputForm.setPadding(new Insets(5));
        inputForm.setAlignment(Pos.CENTER);
        HBox inputForm2 = new HBox(5, inputField,generateButton, btnDijkstra, btnEuler);
        inputForm2.setPadding(new Insets(5));
        inputForm2.setAlignment(Pos.CENTER);

        VBox commonForm = new VBox(20, inputForm, inputForm2);

        root = new BorderPane(gridPane);
        root.setBottom(commonForm);

        addListeners();
    }

    private void addListeners() {
        generateButton.setOnAction(e -> {
            int inputValue = Integer.parseInt(inputField.getText());
            generateAdjMatrix(inputValue);
            this.notifyAllObservers(inputValue);
        });

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

                } else if (x == y) {
                    tf.setDisable(true);

                } else {
                    tf.setEditable(true);
                    addListener(tf, y, x);
                }

                // Iterate the Index using the loops
                GridPane.setRowIndex(tf, y);
                GridPane.setColumnIndex(tf, x);
                gridPane.getChildren().add(tf);

            }
        }
    }

    private void addListener(TextField tf, final int row, final int col) {
        tf.textProperty().addListener(observable -> {

            try {
                int distance = Integer.valueOf(tf.getText());

                notifyAllObservers(new Triple<>(row, col, distance));
            } catch (NumberFormatException e) {
                e.printStackTrace();

                notifyAllObservers(new Pair<>(row, col));
            }
        });
    }

    public Node getView() {
        return root;
    }
}
