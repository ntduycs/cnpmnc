package util;

import javafx.scene.control.Alert;

public class AlertError {

    private static AlertError instance = new AlertError();

    public static final String DEFAULT_TITLE = "Lỗi";
    public static final String DEFAULT_HEADER = "Đã có lỗi xảy ra";

    Alert alert;

    private AlertError() {
        alert = new Alert(Alert.AlertType.ERROR);
    }

    public static AlertError getInstance() {
        return instance;
    }

    public void show(String title, String header, String message) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void show(String title, String message) {
        alert.setTitle(title);
        alert.setHeaderText(DEFAULT_HEADER);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void show(String message) {
        alert.setTitle(DEFAULT_TITLE);
        alert.setHeaderText(DEFAULT_HEADER);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
