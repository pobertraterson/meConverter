package uk.co.mediumeffortmedia;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;


public class FXWorker extends Application {

    /// Variables for use across start function
    String ffmpegValidator;
    File ffmpegDirectoryValidator;
    File ffmpegDirectory;
    File fileToConvert;
    ComboBox sampleRates;
    ComboBox vCodecList;
    ComboBox aCodecList;
    ToggleGroup bitSampleGroup;
    Slider videoQualitySlider;
    int sliderToInt;
    TextField lossyCompressionBitrate;

    @Override
    public void start(Stage stage) {

        MenuBar menuBar = new MenuBar();
        if (System.getProperty("os.name") != null && System.getProperty("os.name").startsWith("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
            System.setProperty("apple.awt.application.name", "My App");
        }
        Menu options = new Menu("Options");
        MenuItem ffmpegDownload = new MenuItem("Download FFMPEG");
        ffmpegDownload.setOnAction(e -> getHostServices().showDocument("https://www.ffmpeg.org/download.html"));
        options.getItems().addAll(ffmpegDownload);
        menuBar.getMenus().addAll(options);
        menuBar.setPrefWidth(1024);
        menuBar.setTranslateX(0);
        menuBar.setTranslateY(0);

        /// Video Options section
        Label videoOptionsLabel = new Label("Video options");
        videoOptionsLabel.setTranslateX(30);
        videoOptionsLabel.setTranslateY(50);
        videoOptionsLabel.setFont(new Font("Arial",15));

        String[] videoCodecs = {"libx264","libx265","MPEG4","mpeg2video"};
        vCodecList = new ComboBox(FXCollections.observableArrayList(videoCodecs));
        vCodecList.setPromptText("Select codec");
        vCodecList.setTranslateX(30);
        vCodecList.setTranslateY(80);
        vCodecList.setDisable(true);

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

        videoQualitySlider = new Slider(0,30,20);
        videoQualitySlider.setTranslateX(30);
        videoQualitySlider.setTranslateY(130);
        videoQualitySlider.setShowTickMarks(true);
        videoQualitySlider.setShowTickLabels(true);
        videoQualitySlider.setBlockIncrement(1);
        videoQualitySlider.setSnapToTicks(true);
        videoQualitySlider.setMajorTickUnit(1);
        videoQualitySlider.setMinorTickCount(0);
        videoQualitySlider.setDisable(true);

        Label vQualitySliderWarning = new Label("Lower number means better quality");
        vQualitySliderWarning.setTranslateX(30);
        vQualitySliderWarning.setTranslateY(165);

        Label vQuality = new Label("Quality: 20");
        vQuality.setTranslateX(180);
        vQuality.setTranslateY(130);

        videoQualitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {

        vQuality.setText("Quality: " + newValue.intValue());
        sliderToInt = (int) Math.round(newValue.doubleValue());
        });
        /// Audio options section
        Label audioOptionsLabel = new Label("Audio options");
        audioOptionsLabel.setTranslateX(30);
        audioOptionsLabel.setTranslateY(200);
//        audioOptionsLabel.setFont(new Font("Arial",15));

        String[] audioCodecs = {"mp3","aac","ogg","opus","flac","alac","wav","aiff","24-bit wav","24-bit aiff"};
        aCodecList = new ComboBox(FXCollections.observableArrayList(audioCodecs));
        aCodecList.setPromptText("Select codec");
        aCodecList.setTranslateX(30);
        aCodecList.setTranslateY(230);
        aCodecList.setDisable(true);


        String[] audioCompressionTypeNotes = {"Lossy compression, lower quality","Lossless compression, higher quality","Uncompressed, high quality"};
        Label audioCompressionType = new Label();
        audioCompressionType.setTranslateX(30);
        audioCompressionType.setTranslateY(260);


        Label audioQualityLabel = new Label("Audio quality");
        audioQualityLabel.setTranslateX(30);
        audioQualityLabel.setTranslateY(280);

        /// Audio Options for lossy compression types
        lossyCompressionBitrate = new TextField();
        lossyCompressionBitrate.setTextFormatter(new TextFormatter<String>(
                change -> {
                    // Only allow digits (0-9)
                    if (change.getText().matches("[0-9]*")) {
                        return change;
                    }
                    return null; // Reject the change if it's not a digit
                })
        );

        lossyCompressionBitrate.setTranslateX(30);
        lossyCompressionBitrate.setTranslateY(300);
        lossyCompressionBitrate.setPrefWidth(80);
        lossyCompressionBitrate.setVisible(false);

        Label audioBitrateLabel = new Label("kB/s");
        audioBitrateLabel.setTranslateX(120);
        audioBitrateLabel.setTranslateY(300);
        audioBitrateLabel.setVisible(false);

        /// Audio Options for lossless compression & uncompressed audio types
        RadioButton radioButton16Bit = new RadioButton("16-bit");
        RadioButton radioButton24Bit = new RadioButton("24-bit");
        radioButton16Bit.setTranslateX(30);
        radioButton16Bit.setTranslateY(300);
        radioButton24Bit.setTranslateX(100);
        radioButton24Bit.setTranslateY(300);
        radioButton16Bit.setVisible(false);
        radioButton24Bit.setVisible(false);


        bitSampleGroup = new ToggleGroup();
        radioButton16Bit.setToggleGroup(bitSampleGroup);
        radioButton24Bit.setToggleGroup(bitSampleGroup);

        String[] sampleRatesArray = {"44.1kHz","48kHz","88.2kHz","96kHz"};
        sampleRates = new ComboBox(FXCollections.observableArrayList(sampleRatesArray));
        sampleRates.setPromptText("Select sample rate");
        sampleRates.setTranslateX(30);
        sampleRates.setTranslateY(340);
        sampleRates.setVisible(false);



//        bitSampleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
//            if (newToggle != null) {
//                RadioButton selectedRadioButton = (RadioButton) newToggle;
//                System.out.println("Selected: " + selectedRadioButton.getText());
//            }
//        });


        aCodecList.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            // switch statement for efficiency (I felt bad for using if/else over and over)
            if (newValue != null) {
                switch (newValue) {
                    case "aac":
                    case "mp3":
                    case "ogg":
                    case "opus":
                        audioBitrateLabel.setVisible(true);
                        lossyCompressionBitrate.setVisible(true);
                        radioButton16Bit.setVisible(false);
                        radioButton24Bit.setVisible(false);
                        sampleRates.setVisible(false);
                        audioCompressionType.setText(audioCompressionTypeNotes[0]);
                        System.out.println(bitSampleGroup.getSelectedToggle());
                        break;
                    case "flac":
                    case "alac":
                        audioBitrateLabel.setVisible(false);
                        lossyCompressionBitrate.setVisible(false);
                        radioButton16Bit.setVisible(true);
                        radioButton24Bit.setVisible(true);
                        radioButton16Bit.setDisable(false);
                        radioButton24Bit.setDisable(false);
                        sampleRates.setVisible(true);
                        System.out.println(bitSampleGroup.getSelectedToggle());
                        audioCompressionType.setText(audioCompressionTypeNotes[1]);
                        break;
                    case "wav":
                    case "aiff":
                    case "24-bit wav":
                    case "24-bit aiff":
                        audioBitrateLabel.setVisible(false);
                        lossyCompressionBitrate.setVisible(false);
                        radioButton16Bit.setVisible(true);
                        radioButton24Bit.setVisible(true);
                        radioButton16Bit.setDisable(true);
                        radioButton24Bit.setDisable(true);
                        sampleRates.setVisible(true);
                        audioCompressionType.setText(audioCompressionTypeNotes[2]);
                        System.out.println(bitSampleGroup.getSelectedToggle());
                        break;
                }
            }
        });


        ///  Main Converter Bits

        ///  File Path stuff
        TextField fileToConvertText = new TextField();
        fileToConvertText.setPrefWidth(400);
        fileToConvertText.setTranslateX(280);
        fileToConvertText.setTranslateY(100);
        fileToConvertText.setEditable(false);

        Button convert = new Button("Convert file");
        convert.setTranslateX(640);
        convert.setTranslateY(300);
        convert.setOnAction(actionEvent -> {
//            try {
//                fxToWorker();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        });
        convert.setDisable(true);


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
            fileToConvert = fileChooser.showOpenDialog(finalStage);
            if (fileToConvert != null) {
                fileToConvertText.setText(fileToConvert.getAbsolutePath());
                if (ffmpegDirectoryValidator.exists()) {
                    vCodecList.setDisable(false);
                    aCodecList.setDisable(false);
                    videoQualitySlider.setDisable(false);
                    convert.setDisable(false);
                }
            }
        });

        TextField ffmpegPathTextBox = new TextField();
        ffmpegPathTextBox.setPrefWidth(400);
        ffmpegPathTextBox.setTranslateX(280);
        ffmpegPathTextBox.setTranslateY(476);
        ffmpegPathTextBox.setEditable(false);

        Label ffmpegNotDetected = new Label("FFMPEG executable not detected in selected directory. Please try again");
        ffmpegNotDetected.setTranslateX(280);
        ffmpegNotDetected.setTranslateY(506);
        ffmpegNotDetected.setVisible(false);

        Button openFFMPEG = new Button();
        openFFMPEG.setText("Open ffmpeg");
        openFFMPEG.setTranslateX(680);
        openFFMPEG.setTranslateY(476);
        openFFMPEG.setOnAction(actionEvent -> {
            DirectoryChooser ffmpegChooser = new DirectoryChooser();
            ffmpegChooser.setTitle("Open FFMPEG Directory");
            ffmpegDirectory = ffmpegChooser.showDialog(finalStage);
            if (ffmpegDirectory != null) {
                if (System.getProperty("os.name").contains("Windows")) {
                    ffmpegValidator = "ffmpeg.exe";
                } else {
                    ffmpegValidator = "ffmpeg";
                }
                ffmpegDirectoryValidator = new File(ffmpegDirectory, ffmpegValidator);
                if (ffmpegDirectoryValidator.exists()) {
                    ffmpegPathTextBox.setText(ffmpegDirectory.getAbsolutePath());
                    ffmpegNotDetected.setVisible(false);
                    if (fileToConvert != null) {
                        vCodecList.setDisable(false);
                        aCodecList.setDisable(false);
                        videoQualitySlider.setDisable(false);
                        convert.setDisable(false);
                    }
                } else {
                    System.out.println("ffmpeg executable not detected in directory.");
                    ffmpegNotDetected.setVisible(true);
                }
            }
        });

        ///  actually shows the components needed for this to work.
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
                audioOptionsLabel,
                audioQualityLabel,
                lossyCompressionBitrate,
                audioBitrateLabel,
                radioButton16Bit,
                radioButton24Bit,
                sampleRates,
                ffmpegNotDetected,
                menuBar,
                convert);
        Scene scene = new Scene(root,1024,576);
        scene.getStylesheets().add(getClass().getResource("theming.css").toExternalForm());
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
