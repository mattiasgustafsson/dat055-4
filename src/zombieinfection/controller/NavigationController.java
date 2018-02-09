package zombieinfection.controller;

import zombieinfection.model.GameEngine;


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

		public void pickUpButtonController() {
		// TODO Auto-generated method stub
		
	}

	
}
