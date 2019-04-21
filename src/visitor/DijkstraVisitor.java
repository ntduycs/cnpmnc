package visitor;

import model.DijkstraNode;
import model.Graph;
import model.Node;

import java.util.*;

public class DijkstraVisitor implements GraphVisitor {

    Graph graph;
    DijkstraNode src;
    Set<Node> settledNodes = new HashSet<>();
    Set<Node> unsettledNodes = new HashSet<>();


    @Override
    public void visit(Graph graph) {
    }

    @Override
    public void visit(Graph visitableGraph, String sourceId) {
        this.initGraph(graph, sourceId);

        this.calculateShortestPathFromSource(this.src);
    }

    private void initGraph(Graph graph, String sourceId) {
        this.graph = new Graph();
        Set<Node> nodes = graph.getNodes();
        for (Node node: nodes) {
            graph.addNode(new DijkstraNode(node));
        }

        this.src = null;
        for (Node node: this.graph.getNodes()) {
            if (node.getName().equals(Integer.valueOf(sourceId))) {
                this.src = (DijkstraNode) node;
                break;
            }
        }
    }

    public DijkstraNode getSrc() {
        return src;
    }

    public void setSrc(DijkstraNode src) {
        this.src = src;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Graph calculateShortestPathFromSource(DijkstraNode src) {
        // Phase 1: Initialization
        src.setDistanceToSource(0);

        unsettledNodes.add(src);

        // Phase 2: Evaluation
        while (unsettledNodes.size() != 0) {
            Node currNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currNode);

            for (Map.Entry<Node, Integer> adjacencyPair : currNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currNode);
        }
        return this.graph;
    }

    /**
     * compares the actual distance with the newly calculated one while following the newly explored path
     * */
    private void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node srcNode) {
        Integer srcDistance = srcNode.getDistanceToSource();
        if (srcDistance + edgeWeight < evaluationNode.getDistanceToSource()) {
            evaluationNode.setDistanceToSource(srcDistance + edgeWeight);
            List<Node> shortestPath = new LinkedList<>(srcNode.getShortestPath());
            shortestPath.add(srcNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    /**
     * @return the node with the lowest distance (to src) from the unsettled nodes set
     * */
    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistanceToSource();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
}
