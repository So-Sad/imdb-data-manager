package main.java.com.imdb_data_manager.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.com.imdb_data_manager.application.IMDBDataManagerApplication;
import main.java.com.imdb_data_manager.service.AccountService;

import java.io.IOException;

public class AuthorizationController {

    @FXML
    private TextField loginField;

    @FXML
    private Button signInBtn;

    @FXML
    private Button skipBtn;

    private String loginInputData = "";
    public static String authLogin = "";

    @FXML
    public void initialize() {
        initLoginField();
    }

    public void initLoginField() {
        loginField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loginInputData = newValue;
            }
        });
    }

    @FXML
    public void setSignInBtn(ActionEvent actionEvent) throws IOException {
        if (AccountService.checkLogin(loginInputData)) {
            authLogin = loginInputData;
            showMainWindow(actionEvent);
        } else {
            showLoginAlert();
        }
    }

    @FXML
    public void setSkipBtn(ActionEvent actionEvent) throws IOException {
        authLogin = "guest";
        showMainWindow(actionEvent);
    }

    private void showLoginAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning alert");
        alert.setHeaderText(null);
        alert.setContentText("Incorrect login");
        alert.showAndWait();
    }

    private void showMainWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(IMDBDataManagerApplication.class.getResource("mainWindow.fxml"));
            Parent fxmlMain = fxmlLoader.load();
            Scene scene = new Scene(fxmlMain, 1200, 760);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("IMDb Data Manager");
            stage.setMinWidth(1200);
            stage.setMinHeight(760);
            stage.show();
            closeOldWindow(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeOldWindow(javafx.event.ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
}
