package program;

import javafx.geometry.Insets;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import model.Point;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Java FX application that plots points
 */
public class PointPlotter extends Application {

    // ObservableList of Points
    private ObservableList<Point> data;

    // Table module
    private PointTable tableModule;

    // Graph module
    private PointGraph graphModule;

    private AdjacencyMatrix matrixModule = new AdjacencyMatrix();

    // Must override this method for class that extends Application
    @Override
    public void start(Stage stage) {

        // Root node
        HBox root = new HBox();

        // Initialize the observable list
        data = FXCollections.observableArrayList();

        // Initialize instance of graph module
        graphModule = new PointGraph(data);

        // Initialize instance of table module
        tableModule = new PointTable(data);

        // Add modules to root
        root.getChildren().addAll(graphModule.getView(), matrixModule.getView());

        // Container associated with root node
        Scene scene = new Scene(root);

        // Set the scene for the stage
        stage.setScene(scene);

        // Set stage title
        stage.setTitle("Plot Points");

        // Show the window
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
