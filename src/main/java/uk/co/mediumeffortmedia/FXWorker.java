package uk.co.mediumeffortmedia;

import javafx.application.Application;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class FXWorker extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1024, 576);

        stage.setTitle("meConverter");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
