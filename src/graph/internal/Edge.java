package graph.internal;

import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import graph.internal.cell.Cell;

public class Edge extends Group {

    private Cell source;
    private Cell target;
    private String labelText;

    private Line line, arrow1, arrow2;
    private Label label;

    private static final double arrowLength = 20;
    private static final double arrowWidth = 10;

    public Edge(Cell source, Cell target) {
        initLine(source, target);
        getChildren().addAll(line, arrow1, arrow2);
    }

    public Edge(Cell source, Cell target, String labelText) {
        initLine(source, target);

        this.labelText = labelText;
        label = new Label(labelText);

        label.layoutXProperty().bind(line.endXProperty().subtract(line.endXProperty().subtract(line.startXProperty()).divide(2).add(10)));
        label.layoutYProperty().bind(line.endYProperty().subtract(line.endYProperty().subtract(line.startYProperty()).divide(2)).add(10));

        getChildren().addAll(line, arrow1, arrow2, label);
    }

    private void initLine(Cell source, Cell target) {
        this.source = source;
        this.target = target;

        source.addCellChild(target);
        target.addCellParent(source);

        line = new Line();

        line.startXProperty().bind(source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind(source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind(target.layoutXProperty().add(target.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind(target.layoutYProperty().add(target.getBoundsInParent().getHeight() / 2.0));

        initDirect();

        resetColor();
    }

    private void initDirect() {
        arrow1 = new Line();
        arrow2 = new Line();

        InvalidationListener updater = o -> {

            double ex = line.getEndX();
            double ey = line.getEndY();
            double sx = line.getStartX();
            double sy = line.getStartY();

            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // length = 0
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);

            } else {

                double wx = target.getBoundsInParent().getWidth() / 2;
                double wy = target.getBoundsInParent().getHeight() / 2;

                double lenLine = Math.hypot(sx - ex, sy - ey);

                double etx = ex, ety = ey;
                ex += (sx - ex) * (wx / lenLine);
                ey += (sy - ey) * (wy / lenLine);
                sx += etx - ex;
                sy += ety - ey;

                lenLine = Math.hypot(sx - ex, sy - ey);

                double factor = arrowLength / lenLine;
                double factorO = arrowWidth / lenLine;

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part ortogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }
        };

        line.startXProperty().addListener(updater);
        line.startYProperty().addListener(updater);
        line.endXProperty().addListener(updater);
        line.endYProperty().addListener(updater);
        updater.invalidated(null);
    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", target=" + target +
                ", labelText='" + labelText + '\'' +
                '}';
    }

    public void highlightColor() {
        line.setStroke(Color.RED);
        arrow1.setStroke(Color.RED);
        arrow2.setStroke(Color.RED);
    }

    public void resetColor() {
        line.setStroke(Color.BLACK);
        arrow1.setStroke(Color.BLACK);
        arrow2.setStroke(Color.BLACK);
    }
}