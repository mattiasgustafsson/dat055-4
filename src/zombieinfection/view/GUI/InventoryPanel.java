package GUI;

import java.awt.*;
import javax.swing.*;

public class InventoryPanel extends JPanel {
	JPanel keys = new JPanel(new GridLayout(1, 4));
	JPanel food = new JPanel(new GridLayout(1, 4));
	JPanel weapons = new JPanel(new GridLayout(1, 4));
	JPanel ingredients = new JPanel(new GridLayout(1, 4));
	
	private static final long serialVersionUID = 4112124953987216988L;

	public InventoryPanel(){
		this.setLayout(new GridLayout(4, 4));
				
		weapons.add(new JButton("Slot1"));
		weapons.add(new JButton("Slot2"));
		weapons.add(new JButton("Slot3"));
		weapons.add(new JButton("Slot4"));
		ingredients.add(new JButton("Slot5"));
		ingredients.add(new JButton("Slot6"));
		ingredients.add(new JButton("Slot7"));
		ingredients.add(new JButton("Slot8"));
		food.add(new JButton("Food1"));
		food.add(new JButton("Food2"));
		food.add(new JButton("Food3"));
		food.add(new JButton("Food4"));
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
