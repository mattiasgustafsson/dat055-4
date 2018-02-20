package zombieinfection.model;
 
import java.beans.*;

import zombieinfection.view.highscore.Highscore;

//@author Gustaf Lindqvist

public class Player  {
	private int health;
	private final int maxHealth;
	private Inventory inventory;
	private boolean infected;
	private PropertyChangeSupport pcs;
	
	public Player(){
		maxHealth = 100;
		health = 100;
		infected = true;
		inventory = new Inventory();
		pcs = new PropertyChangeSupport(this);
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public void setHealth(int newHealth){
		
		int oldHealth;
		
		if(newHealth > maxHealth){
			oldHealth = health;
			health = maxHealth;
		}
		else if(newHealth < 0){
			oldHealth = health;
			health = 0;
			GameEngine.getInstance().setGameOver();
			pcs.firePropertyChange("gameOver", false, true);
            if(GameEngine.getInstance().showLoserMsg()) {;
            new Highscore(0);
            }
		}
		else{
			oldHealth = health;
			health = newHealth;	
		}
		pcs.firePropertyChange("health",oldHealth, health);
		
	}
	
	/**
	 * For use when eating food 
	 * @param healthGained
	 * @param foodName
	 */
	public void eatFood(int healthGained, String foodName){
		
		if ((healthGained + health) > maxHealth){
			int extraHealth = maxHealth - health;
			health = maxHealth;		
			pcs.firePropertyChange("eatingFood", foodName , extraHealth);
		}
		else if ((healthGained + health) <= 0){
			pcs.firePropertyChange("eatingFoodDead", foodName, healthGained);
			GameEngine.getInstance().setGameOver();
			pcs.firePropertyChange("gameOver", false, true);
            if(GameEngine.getInstance().showLoserMsg()) {;
            new Highscore(0);
            }			
		}
		else if(healthGained < 0){
			health = health + healthGained;
			pcs.firePropertyChange("eatingBadFood", foodName, healthGained);
		}
		
		else {
			health = health + healthGained;
			pcs.firePropertyChange("eatingFood", foodName, healthGained);
		}
	}
	

	public void pickUpItem(Item item ){
		if(inventory.itemFits(item)){
            inventory.add(item);
            //Prints which items the player picks up
            pcs.firePropertyChange("pickedUpItem", 0, item.getName());
		}
		else {
		    pcs.firePropertyChange("itemTooHeavy", null, item.getName());
		}
		
	}
	
	public boolean isInfected(){
		return infected;
	}
	
	public void setInfected(boolean infected) {
		this.infected = infected; 
	}
	
	public boolean mixIngredients(){
		if(GameEngine.getInstance().canMixIngredients()) {
			cured();
			setHealth(maxHealth);
            inventory.removeAllIngredients(); 
            //Prints success message 
            pcs.firePropertyChange("mixing", 0, true);
            return true;
		}
		
		//Prints failure message 
		pcs.firePropertyChange("mixing", 0, false);
		return false;
		
	}
	
	
	
	
	
	public void addPropertyChangeListener(PropertyChangeListener l){
		pcs.addPropertyChangeListener(l);
	}
	
	private void cured(){
		infected = false;
	}

}
