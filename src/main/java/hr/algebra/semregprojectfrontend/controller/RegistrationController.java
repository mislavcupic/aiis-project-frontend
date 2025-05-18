package hr.algebra.semregprojectfrontend.controller;

import hr.algebra.semregprojectfrontend.domain.Registration;
import hr.algebra.semregprojectfrontend.domain.Seminar;
import hr.algebra.semregprojectfrontend.domain.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class RegistrationController {

    @FXML private StackPane contentPane;

    @FXML private TableView<Registration> registrationTable;
    @FXML private TableColumn<Registration, Long> idColumn;
    @FXML private TableColumn<Registration, String> studentNameColumn;
    @FXML private TableColumn<Registration, String> seminarTopicColumn;
    @FXML private TableColumn<Registration, String> registeredAtColumn; // Pretpostavljam da želiš prikazati vrijeme registracije

    @FXML private TextField seminarIdField;
    @FXML private TextField studentEmailField;

    private final String BASE_URL = "http://localhost:8081/api/registrations";
    private final RestTemplate restTemplate = createRestTemplate();

    @FXML
    public void initialize() {
        if (idColumn != null) {
            idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        }
        if (studentNameColumn != null) {
            studentNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStudent().getName()));
        }
        if (seminarTopicColumn != null) {
            seminarTopicColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSeminar().getTopic()));
        }
        if (registeredAtColumn != null) {
            registeredAtColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getRegisteredAt().toString()));
        }
    }

    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    // Navigacija po view-ima
    @FXML public void handleGetAllRegistrations() { loadView("registration-get-all.fxml"); }
    @FXML public void handleGetRegistrationsBySeminar() { loadView("registration-get-by-seminar.fxml"); }
    @FXML public void handleGetRegistrationsByStudentEmail() { loadView("registration-get-by-student-email.fxml"); }
    //@FXML public void handleCreateNewRegistration() { loadView("registration-post.fxml"); } // Ako želiš formu za kreiranje

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/hr/algebra/semregprojectfrontend/registration/" + fxmlPath));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            showAlert("Error loading view: " + e.getMessage());
        }
    }

    // API operacije

    @FXML
    public void loadAllRegistrations() {
        try {
            ResponseEntity<List<Registration>> response = restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Registration>>() {}
            );
            List<Registration> registrations = response.getBody();
            registrationTable.setItems(FXCollections.observableArrayList(registrations));
        } catch (Exception e) {
            showAlert("Error fetching registrations: " + e.getMessage());
        }
    }

    @FXML
    public void loadRegistrationsBySeminar() {
        String seminarIdText = seminarIdField.getText();
        if (seminarIdText.isEmpty()) {
            showAlert("Please enter Seminar ID.");
            return;
        }
        try {
            Long seminarId = Long.parseLong(seminarIdText);
            String url = BASE_URL + "/by-seminar/" + seminarId;
            ResponseEntity<List<Registration>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Registration>>() {}
            );
            List<Registration> registrations = response.getBody();
            registrationTable.setItems(FXCollections.observableArrayList(registrations));
        } catch (NumberFormatException e) {
            showAlert("Invalid Seminar ID format.");
        } catch (Exception e) {
            showAlert("Error fetching registrations by seminar: " + e.getMessage());
        }
    }

    @FXML
    public void loadRegistrationsByStudentEmail() {
        String studentEmail = studentEmailField.getText();
        if (studentEmail.isEmpty()) {
            showAlert("Please enter Student Email.");
            return;
        }
        try {
            String url = BASE_URL + "/by-student/" + studentEmail;
            ResponseEntity<List<Registration>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Registration>>() {}
            );
            List<Registration> registrations = response.getBody();
            registrationTable.setItems(FXCollections.observableArrayList(registrations));
        } catch (Exception e) {
            showAlert("Error fetching registrations by student email: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}