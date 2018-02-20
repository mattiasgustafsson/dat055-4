package zombieinfection.view.GUI;

import javax.swing.*;

import zombieinfection.model.*;
import java.awt.*;
import java.util.ArrayList;


public class ItemPickUpPopup extends JDialog {

	private static final long serialVersionUID = -7523837921652492010L;
	
	JPanel panel;
	JPanel checkBoxes;
	JButton ok;
		
	public ItemPickUpPopup() {
		setModal(true); 
        setLocationRelativeTo(null);
		ok = new JButton("Pick up");
		
		//Lambda expression for OK button
		ok.addActionListener(e -> {
			this.okPressed();
		});
		
		//Create the panels
		panel = new JPanel(new BorderLayout());
		checkBoxes = new JPanel(new GridLayout(0, 1));
		
		//Create the buttons dynamically depending on what items are in the room
		createButtons();
		
		//Add the buttons to the panel
		panel.add(checkBoxes, BorderLayout.CENTER);
		panel.add(ok, BorderLayout.SOUTH);
		
		this.add(panel);
				
		setTitle("Select items to pick up");
	    setSize(400,200);
	    setResizable(false);
	    setVisible(true);
	    panel.setFocusable(false);
		checkBoxes.setFocusable(false);
		
	}
	
	private void createButtons(){
		for(Item item: GameEngine.getInstance().getCurrentRoom().getItems()){
			JCheckBox temp = new JCheckBox( item.getClass().getSimpleName()+": " + item.getName() + "    Weight: " + item.getWeight());
			temp.setFont(new Font("Dialog", 0,18));
			checkBoxes.add(temp);
		}
	}
		
		
	private void okPressed() {
		int itemNr = 0;
		ArrayList<Item> items = GameEngine.getInstance().getCurrentRoom().getItems();
				
		//For all components in checkBoxes panel
		for (Component comp: checkBoxes.getComponents()){
			//See if it's a JCheckBox, and for all those
			if(comp instanceof JCheckBox){
				//See if the checkBox is selected, then do this
				if(((JCheckBox) comp).isSelected()){
					//Grab the item that the checkBox represents 
					Item itemToPickUp = items.get(itemNr);
					//Give the item to the player
					GameEngine.getInstance().getPlayer().pickUpItem(itemToPickUp);
					if (GameEngine.getInstance().getPlayer().getInventory().itemFits(itemToPickUp)) {
					    //Remove the item from the room if it fits in the inventory
	                    GameEngine.getInstance().getCurrentRoom().removeItem(itemToPickUp);
					}
				}
				else if(!((JCheckBox) comp).isSelected()){
					itemNr++;
				}
				
			}
		}
		//Closes the window
		this.dispose();
	}

	
	
}
	


