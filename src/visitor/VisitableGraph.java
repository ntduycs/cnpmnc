package visitor;

public interface VisitableGraph {
    void accept(GraphVisitor graphVisitor);
    void accept(GraphVisitor graphVisitor, String sourceId);
}
