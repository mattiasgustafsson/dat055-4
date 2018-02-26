package zombieinfection.model;

/**
 * Subclass of Item, ingredients are needed to make a cure for the infection
 *
 * @author Gustaf Lindqvist
 * @version 2018-02-22
 *
 */
public class Ingredient extends Item {

    /**
     *
     * @param name the name of the ingredient
     * @param weight the weight of the ingredient
     */
    public Ingredient(String name, int weight) {
        super(name, weight);

    }

}
