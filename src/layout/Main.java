package layout;

import graph.internal.Graph;
import graph.internal.GraphState;
import graph.internal.Layout;
import graph.internal.RandomLayout;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import program.AdjacencyMatrix;
import program.PointTable;

public class Main extends Application {

    // Graph module
    private Graph graphModule = new Graph();

    private AdjacencyMatrix matrixModule = new AdjacencyMatrix(graphModule);

    // Must override this method for class that extends Application
    @Override
    public void start(Stage stage) {

        // Root node
        HBox root = new HBox();

        // Add modules to root
        root.getChildren().addAll(
                graphModule.getView(),
                matrixModule.getView()
        );

        // Container associated with root node
        Rectangle2D ourScreen = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, ourScreen.getWidth(), ourScreen.getHeight());

        // Set the scene for the stage
        stage.setScene(scene);

        // Set stage title
        stage.setTitle("Visualization");

        // Show the window
        stage.show();

        Layout layout = new RandomLayout(graphModule);
        layout.execute();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
