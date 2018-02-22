package zombieinfection;

import java.awt.Font;
import javax.swing.UIManager;
import zombieinfection.model.*;
import zombieinfection.view.GUI.*;

/**
 * This class is the main program. It starts the GUI, the Game Engine, and the
 * background music.
 *
 * @author Elena Marzi
 * @version 2018-02-22
 */
public class Game {
    public static void main(String[] arg) {
        Font menuFont = new Font("Dialog", 0, 18);
        UIManager.put("Menu.font", menuFont);
        MainFrame gui = new MainFrame();
        GameEngine.getInstance().setGui(gui);
        GameEngine.getInstance().createNewGame(true); // modified
        MusicPlayer.getInstance().startMusic("darkness");
    }
}
