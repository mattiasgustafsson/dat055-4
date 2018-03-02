package zombieinfection;

import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/*This class is responsible for the sounds. It is a singleton t keep the music going anyway*/
/**
 * This class handles the music playback.
 *
 * @author David.S
 * @version 2018-02-23
 */
public class MusicPlayer extends Application {

    private static MusicPlayer instance;

    private MediaPlayer music;
    private MediaPlayer effect;

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    private MusicPlayer() {
        JFXPanel fxPanel = new JFXPanel();
    }

    /**
     * Plays music from file indefinitely.
     *
     * @param filename takes the name of a music file from the resource library.
     */
    public void startMusic(String filename) {

        URL url = getClass().getClassLoader().getResource("music/" + filename + ".aiff");
        try {
            Media hit = new Media(url.toURI().toString());
            music = new MediaPlayer(hit);
            music.setStartTime(new Duration(0));
            int seconds = 3 * 60 + 50;
            music.setStopTime(new Duration(seconds * 1000));
            music.setCycleCount(MediaPlayer.INDEFINITE);
            music.setRate(0.90);
            music.play();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays effect music file.
     *
     * @param filename takes a relative file path to an effect music file.
     */
    public void playEffect(String filename) {
        if (effect != null) {
            effect.stop();
            effect.dispose();
        }
        URL url = getClass().getClassLoader().getResource("music/" + filename + ".aiff");
        try {
            Media hit = new Media(url.toURI().toString());
            effect = new MediaPlayer(hit);
            effect.play();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage arg0) throws Exception {
    }
}
