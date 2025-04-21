package hr.algebra.semregprojectfrontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    private void loadScene(String fxml, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/semregprojectfrontend/" + fxml));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void openSeminarScene(ActionEvent event) throws IOException {
        loadScene("seminar.fxml", event);
    }

    @FXML
    private void openStudentScene(ActionEvent event) throws IOException {
        loadScene("student.fxml", event);
    }

    @FXML
    private void openRegistrationScene(ActionEvent event) throws IOException {
        loadScene("registration.fxml", event);
    }
}
