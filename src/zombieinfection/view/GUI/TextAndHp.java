package GUI;
import java.awt.*;
import java.beans.*;

import javax.swing.*;


public class TextAndHp extends JPanel implements PropertyChangeListener {
	
	private static final long serialVersionUID = 7875681010024987618L;
	JProgressBar pb = new JProgressBar();
	
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
		JTextArea text = new JTextArea("Here is da info on a da room");
		text.setLineWrap(true);
		text.setPreferredSize(new Dimension(100,195));
		text.setEditable(false);
		//ScrollPane
		JScrollPane scroll = new JScrollPane(text);
		//scroll.setSize(5,15);
		
		southPanel.add(scroll);
		
		this.add(northPanel,BorderLayout.NORTH);
		this.add(southPanel,BorderLayout.SOUTH);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object o = evt.getNewValue();
        
		pb.setValue((int) o);
		
	}

}
