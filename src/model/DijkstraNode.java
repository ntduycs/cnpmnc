package model;

import model.Node;

import java.util.LinkedList;
import java.util.List;

public class DijkstraNode extends Node {

    private List<Node> shortestPath = new LinkedList<>();
    private Integer distanceToSource = Integer.MAX_VALUE;

    public DijkstraNode(Node node) {
        super(node);
        shortestPath = new LinkedList<>();
        distanceToSource = Integer.MAX_VALUE;
    }

    @Override
    public List<Node> getShortestPath() {
        return shortestPath;
    }

    @Override
    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    @Override
    public Integer getDistanceToSource() {
        return distanceToSource;
    }

    @Override
    public void setDistanceToSource(Integer distanceToSource) {
        this.distanceToSource = distanceToSource;
    }
}
