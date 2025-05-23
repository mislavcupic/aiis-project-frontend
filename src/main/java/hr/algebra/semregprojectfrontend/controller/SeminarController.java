/*package hr.algebra.semregprojectfrontend.controller;

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
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    // Navigacija po view-ima
    @FXML public void handleGetAllSeminars() { loadView("seminar-get-all.fxml"); }
    @FXML public void handleGetSeminarByTopic() { loadView("seminar-get-by-topic.fxml"); }
    @FXML public void handleCreateNewSeminar() { loadView("seminar-post.fxml"); }
    @FXML public void handleUpdateSeminar() { loadView("seminar-put.fxml"); }
    @FXML public void handleDeleteSeminar() { loadView("seminar-delete.fxml"); }


    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/hr/algebra/semregprojectfrontend/seminar/" + fxmlPath));
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

            // Konstruiraj URL s ID-jem kao query parametrom
            String updateUrl = BASE_URL + "?id=" + id;

            restTemplate.put(updateUrl, updated);
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

 */
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException; // Dodano za 5xx greške
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Objects; // Dodano za provjeru null-a u FXMLLoader.load

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

    private final RestTemplate restTemplate = createRestTemplate();

    @FXML
    public void initialize() {
        // Inicijalizacija kolona se događa kad je FXML prvi put učitan,
        // ali seminarTable je null ako ova metoda nije vezana uz glavni FXML koji sadrži tablicu.
        // Provjere null-a su već tu, što je dobro.
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
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    // --- Navigacija po view-ima ---
    @FXML
    public void handleGetAllSeminars() {
        // loadSeminars() sada pozivamo unutar loadView ako je view s tablicom.
        loadView("seminar-get-all.fxml");
    }
    @FXML public void handleGetSeminarByTopic() { loadView("seminar-get-by-topic.fxml"); }
    @FXML public void handleCreateNewSeminar() { loadView("seminar-post.fxml"); }
    @FXML public void handleUpdateSeminar() { loadView("seminar-put.fxml"); }
    @FXML public void handleDeleteSeminar() { loadView("seminar-delete.fxml"); }


    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/semregprojectfrontend/seminar/" + fxmlPath));
            Parent view = loader.load();
            contentPane.getChildren().setAll(view);

            // Provjeri je li učitani view onaj koji sadrži seminarTable
            // I ako je, pozovi loadSeminars TEK NAKON što je view učitan
            if ("seminar-get-all.fxml".equals(fxmlPath)) {
                // Važno: Moramo ponovno dobiti kontroler ako se FXML promijenio
                // Ako je SeminarController jedini kontroler za sve FXML-ove, onda je ok.
                // Ako je seminar-get-all.fxml imao svoj vlastiti kontroler, morali bismo ga dohvatiti.
                // U tvom slučaju, izgleda da SeminarController upravlja svim FXML-ovima,
                // pa će @FXML injekcije raditi ispravno nakon učitavanja FXML-a.
                loadSeminars();
            }
        } catch (IOException e) {
            showAlert("Error while trying to get scene view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- API operacije ---

    @FXML
    public void loadSeminars() {
        // Provjera da seminarTable nije null, iako bi sada trebala raditi zbog loadView logike
        if (seminarTable == null) {
            showAlert("Error: Seminar table is not initialized. Ensure FXML is loaded correctly.");
            return;
        }
        try {
            Seminar[] seminars = restTemplate.getForObject(BASE_URL, Seminar[].class);
            if (seminars != null) {
                seminarTable.setItems(FXCollections.observableArrayList(seminars));
            } else {
                seminarTable.setItems(FXCollections.emptyObservableList());
                showAlert("No seminars found.");
            }
        } catch (Exception e) {
            showAlert("Error while trying to get seminars: " + e.getMessage());
            e.printStackTrace();
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
            Seminar seminar = restTemplate.getForObject(BASE_URL + "/" + topic, Seminar.class);

            if (seminar != null) {
                idField.setText(String.valueOf(seminar.getId()));
                topicField.setText(seminar.getTopic());
                lecturerField.setText(seminar.getLecturer());
            } else {
                showAlert("Seminar with entered topic not found.");
                clearForm();
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                showAlert("Seminar with entered topic not found: " + topic);
            } else {
                showAlert("Error while fetching seminar by topic: " + e.getMessage());
            }
            clearForm();
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Error while fetching seminar by topic: " + e.getMessage());
            e.printStackTrace();
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

        Seminar seminarToCreate = new Seminar(null, topic, lecturer);

        try {
            restTemplate.postForLocation(CREATE_URL, seminarToCreate);

            showAlert("Seminar creation process initiated.");
            clearForm();
            loadSeminars(); // Osvježi tablicu
        } catch (HttpClientErrorException e) { // Hvata 4xx greške (npr. 400 Bad Request)
            showAlert("Creating not successful: " + e.getMessage());
            e.printStackTrace();
        } catch (HttpServerErrorException e) { // Dodano za 5xx greške s backendom
            showAlert("Server error during creation: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) { // Općenite greške
            showAlert("Creating not successful: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdate() {
        if (idField.getText().isEmpty()) {
            showAlert("There is no entered ID.");
            return;
        }
        if (topicField.getText().isEmpty() || lecturerField.getText().isEmpty()) {
            showAlert("Topic and Lecturer fields are required for update.");
            return;
        }

        try {
            Long id = Long.parseLong(idField.getText());
            Seminar updated = new Seminar(id, topicField.getText(), lecturerField.getText());

            String updateUrl = BASE_URL + "?id=" + id;

            restTemplate.put(updateUrl, updated);

            showAlert("Seminar update process initiated.");
            clearForm();
            loadSeminars(); // Osvježi tablicu
        } catch (HttpClientErrorException e) { // Hvata 4xx greške
            showAlert("Updating not successful: " + e.getMessage());
            e.printStackTrace();
        } catch (HttpServerErrorException e) { // Dodano za 5xx greške s backendom
            showAlert("Server error during update: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            showAlert("Invalid ID format for update.");
        } catch (Exception e) {
            showAlert("Updating not successful: " + e.getMessage());
            e.printStackTrace();
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
            Long id = Long.parseLong(idText);
            restTemplate.delete(BASE_URL + "/" + id);
            showAlert("Seminar with ID " + id + " deletion process initiated.");
            clearForm();
            loadSeminars();
        } catch (NumberFormatException e) {
            showAlert("Invalid ID format.");
        } catch (HttpClientErrorException e) { // Hvata 4xx greške
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                showAlert("Seminar with ID " + idText + " not found for deletion.");
            } else {
                showAlert("Error while deleting seminar: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (HttpServerErrorException e) { // Dodano za 5xx greške s backendom
            showAlert("Server error during deletion: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Error while deleting seminar: " + e.getMessage());
            e.printStackTrace();
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