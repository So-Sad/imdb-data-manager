package main.java.com.imdb_data_manager.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IMDBDataManagerApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(IMDBDataManagerApplication.class.getResource("mainWindow.fxml"));
        fxmlLoader.setLocation(getClass().getResource("../fxml/authorizationWindow.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        Scene scene = new Scene(fxmlMain, 620, 620);
        primaryStage.setScene(scene);
        primaryStage.setTitle("IMDb Data Manager");
        primaryStage.setMinWidth(620);
        primaryStage.setMinHeight(620);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
