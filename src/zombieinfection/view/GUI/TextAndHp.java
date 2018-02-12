package zombieinfection.view.GUI;
import java.awt.*;
import java.beans.*;

import javax.swing.*;

import zombieinfection.model.GameEngine;
import zombieinfection.model.Room;


public class TextAndHp extends JPanel implements PropertyChangeListener {
	 
	
	private static final long serialVersionUID = 7875681010024987618L;
	private JProgressBar pb = new JProgressBar();
	private JTextArea text = new JTextArea(); 
	
	public TextAndHp() {
		this.setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		northPanel.setLayout(new BorderLayout());
		
		northPanel.setBackground(Color.BLACK);
		//Progress bar
		pb.setValue(80);
		pb.setMinimum(0);
		pb.setMaximum(100);
		pb.setForeground(Color.red);
		
		northPanel.add(pb,BorderLayout.CENTER);
		
		southPanel.setBackground(Color.GREEN);
		//TextArea 
		text = new JTextArea("Here is da info on a da room");
		text.setLineWrap(true);
		text.setPreferredSize(new Dimension(100,195));
		text.setEditable(false);
		//ScrollPane
		JScrollPane scroll = new JScrollPane(text);
		//scroll.setSize(5,15);
		
		southPanel.add(scroll);
		
		this.add(northPanel,BorderLayout.NORTH);
		this.add(southPanel,BorderLayout.SOUTH);
		GameEngine.getInstance().addPropertyChangeListener(this);
		GameEngine.getInstance().getPlayer().addPropertyChangeListener(this);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("currentRoom")) {
			Room room = (Room) evt.getNewValue();
			text.setText(room.getName() + "\n" + room.getDescription() + "\n"); 
		}
		else if(evt.getPropertyName().equals("pickedUpItem")) {
			String itemName = (String) evt.getNewValue();
			text.setText(text.getText() + "\n" + "You picked up " + itemName + " and placed it in your inventory.");
			
		}
		else if(evt.getPropertyName().equals("mixing")) {
			if ((Boolean) evt.getNewValue() == true) {
				text.setText(text.getText() + "\n" + "You successfully made a potion to cure yourself. You're now healthy and immune to the virus!");
			}
			else if ((Boolean) evt.getNewValue() == false) {
				text.setText(text.getText() + "\n" + "Hmm... You seem to be missing some ingredients from the recipie");
			}
			
		}
	}
}
