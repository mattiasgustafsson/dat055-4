package zombieinfection.model;

/**
 * Subclass of Item, armour can be picked up by the player to give some protection against enemy attacks
 * @author Gustaf Lindqvist
 * @version 2018-02-22
 *
 */


public class Armour extends Item {
	private int damage;
	
	public Armour(String name, int weight,int damage){
		super(name,weight);
		this.damage = damage;
	}
	
	/**
	 * 
	 * @return the amount of damage(armour) this item provides
	 */
	public int getDamage(){
		return damage;
	}

}
