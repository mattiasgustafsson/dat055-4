

package GUI;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class MainFrame {
	private JFrame frame;
	private JPanel panel;
	
	public static void main(String[] args) {
		new MainFrame();

	}
	
	public MainFrame(){
		MakeFrame();
		
	}
	
	private void MakeFrame(){
		frame = new JFrame("Zombie game");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//frame.setBounds(0, 0, 800, 800);
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
				
		JPanel roomPicture = new JPanel(new BorderLayout());
		//konstig skit f�r att f� rutan lite mindre, annars blir den enorm med inladdad bild.
		//roomPicture.setPreferredSize(new Dimension(1,1));
		
		try {
			roomPicture.add(new PicturePanel(),BorderLayout.CENTER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 600;
		c.ipadx = 400;
		panel.add(roomPicture,c);
		
		InteractionPanel ip = new InteractionPanel();
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 100;
		c.ipadx = 400;
		
		panel.add(ip, c);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	
	
	}
	
}
