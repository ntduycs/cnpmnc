package graph;

import model.Graph;
import model.Node;
import observer.Observer;

import java.util.Map;

public class GraphUI extends graph.internal.Graph implements Observer<Graph> {

    Graph graph;

    @Override
    public void update(Graph data) {

        this.graph = data;

        this.getModel().clear();

        this.beginUpdate();

        initGraph();

        this.endUpdate();
    }

    protected void initGraph() {
        for (Node node: this.graph.getNodes()) {
            this.getModel().addDefaultNode(node.getName().toString());
        }

        for (Node node: this.graph.getNodes()) {

            Map<Node, Integer> adj = node.getAdjacentNodes();

            for (Map.Entry<Node, Integer> e: adj.entrySet()) {
                Node x = e.getKey();
                Integer distance = e.getValue();
                this.getModel().addEdge(node.getName().toString(), x.getName().toString(), distance.toString());
            }
        }
    }
}
