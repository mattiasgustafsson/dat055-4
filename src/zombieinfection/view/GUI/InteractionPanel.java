package GUI;

import java.awt.*;
import javax.swing.*;

public class InteractionPanel extends JPanel {
	private static final long serialVersionUID = 7584029056674325609L;
	
	public InteractionPanel(){
		this.setLayout(new GridLayout(1, 3));
		this.setBackground(Color.BLACK);
		this.add(new JButton("Inventory"));
		this.add(new TextAndHp());
		this.add(new NavigationPanel());
		
	}
	
	
	
	
	
}
