package uk.co.mediumeffortmedia;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        Stage finalStage = stage;
        openFile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Media File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Video Files", "*.mov","*.mp4","*.mkv","*.avi","*.wmv","*.webm"),
                    new FileChooser.ExtensionFilter("Audio Files","*.mp3","*.m4a","*.aac","*.aif","*.aiff","*.wav","*.flac","*.wma","*.ogg","*.opus")
            );
            fileChooser.showOpenDialog(finalStage);
        });


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
