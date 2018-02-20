/*
 * This class is the main program. It starts the  Gui
 */
package zombieinfection;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.sun.media.jfxmediaimpl.platform.PlatformManager;

import zombieinfection.model.GameEngine;
import zombieinfection.model.MusicPlayer;
import zombieinfection.view.GUI.MainFrame;
import zombieinfection.view.GUI.StartGamePanel;

/**
 *
 * @author Elena Marzi
 */
public class Game {
	
    public static void main (String[]arg){
    	Font menuFont = new Font("Dialog",0,18);
    	UIManager.put("Menu.font", menuFont); 
        MainFrame gui = new MainFrame(); 
        GameEngine.getInstance().setGui(gui);
        GameEngine.getInstance().createNewGame(true); //modified
        MusicPlayer.getInstance().startMusic("darkness");
    }
}
