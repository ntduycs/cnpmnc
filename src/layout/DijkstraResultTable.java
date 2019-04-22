package layout;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import model.DijkstraResult;
import model.Graph;
import model.Node;
import observer.Observer;
import util.AlertError;
import util.BundleDijkstra;
import visitor.DijkstraVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton Pattern
 */
public class DijkstraResultTable extends Group implements Observer {

    private static DijkstraResultTable instance = new DijkstraResultTable();

    public static DijkstraResultTable getInstance() {
        return instance;
    }

    private DijkstraResultTable() {
        init();
    }

    private VBox layout;
    private TableView<DijkstraResult> dijkstraTable = new TableView<>();
    private TableColumn<DijkstraResult, Integer> srcCol = new TableColumn<>("Nguồn");
    private TableColumn<DijkstraResult, Integer> desCol = new TableColumn<>("Đích");
    private TableColumn<DijkstraResult, Integer> minDistance = new TableColumn<>("K.C Ngắn nhất");
    private TableColumn<DijkstraResult, String> path = new TableColumn<>("Đường đi");

    public void show() {
        layout.setVisible(true);
    }

    public void hide() {
        layout.setVisible(false);
    }

    private void init() {
        layout = new VBox(10, new Label("Kết quả giải thuật Dijkstra"), dijkstraTable);
        layout.setPadding(new Insets(10));
        layout.setVisible(false); // Hidden this table until press Dijkstra button
        layout.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

        dijkstraTable.setPrefWidth(layout.getPrefWidth());
        srcCol.setPrefWidth(dijkstraTable.getPrefWidth() * 0.125);
        desCol.setPrefWidth(dijkstraTable.getPrefWidth() * 0.125);
        minDistance.setPrefWidth(dijkstraTable.getPrefWidth() * 0.3);
        path.setPrefWidth(dijkstraTable.getPrefWidth() * 0.45);

        dijkstraTable.getColumns().addAll(srcCol, desCol, minDistance, path);
        dijkstraTable.setEditable(false);

        bindTableToGraphModel();

        this.getChildren().add(layout);
    }

    private void bindTableToGraphModel() {
        srcCol.setCellValueFactory(new PropertyValueFactory<>("src"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("dest"));
        minDistance.setCellValueFactory(new PropertyValueFactory<>("minDistance"));
        path.setCellValueFactory(new PropertyValueFactory<>("path"));

        desCol.setSortType(TableColumn.SortType.ASCENDING);
    }

    @Override
    public void update(Object data) {
        if (data instanceof BundleDijkstra) {

            BundleDijkstra dt = (BundleDijkstra) data;

            if (dt.type.equals(BundleDijkstra.TYPE_EXECUTE)) {

            } else if (dt.type.equals(BundleDijkstra.TYPE_RESULT)) {
                handleUpdate(dt);
                this.show();
            }
        }
    }

    private void handleUpdate(BundleDijkstra result) {
        Graph graph = result.graph;
        model.Node sourceNode = findNodeById(result.sourceId, graph);

        if (sourceNode == null) {
            // TODO
            AlertError.getInstance().show("Đỉnh vừa nhập không tồn tại");
            return;
        }

        ArrayList<Node> path = new ArrayList<>();

        dijkstraTable.getItems().clear();

        for (Node endNode: graph.getNodes()) {
            int minDist = result.distanceTo.get(endNode);
            if (minDist == DijkstraVisitor.INF) {
                // no path
                dijkstraTable.getItems().add(new DijkstraResult(
                        sourceNode.getName(), endNode.getName(), -1,
                        "Path Not Found"));
                continue;
            }
            Node targetNode = endNode;
            path.clear();
            while (endNode != sourceNode) {
                path.add(endNode);
                Node prevNode = result.tracingPath.getOrDefault(endNode, null);
                if (prevNode == null) {
                    // TODO
                    System.out.println("Previous node is null");
                    break;
                } else {
                    endNode = prevNode;
                }
            }
            if (endNode != sourceNode) {
                System.out.println("Something error");
            }
            path.add(sourceNode);
            Collections.reverse(path);

            List<String> stringPath = path.stream()
                    .map(node -> node.getName().toString())
                    .collect(Collectors.toList());

            dijkstraTable.getItems().add(new DijkstraResult(
                    sourceNode.getName(), targetNode.getName(), minDist,
                    String.join(" -> ", stringPath)));
        }
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
