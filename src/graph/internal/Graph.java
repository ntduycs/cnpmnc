package graph.internal;

import javafx.collections.FXCollections;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import graph.internal.cell.Cell;
import graph.internal.cell.CellLayer;
import javafx.scene.shape.Line;
import javafx.stage.Screen;

public class Graph {
    private GraphState model;

    private Group canvas;

    private ZoomableScrollPane scrollPane;

    private MouseGestures mouseGestures;

    /**
     * the pane wrapper is necessary or else the scrollpane would always align
     * the top-most and left-most child to the top and left eg when you drag the
     * top child down, the entire scrollpane would move down
     */
    private CellLayer cellLayer;

    public Graph() {

        this.model = new GraphState();

        canvas = new Group();
        cellLayer = new CellLayer();

        canvas.getChildren().add(cellLayer);

        mouseGestures = new MouseGestures(this);

        scrollPane = new ZoomableScrollPane(canvas);

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        scrollPane.setPrefViewportWidth(visualBounds.getWidth() / 2.5);

        scrollPane.setFitToWidth(true);

    }

    public ScrollPane getView() {
        return this.scrollPane;
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public Pane getCellLayer() {
        return this.cellLayer;
    }

    public GraphState getModel() {
        return model;
    }

    public void beginUpdate() {
    }

    public void endUpdate() {

        // add components to internal pane
        getCellLayer().getChildren().addAll(model.getAddedEdges());
        getCellLayer().getChildren().addAll(model.getAddedCells());

        // remove components from internal pane
        getCellLayer().getChildren().removeAll(model.getRemovedCells());
        getCellLayer().getChildren().removeAll(model.getRemovedEdges());

        // enable dragging of cells
        for (Cell cell : model.getAddedCells()) {
            mouseGestures.makeDraggable(cell);
        }

        for (Edge edge : model.getAllEdges()) {
            edge.resetColor();
        }

        for (Edge edge : model.getHighlightEdges()) {
            edge.highlightColor();
        }

        // every cell must have a parent, if it doesn't, then the graphParent is
        // the parent
        getModel().attachOrphansToGraphParent(model.getAddedCells());

        // remove reference to graphParent
        getModel().disconnectFromGraphParent(model.getRemovedCells());

        // merge added & removed cells with all cells
        getModel().merge();

        FXCollections.sort(cellLayer.getChildren(), (o1, o2) -> {
            if (o1 instanceof Edge && !(o2 instanceof Edge)) {
                return -1;
            }
            if (o2 instanceof Edge && !(o1 instanceof Edge)) {
                return 1;
            }
            return 0;
        });

    }

    public double getScale() {
        return this.scrollPane.getScaleValue();
    }

}
