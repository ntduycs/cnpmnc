package program;

import graph.internal.Graph;
import graph.internal.GraphState;
import graph.internal.Layout;
import graph.internal.RandomLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import observer.Observable;

import java.util.Random;

public class AdjacencyMatrix implements Observable {
    private TextField inputField = new TextField("Input here ...");
    private Button generateButton = new Button("Show");

    private BorderPane root;

    private GridPane gridPane = new GridPane();

    public AdjacencyMatrix(Graph graph) {
        generateButton.setOnAction(e -> {
            int inputValue = Integer.parseInt(inputField.getText());
            generateAdjMatrix(inputValue);
            initializeGraph(inputValue, graph);
        });
        HBox inputForm = new HBox(5, inputField,generateButton);
        inputForm.setPadding(new Insets(5));
        inputForm.setAlignment(Pos.CENTER);
        root = new BorderPane(gridPane);
        root.setBottom(inputForm);
    }

    private void initializeGraph(int numVertices, Graph graph) {
        GraphState graphState = graph.getModel();
        graph.beginUpdate();
        for (int i = 0; i < numVertices; i++) {
            graphState.addDefaultNode(Integer.toString(i+1));
        }
        graphState.addEdge("1", "2", "15");
        graph.endUpdate();
        Layout layout = new RandomLayout(graph);
        layout.execute();
    }

    public void updateGraph(Graph graph) {
        GraphState graphState = graph.getModel();
        graph.beginUpdate();
        // do something
//        graphState.addEdge("1","2", "25");
        graph.endUpdate();
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

    @Override
    public void notifyAllObservers() {

    }
}
