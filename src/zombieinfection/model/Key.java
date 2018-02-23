package zombieinfection.model;

/**
 * Subclass of Item, keys can be picked up by the player to unlock doors
 * 
 * @author Gustaf Lindqvist
 * @version 2018-02-22
 */

public class Key extends Item {

	/**
	 * Creates a Key
	 * 
	 * @param name
	 *            the name of the key
	 * @param weight
	 *            the weight of the key
	 */
	public Key(String name, int weight) {
		super(name, weight);
	}

}
