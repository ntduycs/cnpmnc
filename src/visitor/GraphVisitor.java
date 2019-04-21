package visitor;

import model.Graph;

public interface GraphVisitor {

    void visit(Graph visitableGraph);
    void visit(Graph visitableGraph, String sourceId);

}
