package zombieinfection.model;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

//This class is a bit out of place, just wanted to get it working and try it out!

public class MusicPlayer extends Application {
	
	//Have to declare the MediaPlayer instance to avoid garbage collection
	private static MediaPlayer mediaPlayer;
	
	public MusicPlayer(String musicFile) { 
		JFXPanel fxPanel = new JFXPanel();
		URL url = getClass().getClassLoader().getResource("music/" + musicFile + ".aiff");
		try {
			Media hit = new Media(url.toURI().toString());
			mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.play();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
	}
}
