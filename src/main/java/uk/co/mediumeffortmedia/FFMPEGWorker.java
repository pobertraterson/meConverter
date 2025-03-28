package uk.co.mediumeffortmedia;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.*;
import net.bramp.ffmpeg.FFmpegExecutor;
import java.io.IOException;

public class FFMPEGWorker {
    public static void worker(String ffmpegPath, String ffprobePath) throws IOException {
        System.out.println("The Medium Effort Converter:meConverter");

        FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
        FFprobe ffprobe = new FFprobe(ffprobePath);

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(FXWorker.fileToConvert.getAbsolutePath().toString())
                .overrideOutputFiles(true)

                .addOutput(output)
                .setFormat(format)
                .setAudioChannels(audioChannels)
                .setAudioCodec(audioCodec)
                .setAudioSampleRate(audioSampleRate)
                .setAudioBitRate(audioBitrate)
                .setVideoCodec(videoCodec)
                .setVideoFrameRate(25, 1)
                .setVideoResolution(resolutionW,resolutionH)


                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        try {
            executor.createJob(builder).run();
        } catch (Exception e) {
            System.out.println("Something went wrong. Please check if your ffmpeg is correct with the ffmpeg and ffprobe executable.");
        }
        System.out.println("Converting using FFMPEG with bramp/ffmpeg-cli-wrapper");
    }
}
