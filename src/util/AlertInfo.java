package util;

import javafx.scene.control.Alert;

public class AlertInfo {

    private static AlertInfo instance = new AlertInfo();

    public static final String DEFAULT_TITLE = "Thông báo";
    public static final String DEFAULT_HEADER = "Không tìm thấy";

    private Alert alert;

    private AlertInfo() {
        alert = new Alert(Alert.AlertType.INFORMATION);
    }

    public static AlertInfo getInstance(){
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
