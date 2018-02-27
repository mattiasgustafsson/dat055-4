package zombieinfection.model;

/**
 * This is an abstract class which is the parent class for armours, ingredients,
 * key, recipe and food. Items can be picked up by the player
 *
 * @author Gustaf Lindqvist
 * @version 2018-02-22
 */
public abstract class Item {

    private final String name;
    private final int weight;

    /**
     *
     * @param name the name of the item
     * @param weight the weight of the item
     */
    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    /**
     * Returns the name of the Item
     *
     * @return the name of the Item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the weight of the Item
     *
     * @return the weight of the Item
     */
    public int getWeight() {
        return weight;
    }

}
