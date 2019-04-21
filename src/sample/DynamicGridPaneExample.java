package sample;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Person;

public class DynamicGridPaneExample extends Application {

    private final List<Person> personList = new ArrayList<>();
    private  GridPane grid ;

    private final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT) ;


    private void addNewRow() {

        // create a new person, and add to list:
        Person person = new Person();
        personList.add(person);

        // create controls:
        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();
        DatePicker dateOfBirthPicker = new DatePicker();

        // bind controls to person:
        firstNameTextField.textProperty().bindBidirectional(person.firstNameProperty());
        lastNameTextField.textProperty().bindBidirectional(person.lastNameProperty());
        dateOfBirthPicker.valueProperty().bindBidirectional(person.dateOfBirthProperty());

        // add controls to grid:
        grid.addRow(personList.size(), firstNameTextField, lastNameTextField, dateOfBirthPicker);
    }

    @Override
    public void start(Stage primaryStage) {
        grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);

        grid.addRow(0, new Label("First Name"), new Label("Last Name"), new Label("Date of Birth"));

        Button add = new Button("Add");
        add.setOnAction(e -> addNewRow());

        // just as a demo of getting the values:
        Button process = new Button("Process data");
        process.setOnAction(e -> {
            for (Person person : personList) {

                String firstName = person.getFirstName();
                String lastName = person.getLastName();
                LocalDate dateOfBirth = person.getDateOfBirth();
                Object formattedDOB = dateOfBirth == null ? "" : format.format(dateOfBirth);
                System.out.printf("%s %s %s %n", firstName, lastName, formattedDOB);
            }
        });

        BorderPane root = new BorderPane(grid);
        HBox buttons = new HBox(5, add, process);
        buttons.setPadding(new Insets(5));
        buttons.setAlignment(Pos.CENTER);
        root.setBottom(buttons);

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
