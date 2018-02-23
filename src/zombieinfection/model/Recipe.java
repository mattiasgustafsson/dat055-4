package zombieinfection.model;

/**
 * Subclass of Item,
 * 
 * @author Gustaf Lindqvist
 * @version 2018-02-22
 */

public class Recipe extends Item {
	private String description;

	/**
	 * Creates a recipe
	 * 
	 * @param name
	 *            the name of the recipe
	 * @param weight
	 *            the weight of the recipe
	 */
	public Recipe(String name, int weight) {
		super(name, weight);
		description = ("Rotten jelly beans, Alvedon pills, Hydrochloric acid and Caustic soda");
	}

	/**
	 * 
	 * @return the description of the recipe
	 */
	public String getDescription() {
		return description;
	}
}
