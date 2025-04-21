package hr.algebra.semregprojectfrontend.controller;

import hr.algebra.semregprojectfrontend.domain.Seminar;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class SeminarController {

    @FXML private StackPane contentPane;

    @FXML private TableView<Seminar> seminarTable;
    @FXML private TableColumn<Seminar, Long> idColumn;
    @FXML private TableColumn<Seminar, String> topicColumn;
    @FXML private TableColumn<Seminar, String> lecturerColumn;

    @FXML private TextField topicField;
    @FXML private TextField lecturerField;
    @FXML private TextField idField;
    @FXML private TextField topicSearchField;

    private final String BASE_URL = "http://localhost:8081/api/seminars";
    private final String CREATE_URL = BASE_URL + "/create";
    //private final String BY_TOPIC_URL = BASE_URL + "/by-topic";

    private final RestTemplate restTemplate = createRestTemplate();
    @FXML
    public void initialize() {
        if (idColumn != null) {
            idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        }
        if (topicColumn != null) {
            topicColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTopic()));
        }
        if (lecturerColumn != null) {
            lecturerColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLecturer()));
        }
    }
    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.ALL));
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    // Navigacija po view-ima
    @FXML public void handleGetAll() { loadView("seminar-get-all.fxml"); }
    @FXML public void handleGetByTopic() { loadView("seminar-get-by-topic.fxml"); }
    @FXML public void handlePost() { loadView("seminar-post.fxml"); }
    @FXML public void handlePut() { loadView("seminar-put.fxml"); }
    @FXML public void handleDelete() { loadView("seminar-delete.fxml"); }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/hr/algebra/semregprojectfrontend/" + fxmlPath));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            showAlert("Error while trying to get scene view: " + e.getMessage());
        }
    }

    // API operacije

    @FXML
    public void loadSeminars() {
        try {
            Seminar[] seminars = restTemplate.getForObject(BASE_URL, Seminar[].class);
            seminarTable.setItems(FXCollections.observableArrayList(seminars));
        } catch (Exception e) {
            showAlert("Error while trying to get seminars: " + e.getMessage());
        }
    }

    @FXML
    public void handleFetchByTopic() {
        String topic = topicSearchField.getText();
        if (topic.isEmpty()) {
            showAlert("Enter topic");
            return;
        }

        try {
            Seminar seminar = restTemplate.getForObject(BASE_URL  + "/"+ topic, Seminar.class);

                if (seminar != null) {
                    idField.setText(String.valueOf(seminar.getId()));
                    topicField.setText(seminar.getTopic());
                    lecturerField.setText(seminar.getLecturer());
                } else {
                    showAlert("Seminar with entered topic not found.");
                }


        } catch (Exception e) {
            showAlert("Error while updating seminar: " + e.getMessage());
        }
    }

    @FXML
    public void handleCreate() {
        String topic = topicField.getText();
        String lecturer = lecturerField.getText();

        if (topic.isEmpty() || lecturer.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        Seminar seminar = new Seminar(null, topic, lecturer);
        try {
            restTemplate.postForEntity(CREATE_URL, seminar, Seminar.class);
            showAlert("Seminar successfully created.");
            clearForm();
        } catch (Exception e) {
            showAlert("Creating not succeded: " + e.getMessage());
        }
    }

    @FXML
    public void handleUpdate() {
        if (idField.getText().isEmpty()) {
            showAlert("There is no entered id.");
            return;
        }

        try {
            Long id = Long.parseLong(idField.getText());
            Seminar updated = new Seminar(id, topicField.getText(), lecturerField.getText());
            restTemplate.put(BASE_URL + "/" + id, updated);
            showAlert("Seminar updated.");
        } catch (Exception e) {
            showAlert("Updating not successful: " + e.getMessage());
        }
    }

    @FXML
    public void handleDeleteById() {
        if (idField == null || idField.getText().isEmpty()) {
            showAlert("Please enter a valid seminar ID to delete.");
            return;
        }

        String idText = idField.getText();
        try {
            // Implementacija za brisanje na temelju ID-a
            Long id = Long.parseLong(idText);
            restTemplate.delete(BASE_URL + "/" + id);
            showAlert("Seminar with ID " + id + " deleted successfully.");
        } catch (NumberFormatException e) {
            showAlert("Invalid ID format.");
        } catch (Exception e) {
            showAlert("Error while deleting seminar: " + e.getMessage());
        }
    }

    private void clearForm() {
        if (topicField != null) topicField.clear();
        if (lecturerField != null) lecturerField.clear();
        if (idField != null) idField.clear();
        if (topicSearchField != null) topicSearchField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}
