

package zombieinfection.view.GUI;

import java.awt.*;

import javax.swing.*;

public class MainFrame {
	private JFrame frame;
	private JPanel panel;
	
	
	public MainFrame(){
		MakeFrame();
		
	}
	
	private void MakeFrame(){
		frame = new JFrame("Zombie game");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
				
		JPanel roomPicture = new JPanel();
		roomPicture.setBackground(Color.BLUE);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 600;
		c.ipadx = 400;
		panel.add(roomPicture,c);
		
		InteractionPanel ip = new InteractionPanel();
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 200;
		c.ipadx = 400;
		
		panel.add(ip, c);
		
		
		
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	
	
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainFrame();

	}

}
