/*
 * This class is the main program. It starts the  Gui
 */
package zombieinfection;

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
        //Remove comment below to play music!
        //new MusicPlayer("darkness");
    }
}
