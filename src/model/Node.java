package model;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private Integer name;
    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(Integer name) {
        this.name = name;
    }

    public Node(String a) {
        this.name = Integer.valueOf(a);
    }

    public void addAdjacentNode(Node node, Integer weight) {
        this.adjacentNodes.put(node, weight);
    }

    public void removeAdjacentNode(Node node) {
        this.adjacentNodes.remove(node);
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name=" + name +
                '}';
    }
}
