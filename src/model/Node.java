package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {
    private Integer name;
    private Map<Node, Integer> adjacentNodes = new HashMap<>();
    private List<Node> shortestPath = new LinkedList<>();
    private Integer distanceToSource = Integer.MAX_VALUE;

    public Node(Node node) {
        name = node.name;
        adjacentNodes = node.adjacentNodes;
    }

    public Node() {

    }

    public Node(Integer name) {
        this.name = name;
    }

    public Node(String a) {
        this.name = Integer.valueOf(a);
    }

    public void addAdjacentNode(Node node, Integer weight) {
        this.adjacentNodes.put(node, weight);
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

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistanceToSource() {
        return distanceToSource;
    }

    public void setDistanceToSource(Integer distanceToSource) {
        this.distanceToSource = distanceToSource;
    }
}
