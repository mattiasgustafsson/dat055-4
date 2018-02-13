package zombieinfection.view.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import zombieinfection.Game;
import zombieinfection.model.GameEngine;
import zombieinfection.view.highscore.Highscore;


public class BarMenu extends JMenuBar implements ActionListener {
	
	JMenu game;
	JMenu highscore;
	JMenuItem quit;
	JMenuItem restart;
	
	public BarMenu(){
	
		createMenuBar();
		
	}
	
	public void createMenuBar(){
		game = new JMenu("Game");
		highscore = new JMenu("Highscore");
		quit = new JMenuItem("Quit Game");
		restart = new JMenuItem("Restart Game");
		this.add(game);
		game.add(quit);
		game.add(restart);
		this.add(highscore);
		
		quit.addActionListener(this);
		//restart.addActionListener(this);
		//highscore.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {

	    if("Quit Game".equals(e.getActionCommand())){
	    	System.exit(0);
	    }
	    /*if("Restart Game".equals(e.getActionCommand())){
	    	
	    		
	    }
	    if("Highscore".equals(e.getActionCommand())){
	    	
	    }*/
	}

}

