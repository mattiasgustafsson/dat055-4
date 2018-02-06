package zombieinfection.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int capacity = 200; // TODO Decide
    private List<Item> items = new ArrayList<>();
    
    // TODO No consctructor needed?
    
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * Returns total weight currently in inventory.
     */
    public int getTotalWeight() { // TODO Add to UML
        int combinedWeight = 0;
        for (Item item : items) {
            combinedWeight += item.getWeight();
        }
        return combinedWeight;
    }
    
    /**
     * Returns boolean depending on if the item fits in the inventory or not
     * Returns true if the item fits.
     */
    public boolean itemFits(Item item) { // TODO Change in UML
        return getTotalWeight() + item.getWeight() < capacity;
    }

    /**
     * Return the damage of the strongest weapon in the inventory as an int
     */
    public int getStrongestWeapon() { // TODO Change in UML
        int damage = 0;
        for (Item item : items) {
            if (item instanceof Weapon) {
                if (item.getDamage() > damage) {
                    damage = item.getDamage();
                }
            }
        }
        return damage;
    }
     
     public void add(Item item) {
         items.add(item);
     }
     
     public void remove(Item item) {
         items.remove(item);
     }
     
     public List<Item> getItems() {
         return items;
     }
}
