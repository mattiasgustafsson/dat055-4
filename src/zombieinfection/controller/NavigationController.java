package zombieinfection.controller;

import zombieinfection.model.GameEngine;


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

		public void pickUpButtonController() {
		// TODO Auto-generated method stub
		
	}

	
}
