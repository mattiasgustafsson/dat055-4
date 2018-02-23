package zombieinfection.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * The Room class is used to represent the different rooms of the game. 
 *
 * @author Daniel Duvanå
 * @version 2018-02-23
 */
public class Room {
	
	private String name;
	private String description;
	private String picture;
	private HashMap<String, Room> exits;
	private Enemy enemy;
	private boolean hasEnemy;
	private ArrayList<Item> items;
    private PropertyChangeSupport pcs; 
    
    /**
     * Creates a Room with a given name 
     * @param name	The name to give the Room
     */
    public Room(String name) {
		this.name = name;
		this.exits = new HashMap<>(); 
		this.items = new ArrayList<>();
		hasEnemy = false;
        pcs = new PropertyChangeSupport(this);
	}
	 
	/**
	 * Returns the name of the Room
	 * @return A String with the name of the Room
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the Room
	 * @param name	The name that will be given to the Room
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the description of the Room
	 * @return A String with the description of the Room
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of the Room
	 * @param description	The description of the Room 
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Sets the location of the picture of the Room.
	 * @param picture	The relative path of the stored picture.
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	/**
	 * Returns the location of the picture of the Room
	 * @return A String with the relative path of the stored picture
	 */
	public String getPicture() {
		return picture;
	}
		
	/**
	 * Sets one of the exits of the Room so that if you exit
	 * the Room in "direction" you reach "neighbor".  
	 * @param direction	The direction of the exit
	 * @param neighbor	The neighboring Room that the exit leads to
	 * @return	FALSE if direction isn't valid or neighbor is null.
	 * 			Returns TRUE if successful. 
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
	 * Returns what Room is reached when exiting this Room in a specific direction.
	 * @param direction	The direction of the exit.
	 * @return A Room that is reached by going through the given exit.
	 */
	public Room getExit(String direction) {
		return exits.get(direction);
	}
	
	/**
	 * Returns the Enemy object in the Room, or null if there is non. 
	 * @return The Enemy object in the Room, or null if there is non.
	 */
	public Enemy getEnemy() {
		return enemy;
	}
	
	/**
	 * Gives the Room an Enemy object. 
	 * @param enemy	The Enemy to be given to this Room.
	 */
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
		hasEnemy = true;
	}
	
	/**
	 * Declares if the Room has an Enemy in it or not. 
	 * @return TRUE if the Room has an Enemy, FALSE if it doesn't. 
	 */
	public boolean hasEnemy() {
	    return hasEnemy;
	}
	
	/**
	 * Returns a list of all items in the Room. 
	 * @return An ArrayList of Item objects.
	 */
	public ArrayList<Item> getItems() {
		return items;
	}
	
	/**
	 * Declares if the Room contains any items or not.
	 * @return TRUE if the Room contains items, FALSE if it doesn't. 
	 */
	public boolean hasItem() {
		return !items.isEmpty();
	}
	
	/**
	 * Adds an Item to the Room
	 * @param item	The Item the be added to the Room
	 */
	public void addItem(Item item) {
		items.add(item);
        pcs.firePropertyChange("items",null, items);
	}
	
	/**
	 * Declares if the Room has an exit in a given direction.
	 * @param	direction	The direction to check for an exit in. 
	 * @return	TRUE if the Room has an exit in the given direction,
	 * 			FALSE if it doesn't. 
	 */
	public boolean hasExit(String direction) {
		return exits.containsKey(direction);
	}

	/**
	 * Removes all items from the Room. 
	 */
    public void removeItems() {
        items.clear();
        pcs.firePropertyChange("items", null, items);
    }
    
    /**
     * Removes one Item from the Room
     * @param item The Item to be removed
     */
    public void removeItem(Item item){
    	items.remove(item);
    	if(items.isEmpty())
    		pcs.firePropertyChange("items", null, items);
    	
    }

    /**
     * Adds a PropertyChangeListener as listener to the Room. 
     * @param l The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }
    
    /**
     * Declares if the Room contains an Item of the same class as a given Item
     * @param theItem	The Item to compare class with.  
     * @return 	TRUE if the Room contains an Item of the same class as theItem,
     * 			FALSE if it doesn't. 
     */
    public boolean hasItemOfthisType(Item theItem) {
        for(Item item:items){
            if(theItem.getClass() == item.getClass()){
                return true;
            }
        }
        return false; 
     }
    
    /**
     * Sets if the Room has an Enemy or not.
     * @param b	A Boolean that sets the value of hasEnemy.
     */
    public void setHasEnemy(boolean b) {
        hasEnemy = b;
    }
}

