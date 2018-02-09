package zombieinfection.view.GUI;

import java.awt.*;
import javax.swing.*;

import zombieinfection.controller.InventoryController;

public class InventoryPanel extends JPanel {
	JPanel keys = new JPanel(new GridLayout(1, 4));
	JPanel food = new JPanel(new GridLayout(1, 4));
	JPanel weapons = new JPanel(new GridLayout(1, 4));
	JPanel ingredients = new JPanel(new GridLayout(1, 4));
	
	private static final long serialVersionUID = 4112124953987216988L;

	public InventoryPanel(){
		InventoryController ic = new InventoryController();
		this.setLayout(new GridLayout(4, 4));
				
		weapons.add(new JButton("Slot1"));
		weapons.add(new JButton("Slot2"));
		weapons.add(new JButton("Slot3"));
		weapons.add(new JButton("Slot4"));
		ingredients.add(new JButton("Slot5"));
		ingredients.add(new JButton("Slot6"));
		ingredients.add(new JButton("Slot7"));
		ingredients.add(new JButton("Slot8"));
		
		JButton fbutton1 = new JButton("Food1"); // TODO Make loop great again
		fbutton1.addActionListener(e -> {ic.foodSlotClicked(1);});
		food.add(fbutton1);
		JButton fbutton2 = new JButton("Food2");
		fbutton2.addActionListener(e -> {ic.foodSlotClicked(2);});
		food.add(fbutton2);
		JButton fbutton3 = new JButton("Food3");
		fbutton3.addActionListener(e -> {ic.foodSlotClicked(3);});
		food.add(fbutton3);
		JButton fbutton4 = new JButton("Food4");
		fbutton4.addActionListener(e -> {ic.foodSlotClicked(4);});
		food.add(fbutton4);

		keys.add(new JButton("Key1"));
		keys.add(new JButton("Key2"));
		keys.add(new JButton("Key3"));
		keys.add(new JButton("Key4"));
				
		this.add(food);
		this.add(weapons);
		this.add(ingredients);
		this.add(keys);
		
	}
	
	
	
	
	
}
