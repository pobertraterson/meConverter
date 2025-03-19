package uk.co.mediumeffortmedia;

public class FXLoader {
    public static void main(String[] args) {
        if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "meConverter");
        }
        FXWorker.main(args);
    }
}
