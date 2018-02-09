package zombieinfection.model;

import java.util.*;

public class Room {
	
	private String name;
	private String description;
	private HashMap<String, Room> exits;
	private Enemy enemy;
	private ArrayList<Item> items;
	
	
	public Room(String name) {
		this.name = name;
		//this.description = null;
		this.exits = new HashMap<>(); 
		//this.enemy = null;
		this.items = new ArrayList<>();
	}
	
	/*public Room(String name, String description, Enemy enemy) {
		this.name = name;
		this.description = description;
		this.enemy = enemy;
		this.exits = null;
		this.items = null;
	} */
	 
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
		// TODO Auto-generated method stub
		return false;
	}

	public void addItem(Item item) {
		// TODO Auto-generated method stub
		
	}

}

