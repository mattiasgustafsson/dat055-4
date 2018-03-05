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

    private final int defence;

    /**
     * Creates a name armour
     *
     * @param name the name of the armour
     * @param weight the weight of the armour
     * @param defence the amount of defence the armour will provide
     */
    public Armour(String name, int weight, int defence) {
        super(name, weight);
        this.defence = defence;
    }

    /**
     * Returns the amount of defence this item provides
     *
     * @return the amount of defence this item provides
     */
    public int getDefense() {
        return defence;
    }

}
