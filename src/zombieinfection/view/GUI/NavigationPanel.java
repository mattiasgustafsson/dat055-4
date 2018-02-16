package zombieinfection.view.GUI;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.*;

import zombieinfection.controller.NavigationController;
import zombieinfection.model.GameEngine;
import zombieinfection.model.Room;

public class NavigationPanel extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 338833966047467161L;
	private JButton north, south, west, east, map, pickUp, mix;

	public NavigationPanel() {
		this.setFocusable(false);
		createButtons();
		addButtons();
		
		NavigationController controller = new NavigationController();
		addControllers(controller);
		GameEngine.getInstance().addPropertyChangeListener(this);
		
	}
 
    private void createButtons() {
		north = new JButton("North");
		west = new JButton("West");
		south = new JButton("South");
		east = new JButton("East");
		map = new JButton("Map");
		pickUp = new JButton("Pick up items");
		mix = new JButton("Mix ingredients"); //maybe change color for some buttons...  
				
		north.setFocusable(false);
		west.setFocusable(false);
		south.setFocusable(false);
		east.setFocusable(false);
		map.setFocusable(false);
		pickUp.setFocusable(false);
		mix.setFocusable(false);
	}
    
	private void addButtons() {
		this.setLayout(new GridLayout(3, 3));
		this.add(new JLabel(""));
		this.add(north);
		this.add(new JLabel(""));
		this.add(west);
		this.add(map);
		this.add(east);
		this.add(pickUp);
		this.add(south);  
        this.add(mix); 
	}

	private void addControllers(NavigationController controller) {
		north.addActionListener(e -> {
			controller.northButtonController();
		});
		west.addActionListener(e -> {
			controller.westButtonController();
		});
		map.addActionListener(e -> {
			controller.mapButtonController();
		});
		east.addActionListener(e -> {
			controller.eastButtonController();
		});
		pickUp.addActionListener(e -> {
			controller.pickUpButtonController();
		});
		south.addActionListener(e -> {
			controller.southButtonController();
		});
        mix.addActionListener(e -> {
			controller.mixButtonController();
		});
        
   }

	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
        Room room;
		if (evt.getPropertyName().equals("currentRoom")) {
			room = (Room) evt.getNewValue();
			north.setEnabled(room.hasExit("north"));
			south.setEnabled(room.hasExit("south"));
			east.setEnabled(room.hasExit("east"));
			west.setEnabled(room.hasExit("west"));
			pickUp.setEnabled(room.hasItem());
            mix.setEnabled(GameEngine.getInstance().getCurrentRoom().getName().equals("Mix Room"));
		}
        if(evt.getPropertyName().equals("items")){
            room = (Room)evt.getSource();
            pickUp.setEnabled(room.hasItem());
        }
        
        //Vad är poängen med denna koden? Vi enable knappen några rader ovanför ju? 
        if(evt.getPropertyName().equals("inventory")){
           mix.setEnabled(GameEngine.getInstance().getCurrentRoom().getName().equals("Mix Room"));
        }

	}
}
