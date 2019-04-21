package adapter;

import model.Node;

import java.util.LinkedList;
import java.util.List;

public class DijkstraNodeAdapter {

    Node node;

    private List<Node> shortestPath = new LinkedList<>();
    private Integer distanceToSource = Integer.MAX_VALUE;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
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
