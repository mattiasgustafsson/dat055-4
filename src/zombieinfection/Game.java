/*
 * This class is the main program. It starts the  Gui
 */
package zombieinfection;

import com.sun.media.jfxmediaimpl.platform.PlatformManager;

import zombieinfection.model.GameEngine;
import zombieinfection.model.MusicPlayer;
import zombieinfection.view.GUI.MainFrame;

/**
 *
 * @author Elena Marzi
 */
public class Game {
    public static void main (String[]arg){
        MainFrame gui = new MainFrame(); 
        GameEngine.getInstance().createNewGame(); //modified
        MusicPlayer.getInstance().startMusic("darkness");
    }
}
