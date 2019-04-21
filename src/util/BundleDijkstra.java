package util;

import model.Graph;
import model.Node;

import java.util.HashMap;

public class BundleDijkstra {

    public static final String TYPE_EXECUTE = "TYPE_EXECUTE";
    public static final String TYPE_RESULT = "TYPE_RESULT";

    public String type = TYPE_EXECUTE;
    public String sourceId;
    public String endId;
    public Graph graph;

    public HashMap<Node, Integer> distanceTo;
    public HashMap<Node, Node> tracingPath;

    public BundleDijkstra(String sourceId, String endId) {
        this.sourceId = sourceId;
        this.endId = endId;
    }

    public BundleDijkstra(String type, String sourceId, String endId, Graph graph, HashMap<Node, Integer> distanceTo, HashMap<Node, Node> tracingPath) {
        this.type = type;
        this.sourceId = sourceId;
        this.endId = endId;
        this.graph = graph;
        this.distanceTo = distanceTo;
        this.tracingPath = tracingPath;
    }
}
