package zombieinfection.controller;

import zombieinfection.model.*;

public class InventoryController {
    private Inventory inventory;
    
    public InventoryController(Inventory inventory) {
        this.inventory = inventory;
    }
	
	public void foodSlotClicked(int nr) {
		System.out.println("Food slot " + nr + " was clicked.");
	}
	
	public void ingredientPicked(String name) {
	    System.out.println("Picked up " + name);
	}
}
