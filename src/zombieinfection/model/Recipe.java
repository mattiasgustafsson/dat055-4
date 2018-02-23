package zombieinfection.model;

/**
 * Subclass of Item, 
 *@author Gustaf Lindqvist
 *@version 2018-02-22 
*/

public class Recipe extends Item {
	private String description;
	
	public Recipe(String name, int weight){
		super(name, weight);
		description = ("Rotten jelly beans, Alvedon pills, Hydrochloric acid and Caustic soda");
    }
	
	/**
	 * 
	 * @return the desciption of the recipe
	 */
	public String getDescription() {
		return description;
	}
}

