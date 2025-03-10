package uk.co.mediumeffortmedia;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.*;
import net.bramp.ffmpeg.FFmpegExecutor;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("The Medium Effort Converter:meConverter");

        FFmpeg ffmpeg = new FFmpeg("/opt/homebrew/Cellar/ffmpeg/7.1_4/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/opt/homebrew/Cellar/ffmpeg/7.1_4/bin/ffprobe");

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput("/Users/paterson/Desktop/input.mov")
                .overrideOutputFiles(true)

                .addOutput("output.mp4")
                .setFormat("mp4")
                .setAudioChannels(2)
                .setAudioCodec("aac")
                .setAudioSampleRate(48_000)
                .setAudioBitRate(320)
                .setVideoCodec("libx264")
                .setVideoFrameRate(25, 1)
                .setVideoResolution(1024,576)
                .done();
        System.out.println("Has this worked yet?!?!?!?!");

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
    }
}
