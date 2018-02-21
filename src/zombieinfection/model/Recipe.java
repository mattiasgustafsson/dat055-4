package zombieinfection.model;

//@author Gustaf Lindqvist

public class Recipe extends Item {
	private String description;
	
	public Recipe(String name, int weight){
		super(name, weight);
		description = ("Rotten jelly beans, Alvedon pills, Hydrochloric acid and Caustic soda");
    }

	public String getDescription() {
		return description;
	}
}

