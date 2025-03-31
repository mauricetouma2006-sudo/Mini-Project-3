package com.example.miniproject2;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

public class EmployeeController extends Application {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField annualLeaveField;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> firstNameCol;
    @FXML
    private TableColumn<Employee, String> lastNameCol;
    @FXML
    private TableColumn<Employee, Integer> ageCol;
    @FXML
    private TableColumn<Employee, Double> salaryCol;
    @FXML
    private TableColumn<Employee, Integer> leaveCol;

    private ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employee.fxml"));
        loader.setController(this);
        VBox root = loader.load();

        primaryStage.setTitle("Employee Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    private void initialize() {
        // Setup Table Columns
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        leaveCol.setCellValueFactory(new PropertyValueFactory<>("annualLeave"));

        employeeTable.setItems(employeeList);
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
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        // Prevent NumberFormatException by adding input validation
        if (firstName.isEmpty() || lastName.isEmpty() ||
                ageField.getText().isEmpty() || salaryField.getText().isEmpty()
                || annualLeaveField.getText().isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return;
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            double salary = Double.parseDouble(salaryField.getText());
            int annualLeave = Integer.parseInt(annualLeaveField.getText());

            Employee newEmployee = new Employee(firstName, lastName, age, salary, annualLeave);
            employeeList.add(newEmployee);
            employeeTable.refresh(); // Ensure the table updates
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for Age, Salary, and Annual Leave.");
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                selectedEmployee.setFirstName(firstNameField.getText());
                selectedEmployee.setLastName(lastNameField.getText());
                selectedEmployee.setAge(Integer.parseInt(ageField.getText()));
                selectedEmployee.setSalary(Double.parseDouble(salaryField.getText()));
                selectedEmployee.setAnnualLeave(Integer.parseInt(annualLeaveField.getText()));

                employeeTable.refresh();
                clearFields();
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numeric values.");
            }
        } else {
            showAlert("No Selection", "Please select an employee to update.");
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeList.remove(selectedEmployee);
        } else {
            showAlert("No Selection", "Please select an employee to delete.");
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

    public static void main(String[] args) {
        launch(args);
    }
}
