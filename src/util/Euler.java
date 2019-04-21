package util;

import model.Graph;
import model.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Euler {
    private Graph graph;
    private Stack<Node> path = null;

    public Euler(Graph graph) {
        this.graph = graph;
    }

    private void DFSUtil(Node startVertex, boolean[] visited) {
        // Mark the current node as visited
        visited[startVertex.getName()] = true;

        for (Node adjNode : startVertex.getAdjacentNodes().keySet()) {
            if (!visited[adjNode.getName()])
                DFSUtil(adjNode, visited);
        }
    }

    private Boolean isStrongConnected() {
        int graphSize = graph.getNodes().size();

        boolean[] visited = new boolean[graphSize];

        //Do DFS traversal starting from first vertex.
        DFSUtil(graph.getNodeByName(0), visited);

        // If DFS traversal doesn't visit all vertices, then return false.
        for (int i = 0; i < graphSize; i++)
            if (!visited[i]) return false;

        return true;
    }

    public Boolean existEulerCircuit() {
        if (!isStrongConnected()) return false;

        Map<Node, Integer> inward = new HashMap<>();
        Map<Node, Integer> outward = new HashMap<>();

        for (Node node : graph.getNodes()) {
            inward.put(node, 0);
            outward.put(node, 0);
        }

        for (Node node: graph.getNodes()) {
            for (Node adjNode : node.getAdjacentNodes().keySet()) {
                Integer inDegree = inward.get(adjNode) + 1;
                inward.put(adjNode, inDegree);
            }
            // outward stores the out degree of each vertex
            outward.put(node, node.getAdjacentNodes().size());
        }
        System.out.println("Inward:");
        for (Map.Entry<Node, Integer> nodeIntegerEntry : inward.entrySet()) {
            System.out.println( nodeIntegerEntry.getKey().getName() + ":"+nodeIntegerEntry.getValue());
        }
        System.out.println("Outward:");
        for (Map.Entry<Node, Integer> nodeIntegerEntry : outward.entrySet()) {
            System.out.println( nodeIntegerEntry.getKey().getName() + ":"+nodeIntegerEntry.getValue());
        }

            for (Map.Entry<Node, Integer> nodeIntegerEntry : inward.entrySet()) {
            Node currNode = (Node) ((Map.Entry) nodeIntegerEntry).getKey();
            if (((Map.Entry) nodeIntegerEntry).getValue() != outward.get(currNode)) return false;
        }
        return true;
    }
}
