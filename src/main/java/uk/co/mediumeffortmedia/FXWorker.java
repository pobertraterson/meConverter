package uk.co.mediumeffortmedia;
import javafx.scene.Group;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.*;

import java.io.File;

public class FXWorker extends Application {

    @Override
    public void start(Stage stage) {

        TextField fileToConvertText = new TextField();
        fileToConvertText.setPrefWidth(400);
        fileToConvertText.setTranslateX(130);
        fileToConvertText.setTranslateY(200);
        fileToConvertText.setEditable(false);


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
            File fileToConvert = fileChooser.showOpenDialog(finalStage);
            fileToConvertText.setText(fileToConvert.getAbsolutePath());
        });

        TextField ffmpegPathTextBox = new TextField();
        TextField ffprobePathTextBox = new TextField();


        Group root = new Group(openFile,fileToConvertText);
        Scene scene = new Scene(root,1024,576);
        scene.getStylesheets().add("/uk/co/mediumeffortmedia/theming.css");
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
