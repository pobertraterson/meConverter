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
        fileToConvertText.setTranslateX(280);
        fileToConvertText.setTranslateY(100);
        fileToConvertText.setEditable(false);


        Button openFile = new Button();
        openFile.setText("Open File");
        openFile.setTranslateX(680);
        openFile.setTranslateY(100);
        Stage finalStage = stage;
        openFile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Media File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Video Files", "*.mov","*.mp4","*.mkv","*.avi","*.wmv","*.webm"),
                    new FileChooser.ExtensionFilter("Audio Files","*.mp3","*.m4a","*.aac","*.aif","*.aiff","*.wav","*.flac","*.wma","*.ogg","*.opus")
            );
            File fileToConvert = fileChooser.showOpenDialog(finalStage);
            if (fileToConvert != null) {
                fileToConvertText.setText(fileToConvert.getAbsolutePath());
            }
        });

        TextField ffmpegPathTextBox = new TextField();
        ffmpegPathTextBox.setPrefWidth(400);
        ffmpegPathTextBox.setTranslateX(280);
        ffmpegPathTextBox.setTranslateY(476);
        ffmpegPathTextBox.setEditable(false);

        Button openFFMPEG = new Button();
        openFFMPEG.setText("Open ffmpeg");
        openFFMPEG.setTranslateX(680);
        openFFMPEG.setTranslateY(476);
        openFFMPEG.setOnAction(actionEvent -> {
            DirectoryChooser ffmpegChooser = new DirectoryChooser();
            ffmpegChooser.setTitle("Open FFMPEG Directory");
            File ffmpegDirectory = ffmpegChooser.showDialog(finalStage);
            if (ffmpegDirectory != null) {
                String ffmpegValidator;
                File ffmpegDirectoryValidator;
                if (System.getProperty("os.name").contains("Windows")) {
                    ffmpegValidator = "ffmpeg.exe";
                } else {
                    ffmpegValidator = "ffmpeg";
                }
                ffmpegDirectoryValidator = new File(ffmpegDirectory, ffmpegValidator);
                if (ffmpegDirectoryValidator.exists()) {
                    ffmpegPathTextBox.setText(ffmpegDirectory.getAbsolutePath());
                } else {
                    System.out.println("ffmpeg executable not detected in directory.");
                }
            }
        });

        Group root = new Group(openFile,fileToConvertText,openFFMPEG,ffmpegPathTextBox);
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
        String operatingSys = System.getProperty("os.name");
        System.out.println("The Medium Effort Converter. Developed by Robert Paterson, Oliver Strange, Zaid Faisal, Kheder Tallaa, Muhammad Ibrahim, & Abdul Ibn-E-Ali");
        System.out.println("Current Version: 1.0.0-SNAPSHOT");
        System.out.println("System.getProperty(\"os.name\") reports:");
        System.out.println(operatingSys);
        launch(args);
    }
}
