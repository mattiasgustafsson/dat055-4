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
		if(newHealth > maxHealth){
			health = maxHealth;
		}
		else if(newHealth < 0){
			health = 0;
		}
		else{
			health = newHealth;	
		}
		pcs.firePropertyChange("health",health);
		
	}
	
	
	public void pickUpItem( )
	
	public boolean isInfected(){
		return infected;
	}
	
	public void setInfected(boolean infected) {
		this.infected = infected; 
	}
	
	public boolean mixIngridients(){
		if(inventory.contains(item ingredient1) && inventory.contains(item ingredient2) && inventory.contains(item ingredient3) && GameEngine.getCurrentRoom().getName().equals("mixingRoom") ){
			cured();
			health = maxHealth;
			return true;
		}
		return false;
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener e){
		pcs.addPropertyChangeListener(e);
	}
	
	private void cured(){
		infected = false;
	}

}