package zombieinfection.controller;

import zombieinfection.model.GameEngine;


public class NavigationController {

	
	
	public void northButtonController() {
		GameEngine.getInstance().goToRoom("north");		
	}
}
