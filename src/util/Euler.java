package util;

import model.Graph;
import model.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Euler {
    private Graph graph;

    public Euler(Graph graph) {
        this.graph = graph;
    }

    private void DFSUtil(Node startVertex, Boolean[] visited) {
        // Mark the current node as visited
        visited[startVertex.getName()] = true;

        Node node;

        Iterator<Node> adjList =startVertex.getAdjacentNodes().keySet().iterator();
        while (adjList.hasNext()) {
            node = adjList.next();
            System.out.println(node.getName());
            if (!visited[node.getName()])
                DFSUtil(node,visited);
        }
    }

    private Boolean isStrongConnected() {
        int graphSize = graph.getNodes().size();
        Boolean[] visited = new Boolean[graphSize];

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
        for (Node node :
                graph.getNodes()) {
            inward.put(node, 0);
            outward.put(node, 0);
        }
        int graphSize = graph.getNodes().size();
        for (Node node: graph.getNodes()) {
            int sum = 0;
            for (Node adjNode : node.getAdjacentNodes().keySet()) {
                inward.put(adjNode, inward.get(adjNode) + 1);
                sum = sum +1;
            }
            outward.put(node, sum);
        }

        Iterator iterator = inward.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry ele = (Map.Entry) iterator.next();
            if (ele.getValue() != outward.get(ele.getKey())) return false;
        }
        return true;
    }
}
