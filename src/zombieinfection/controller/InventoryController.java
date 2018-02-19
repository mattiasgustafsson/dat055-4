package zombieinfection.controller;


import zombieinfection.model.*;

public class InventoryController {
    private Inventory inventory;
    private GameEngine gameEngine;
        
    public InventoryController(Inventory inventory) {
        this.inventory = inventory;
        gameEngine = GameEngine.getInstance();
    }
	
    /**
     * Handles the event that one of the food slots in the GUI is clicked.
     * 
     * If the food slot contains the name of a food item it is removed from the
     * GUI and from the Inventory. The health gain of the food item is added to
     * the health of the player.
     */
	public void foodSlotClicked(String name) {
		if(gameEngine.getGameOver())return; 
		if (inventory.containsItem(name)) {
		    Food foodItem = (Food) inventory.getItemByName(name);
		    inventory.remove(foodItem);
		    int healthGain = foodItem.getHealthGained();
		    gameEngine.getPlayer().eatFood(healthGain, foodItem.getName());
		}
	}
}
