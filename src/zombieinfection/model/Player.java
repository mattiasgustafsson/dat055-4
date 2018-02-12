package zombieinfection.model;
 
import java.beans.*;

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
		}
		else{
			oldHealth = health;
			health = newHealth;	
		}
		pcs.firePropertyChange("health",oldHealth, health);
		
	}

	public void pickUpItem(Item item ){
		if(inventory.itemFits(item)){
            inventory.add(item);
            //Prints which items the player picks up
            pcs.firePropertyChange("pickedUpItem", 0, item.getName());
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
