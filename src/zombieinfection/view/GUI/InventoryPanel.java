package GUI;

import java.awt.*;
import javax.swing.*;

public class InventoryPanel extends JPanel {

	private static final long serialVersionUID = 4112124953987216988L;

	public InventoryPanel(){
		this.setLayout(new GridLayout(4, 4));
		this.add(new JButton("Slot1"));
		this.add(new JButton("Slot2"));
		this.add(new JButton("Slot3"));
		this.add(new JButton("Slot4"));
		this.add(new JButton("Slot5"));
		this.add(new JButton("Slot6"));
		this.add(new JButton("Slot7"));
		this.add(new JButton("Slot8"));
		this.add(new JButton("Food1"));
		this.add(new JButton("Food2"));
		this.add(new JButton("Food3"));
		this.add(new JButton("Food4"));
		this.add(new JButton("Key1"));
		this.add(new JButton("Key2"));
		this.add(new JButton("Key3"));
		this.add(new JButton("Key4"));
		
	}
	
}
