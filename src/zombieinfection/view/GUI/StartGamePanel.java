/*
 * 
 */
package zombieinfection.view.GUI;

import java.awt.*;
import java.io.InputStream;
import java.util.Scanner;
import javax.swing.*; 
import javax.swing.border.LineBorder;

/**
 *
 * @author elena
 */
public class StartGamePanel extends JPanel{

    
     public StartGamePanel() {
        
        // open a stream to read the file
        InputStream file = getClass().getClassLoader().getResourceAsStream("txt/introduction.txt");
        // read the file using a scanner
        Scanner scanner = new Scanner(file);
        // read until the end of the file
        scanner.useDelimiter("\\Z");
        // read the file to a string
        String help = scanner.next();
        
        
        JTextArea area = new JTextArea(help);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Verdana", 1, 20));
        area.setBorder(new LineBorder(Color.BLACK)); // make it easy to see
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        
        JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        add(scroll);
        
        scroll.setPreferredSize(new Dimension(600, 400));
       
    }
}

//TO DO: button OK
// when ok, start new game 
// no new game button i menu 
