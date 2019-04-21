package graph;

import graph.internal.GraphState;
import graph.internal.RandomLayout;
import javafx.util.Pair;
import observer.Observer;
import util.Triple;

public class GraphUI extends graph.internal.Graph implements Observer {

    private void initializeGraph(int numVertices) {
        GraphState graphState = getModel();
        beginUpdate();
        for (int i = 0; i < numVertices; i++) {
            graphState.addDefaultNode(Integer.toString(i+1));
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

        graphState.removeEdge(src+"", dest+"");
        graphState.addEdge(src+"", dest+"", distance+"");

        endUpdate();
    }

    private void removeEdge(int src, int dest) {
        GraphState graphState = getModel();
        beginUpdate();

        graphState.removeEdge(src+"", dest+"");

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
        }
    }
}
