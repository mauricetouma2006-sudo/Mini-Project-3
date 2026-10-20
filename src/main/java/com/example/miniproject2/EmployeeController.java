package com.example.miniproject2;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller for employee.fxml. It only talks to the Employee and
 * EmployeeStore models and never stores the data itself.
 */
public class EmployeeController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField ageField;
    @FXML private TextField salaryField;
    @FXML private TextField annualLeaveField;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> firstNameCol;
    @FXML private TableColumn<Employee, String> lastNameCol;
    @FXML private TableColumn<Employee, Integer> ageCol;
    @FXML private TableColumn<Employee, Double> salaryCol;
    @FXML private TableColumn<Employee, Integer> leaveCol;

    private final EmployeeStore store = EmployeeStore.getInstance();

    @FXML
    private void initialize() {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        leaveCol.setCellValueFactory(new PropertyValueFactory<>("annualLeave"));

        employeeTable.setItems(store.getEmployees());

        // Selecting a row fills the form so the user can edit it, then press Update
        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                firstNameField.setText(selected.getFirstName());
                lastNameField.setText(selected.getLastName());
                ageField.setText(String.valueOf(selected.getAge()));
                salaryField.setText(String.valueOf(selected.getSalary()));
                annualLeaveField.setText(String.valueOf(selected.getAnnualLeave()));
            }
        });
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home View");
        stage.show();
    }

    @FXML
    private void handleInsert(ActionEvent event) {
        Employee input = readForm();
        if (input == null) {
            return;
        }
        store.addEmployee(input);
        clearFields();
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an employee to update.");
            return;
        }
        Employee input = readForm();
        if (input == null) {
            return;
        }
        store.updateEmployee(selected, input.getFirstName(), input.getLastName(),
                input.getAge(), input.getSalary(), input.getAnnualLeave());
        employeeTable.refresh();
        employeeTable.getSelectionModel().clearSelection();
        clearFields();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an employee to delete.");
            return;
        }
        store.deleteEmployee(selected);
        clearFields();
    }

    /** Validates the form. Returns a new Employee with the typed values, or null after showing an alert. */
    private Employee readForm() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String ageText = ageField.getText().trim();
        String salaryText = salaryField.getText().trim();
        String leaveText = annualLeaveField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || ageText.isEmpty()
                || salaryText.isEmpty() || leaveText.isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return null;
        }
        try {
            int age = Integer.parseInt(ageText);
            double salary = Double.parseDouble(salaryText);
            int annualLeave = Integer.parseInt(leaveText);

            if (age < 18 || age > 100) {
                showAlert("Invalid Input", "Age must be between 18 and 100.");
                return null;
            }
            if (salary < 0 || annualLeave < 0) {
                showAlert("Invalid Input", "Salary and Annual Leave cannot be negative.");
                return null;
            }
            return new Employee(firstName, lastName, age, salary, annualLeave);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for Age, Salary, and Annual Leave.");
            return null;
        }
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        ageField.clear();
        salaryField.clear();
        annualLeaveField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
