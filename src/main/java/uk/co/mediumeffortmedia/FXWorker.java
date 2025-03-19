package uk.co.mediumeffortmedia;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.*;

import java.io.File;

public class FXWorker extends Application {

    @Override
    public void start(Stage stage) {

        /// Video Options section
        Label videoOptionsLabel = new Label("Video options");
        videoOptionsLabel.setTranslateX(30);
        videoOptionsLabel.setTranslateY(50);
        videoOptionsLabel.setFont(new Font("Arial",15));

        String[] videoCodecs = {"libx264","libx265","MPEG4","mpeg2video"};
        ComboBox vCodecList = new ComboBox(FXCollections.observableArrayList(videoCodecs));
        vCodecList.setPromptText("Select codec");
        vCodecList.setTranslateX(30);
        vCodecList.setTranslateY(80);

        Label bestFor = new Label();
        bestFor.setTranslateX(30);
        bestFor.setTranslateY(110);

        vCodecList.setOnAction(actionEvent -> {
            if (vCodecList.getValue() == "libx264") {
                bestFor.setText("Best for most applications");
            } else if (vCodecList.getValue() == "libx265") {
                bestFor.setText("Best for space saving");
            } else if (vCodecList.getValue() == "MPEG4") {
                bestFor.setText("Best for older devices & apps");
            } else if (vCodecList.getValue() == "mpeg2video") {
                bestFor.setText("Best for DVDs");
            }
        });

        Slider videoQualitySlider = new Slider(0,30,20);
        videoQualitySlider.setTranslateX(30);
        videoQualitySlider.setTranslateY(130);
        videoQualitySlider.setShowTickMarks(true);
        videoQualitySlider.setShowTickLabels(true);
        videoQualitySlider.setBlockIncrement(1);
        videoQualitySlider.setSnapToTicks(true);
        videoQualitySlider.setMajorTickUnit(1);
        videoQualitySlider.setMinorTickCount(0);

        Label vQualitySliderWarning = new Label("Lower number means better quality");
        vQualitySliderWarning.setTranslateX(30);
        vQualitySliderWarning.setTranslateY(165);

        Label vQuality = new Label("Quality: 20");
        vQuality.setTranslateX(180);
        vQuality.setTranslateY(130);

        videoQualitySlider.valueProperty().addListener((observable, oldValue, newValue) -> vQuality.setText("Quality: " + newValue.intValue()));

        /// Audio options section
        Label audioOptionsLabel = new Label("Audio options");
        audioOptionsLabel.setTranslateX(30);
        audioOptionsLabel.setTranslateY(200);
//        audioOptionsLabel.setFont(new Font("Arial",15));

        String[] audioCodecs = {"mp3","aac","ogg","opus","flac","alac","wav","aiff","24-bit wav","24-bit aiff"};
        ComboBox aCodecList = new ComboBox(FXCollections.observableArrayList(audioCodecs));
        aCodecList.setPromptText("Select codec");
        aCodecList.setTranslateX(30);
        aCodecList.setTranslateY(230);

        Label audioCompressionType = new Label();
        audioCompressionType.setTranslateX(30);
        audioCompressionType.setTranslateY(260);

        aCodecList.setOnAction(actionEvent -> {
            if (aCodecList.getValue() == "mp3" || aCodecList.getValue() == "aac" || aCodecList.getValue() == "ogg" || aCodecList.getValue() == "opus") {
                audioCompressionType.setText("Lossy, low quality compression");
            } else if (aCodecList.getValue() == "flac" || aCodecList.getValue() == "alac") {
                audioCompressionType.setText("Lossless, high quality compression");
            } else if (aCodecList.getValue() == "wav" || aCodecList.getValue() == "aiff" || aCodecList.getValue() == "24-bit wav" || aCodecList.getValue() == "24-bit aiff") {
                audioCompressionType.setText("Uncompressed audio");
            }
        });

//        Slider videoQualitySlider = new Slider(0,30,20);
//        videoQualitySlider.setTranslateX(30);
//        videoQualitySlider.setTranslateY(130);
//        videoQualitySlider.setShowTickMarks(true);
//        videoQualitySlider.setShowTickLabels(true);
//        videoQualitySlider.setBlockIncrement(1);
//        videoQualitySlider.setSnapToTicks(true);
//        videoQualitySlider.setMajorTickUnit(1);
//        videoQualitySlider.setMinorTickCount(0);

//        Label vQualitySliderWarning = new Label("Lower number means better quality");
//        vQualitySliderWarning.setTranslateX(30);
//        vQualitySliderWarning.setTranslateY(165);
//
//        Label vQuality = new Label("Quality: 20");
//        vQuality.setTranslateX(180);
//        vQuality.setTranslateY(130);

//        videoQualitySlider.valueProperty().addListener((observable, oldValue, newValue) -> vQuality.setText("Quality: " + newValue.intValue()));

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

        Group root = new Group(openFile,
                fileToConvertText,
                openFFMPEG,
                ffmpegPathTextBox,
                videoOptionsLabel,
                vCodecList,
                bestFor,
                videoQualitySlider,
                vQualitySliderWarning,
                vQuality,
                aCodecList,
                audioCompressionType,
                audioOptionsLabel);
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
