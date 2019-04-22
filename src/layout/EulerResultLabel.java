package layout;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import model.EulerResult;
import model.Node;
import observer.Observer;
import util.BundleEuler;

import java.util.List;
import java.util.stream.Collectors;

public class EulerResultLabel extends Group implements Observer {

    private static EulerResultLabel instance = new EulerResultLabel();

    public static EulerResultLabel getInstance() {
        return instance;
    }

    private EulerResultLabel() {
        init();
    }

    private VBox layout;
    private TableView<EulerResult> eulerTable = new TableView<>();
    private TableColumn<EulerResult, String> statusCol = new TableColumn<>("Trạng thái");
    private TableColumn<EulerResult, String> pathCol = new TableColumn<>("Đường đi");

    public void show() {
        layout.setVisible(true);
    }

    public void hide() {
        layout.setVisible(false);
    }

    private void init() {
        layout = new VBox(10, new Label("Kết quả giải thuật Euler"), eulerTable);
        layout.setPadding(new Insets(10));
        layout.setVisible(false);
        layout.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

        eulerTable.setPrefWidth(layout.getPrefWidth());
        statusCol.setPrefWidth(eulerTable.getPrefWidth() * 0.25);
        pathCol.setPrefWidth(eulerTable.getPrefWidth() * 0.75);

        eulerTable.getColumns().addAll(statusCol, pathCol);
        eulerTable.setEditable(false);

        bindTableToGraphModel();

        this.getChildren().add(layout);
    }

    private void bindTableToGraphModel() {
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));
    }


    @Override
    public void update(Object data) {
        BundleEuler dt = (BundleEuler) data;

        if (dt.type.equals(BundleEuler.EXECUTING)) {

        } else if (dt.type.equals(BundleEuler.FINISHED)) {
            handleUpdate(dt);
            this.show();
        }
    }

    private void handleUpdate(BundleEuler result) {

        List<Node> path;

        eulerTable.getItems().clear();

        boolean existPath = result.status;
        if (!existPath) {
            eulerTable.getItems().add(new EulerResult("Not found", ""));
        } else {
            path = result.path;

            List<String> stringPath = path.stream()
                    .map(node -> node.getName().toString())
                    .collect(Collectors.toList());

            System.out.println(String.join(" -> ", stringPath));

            eulerTable.getItems().add(new EulerResult("Found",
                    String.join(" ->", stringPath)));
        }
    }
}
