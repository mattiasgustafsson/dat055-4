package zombieinfection.model;

//@author Gustaf Lindqvist

public class Recipe extends Item {
	private String description;
	
	public Recipe(String name, int weight){
		super(name, weight);
		description = "Mj�l, �gg, sm�r och kebab";
	}
}

