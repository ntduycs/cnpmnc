package visitor;

import javafx.util.Pair;
import model.Graph;
import model.Node;

import java.util.*;

public class EulerVisitor implements GraphVisitor {

    Graph graph;
    HashMap<Node,Integer> inDegree = new HashMap<>();
    HashMap<Node,Integer> outDegree = new HashMap<>();
    HashSet<Pair<Node,Node>> usedEdges = new HashSet<>();
    Stack<Node> currentPath = new Stack<>();
    List<Node> path = new ArrayList<>();

    boolean isEulerianCycle = false;

    @Override
    public void visit(Graph visitableGraph) {
        this.graph = visitableGraph;

        if (this.graph.getNodes().size() == 0) return;

        this.init();

        if (!checkEulerCycle()) {
            return;
        }

        findEulerCircuit();

        this.recheckEuler();
    }

    private void recheckEuler() {
        if (path.size() < graph.getNodes().size()) {
            this.isEulerianCycle = false;
            this.path.clear();
        }
    }

    private void findEulerCircuit() {
        Node currentNode = graph.getNodes().iterator().next();
        currentPath.push(currentNode);

        while (!currentPath.isEmpty()) {
            Node nextNode = null;
            for (Node v: currentNode.getAdjacentNodes().keySet()) {
                Pair<Node,Node> edge = new Pair<>(currentNode, v);
                if (!usedEdges.contains(edge)) {
                    usedEdges.add(edge);
                    nextNode = v;
                    break;
                }
            }
            if (nextNode != null) {
                currentPath.add(currentNode);
                currentNode = nextNode;
            } else {
                path.add(currentNode);
                currentNode = currentPath.pop();
            }
        }

        Collections.reverse(path);
    }

    private boolean checkEulerCycle() {
        isEulerianCycle = false;

        if (!checkInOutDegree()) {
            return false;
        }

        this.isEulerianCycle = true;
        return true;
    }

    private  boolean checkInOutDegree() {
        for (Node u: graph.getNodes()) {
            if (!outDegree.get(u).equals(inDegree.get(u))) {
                return false;
            }
        }
        return true;
    }

    private void init() {
        for (Node node: this.graph.getNodes()) {
            outDegree.put(node, node.getAdjacentNodes().size());
            for (Node v: node.getAdjacentNodes().keySet()) {
                inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
            }
        }
    }

    public boolean isEulerianCycle() {
        return isEulerianCycle;
    }

    public List<Node> getPath() {
        return path;
    }

    @Override
    public void visit(Graph visitableGraph, String sourceId) {

    }
}
