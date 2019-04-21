package visitor;

import model.Graph;
import model.Node;

import java.util.HashMap;
import java.util.PriorityQueue;

public class DijkstraVisitor implements GraphVisitor {

    private final int INF = Integer.MAX_VALUE;

    Graph graph;
    Node sourceNode;

    HashMap<Node,Integer> distanceTo = new HashMap<>();
    PriorityQueue<Data> priorityQueue;


    @Override
    public void visit(Graph graph) {
    }

    @Override
    public void visit(Graph visitableGraph, String sourceId) {
        this.initGraph(visitableGraph, sourceId);
        this.runDijkstra();
    }

    private void initGraph(Graph originGraph, String sourceId) {
        this.graph = originGraph;

        this.sourceNode = null;
        for (Node node: this.graph.getNodes()) {
            if (node.getName().equals(Integer.valueOf(sourceId))) {
                this.sourceNode = node;
                this.distanceTo.put(node, 0);
            } else {
                this.distanceTo.put(node, INF);
            }
        }
    }

    private void runDijkstra() {

        this.priorityQueue = new PriorityQueue<Data>();
        this.priorityQueue.add(new Data(this.sourceNode, this.distanceTo.get(this.sourceNode)));

        while (!this.priorityQueue.isEmpty()) {
            Data top = this.priorityQueue.poll();
            Node u = top.node;
            if (distanceTo.get(u) < top.distance) {
                continue;
            }
            for (Node v: u.getAdjacentNodes().keySet()) {
                int d = u.getAdjacentNodes().get(v);
                if (distanceTo.get(v) == INF || distanceTo.get(v) > top.distance + d) {
                    distanceTo.put(v, top.distance + d);
                    priorityQueue.add(new Data(v, distanceTo.get(v)));
                }
            }
        }
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public HashMap<Node, Integer> getDistanceTo() {
        return distanceTo;
    }


    class Data implements Comparable<Data> {

        Node node;
        Integer distance;

        public Data(Node node) {
            this.node = node;
        }

        public Data(Node node, Integer distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(Data o) {
            return distance - o.distance;
        }
    }
}
