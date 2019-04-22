package util;

import model.Graph;
import model.Node;

import java.util.ArrayList;
import java.util.List;

public class BundleEuler {

    public static final String EXECUTING = "EXECUTING";
    public static final String FINISHED = "FINISHED";

    public String type = EXECUTING;
    public boolean status;
    public Graph graph;

    public List<Node> path = new ArrayList<>();

    public BundleEuler() {
    }

    public BundleEuler(boolean status) {
        this.status = status;
    }

    public BundleEuler(boolean status, List<Node> path) {
        this.status = status;
        this.path = path;
    }

    public BundleEuler(String type, boolean status, List<Node> path) {
        this.type = type;
        this.status = status;
        this.path = path;
    }

    public BundleEuler(String type, boolean status, Graph graph, List<Node> path) {
        this.type = type;
        this.status = status;
        this.graph = graph;
        this.path = path;
    }
}
