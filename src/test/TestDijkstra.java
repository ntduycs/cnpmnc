package test;

import model.Graph;
import model.Node;
import visitor.DijkstraVisitor;

import java.util.HashMap;

public class TestDijkstra {

    public static void main(String[] args) {

        Node nodeA = new Node("1");
        Node nodeB = new Node("2");
        Node nodeC = new Node("3");
        Node nodeD = new Node("4");
        Node nodeE = new Node("5");
        Node nodeF = new Node("6");

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

        DijkstraVisitor visitor = new DijkstraVisitor();
        visitor.visit(graph, nodeA.getName().toString());

        HashMap<Node,Integer> result = visitor.getDistanceTo();

        for (Node node: result.keySet()) {
            System.out.println("Node: " + node.getName() + ": " + result.get(node));
            Node u = node;
            while (u != null) {
                System.out.print(u.getName() + " <- ");
                u = visitor.getTracingPath().getOrDefault(u, null);
            }
            System.out.println();
        }

    }

}
