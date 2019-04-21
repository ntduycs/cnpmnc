package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Person {
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dateOfBirth = new SimpleObjectProperty<>();
    public final StringProperty firstNameProperty() {
        return this.firstName;
    }

    public final String getFirstName() {
        return this.firstNameProperty().get();
    }

    public final void setFirstName(final String firstName) {
        this.firstNameProperty().set(firstName);
    }

    public final StringProperty lastNameProperty() {
        return this.lastName;
    }

    public final String getLastName() {
        return this.lastNameProperty().get();
    }

    public final void setLastName(final String lastName) {
        this.lastNameProperty().set(lastName);
    }

    public final ObjectProperty<LocalDate> dateOfBirthProperty() {
        return this.dateOfBirth;
    }

    public final LocalDate getDateOfBirth() {
        return this.dateOfBirthProperty().get();
    }

    public final void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirthProperty().set(dateOfBirth);
    }

}
