package zombieinfection.controller;

import zombieinfection.model.GameEngine;
import zombieinfection.model.Item;


public class NavigationController {

	
	
	public void northButtonController() {
		System.out.println("HEHEeheh    " + GameEngine.getInstance().getCurrentRoom().getName());
		GameEngine.getInstance().goToRoom("north");	
		System.out.println("HEHEeheh    " + GameEngine.getInstance().getCurrentRoom().getName());
		
	}

	public void westButtonController() {
		GameEngine.getInstance().goToRoom("west");
		
	}
	
	public void southButtonController() {
		System.out.println("HEHEeheh    " + GameEngine.getInstance().getCurrentRoom().getName());
		GameEngine.getInstance().goToRoom("south");
		System.out.println("HEHEeheh    " + GameEngine.getInstance().getCurrentRoom().getName());
		
	}

	public void eastButtonController() {
		GameEngine.getInstance().goToRoom("east");
		
	}

	
	public void mapButtonController() {
		// TODO Auto-generated method stub
		
	}
        //problem: to choose which item player will pick up. All or nothing or some of them?
		public void pickUpButtonController() {
            for(Item item: GameEngine.getInstance().getCurrentRoom().getItems()){
		          GameEngine.getInstance().getPlayer().pickUpItem(item);
            }
            GameEngine.getInstance().getCurrentRoom().removeItems(); 
	}
        
        public void mixButtonController() {
		GameEngine.getInstance().getPlayer().mixIngredients();
	}

	
}
