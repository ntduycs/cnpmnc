package util;

import model.Graph;
import model.Node;

import java.util.*;

public class Dijkstra {
    public static Graph calculateShortestPathFromSource(Graph graph, Node src) {
        // Phase 1: Initialization
        src.setDistanceToSource(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

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
        return graph;
    }

    /**
     * compares the actual distance with the newly calculated one while following the newly explored path
     * */
    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node srcNode) {
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
    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
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
