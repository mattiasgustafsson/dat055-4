package zombieinfection.model;

import java.io.File;

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
		File music = new File("resources/music/"+musicFile+".mp3");
		Media hit = new Media(music.toURI().toString());
		mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
	}
}
