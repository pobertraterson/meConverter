package uk.co.mediumeffortmedia;
import net.bramp.ffmpeg.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Testing!");
        FFmpeg ffmpeg = new FFmpeg("/opt/homebrew/Cellar/ffmpeg/7.1_4/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/opt/homebrew/Cellar/ffmpeg/7.1_4/bin/ffprobe");
    }
}
