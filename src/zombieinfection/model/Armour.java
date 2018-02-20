package zombieinfection.model;

//@author Gustaf Lindqvist

public class Armour extends Item {
	private int damage;
	
	public Armour(String name, int weight,int damage){
		super(name,weight);
		this.damage = damage;
	}
	
	public int getDamage(){
		return damage;
	}

}
