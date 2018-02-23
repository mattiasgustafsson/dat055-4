package zombieinfection.model;

/**
 * 
 * Subclass of Item, Food can be consumed by the player to recover health
 * @author Gustaf Lindqvist
 * @version 2018-02-22
 *
 */
public class Food extends Item {
	private int healthGained;
	
	public Food(String name, int weight, int healthGained){
		super(name, weight);
		this.healthGained = healthGained;
	}
	
	/**
	 * 
	 * @return the amount of health you gain from eating the food
	 */	
	public int getHealthGained(){
		return healthGained;
	}
	
}
