package graph;


import graph.internal.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class Sample extends Application {

    Graph graph = new Graph();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        graph = new Graph();

        root.setCenter(graph.getScrollPane());

        Scene scene = new Scene(root, 1024, 768);

        primaryStage.setScene(scene);
        primaryStage.show();

        addGraphComponents();

        Layout layout = new RandomLayout(graph);
        layout.execute();

    }

    private void addGraphComponents() {

        GraphState model = graph.getModel();

        graph.beginUpdate();

        model.addDefaultNode("1");
        model.addDefaultNode("2");
        model.addDefaultNode("3");
        model.addDefaultNode("4");
        model.addDefaultNode("5");
        model.addDefaultNode("6");
        model.addDefaultNode("7");
        model.addDefaultNode("8");

        model.addEdge("1", "2", "15");
        model.addEdge("1", "3", "26");
        model.addEdge("1", "5", "13");
        model.addEdge("3", "6", "12");
        model.addEdge("4", "5", "45");
        model.addEdge("7", "8", "9");

        List<Edge> edges = model.getAddedEdges();
        model.highlightEdges(edges);

        model.addEdge("6", "1", "5");
        model.addEdge("7", "2", "15");
        model.addEdge("2", "7", "1");
        model.addEdge("8", "5", "18");
        model.addEdge("1", "8", "51");
        model.addEdge("3", "4", "23");

        graph.endUpdate();
    }

    public static void main(String[] args) {
        launch(args);
    }
}