package model;

import javafx.util.Pair;
import observer.Observer;
import util.Triple;
import visitor.GraphVisitor;
import visitor.VisitableGraph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class Graph implements VisitableGraph, Observer {
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
        for (Node node: nodes) {
            System.out.println("Node: " + node.getName());
            System.out.println("Adj nodes:");
            for (Node adjNode: node.getAdjacentNodes().keySet()) {
                System.out.print(adjNode.getName() + "\t");
            }
            System.out.println("===========================");
        }
    }

    @Override
    public void update(Object data) {
        // User inputs the number of vertices
        if (data instanceof Integer) {
            int numVertices = (int) data;
            for (int i = 0; i< numVertices; i++) {
                nodes.add(new Node(i+1));
            }

        } else if (data instanceof Triple) {
            Triple<Integer> d = (Triple<Integer>) data;
            int src = d.x;
            int dest = d.y;
            int distance = d.z;
            Node srcNode = getNodeByName(src);
            Node desNode = getNodeByName(dest);
            srcNode.addAdjacentNode(desNode, distance);

        } else if (data instanceof Pair) {
            Pair<Integer, Integer> d = (Pair<Integer, Integer>) data;
            int src = d.getKey();
            int dest = d.getValue();
            Node srcNode = getNodeByName(src);
            Node destNode = getNodeByName(dest);
            srcNode.removeAdjacentNode(destNode);

        } else if (data instanceof String) {
//            printGraph();
            // TODO
        }
    }
}
