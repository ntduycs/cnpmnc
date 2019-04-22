package graph.internal.cell;

public class FactoryCell {

    public static Cell createCell(String id, CellType type) {

        switch (type) {

            case RECTANGLE:
                return new RectangleCell(id);

            case TRIANGLE:
                return new TriangleCell(id);

            case CIRCLE:
                return new CircleCell(id);

            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

}
