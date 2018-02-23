package zombieinfection.model;

/**
 * Subclass of Item, armour can be picked up by the player to give some
 * protection against enemy attacks
 * 
 * @author Gustaf Lindqvist
 * @version 2018-02-22
 *
 */

public class Armour extends Item {
	private int defense;

	/**
	 * Creates a name armour
	 * 
	 * @param name
	 *            the name of the armour
	 * @param weight
	 *            the weight of the armour
	 * @param defence
	 *            the amount of defense the armour will provide
	 */
	public Armour(String name, int weight, int defense) {
		super(name, weight);
		this.defense = defense;
	}

	/**
	 * 
	 * @return the amount of defense this item provides
	 */
	public int getDefense() {
		return defense;
	}

}
