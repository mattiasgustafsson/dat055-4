package zombieinfection.model;

import java.beans.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int capacity;
    private List<Item> items;
    private FoodSlot[] foodSlots;
    private PropertyChangeSupport pcs;

    public Inventory() {
        capacity = 200; // TODO Decide what this number should be
        items = new ArrayList<>();
        foodSlots = new FoodSlot[4];
        for (int i = 0; i < 4; i++) {
            foodSlots[i] = new FoodSlot(null, i + 1);
        }
        pcs = new PropertyChangeSupport(this);
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns total weight currently in inventory.
     */
    public int getTotalWeight() {
        int combinedWeight = 0;
        for (Item item : items) {
            combinedWeight += item.getWeight();
        }
        return combinedWeight;
    }

    /**
     * Returns boolean depending on if the item fits in the inventory or not.
     * Returns true if the item fits.
     */
    public boolean itemFits(Item item) {
        return getTotalWeight() + item.getWeight() < capacity;
    }

    /**
     * Return the damage of the strongest weapon in the inventory as an int.
     * Returns 0 if there is no weapon in the inventory.
     */
    public int getStrongestWeaponDamage() {
        int damage = 0;
        for (Item item : getItems()) {
            if (item instanceof Weapon) {
                if (((Weapon) item).getDamage() > damage) {
                    damage = ((Weapon) item).getDamage();
                }
            }
        }
        return damage;
    }

    public void add(Item item) {
        items.add(item);
        if (item instanceof Food /*TODO And other conditions*/) {
            foodSlots[0].setFood((Food) item);
            pcs.firePropertyChange("FOOD", true, false);
            System.out.println("It is " + foodSlots[0].isEmpty() + " that Food Slot 1 is Empty");
        }
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    /**
     * Returns boolean depending on if there is an item in the inventory that
     * has the named that is passed to the method as a String
     */
    public boolean containsItem(String name) {
        for (Item i : items) {
            if (i.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    // TODO Remove?
    boolean hasItem(Item i) {
        return items.contains(i);
    }

    public List<Food> getFoodItems() { // TODO Unnecessary?
        List<Food> f = new ArrayList<>();
        for (Item i : items) {
            if (i instanceof Food) {
                f.add((Food) i); // If food item - cast to Food, add to list
            }
        }
        return f;
    }

    public FoodSlot[] getFoodSlots() {
        return foodSlots;
    }

    // TODO Move this to Interface which all "observable" classes implements
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) { // TODO Is this ever used?
        pcs.removePropertyChangeListener(pcl);
    }
    protected void firePropertyChange(String propertyName,Object oldValue,Object newValue) {
        pcs.firePropertyChange(propertyName,oldValue,newValue);
    }
}
