package zombieinfection.controller;

import zombieinfection.model.GameEngine;
import zombieinfection.model.Item;


public class NavigationController {

	
	
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
		// TODO Auto-generated method stub
		
	}
    
	//problem: to choose which item player will pick up. All or nothing or some of them?
	public void pickUpButtonController() {
		if (GameEngine.getInstance().getGameOver()) return;
        //Lägger till de items som finns i rummet i Players inventory
        for (Item item : GameEngine.getInstance().getCurrentRoom().getItems()) {
            GameEngine.getInstance().getPlayer().pickUpItem(item);
        }
        //Tar bort items från rummet
        GameEngine.getInstance().getCurrentRoom().removeItems();
    }

    public void mixButtonController() {
        GameEngine.getInstance().getPlayer().mixIngredients();
    }
	}

	

