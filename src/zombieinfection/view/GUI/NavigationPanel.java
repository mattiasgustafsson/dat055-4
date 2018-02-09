package zombieinfection.view.GUI;
import java.awt.*;
import javax.swing.*;

import zombieinfection.controller.NavigationController;


public class NavigationPanel extends JPanel {
	private static final long serialVersionUID = 338833966047467161L;
    private JButton north = new JButton("North"); 
	private JButton west = new JButton("West");
	private JButton south = new JButton("South");
	private JButton east = new JButton("East");
	private JButton map = new JButton("Map");
	private JButton pickUp = new JButton("Pick up items");
	

	public NavigationPanel(){
		NavigationController controller = new NavigationController(); 
		this.setLayout(new GridLayout(3, 3));
		
		
		//Empty button
		this.add(new JButton(""));
	
		//North button
		north.addActionListener(e -> { controller.northButtonController(); });
		this.add(north);
		
		//Empty button
		this.add(new JButton(""));
		
		//West button
		west.addActionListener(e -> { controller.westButtonController(); });
		this.add(west);
		
		//Map button
		map.addActionListener(e -> { controller.mapButtonController(); });
		this.add(map);
		
		//East button		
		east.addActionListener(e -> { controller.eastButtonController(); });
		this.add(east);
		
		//Pick up items button
		pickUp.addActionListener(e -> { controller.pickUpButtonController(); });
		this.add(pickUp);
		
		//South button
		south.addActionListener(e -> { controller.southButtonController(); });
		this.add(south);
		
		//Empty button
		this.add(new JButton(""));
		
	}
	
	
	
}



