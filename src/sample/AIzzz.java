package sample;

import model.Graph;
import model.Node;
import util.Dijkstra;

public class AIzzz {
    public static void main(String[] args) {

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addAdjacentNode(nodeB, 10);
        nodeA.addAdjacentNode(nodeC, 15);

        nodeB.addAdjacentNode(nodeD, 12);
        nodeB.addAdjacentNode(nodeF, 15);

        nodeC.addAdjacentNode(nodeE, 10);

        nodeD.addAdjacentNode(nodeE, 2);
        nodeD.addAdjacentNode(nodeF, 1);

        nodeF.addAdjacentNode(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);

        for (Node node: graph.getNodes()) {
            System.out.println("\nShortest path from nodeA to node" + node.getName() + " is " + node.getDistanceToSource());
        }

    }
}
