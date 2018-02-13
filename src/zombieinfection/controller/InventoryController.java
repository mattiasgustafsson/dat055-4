package zombieinfection.controller;

import zombieinfection.model.*;

public class InventoryController {
    private Inventory inventory;
    
    public InventoryController(Inventory inventory) {
        this.inventory = inventory;
    }
	
	public void foodSlotClicked(String name) {
		if (inventory.containsItem(name)) {
		    inventory.remove(inventory.getItemByName(name));
		}
	}
	
	public void ingredientPicked(String name) {
	    System.out.println("Picked up " + name);
	}
}
