package zombieinfection.view.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import zombieinfection.Game;
import zombieinfection.model.GameEngine;
import zombieinfection.view.highscore.Highscore;


public class BarMenu extends JMenuBar implements ActionListener {
	
	JMenu gamemenu;
	JMenu highscoremenu;
	JMenu helpmenu;
	JMenuItem highscore;
	JMenuItem quit;
	JMenuItem restart;
	JMenuItem help;
	
	public BarMenu(){
		createMenuBar();	
	}
	
	private void createMenuBar(){
		gamemenu = new JMenu("Game");
		highscoremenu = new JMenu("Highscore");
		helpmenu = new JMenu("Help");
		highscore = new JMenuItem("Show Highscore");
		quit = new JMenuItem("Quit Game");
		restart = new JMenuItem("Restart Game");
		help = new JMenuItem("Show Instructions");
		
		this.add(gamemenu);
		gamemenu.add(restart);
		gamemenu.add(quit);
		this.add(highscoremenu);
		highscoremenu.add(highscore);
		this.add(helpmenu);
		helpmenu.add(help);
		
		quit.addActionListener(this);
		restart.addActionListener(this);
		highscore.addActionListener(this);
		help.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {

	    if("Quit Game".equals(e.getActionCommand())){
	    	System.exit(0);
	    }
	    if("Restart Game".equals(e.getActionCommand())){
	    	GameEngine.getInstance().createNewGame(false);
	    }
	    if("Show Highscore".equals(e.getActionCommand())){
	    	new Highscore(0);
	    }
	    if("Show Instructions".equals(e.getActionCommand())){
	    	try {
				new HelpFrame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
	}

}

