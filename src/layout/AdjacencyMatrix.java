package layout;

import graph.internal.Graph;
import graph.internal.GraphState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import observer.Observable;
import observer.Observer;
import util.Triple;

import java.util.Random;

public class AdjacencyMatrix implements Observable {

    private TextField inputField = new TextField("Nhập số đỉnh đồ thị");
    private Button generateButton = new Button("Tạo mới");
    private GridPane gridPane = new GridPane();
    private BorderPane root;

    public AdjacencyMatrix() {
        HBox inputForm = new HBox(5, inputField,generateButton);
        inputForm.setPadding(new Insets(5));
        inputForm.setAlignment(Pos.CENTER);

        root = new BorderPane(gridPane);
        root.setBottom(inputForm);

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
            int src = row;
            int dest = col;

            try {
                int distance = Integer.valueOf(tf.getText().toString());

                notifyAllObservers(new Triple<Integer>(src, dest, distance));
            } catch (NumberFormatException e) {
                e.printStackTrace();

                notifyAllObservers(new Pair<>(src, dest));
            }
        });
    }

    public Node getView() {
        return root;
    }
}
