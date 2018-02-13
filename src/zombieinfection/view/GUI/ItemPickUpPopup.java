package zombieinfection.view.GUI;

import javax.swing.*;

import zombieinfection.model.GameEngine;
import zombieinfection.model.Item;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class ItemPickUpPopup extends JFrame implements ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7523837921652492010L;
	
	JCheckBox item1;
	JCheckBox item2;
	JCheckBox item3;
	JCheckBox item4;
	JPanel panel;
	JButton ok;
	int i = 0;
	
	public ItemPickUpPopup() {
		item1 = new JCheckBox("hello there");
		item2 = new JCheckBox("22222");
		item3 = new JCheckBox("33333");
		item4 = new JCheckBox("44444");
		ok = new JButton("OK");
		
		item1.addItemListener(this);
		item2.addItemListener(this);
		item3.addItemListener(this);
		item4.addItemListener(this);
		ok.addActionListener(e -> {
			this.okPressed();
		});
				
		panel = new JPanel(new GridLayout(0,1));
		/*panel.add(item1);
		panel.add(item2);
		panel.add(item3);
		panel.add(item4);*/
		createButtons();
		panel.add(ok);
		
		this.add(panel);
				
		setTitle("Select items to pick up");
	    setSize(300,300);
	    setVisible(true);	
		
	}
	
	private void createButtons(){
		for(Item item: GameEngine.getInstance().getCurrentRoom().getItems()){
			JCheckBox temp = new JCheckBox(item.getName() + " Weight " + item.getWeight());
			panel.add(temp);
			
		}
		
		
	}
		
		
	private void okPressed() {
		System.out.println("nu tryckte du på OK");
		
	}




	/** Listens to the check boxes. */
    public void itemStateChanged(ItemEvent e) {
        int index = 0;
        String info = "-";
        Object source = e.getItemSelectable();
        Object source2 = e.getItem();

        if (source == item1) {
            index = 0;
            info = "item1";
            System.out.println("NU TRYCKTE DU PÅ ITEM1 KNAPPEN");
        } 
        else if (source == item2) {
            index = 1;
            info = "g";
            System.out.println("NU TRYCKTE DU PÅ ITEM2 KNAPPEN");
            
        }
        /* else if (source == hairButton) {
            index = 2;
            c = 'h';
        } else if (source == teethButton) {
            index = 3;
            c = 't';
        }*/

        //Now that we know which button was pushed, find out
        //whether it was selected or deselected.
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            info = "-";
        }

        //Apply the change to the string.
       // choices.setCharAt(index, c);

    }
	
}
	


