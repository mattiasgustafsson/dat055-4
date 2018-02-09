package zombieinfection.view.GUI;
import java.awt.*;
import javax.swing.*;

import zombieinfection.controller.NavigationController;

import zombieinfection.controller.*;

public class NavigationPanel extends JPanel {
	private static final long serialVersionUID = 338833966047467161L;
    //private JButton north = 
	
	

	public NavigationPanel(){
		NavigationController controller = new NavigationController(); 
		this.setLayout(new GridLayout(3, 3));
		this.add(new JButton(""));
	
		//this.add(new JButton("North"));
		JButton north = new JButton("North");
		north.addActionListener(e -> { controller.northButtonController(); });
		this.add(north);
		
		this.add(new JButton(""));
		this.add(new JButton("West"));
		this.add(new JButton("Map"));
		this.add(new JButton("East"));
		this.add(new JButton("Pick up items"));
		this.add(new JButton("South"));
		this.add(new JButton(""));
		
	}
	
	
	
}



