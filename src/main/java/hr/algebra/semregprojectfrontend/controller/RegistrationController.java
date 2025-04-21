package hr.algebra.semregprojectfrontend.controller;

import hr.algebra.semregprojectfrontend.domain.Registration;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import org.springframework.http.MediaType;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import java.util.List;
public class RegistrationController {
    @FXML private TableView<Registration> registrationTable;
    @FXML private TableColumn<Registration, Long> idColumn;
    @FXML private TableColumn<Registration, String> studentNameColumn;
    @FXML private TableColumn<Registration, String> seminarTopicColumn;

    @FXML private TextField studentNameField;
    @FXML private TextField seminarTopicField;

    private final String BASE_URL = "http://localhost:8081/api/registrations";
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
        idColumn.setCellValueFactory(new PropertyValueFactory<Registration, Long>("id"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<Registration, String>("studentName"));
        seminarTopicColumn.setCellValueFactory(new PropertyValueFactory<Registration, String>("seminarTopic"));
        loadRegistrations();
    }

    @FXML
    public void loadRegistrations() {
        try {
            Registration[] registrations = restTemplate.getForObject(BASE_URL, Registration[].class);
            registrationTable.setItems(FXCollections.observableArrayList(registrations));
        } catch (Exception e) {
            showAlert("Error loading registrations: " + e.getMessage());
        }
    }

    @FXML
    public void handleCreate() {
        String studentName = studentNameField.getText();
        String seminarTopic = seminarTopicField.getText();

        if (studentName.isEmpty() || seminarTopic.isEmpty()) {
            showAlert("All fields must be filled.");
            return;
        }

        Registration registration = new Registration(null, studentName, seminarTopic);
        try {
            restTemplate.postForEntity(BASE_URL, registration, Registration.class);
            loadRegistrations();
            clearForm();
        } catch (Exception e) {
            showAlert("Create failed: " + e.getMessage());
        }
    }

    @FXML
    public void handleUpdate() {
        Registration selected = registrationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No registration selected.");
            return;
        }

        selected.setStudentName(studentNameField.getText());
        selected.setSeminarTopic(seminarTopicField.getText());

        try {
            restTemplate.put(BASE_URL + "/" + selected.getId(), selected);
            loadRegistrations();
        } catch (Exception e) {
            showAlert("Update failed: " + e.getMessage());
        }
    }

    @FXML
    public void handleDelete() {
        Registration selected = registrationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No registration selected.");
            return;
        }

        try {
            restTemplate.delete(BASE_URL + "/" + selected.getId());
            loadRegistrations();
        } catch (Exception e) {
            showAlert("Delete failed: " + e.getMessage());
        }
    }

    private void clearForm() {
        studentNameField.clear();
        seminarTopicField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }
}
