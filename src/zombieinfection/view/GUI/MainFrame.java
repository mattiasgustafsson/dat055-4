package zombieinfection.view.GUI;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import zombieinfection.controller.NavigationController;

public class MainFrame {
	private JFrame frame;
	private JPanel panel;

	public MainFrame() {
		MakeFrame();

	}

	private void MakeFrame() {
		frame = new JFrame("Zombie game");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.addKeyListener(new NavigationController());
		// frame.setBounds(0, 0, 800, 800);
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel roomPicture = new JPanel(new BorderLayout());
		// konstig skit f�r att f� rutan lite mindre, annars blir den enorm med inladdad
		// bild.
		// roomPicture.setPreferredSize(new Dimension(1,1));

		try {
			roomPicture.add(new PicturePanel(), BorderLayout.CENTER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		//create a clock display
		ClockPanel clock = new ClockPanel();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		panel.add(clock, c);
		
		/*create a zombie display
        ZombiePanel zp = new ZombiePanel();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.ipadx = 2;
        c.ipady = 2;
        panel.add(zp, c);*/

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.ipady = 600;
		c.ipadx = 400;
		panel.add(roomPicture, c);

		InteractionPanel ip = new InteractionPanel();
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 2;
		c.ipady = 100;
		c.ipadx = 400;
		panel.add(ip, c);

		frame.add(panel);
		frame.setJMenuBar(new BarMenu());
		frame.pack();
		frame.setVisible(true);

	}

}
