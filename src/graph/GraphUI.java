package graph;

import graph.internal.Edge;
import graph.internal.GraphState;
import graph.internal.RandomLayout;
import javafx.util.Pair;
import model.Node;
import observer.Observer;
import util.BundleDijkstra;
import util.BundleEuler;
import util.Triple;

import java.util.ArrayList;
import java.util.List;

public class GraphUI extends graph.internal.Graph implements Observer {

    private void initializeGraph(int numVertices) {
        clear();

        GraphState graphState = getModel();
        beginUpdate();
        for (int i = 0; i < numVertices; i++) {
            graphState.addDefaultNode(Integer.toString(i + 1));
        }
        endUpdate();

        randomLayout();
    }

    private void randomLayout() {
        new RandomLayout(this).execute();
    }

    private void updateEdge(int src, int dest, int distance) {
        GraphState graphState = getModel();
        beginUpdate();

        graphState.removeEdge(src + "", dest + "");
        graphState.addEdge(src + "", dest + "", distance + "");

        endUpdate();
    }

    private void removeEdge(int src, int dest) {
        GraphState graphState = getModel();
        beginUpdate();

        graphState.removeEdge(src + "", dest + "");

        endUpdate();
    }


    @Override
    public void update(Object data) {
        if (data instanceof Integer) {
            int numVertices = (int) data;
            initializeGraph(numVertices);

        } else if (data instanceof Triple) {
            Triple<Integer> d = (Triple<Integer>) data;
            int src = d.x;
            int dest = d.y;
            int distance = d.z;
            updateEdge(src, dest, distance);

        } else if (data instanceof Pair) {
            Pair<Integer, Integer> d = (Pair<Integer, Integer>) data;
            int src = d.getKey();
            int dest = d.getValue();
            removeEdge(src, dest);

        } else if (data instanceof BundleDijkstra) {
            BundleDijkstra dt = (BundleDijkstra) data;

            if (dt.type.equals(BundleDijkstra.TYPE_EXECUTE)) {

            } else if (dt.type.equals(BundleDijkstra.TYPE_RESULT)) {
                renderResultDijkstra(dt);
            }

        } else if (data instanceof BundleEuler) {
            BundleEuler dt = (BundleEuler) data;

            if (dt.type.equals(BundleEuler.EXECUTING)) {

            } else if (dt.type.equals(BundleEuler.FINISHED)) {
                renderResultEuler(dt);
            }

        }
    }

    private void renderResultEuler(BundleEuler result) {
        // TODO
    }

    void renderResultDijkstra(BundleDijkstra result) {
        model.Graph graph = result.graph;
        Node sourceNode = findNodeById(result.sourceId, graph);
        Node endNode = findNodeById(result.endId, graph);
        if (sourceNode == null || endNode == null) {
            // TODO
            return;
        }
        List<Edge> highlightEdges = new ArrayList<>();
        while (endNode != sourceNode) {
            Node prevNode = result.tracingPath.getOrDefault(endNode, null);
            if (prevNode == null) {
                // TODO
                System.out.println("Previous node is null");
                break;
            } else {
                Edge e = findEdgeBy2Point(prevNode.getName().toString(), endNode.getName().toString());
                if (e == null) {
                    // TODO
                    System.out.println("Edge is null");
                    break;
                } else {
                    highlightEdges.add(e);
                }
                endNode = prevNode;
            }
        }
        beginUpdate();
        getModel().resetColorEdges();
        getModel().highlightEdges(highlightEdges);
        endUpdate();
    }

    Edge findEdgeBy2Point(String srcId, String destId) {
        for (Edge e: getModel().getAllEdges()) {
            if (e.getSource().getCellId().equals(srcId) && e.getTarget().getCellId().equals(destId)) {
                return  e;
            }
        }
        return null;
    }

    Node findNodeById(String id, model.Graph graph) {
        for (Node u: graph.getNodes()) {
            if (u.getName().toString().equals(id)) {
                return u;
            }
        }
        return null;
    }
}
