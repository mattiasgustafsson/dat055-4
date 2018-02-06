package modell;

//@author Gustaf Lindqvist

public class Player {
	private int health;
	private final int maxHealth;
//	private Inventory inventory;
	private boolean infected;
	
	public Player(){
		maxHealth = 100;
		health = 100;
		infected = true;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	/*public Inventory getInventory(){
		return inventory;
	}*/
	
	public void SetHealth(int newHealth){
		if(newHealth > maxHealth){
			health = maxHealth;
		}
		else if(newHealth < 0){
			health = 0;
		}
		else{
			health = newHealth;	
		}
		
	}
	
	public void gainHealth(){
		
	}
	
	public boolean isInfected(){
		return infected;
	}
	
	public boolean mixIngridients(){
		if(inventory.contains(item ingredient1) && inventory.contains(item ingredient2) && inventory.contains(item ingredient3) && GameEngine.getCurrentRoom().getName().equals("mixingRoom") ){
			Cured();
			health = maxHealth;
			return true;
		}
		return false;
		
	}
	
	private void Cured(){
		infected = false;
	}

}
