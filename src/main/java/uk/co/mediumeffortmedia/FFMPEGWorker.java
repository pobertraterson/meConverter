package uk.co.mediumeffortmedia;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.*;
import net.bramp.ffmpeg.FFmpegExecutor;
import java.io.IOException;

public class FFMPEGWorker {
    public static void worker(String ffmpegPath, String ffprobePath, String inputPath, String output, String format, int audioChannels, String audioCodec, int audioSampleRate, String videoCodec, int frameRate, int audioBitrate, int resolutionH, int resolutionW) throws IOException {
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
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
        System.out.println("Converting using FFMPEG with bramp/ffmpeg-cli-wrapper");
    }
}
