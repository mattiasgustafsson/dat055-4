package zombieinfection.model;

/**
 * This is an abstract class which is the parent class for armours, ingredients, key, recipe and food
 * @author Gustaf Lindqvist
 * @version 2018-02-22 
*/

public abstract class Item {
	private String name;
	private int weight;
	
	public Item(String name, int weight){
		this.name = name;
		this.weight = weight;
	}
	
	/**
	 * 
	 * @return the name of the Item
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @return the weight of the Item
	 */
	public int getWeight(){
		return weight;
	}
	
}
