package zombieinfection.view.GUI;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import zombieinfection.controller.*;
import zombieinfection.model.*;

public class InventoryPanel extends JPanel implements PropertyChangeListener {
	JPanel keys = new JPanel(new GridLayout(1, 4));
	JPanel food = new JPanel(new GridLayout(1, 4));
	JPanel weapons = new JPanel(new GridLayout(1, 4));
	JPanel ingredients = new JPanel(new GridLayout(1, 4));
	JButton[] fbuttons;
	
	private static final long serialVersionUID = 4112124953987216988L;

	public InventoryPanel(InventoryController ic) {
		this.setLayout(new GridLayout(4, 4));
				
		weapons.add(new JButton("Slot1"));
		weapons.add(new JButton("Slot2"));
		weapons.add(new JButton("Slot3"));
		weapons.add(new JButton("Slot4"));
		ingredients.add(new JButton("Slot5"));
		ingredients.add(new JButton("Slot6"));
		ingredients.add(new JButton("Slot7"));
		ingredients.add(new JButton("Slot8"));
		
		fbuttons = new JButton[4];
		for (int i = 0; i < 4; i++) {
			fbuttons[i] = new JButton("Food" + (i + 1));
			food.add(fbuttons[i]);
		}
		
		fbuttons[0].addActionListener(e -> {ic.foodSlotClicked(1);});
		fbuttons[1].addActionListener(e -> {ic.foodSlotClicked(2);});
		fbuttons[2].addActionListener(e -> {ic.foodSlotClicked(3);});
		fbuttons[3].addActionListener(e -> {ic.foodSlotClicked(4);});

		keys.add(new JButton("Key1"));
		keys.add(new JButton("Key2"));
		keys.add(new JButton("Key3"));
		keys.add(new JButton("Key4"));
				
		this.add(food);
		this.add(weapons);
		this.add(ingredients);
		this.add(keys);
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object o = evt.getNewValue();
        if (evt.getPropertyName().equals("ADDFOOD")) {
            System.out.println("Food was added or removed!");
        }
    }	
}
