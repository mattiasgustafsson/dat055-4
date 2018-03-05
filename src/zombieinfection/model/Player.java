package zombieinfection.model;

import java.beans.*;

import zombieinfection.view.highscore.Highscore;

/**
 * The Player class keeps track of the players health and inventory as well as
 * infection status
 *
 *
 * @author Gustaf Lindqvist
 * @version 2018-02-22
 */
public class Player {

    private int health;
    private final int maxHealth;
    private final Inventory inventory;
    private boolean infected;
    private final PropertyChangeSupport pcs;

    /**
     * Creates a Player
     */
    public Player() {
        maxHealth = 100;
        health = 100;
        infected = true;
        inventory = new Inventory();
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * Returns the current amount of health the player has
     *
     * @return the current amount of health the player has
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the maximum amount of health a player can have
     *
     * @return the maximum amount of health a player can have
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Returns the inventory the player is carrying
     *
     * @return the inventory the player is carrying
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Set the health of the player
     *
     * @param newHealth changes the players health to the new value
     */
    public void setHealth(int newHealth) {

        int oldHealth;

        if (newHealth > maxHealth) {
            oldHealth = health;
            health = maxHealth;
        } else if (newHealth < 0) {
            oldHealth = health;
            health = 0;
        } else {
            oldHealth = health;
            health = newHealth;
        }
        pcs.firePropertyChange("health", oldHealth, health);

        if (health == 0) {
            GameEngine.getInstance().setGameOver();
            pcs.firePropertyChange("gameOver", false, true);
            try {
				if (GameEngine.getInstance().showLoserMsg()) {
				    new Highscore(0);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    /**
     * For use when eating food
     *
     * @param healthGained amount of health gained from eating it
     * @param foodName the name of the food
     */
    public void eatFood(int healthGained, String foodName) {
        int oldHealth = health;

        if ((healthGained + health) > maxHealth) {
            int extraHealth = maxHealth - health;
            health = maxHealth;
            pcs.firePropertyChange("eatingFood", foodName, extraHealth);
        } else if ((healthGained + health) <= 0) {
            pcs.firePropertyChange("eatingFoodDead", foodName, healthGained);
            health = 0;
            GameEngine.getInstance().setGameOver();
            pcs.firePropertyChange("gameOver", false, true);
            try {
				if (GameEngine.getInstance().showLoserMsg()) {
				    ;
				    new Highscore(0);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if (healthGained < 0) {
            health = health + healthGained;
            pcs.firePropertyChange("eatingBadFood", foodName, healthGained);
        } else {
            health = health + healthGained;
            pcs.firePropertyChange("eatingFood", foodName, healthGained);
        }

        pcs.firePropertyChange("health", oldHealth, health);
    }

    /**
     *
     * Picks up the specified item
     *
     * @param item the item to pick up
     */
    public void pickUpItem(Item item) {
        if (inventory.itemFits(item)) {
            inventory.add(item);
            // Prints which items the player picks up
            pcs.firePropertyChange("pickedUpItem", 0, item.getName());
        } else {
            pcs.firePropertyChange("itemTooHeavy", null, item.getName());
        }

    }

    /**
     * Returns true if the player is infected
     *
     * @return true if the player is infected
     */
    public boolean isInfected() {
        return infected;
    }

    /**
     * Sets the infected to true or false
     *
     *
     * @param infected Set infected to true or false for this object value.
     */
    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    /**
     * Returns true if you can mix the ingredients
     *
     * @return true if you can mix the ingredients
     */
    public boolean mixIngredients() {
        if (GameEngine.getInstance().canMixIngredients()) {
            cured();
            setHealth(maxHealth);
            inventory.removeAllIngredients();
            // Prints success message
            pcs.firePropertyChange("mixing", 0, true);
            return true;
        }

        // Prints failure message
        pcs.firePropertyChange("mixing", 0, false);
        return false;

    }

    /**
     * Adds a PropertyChangeListener
     *
     * @param l the PropertChangeListener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    private void cured() {
        infected = false;
    }

}
