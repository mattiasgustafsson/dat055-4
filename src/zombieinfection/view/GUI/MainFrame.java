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
		
		panel = new JPanel(new BorderLayout());
		
		JLayeredPane centrePane = new JLayeredPane(); 
		centrePane.setPreferredSize(new Dimension (1200,605));
		
		JPanel roomPicture = new JPanel(new BorderLayout());
		roomPicture.add(new PicturePanel(), BorderLayout.CENTER);
		
		//create a clock display
		ClockPanel clock = new ClockPanel();
		//clock is shown above the picture panel
		centrePane.add(clock, new Integer(1));
		centrePane.add(roomPicture,new Integer(0));
		
		clock.setBounds(0,0,100,40);
		roomPicture.setBounds(0,0,1200,600);
		
		InteractionPanel ip = new InteractionPanel();
		ip.setPreferredSize(new Dimension (1200,215));
		
		panel.add(ip,BorderLayout.SOUTH);
		panel.add(centrePane, BorderLayout.CENTER);

		frame.add(panel);
		frame.setJMenuBar(new BarMenu());
		frame.pack();
		frame.setVisible(true);

	}

}
