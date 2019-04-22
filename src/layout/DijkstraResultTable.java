package layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import model.DijkstraResult;
import observer.Observer;
import util.BundleDijkstra;

/**
 * Singleton Pattern
 */
public class DijkstraResultTable extends Group implements Observer<BundleDijkstra> {

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
        renderDijkstraResult();
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

    public void renderDijkstraResult() {
        ObservableList<DijkstraResult> dijkstraResults = getDijkstraResult();
        dijkstraTable.setItems(dijkstraResults);
    }

    private ObservableList<DijkstraResult> getDijkstraResult() {
        DijkstraResult row1 = new DijkstraResult(1, 2, 2, "A -> B -> C");
        DijkstraResult row2 = new DijkstraResult(1, 2, 2, "A -> C -> D -> E -> G");
        DijkstraResult row3 = new DijkstraResult(1, 2, 2, "A -> E");
        DijkstraResult row4 = new DijkstraResult(1, 2, 2, "A");

        return FXCollections.observableArrayList(row1, row2, row3, row4);
    }

    @Override
    public void update(BundleDijkstra data) {

    }
}
