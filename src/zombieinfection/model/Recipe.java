package zombieinfection.model;

/**
 * Subclass of Item, contains a description which tells the player what
 * ingredients to collect
 *
 * @author Gustaf Lindqvist
 * @version 2018-02-22
 */
public class Recipe extends Item {

    private String description;

    /**
     * Creates a recipe
     *
     * @param name the name of the recipe
     * @param weight the weight of the recipe
     */
    public Recipe(String name, int weight) {
        super(name, weight);
        description = ("Rotten jelly beans, Alvedon pills, Hydrochloric acid and Caustic soda");
    }

    /**
     * Returns the description of the recipe
     *
     * @return the description of the recipe
     */
    public String getDescription() {
        return description;
    }
}
