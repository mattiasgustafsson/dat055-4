package zombieinfection.view.GUI;
import java.awt.*;
import javax.swing.*;

public class NavigationPanel extends JPanel {
	private static final long serialVersionUID = 338833966047467161L;


	public NavigationPanel(){
		this.setLayout(new GridLayout(3, 3));
		this.add(new JButton(""));
		this.add(new JButton("North"));
		this.add(new JButton(""));
		this.add(new JButton("West"));
		this.add(new JButton("Map"));
		this.add(new JButton("East"));
		this.add(new JButton(""));
		this.add(new JButton("South"));
		this.add(new JButton(""));
		
	}
	
	
	
}
