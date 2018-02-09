/*
 * This class is the main program. It starts the  Gui
 */
package zombieinfection;

import zombieinfection.model.GameEngine;
import zombieinfection.view.GUI.MainFrame;

/**
 *
 * @author Elena Marzi
 */
public class Game {
    public static void main (String[]arg){
    	MainFrame gui = new MainFrame();
    	GameEngine.getInstance().createNewGame();
    }
}
