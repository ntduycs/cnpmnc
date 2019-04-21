package program;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Random;

public class AdjacencyMatrix {
    private TextField inputField = new TextField("Input here ...");
    private Button generateButton = new Button("Show");

    private BorderPane root;

    private GridPane gridPane = new GridPane();

    public AdjacencyMatrix() {
        generateButton.setOnAction(e -> {
            int inputValue = Integer.parseInt(inputField.getText());
            generateAdjMatrix(inputValue);
        });
        HBox button = new HBox(5, inputField,generateButton);
        button.setPadding(new Insets(5));
        button.setAlignment(Pos.CENTER);
        root = new BorderPane(gridPane);
        root.setBottom(button);
    }


    private void generateAdjMatrix(int numVertices) {
        int length = numVertices;
        int width = numVertices;

        for(int y = 0; y < length; y++){
            for(int x = 0; x < width; x++){

                Random rand = new Random();
                int rand1 = rand.nextInt(5);

                // Create a new TextField in each Iteration
                TextField tf = new TextField();
                tf.setPrefHeight(30);
                tf.setPrefWidth(50);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(true);
                tf.setText(Integer.toString(rand1));
                System.out.println(tf.getText());

                // Iterate the Index using the loops
                GridPane.setRowIndex(tf,y);
                GridPane.setColumnIndex(tf,x);
                gridPane.getChildren().add(tf);
            }
        }
    }



    public Node getView() {
        return root;
    }

}
