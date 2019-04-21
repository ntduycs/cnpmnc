package graph.internal.cell;

public class FactoryCell {

    public static Cell createCell(String id, CellType type) {

        switch (type) {

            case RECTANGLE:
                RectangleCell rectangleCell = new RectangleCell(id);
                return rectangleCell;

            case TRIANGLE:
                TriangleCell triangleCell = new TriangleCell(id);
                return triangleCell;

            case CIRCLE:
                CircleCell circleCell = new CircleCell(id);
                return circleCell;

            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

}
