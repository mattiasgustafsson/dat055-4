package zombieinfection.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class HelpFrame extends JFrame {
	
	JTextArea textArea = new JTextArea();
	
	public HelpFrame() throws IOException{
		MakeHelpPanel();
	}
	
		private void MakeHelpPanel() throws IOException {
	    	setSize(500, 500);
	        setTitle("Help");
	        textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setEditable(false);
			
			JScrollPane scrollPane = new JScrollPane(textArea);
			
	        try {
	        
	        InputStream text = getClass().getClassLoader().getResourceAsStream("txt/help.txt");
	        textArea.read(new InputStreamReader(text),
	                    null);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    
	    this.add(scrollPane);
		this.setVisible(true);
		
	    }
}


