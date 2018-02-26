package zombieinfection.controller;

import zombieinfection.model.*;

/**
 * Class for handling communication between inventory part of GUI and inventory
 * part of model.
 *
 * @author Mattias Gustafsson
 * @version 2018-02-22
 */
public class InventoryController {

    private Inventory inventory;
    private GameEngine gameEngine;

    /**
     * Creates an object of the Inventory class.
     *
     * @param inventory the inventory object the inventory part of the GUI is
     * going to communicate with
     */
    public InventoryController(Inventory inventory) {
        this.inventory = inventory;
        gameEngine = GameEngine.getInstance();
    }

    /**
     * Handles the event that one of the food slots in the GUI is clicked. If
     * the food slot contains the name of a food item it is removed from the GUI
     * and from the Inventory. The health gain of the food item is added to the
     * health of the player.
     *
     * @param name the text written on the clicked food slot button
     */
    public void foodSlotClicked(String name) {
        if (gameEngine.getGameOver()) {
            return;
        }
        if (inventory.containsItem(name)) {
            Food foodItem = (Food) inventory.getItemByName(name);
            int healthGain = foodItem.getHealthGained();
            gameEngine.getPlayer().eatFood(healthGain, foodItem.getName());
            inventory.remove(foodItem);
        }
    }
}
