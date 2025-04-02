package com.example.miniproject2;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PersonsController {

    @FXML
    private Button addBtn;

    @FXML
    private TableColumn<Person, SimpleStringProperty> addressCol;

    @FXML
    private TableColumn<Person, String> statusCol;

    @FXML
    private RadioButton c1, c2, c3;
    @FXML
    private ToggleGroup statusGroup;

    @FXML
    private TextField addressFld;

    @FXML
    private TableColumn<Person, SimpleIntegerProperty> ageCol;

    @FXML
    private TextField ageFld;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Person, SimpleStringProperty> nameCol;

    @FXML
    private TextField nameFld;

    @FXML
    private TableView<Person> personsTable;

    @FXML
    private Button updateBtn;

    @FXML
    private Label errorMsg;

    private final PersonsStore personStore = new PersonsStore();

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusGroup = new ToggleGroup();
        c1.setToggleGroup(statusGroup);
        c2.setToggleGroup(statusGroup);
        c3.setToggleGroup(statusGroup);

        ObservableList<Person> persons = personStore.getPersonsList();
        personsTable.setItems(persons);

        personsTable.getSelectionModel().selectedItemProperty().addListener(evt -> {
            Person selectedPerson = personsTable.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                nameFld.setText(selectedPerson.getName());
                ageFld.setText(Integer.toString(selectedPerson.getAge()));
                addressFld.setText(selectedPerson.getAddress());
                setStatusRadioButton(selectedPerson.getStatus());
            }
        });

    }

    private String getSelectedStatus() {
        RadioButton selectedRadioButton = (RadioButton) statusGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText(); // Returns the text of the selected RadioButton ("single", "married",
                                                  // or "divorced")
        }
        return ""; // Default if nothing is selected
    }

    private void setStatusRadioButton(String status) {
        if (status.equals("single"))
            c1.setSelected(true);
        else if (status.equals("married"))
            c2.setSelected(true);
        else if (status.equals("divorced"))
            c3.setSelected(true);
    }

    @FXML
    void addPerson(ActionEvent event) {
        String error = "";
        boolean isValid = true;

        String name = nameFld.getText();
        if (name.isEmpty()) {
            error += "Error: Name is required\n";
            isValid = false;
        }

        Integer age = null;
        if (ageFld.getText().isEmpty()) {
            error += "Error: Age is required\n";
            isValid = false;
        } else
            try {
                age = Integer.parseInt(ageFld.getText());
            } catch (NumberFormatException ex) {
                error += "Error: Invalid age value!\n";
                isValid = false;
            }

        String address = addressFld.getText();
        if (address.isEmpty()) {
            error += "Error: Address is required\n";
            isValid = false;
        }
        String status = getSelectedStatus();
        if (status.isEmpty()) {
            error += "Status is required.\n";
            isValid = false;
        }

        if (isValid) {
            personStore.addPerson(new Person(name, age, address, status));

            nameFld.setText("");
            ageFld.setText("");
            addressFld.setText("");
            errorMsg.setText("");
        } else
            errorMsg.setText(error);

    }

    @FXML
    void deletePerson(ActionEvent event) {
        Person selectedPerson = personsTable.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            personStore.deletePerson(selectedPerson);

        }
    }

    @FXML
    void updatePerson(ActionEvent event) {

        Person selectedPerson = personsTable.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            String error = "";
            boolean isValid = true;

            String name = nameFld.getText();
            if (name.isEmpty()) {
                error += "Error: Name is required!\n";
                isValid = false;
            }

            Integer age = null;
            if (ageFld.getText().isEmpty()) {
                error += "Error: Age is required!\n";
                isValid = false;
            } else
                try {
                    age = Integer.parseInt(ageFld.getText());
                } catch (NumberFormatException ex) {
                    error += "Error: Invalid age value!\n";
                    isValid = false;
                }

            String address = addressFld.getText();
            if (address.isEmpty()) {
                error += "Error: Address is required!";
                isValid = false;
            }

            String status = getSelectedStatus(); // Use this instead of the above code

            if (isValid) {
                personStore.updatePerson(selectedPerson, name, age, address, status);
                personsTable.refresh();

                errorMsg.setText("");
            } else
                errorMsg.setText(error);

        }

    }

}
