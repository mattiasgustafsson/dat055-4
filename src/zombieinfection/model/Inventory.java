package zombieinfection.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int capacity;
    private List<Food> food;
    private List<Ingredient> ingredients;
    private List<Weapon> weapons;
    private List<Recipe> recipes;
    private PropertyChangeSupport pcs;

    public Inventory() {
        capacity = 200; // TODO Decide what this number should be
        food = new ArrayList<>();
        ingredients = new ArrayList<>();
        weapons = new ArrayList<>();
        recipes = new ArrayList<>();
        pcs = new PropertyChangeSupport(this);
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the total weight of all items currently in inventory.
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
     * Returns boolean depending on if the item given as an argument to the
     * function would fit in the inventory. Returns true if the item would fit
     * and false if the total weight would exceed the inventory capacity.
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
        for (Weapon w : weapons) {
            if (w.getDamage() > damage) {
                damage = w.getDamage();
            }
        }
        return damage;
    }

    /**
     * Adds item to one of four lists depending on the item type.
     * @param item
     */
    public void add(Item item) {
        if (item instanceof Food && food.size() < 4) {
            food.add((Food) item);
            pcs.firePropertyChange("foodPicked", null, item.getName());
        }
        else if (item instanceof Ingredient && ingredients.size() < 4) {
            ingredients.add((Ingredient) item);
            pcs.firePropertyChange("ingredientPicked", null, item.getName());
        }
        else if (item instanceof Weapon && weapons.size() < 4) {
            weapons.add((Weapon) item);
            pcs.firePropertyChange("weaponPicked", null, item.getName());
        }
        else if (item instanceof Recipe) {
            recipes.add((Recipe) item);
            pcs.firePropertyChange("recipePicked", null, item.getName());
        }
    }

    /**
     * Removes an item from the inventory.
     */
    public void remove(Item item) {
        if (item instanceof Food) {
            food.remove((Food) item);
            pcs.firePropertyChange("foodEaten", null, item.getName());
        }
        // Keys?
    }

    /**
     * Returns all items currently in inventory
     * @return List<Item>
     */
    public List<Item> getItems() {
        List<Item> allItems = new ArrayList<Item>();
        allItems.addAll(food);
        allItems.addAll(ingredients);
        allItems.addAll(weapons);
        allItems.addAll(recipes);
        // TODO Maybe add keys
        return allItems;
    }

    /**
     * Returns boolean depending on if there is an item in the inventory that
     * has the named that is passed to the method as a String
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
     * Checks if an item is currently in the inventory.
     * @param Item i
     * @return boolean
     */
    boolean hasItem(Item i) {
        return getItems().contains(i);
    }
    
    /**
     * Returns an item that has the name given in the parameter. If no such Item
     * exists the method returns null.
     * @param name
     * @return
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
        pcs.firePropertyChange("inventory",1, 2);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }
}
