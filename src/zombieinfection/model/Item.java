package zombieinfection.model;

//@author Gustaf Lindqvist
//This is an abstract class which is the parent class for weapons, ingredients, recipe and food

public abstract class Item {
	private String name;
	private int weight;
	
	public Item(String name, int weight){
		this.name = name;
		this.weight = weight;
	}
	public String getName(){
		return name;
	}
	
	public int getWeight(){
		return weight;
	}
	
}
