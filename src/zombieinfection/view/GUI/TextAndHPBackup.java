package zombieinfection.view.GUI;
import java.awt.*;

import javax.swing.*;


public class TextAndHpBackup extends JPanel {
	
	private JPanel wow = new JPanel();
	
	private static final long serialVersionUID = 1L;

	public TextAndHp(){
		
	wow.setLayout(new GridBagLayout());
	wow.setPreferredSize(new Dimension(300, 300));
	GridBagConstraints c = new GridBagConstraints();
	
	//c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	//c.ipady = 1;
	//c.ipadx = 1;
	
	JProgressBar pb = new JProgressBar();
	pb.setValue(100);
	pb.setMinimupackage GUI;m(0);
	pb.setMaximum(100);
	pb.setForeground(Color.red);
	//pb.setSize(200,50);
	//pb.setStringPainted(false);
	wow.add(pb,c);
	
	//c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 1;
	//c.ipady = 1;
	//c.ipadx = 1;
	//c.anchor = GridBagConstraints.PAGE_END;
	c.insets = new Insets(20, 0, 0, 0);
	
	JTextArea text = new JTextArea("Here is da info on a da room");
	text.setLineWrap(true);
	text.setPreferredSize(new Dimension(250,150));
	text.setEditable(false);
	wow.add(text,c);
	
	this.add(wow);

	
	
	}

}
