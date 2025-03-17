package uk.co.mediumeffortmedia;
import javafx.scene.Group;
import javafx.stage.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.scene.control.*;

public class FXWorker extends Application {

    @Override
    public void start(Stage stage) {

        Button openFile = new Button();
        openFile.setText("Open File");
        openFile.setTranslateX(50);
        openFile.setTranslateY(200);

        Group root = new Group(openFile);
        Scene scene = new Scene(root,1024,576);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("theming.css")));
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("meConverter");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
