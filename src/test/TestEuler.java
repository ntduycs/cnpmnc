package test;

import model.Graph;
import model.Node;
import visitor.EulerVisitor;

import java.util.List;

public class TestEuler {

    public static void main(String[] args) {

        Node a1 = new Node("1");
        Node a2 = new Node("2");
        Node a3 = new Node("3");
        Node a4 = new Node("4");
        Node a5 = new Node("5");
        Node a6 = new Node("6");
        Node a7 = new Node("7");

        a1.addAdjacentNode(a2, 1);
        a1.addAdjacentNode(a7, 1);
        a2.addAdjacentNode(a3, 1);
        a3.addAdjacentNode(a1, 1);
        a3.addAdjacentNode(a4, 1);
        a4.addAdjacentNode(a5, 1);
        a5.addAdjacentNode(a3, 1);
        a5.addAdjacentNode(a6, 1);
        a6.addAdjacentNode(a1, 1);
        a7.addAdjacentNode(a5, 1);

        Graph graph = new Graph();
        graph.addNode(a1);
        graph.addNode(a2);
        graph.addNode(a3);
        graph.addNode(a4);
        graph.addNode(a5);
        graph.addNode(a6);
        graph.addNode(a7);

        EulerVisitor visitor = new EulerVisitor();
        visitor.visit(graph);

        System.out.println(visitor.isEulerianCycle());
        List<Node> result = visitor.getPath();
        for (Node u: result) {
            System.out.println(u);
        }
    }
}
