package zombieinfection.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int capacity;
    private List<Item> items;
    private List<FoodSlot> foodSlots;
    
    public Inventory() {
        capacity = 200; // TODO Decide what this number should be
        items = new ArrayList<>();
        foodSlots = new ArrayList<>();
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
         if (foodSlots.size() < 4) { // Check this condition elsewhere?
        	 foodSlots.add(new FoodSlot());
         }
     }
     
     public void remove(Item item) {
         items.remove(item);
     }
     
     public List<Item> getItems() {
         return items;
     }
     
     public boolean containsItem(String name) {
    	 for (Item i : items) {
    		 if (i.getName().toLowerCase().equals(name.toLowerCase())) {
    			 return true;
    		 }
    	 }
    	 return false;
     }

     boolean hasItem(Item i) {
         return items.contains(i);
     }
     
     public List<Food> getFoodItems() { // TODO Remove?
    	 List<Food> f = new ArrayList<>();
    	 for (Item i : items) {
    		 if (i instanceof Food) {
    			 f.add((Food) i); // If food item - cast to Food, add to list
    		 }
    	 }
    	 return f;
     }
     
     // Test Inventory // TODO Remove
     public static void main(String[] args) {
        Inventory inv = new Inventory();
        Weapon axe = new Weapon("Axe", 50, 60);
        Weapon crossbow = new Weapon("Crossbow", 70, 100);
        Weapon pencil = new Weapon("Pencil", 5, 5);
        Food apple = new Food("Apple", 5, 10);
        inv.add(axe);
        inv.add(crossbow);
        inv.add(pencil);
        inv.add(apple);
        for (Item i : inv.items) {
            System.out.println(i.getName());
        }
        System.out.println("Strongest weapon damage:");
        System.out.println(inv.getStrongestWeaponDamage());
        System.out.println(inv.containsItem("APpLE"));
    }    
}