package zombieinfection.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class Room {
	
	private String name;
	private String description;
	private String picture;
	private HashMap<String, Room> exits;
	private Enemy enemy;
	private boolean hasEnemy;
	private ArrayList<Item> items;
    private PropertyChangeSupport pcs; 

	public Room(String name) {
		this.name = name;
		this.exits = new HashMap<>(); 
		this.items = new ArrayList<>();
		hasEnemy = false;
        pcs = new PropertyChangeSupport(this);
	}
	 
	/**
	 * Returns the name of the Room
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the Room
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the description of the Room
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of the Room
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Sets the picture of the Room
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	/**
	 * Returns the picture of the Room
	 */
	public String getPicture() {
		return picture;
	}
	
	
	/**
	 * Sets one (1) of the exits of the room so that if you
	 * exit the room in "direction" you reach "neighbor".
	 * Returns FALSE if direction isn't valid or Room is null
	 * Returns TRUE if successful
	 */
	public boolean setExit(String direction, Room neighbor) {
		if (direction.matches("north|south|west|east") && !(neighbor == null)) {
			exits.put(direction, neighbor);
			return true;
		}
		else 
			return false;	
	}
	
	/**
	 *  Return the room that is reached if we go from this room in direction
	 *  "direction". If there is no room in that direction, return null.
	 */
	public Room getExit(String direction) {
		return exits.get(direction);
	}
	
	/**
	 * Return the enemy in this room. If there is no enemy in the room, return null.
	 */
	public Enemy getEnemy() {
		return enemy;
	}
	
	/**
	 * Set an enemy object in the room. 
	 */
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
		hasEnemy = true;
	}
	
	/**
	 * Returns true if the room has an Enemy.
	 */
	public boolean hasEnemy() {
	    return hasEnemy;
	}
	
	/**
	 * Return an ArrayList of Item objects in the room. 
	 */
	public ArrayList<Item> getItems() {
		return items;
	}
	
	/**
	 * Return a string describing the room's exits, for example
	 * "Exits: north west"
	 * Vet ej om vi kommer ha anv�ndning av denna, fanns i Zuul s� jag 
	 * kopierade den
	 */
	@SuppressWarnings("unused")
	private String getExitString() {
		String returnString = "Exits:";
		Set<String> keys = exits.keySet();
		for(Iterator<String> iter = keys.iterator(); iter.hasNext(); )
			returnString += " " + iter.next();
		return returnString;
	}

	public boolean hasItem() {
		return !items.isEmpty();
	}

	public void addItem(Item item) {
		items.add(item);
        pcs.firePropertyChange("items",null, items);
	}

	public boolean hasExit(String direction) {
		return exits.containsKey(direction);
	}

    public void removeItems() {
        items.clear();
        pcs.firePropertyChange("items", null, items);
    }

    void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void setHasEnemy(boolean b) {
        hasEnemy = b;
    }

}

