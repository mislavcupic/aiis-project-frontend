package hr.algebra.semregprojectfrontend.controller;

import hr.algebra.semregprojectfrontend.domain.Seminar;
import hr.algebra.semregprojectfrontend.domain.Student; // Pretpostavljam da ti je Student domenski objekt u backend projektu
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class StudentController {

    @FXML private StackPane contentPane;

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Long> studentIdColumn;
    @FXML private TableColumn<Student, String> studentNameColumn;
    @FXML private TableColumn<Student, String> studentEmailColumn;

    @FXML private TextField studentNameField;
    @FXML private TextField studentEmailField;
    @FXML private TextField studentIdField;
    @FXML private TextField emailSearchField;

    private final String BASE_URL = "http://localhost:8081/api/students";
    private final String CREATE_URL = BASE_URL + "/create";

    private final RestTemplate restTemplate = createRestTemplate();

    @FXML
    public void initialize() {
        if (studentIdColumn != null) {
            studentIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        }
        if (studentNameColumn != null) {
            studentNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        }
        if (studentEmailColumn != null) {
            studentEmailColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEmail()));
        }
    }

    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON)); // Preporučujem application/json
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    // Navigacija po view-ima
    @FXML public void handleGetAllStudents() { loadView("student-get-all.fxml"); }
    @FXML public void handleGetStudentByEmail() { loadView("student-get-by-email.fxml"); } // Ako želiš poseban view
    @FXML public void handleCreateNewStudent() { loadView("student-create-new.fxml"); }
    @FXML public void handleUpdateStudent() { loadView("student-update-by-id.fxml"); }
    @FXML public void handleDeleteStudent() { loadView("student-delete-by-id.fxml"); }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/hr/algebra/semregprojectfrontend/student/" + fxmlPath)));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            showAlert("Error loading view: " + e.getMessage());
        }
    }

    // API operacije

    @FXML
    public void loadStudents() {
        try {
            ResponseEntity<List<Student>> response = restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Student>>() {}
            );
            List<Student> students = response.getBody();
            studentTable.setItems(FXCollections.observableArrayList(students));
        } catch (Exception e) {
            showAlert("Error fetching students: " + e.getMessage());
        }
    }

    @FXML
    public void handleCreate() {
        String name = studentNameField.getText();
        String email = studentEmailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        Student student = new Student(null, name, email);
        try {
            ResponseEntity<Student> response = restTemplate.postForEntity(CREATE_URL, student, Student.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                showAlert("Student created successfully. ID: " + response.getBody().getId());
                clearForm();
            } else {
                showAlert("Failed to create student.");
            }
        } catch (Exception e) {
            showAlert("Error creating student: " + e.getMessage());
        }
    }

    @FXML
    public void handleUpdate() {
        if (studentIdField.getText().isEmpty()) {
            showAlert("Please enter the student email to update.");
            return;
        }

        try {
            Long id = Long.parseLong(studentIdField.getText());
            String name = studentNameField.getText();
            String email = studentEmailField.getText();
            Student updatedStudent = new Student(id, name, email);
            restTemplate.put(BASE_URL + "/" + id, updatedStudent);
            showAlert("Student updated successfully.");
            clearForm();
        } catch (NumberFormatException e) {
            showAlert("Invalid ID format.");
        } catch (Exception e) {
            showAlert("Error updating student: " + e.getMessage());
        }
    }

    @FXML
    public void handleDeleteById() {
        if (studentIdField.getText().isEmpty()) {
            showAlert("Please enter the ID of the student to delete.");
            return;
        }

        try {
            Long id = Long.parseLong(studentIdField.getText());
            restTemplate.delete(BASE_URL + "/" + id);
            showAlert("Student with ID " + id + " deleted successfully.");
            clearForm();
        } catch (NumberFormatException e) {
            showAlert("Invalid ID format.");
        } catch (Exception e) {
            showAlert("Error deleting student: " + e.getMessage());
        }
    }

    @FXML
    public void handleFetchByEmail() {
        String email = emailSearchField.getText();
        if (email.isEmpty()) {
            showAlert("Enter student's email");
            return;
        }

        try {
            Student student = restTemplate.getForObject(BASE_URL  + "?email="+ email, Student.class);

            if (student != null) {
                studentIdField.setText(String.valueOf(student.getId()));
                studentEmailField.setText(student.getEmail());
                studentNameField.setText(student.getName());
            } else {
                showAlert("Student with entered email not found.");
            }


        } catch (Exception e) {
            showAlert("Error while finding student by email: " + e.getMessage());
        }
    }
    private void clearForm() {
        if (studentNameField != null) studentNameField.clear();
        if (studentEmailField != null) studentEmailField.clear();
        if (studentIdField != null) studentIdField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}