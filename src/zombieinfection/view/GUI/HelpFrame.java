package zombieinfection.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class HelpFrame extends JFrame {
	
	JTextArea textArea = new JTextArea();
	
	public HelpFrame(){
		MakeHelpPanel();
	}
	
		private void MakeHelpPanel() {
	    	setSize(500, 500);
	        setTitle("Help");
	        textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setEditable(false);
			
			JScrollPane scrollPane = new JScrollPane(textArea);
			
	        try {
	            textArea.read(new InputStreamReader(
	                    getClass().getResourceAsStream("help.txt")),
	                    null);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    
	    this.add(scrollPane);
		this.setVisible(true);
		
	    }
}


