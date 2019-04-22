package model;

import javafx.util.Pair;
import observer.Observable;
import observer.Observer;
import util.BundleDijkstra;
import util.BundleEuler;
import util.Triple;
import visitor.DijkstraVisitor;
import visitor.EulerVisitor;
import visitor.GraphVisitor;
import visitor.VisitableGraph;

import java.util.*;

public class Graph extends Observable implements VisitableGraph, Observer {

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

    public Node getNodeByName(Integer name) {
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
        for (Node node : nodes) {
            System.out.println("Node: " + node.getName());
            System.out.println("Adj nodes:");
            for (Node adjNode : node.getAdjacentNodes().keySet()) {
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
            nodes.clear();
            for (int i = 0; i < numVertices; i++) {
                nodes.add(new Node(i + 1));
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

        } else if (data instanceof BundleDijkstra) {
            BundleDijkstra dt = (BundleDijkstra) data;

            if (dt.type.equals(BundleDijkstra.TYPE_EXECUTE)) {
                runDijkstra(dt.sourceId, dt.endId);

            } else if (dt.type.equals(BundleDijkstra.TYPE_RESULT)) {

            }

        } else if (data instanceof BundleEuler) {
            BundleEuler dt = (BundleEuler) data;
            if (dt.type.equals(BundleEuler.EXECUTING)) {

            } else if (dt.type.equals(BundleEuler.FINISHED)){
                runEuler();
            }

        }
    }

    void runDijkstra(String sourceId, String endId) {
        DijkstraVisitor visitor = new DijkstraVisitor();
        visitor.visit(this, sourceId);

        HashMap<Node, Integer> result = visitor.getDistanceTo();
        HashMap<Node, Node> tracingPath = visitor.getTracingPath();

        for (Node node : nodes) {
            System.out.println("Node: " + node.getName() + " - Distance: " + result.get(node));
            Node u = node;
            while (u != null) {
                System.out.print(u.getName() + " <- ");
                u = tracingPath.getOrDefault(u, null);
            }
            System.out.println("X");
        }

        this.notifyAllObservers(new BundleDijkstra(BundleDijkstra.TYPE_RESULT, sourceId, endId, this, result, tracingPath));
    }

    void runEuler() {
        EulerVisitor visitor = new EulerVisitor();
        visitor.visit(this);

        System.out.println(visitor.isEulerianCycle());
        for (Node node : visitor.getPath()) {
            System.out.println(node.getName());
        }

        this.notifyAllObservers(new BundleEuler(BundleEuler.FINISHED, visitor.isEulerianCycle(), this, visitor.getPath()));
    }
}
