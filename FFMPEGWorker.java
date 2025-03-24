package uk.co.mediumeffortmedia;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.*;
import net.bramp.ffmpeg.FFmpegExecutor;
import java.io.IOException;

public class FFMPEGWorker {
    public static void worker(String ffmpegPath, String ffprobePath, String inputPath, String output, String format, int audioChannels, String audioCodec, int audioSampleRate, String videoCodec, int frameRate, int audioBitrate, int resolutionH, int resolutionW, String extraArgs, int vQuality) throws IOException {
        System.out.println("The Medium Effort Converter:meConverter");

        FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
        FFprobe ffprobe = new FFprobe(ffprobePath);
        FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(inputPath)
                    .overrideOutputFiles(true)

                    .addOutput(output)
                    .setFormat(format)

                    .setAudioChannels(audioChannels)
                    .setAudioCodec(audioCodec)
                    .setAudioSampleRate(audioSampleRate)
                    .setAudioBitRate(audioBitrate)
                    .setVideoCodec(videoCodec)
                    .setVideoFrameRate(frameRate, 1)
                    .setVideoResolution(resolutionW,resolutionH)
                    .setVideoQuality(vQuality)

                    .done();


        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        try {
            executor.createJob(builder).run();
        } catch (Exception e) {
            System.out.println("Something went wrong. Please check if your ffmpeg directory is correct so it contains the ffmpeg and ffprobe executable.");
        }
        System.out.println("Converting using FFMPEG with bramp/ffmpeg-cli-wrapper");
    }
}
