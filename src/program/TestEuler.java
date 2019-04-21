package program;

import model.Graph;
import model.Node;
import util.Euler;

public class TestEuler {
    public static void main(String[] args) {
        Graph graph = new Graph();

        for (int i = 0; i<4;i++) {
            graph.addNode(new Node(i));
        }

        Euler euler = new Euler(graph);

        graph.getNodeByName(0).addAdjacentNode(graph.getNodeByName(1), 1);
        graph.getNodeByName(0).addAdjacentNode(graph.getNodeByName(2), 1);
        graph.getNodeByName(1).addAdjacentNode(graph.getNodeByName(2), 1);
        graph.getNodeByName(2).addAdjacentNode(graph.getNodeByName(3), 1);
        graph.getNodeByName(3).addAdjacentNode(graph.getNodeByName(0), 1);

        graph.printGraph();
        System.out.println(euler.existEulerCircuit());
    }
}
