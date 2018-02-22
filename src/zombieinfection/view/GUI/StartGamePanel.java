package zombieinfection.view.GUI;

import java.awt.*;
import java.io.InputStream;
import java.util.Scanner;
import javax.swing.*; 
import javax.swing.border.LineBorder;

/**
 * Shows the start game panel with information about the game and instructions
 * on how to play.
 * 
 * @author Elena Marzi
 * @version 2018-02-21
 */
public class StartGamePanel extends JPanel{
     public StartGamePanel() {
        String help = readIntroductionFile();
        JTextArea area = new JTextArea(help);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Verdana", 1, 16));
        area.setBorder(new LineBorder(Color.BLACK)); // make it easy to see
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll);
        scroll.setPreferredSize(new Dimension(600, 400));
    }

     /**
      * Opens a stream from the JAR and reads the entire introduction.txt file
      * 
      * @return the contents of the file as a string
      */
    private String readIntroductionFile() {
        InputStream file = getClass().getClassLoader().getResourceAsStream("txt/introduction.txt");
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\Z");
        String help = scanner.next();
        return help;
    }
}
