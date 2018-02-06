package GUI;
import java.awt.*;

import javax.swing.*;


public class TextAndHp extends JPanel {
	
	private static final long serialVersionUID = 7875681010024987618L;

	public TextAndHp() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setBackground(Color.YELLOW);
		
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		northPanel.setBackground(Color.BLACK);
		//Progress bar
		JProgressBar pb = new JProgressBar();
		pb.setValue(100);
		pb.setMinimum(0);
		pb.setMaximum(100);
		pb.setForeground(Color.red);
		
		northPanel.add(pb);
		
		southPanel.setBackground(Color.GREEN);
		//TextArea 
		JTextArea text = new JTextArea("Here is da in room info", 5, 20);
		text.setLineWrap(true);
		text.setEditable(false);
		//ScrollPane
		JScrollPane scroll = new JScrollPane(text);
		//scroll.setSize(5,15);
		
		southPanel.add(scroll);
		
		
		
		
		
		
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 100;
		c.ipadx = 150;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.PAGE_START;
		this.add(northPanel, c);
		
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 100;
		c.ipadx = 150;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.PAGE_END;
		this.add(southPanel, c);
		
		
		
		
		
		/*
		//Progress bar
		JProgressBar pb = new JProgressBar();
		pb.setValue(100);
		pb.setMinimum(0);
		pb.setMaximum(100);
		pb.setForeground(Color.red);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(20, 5, 0, 5);
		
		this.add(pb, c);
		
		//TextArea 
		JTextArea text = new JTextArea("Here is da in room info");
		text.setLineWrap(true);
		//text.setPreferredSize(new Dimension(250,150));
		text.setEditable(false);
		
		//ScrollPane
		JScrollPane scroll = new JScrollPane(text);
		scroll.setSize(5,15);
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		
		c.anchor = GridBagConstraints.PAGE_END;
		//c.insets = new Insets(0, 5, 0, 5);
		
		this.add(scroll, c); 
		
		*/
	}

}
