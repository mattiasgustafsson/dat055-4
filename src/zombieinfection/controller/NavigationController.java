package zombieinfection.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import zombieinfection.model.GameEngine;
import zombieinfection.view.GUI.ItemPickUpPopup;
import zombieinfection.view.GUI.Map;


public class NavigationController implements KeyListener {

	
    
	
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:	northButtonController();
								break;
		case KeyEvent.VK_DOWN:	southButtonController();
								break;
		case KeyEvent.VK_LEFT:	westButtonController();
								break;
		case KeyEvent.VK_RIGHT:	eastButtonController();
								break;
		
		}
		
		
	
	}
	
	
	public void northButtonController() {
		GameEngine.getInstance().goToRoom("north");	
	}

	public void westButtonController() {
		GameEngine.getInstance().goToRoom("west");
	}
	
	public void southButtonController() {
		GameEngine.getInstance().goToRoom("south");
	}

	public void eastButtonController() {
		GameEngine.getInstance().goToRoom("east");
	}

	
	public void mapButtonController() {
        Map map = new Map(GameEngine.getInstance().getEntryRoom(),
                          GameEngine.getInstance().getCurrentRoom(),
                          GameEngine.getInstance().getEndRoom());
		JOptionPane.showMessageDialog(null, map, "Map", JOptionPane.PLAIN_MESSAGE);
	}
    
	//problem: to choose which item player will pick up. All or nothing or some of them?
	public void pickUpButtonController() {
		@SuppressWarnings("unused")
		ItemPickUpPopup ipup = new ItemPickUpPopup();
		
		//Gammal kod, sparar det så länge ifall något skulle gå sönder. 
		/*if (GameEngine.getInstance().getGameOver()) return;
        //Lägger till de items som finns i rummet i Players inventory
        for (Item item : GameEngine.getInstance().getCurrentRoom().getItems()) {
            GameEngine.getInstance().getPlayer().pickUpItem(item);
        }
        //Tar bort items från rummet
        GameEngine.getInstance().getCurrentRoom().removeItems(); */
    }

    public void mixButtonController() {
        GameEngine.getInstance().getPlayer().mixIngredients();
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
	


	

