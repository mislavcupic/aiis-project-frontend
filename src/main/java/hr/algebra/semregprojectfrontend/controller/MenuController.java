/*package hr.algebra.semregprojectfrontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private AnchorPane contentPane; // <--- OVO JE VAŽNO!

    private void loadScene(String fxml, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/semregprojectfrontend/" + fxml));
        // Ako je fxml npr. "seminar/seminar.fxml", ova linija bi trebala raditi

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void openMenuScene(ActionEvent event) throws IOException {
        contentPane.getChildren().clear();
        // Ako želiš prikazati neki početni sadržaj na glavnom izborniku (npr., dobrodošlicu):
        // loadFXMLIntoContent("pocetni_izbornik.fxml");
    }

    private void loadFXMLIntoContent(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Node content = loader.load();
        contentPane.getChildren().setAll(content);
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
    }

    @FXML
    private void openSeminarScene(ActionEvent event) throws IOException {
        loadScene("seminar/seminar.fxml", event); // Dodaj "seminar/"
    }


    @FXML
    private void openStudentScene(ActionEvent event) throws IOException {
        loadScene("student/student.fxml", event);   // Dodaj "student/"
    }

    @FXML
    private void openRegistrationScene(ActionEvent event) throws IOException {
        loadScene("registration/registration.fxml", event); // Dodaj "registration/" (ako postoji poddirektorij)
    }
}
*/
package hr.algebra.semregprojectfrontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuController {
    @FXML
    private AnchorPane contentPane; // <--- OVO JE VAŽNO!

    @FXML
    private void openMenuScene(ActionEvent event) throws IOException {
        contentPane.getChildren().clear();
        // Ako želiš prikazati neki početni sadržaj na glavnom izborniku:
        // loadFXMLIntoContent("pocetni_izbornik.fxml");
    }

    private void loadFXMLIntoContent(String fxmlPath) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/semregprojectfrontend/" + fxmlPath));
        Node content = loader.load();
        contentPane.getChildren().setAll(content);
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
    }

    @FXML
    private void openSeminarScene(ActionEvent event) throws IOException {
        loadFXMLIntoContent("seminar/seminar.fxml"); // Koristi loadFXMLIntoContent
    }


    @FXML
    private void openStudentScene(ActionEvent event) throws IOException {
        loadFXMLIntoContent("student/student.fxml");   // Koristi loadFXMLIntoContent
    }

    @FXML
    private void openRegistrationScene(ActionEvent event) throws IOException {
        loadFXMLIntoContent("registration/registration.fxml"); // Koristi loadFXMLIntoContent
    }
}