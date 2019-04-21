package model;

import visitor.GraphVisitor;
import visitor.VisitableGraph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
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

    public  Node getNodeByName(Integer name) {
        Node node = null;
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            node = iterator.next();
            if (node.getName().equals(name)) break;
            if (!iterator.hasNext()) throw new NoSuchElementException();
        }
        return node;
    }

    public void printGraph() {
        for (Node node :
                nodes) {
            System.out.print("This is node: ");
            System.out.println(node.getName());
            System.out.print("This is adjacent list: ");
            for (Node adjNode :
                    node.getAdjacentNodes().keySet()) {
                System.out.print(adjNode.getName() + "\t");
            }
            System.out.println("\n=========");
        }
    }
}
