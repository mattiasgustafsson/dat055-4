package zombieinfection.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * The Inventory class represents the player's inventory - the set of items the
 * player is currently carrying.
 *
 * @author Mattias Gustafsson
 * @version 2018-02-22
 *
 */
public class Inventory {

    private int capacity;
    private List<Food> food;
    private List<Ingredient> ingredients;
    private List<Armour> armours;
    private List<Recipe> recipes;
    private List<Key> keys;
    private PropertyChangeSupport pcs;
    private final int MAX_NO_OF_SAME_TYPE_ITEMS;

    /**
     * Creates an Inventory.
     */
    public Inventory() {
        capacity = 180;
        food = new ArrayList<>();
        ingredients = new ArrayList<>();
        armours = new ArrayList<>();
        recipes = new ArrayList<>();
        keys = new ArrayList<>();
        pcs = new PropertyChangeSupport(this);
        MAX_NO_OF_SAME_TYPE_ITEMS = 4;
    }

    /**
     * Returns the weight the player is able to carry.
     *
     * @return the weight the player is able to carry.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns a list of all items currently in the inventory.
     *
     * @return a list of all items currently in the inventory.
     */
    public List<Item> getItems() {
        List<Item> allItems = new ArrayList<Item>();
        allItems.addAll(food);
        allItems.addAll(ingredients);
        allItems.addAll(armours);
        allItems.addAll(recipes);
        allItems.addAll(keys);
        return allItems;
    }

    /**
     * Returns the total weight of all items currently in inventory.
     *
     * @return the total weight of all items currently in inventory.
     */
    public int getTotalWeight() {
        List<Item> allItems = getItems();
        int combinedWeight = 0;
        for (Item item : allItems) {
            combinedWeight += item.getWeight();
        }
        return combinedWeight;
    }

    /**
     * Returns boolean depending on if the parameter item has a weight low
     * enough to fit in the player's current inventory and if the player is not
     * carrying too many items of the same type as the item given as a
     * parameter.
     *
     * @param item the item to be checked if it fits in the inventory
     * @return <code>true</code> if the item would fit in the inventory and the
     * player is not carrying too many items of the same type as the parameter
     * item; <code>false</code> if the item is too heavy to fit in the inventory
     * or if there are already too many items of the same type as the parameter
     * item in the inventory
     */
    public boolean itemFits(Item item) {
        if (getTotalWeight() + item.getWeight() > capacity) {
            return false;
        }
        if (item instanceof Food && food.size() >= MAX_NO_OF_SAME_TYPE_ITEMS) {
            return false;
        }
        if (item instanceof Armour && armours.size() >= MAX_NO_OF_SAME_TYPE_ITEMS) {
            return false;
        }
        return true;
    }

    /**
     * Returns the damage the strongest armour currently in the inventory is
     * able to take. Returns 0 if there is no armour in the inventory.
     *
     * @return the damage the armour is able to take from enemy attacks without
     * hurting the player
     */
    public int getStrongestArmour() {
        int damage = 0;
        for (Armour a : armours) {
            if (a.getDefense() > damage) {
                damage = a.getDefense();
            }
        }
        return damage;
    }

    /**
     * Adds an item to the inventory.
     *
     * @param item the item that should be added to the player's inventory
     */
    public void add(Item item) {
        if (item instanceof Food) {
            food.add((Food) item);
            pcs.firePropertyChange("foodPicked", null, item.getName());
        } else if (item instanceof Ingredient) {
            ingredients.add((Ingredient) item);
            pcs.firePropertyChange("ingredientPicked", null, item.getName());
        } else if (item instanceof Armour) {
            armours.add((Armour) item);
            pcs.firePropertyChange("armourPicked", null, item.getName());
        } else if (item instanceof Recipe) {
            recipes.add((Recipe) item);
            // Show recipe icon in inventory part of GUI
            pcs.firePropertyChange("recipePicked", null, item.getName());
            // Print text of recipe in text area
            pcs.firePropertyChange("recipePrint", null, ((Recipe) item).getDescription());
        } else if (item instanceof Key) {
            keys.add((Key) item);
            pcs.firePropertyChange("keyPicked", null, item.getName());
        }
    }

    /**
     * Removes an item from the inventory.
     *
     * @param item the item that should be removed from the player's inventory
     */
    public void remove(Item item) {
        if (item instanceof Food) {
            food.remove((Food) item);
            pcs.firePropertyChange("foodEaten", null, item.getName());
        }
    }

    /**
     * Returns boolean depending on if there is an item in the inventory that
     * has the named that is passed to the method as a parameter
     *
     * @param name the name of an item we want to check is in the inventory or
     * not
     * @return <code>true</code> if there is an item with the name in the method
     * parameter; <code>false</code> otherwise
     */
    public boolean containsItem(String name) {
        for (Item i : getItems()) {
            if (i.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given item is currently in the inventory.
     *
     * @param i the item we want to know is in the inventory or not
     * @return <code>true</code> if the given item is in the inventory
     */
    public boolean hasItem(Item i) {
        return getItems().contains(i);
    }

    /**
     * Returns an Item that has the name given in the parameter.
     *
     * @param name the name of the item we want to get
     * @return Item that has the name given in the method parameter or
     * <code>null</code> if such an item does not exist
     */
    public Item getItemByName(String name) {
        for (Item i : getItems()) {
            if (i.getName().toLowerCase().equals(name.toLowerCase())) {
                return i;
            }
        }
        return null;
    }

    /**
     * Removes all ingredient items from inventory.
     */
    public void removeAllIngredients() {
        ingredients.clear();
        pcs.firePropertyChange("inventory", 1, 2);
    }

    /**
     * Add a PropertyChangeListener to the listener pcl.
     *
     * @param pcl the listener object
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    /**
     * Removes all items currently in the inventory.
     */
    public void removeAll() {
        food.clear();
        ingredients.clear();
        armours.clear();
        keys.clear();
        recipes.clear();
        pcs.firePropertyChange("inventoryCleared", 1, 2);
    }
}
