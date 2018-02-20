package zombieinfection.model;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/*This class is responsible for the sounds. It is a singleton t keep the music going anyway*/

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
	
	public void startMusic(String filename) { 
		
		URL url = getClass().getClassLoader().getResource("music/" + filename + ".aiff");
		try {
			Media hit = new Media(url.toURI().toString());
			music = new MediaPlayer(hit);
			music.setCycleCount(MediaPlayer.INDEFINITE);
			music.play();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
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
