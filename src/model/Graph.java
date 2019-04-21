package model;

import visitor.GraphVisitor;
import visitor.VisitableGraph;

import java.util.HashSet;
import java.util.Set;

public class Graph implements VisitableGraph {
    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node node) {
        nodes.add(node);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void accept(GraphVisitor graphVisitor) {
        graphVisitor.visit(this);
    }

    @Override
    public void accept(GraphVisitor graphVisitor, String sourceId) {
        graphVisitor.visit(this, sourceId);
    }
}
