package hr.algebra.semregprojectfrontend.controller;

import hr.algebra.semregprojectfrontend.domain.Student;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import org.springframework.http.MediaType;


import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import java.util.List;

public class StudentController {
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Long> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> emailColumn;

    @FXML private TextField nameField;
    @FXML private TextField emailField;

    private final String BASE_URL = "http://localhost:8081/api/students";
    private final RestTemplate restTemplate = createRestTemplate();

    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.ALL));
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Student, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        loadStudents();
    }

    @FXML
    public void loadStudents() {
        try {
            Student[] students = restTemplate.getForObject(BASE_URL, Student[].class);
            studentTable.setItems(FXCollections.observableArrayList(students));
        } catch (Exception e) {
            showAlert("Error loading students: " + e.getMessage());
        }
    }

    @FXML
    public void handleCreate() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert("All fields must be filled.");
            return;
        }

        Student student = new Student(null, name, email);
        try {
            restTemplate.postForEntity(BASE_URL, student, Student.class);
            loadStudents();
            clearForm();
        } catch (Exception e) {
            showAlert("Create failed: " + e.getMessage());
        }
    }

    @FXML
    public void handleUpdate() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No student selected.");
            return;
        }

        selected.setName(nameField.getText());
        selected.setEmail(emailField.getText());

        try {
            restTemplate.put(BASE_URL + "/" + selected.getId(), selected);
            loadStudents();
        } catch (Exception e) {
            showAlert("Update failed: " + e.getMessage());
        }
    }

    @FXML
    public void handleDelete() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No student selected.");
            return;
        }

        try {
            restTemplate.delete(BASE_URL + "/" + selected.getId());
            loadStudents();
        } catch (Exception e) {
            showAlert("Delete failed: " + e.getMessage());
        }
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }

    public void handleSearch(ActionEvent actionEvent) {
    }
}
