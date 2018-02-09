/*
 * This class is responsible for the rules of the game and it keeps track of all objects in the game world.
 * The class creates random objects to pick, enemies to fight with. 
 */
package zombieinfection.model;

import java.beans.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import zombieinfection.view.highscore.Highscore;

/**
 * @author Elena Marzi
 */
public class GameEngine {

	private HashMap<String, Room> rooms;
	private ArrayList<Item> items;
	// private Collection<Enemy> enemies;
	private Player player;
	private Room currentRoom;
	private Clock clock;
	private Room entryRoom;
	private Random random;
	private static GameEngine instance = null;
	private PropertyChangeSupport pcs;
	private Room mixingRoom;
	private Room exit; 

	// singleton
	private GameEngine() {
		createInstances();
		// read the map from the file
		readMap("map.txt");
		createItems();
		// createEnemies();
		pcs = new PropertyChangeSupport(this); 
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		// När en ny lyssnare läggs till för GameEngine, kopplas
		// den lyssnaren både till GameEngine och till Clock
		
		// pcs.addPropertyChangeListener(l);
		clock.addPropertyChangeListener(l);
	}

	public static GameEngine getInstance() {
		if (instance == null) {
			instance = new GameEngine();
		}
		return instance;
	}

	private void createInstances() {
		rooms = new HashMap<String, Room>();
		items = new ArrayList<Item>();
		// enemies= new ArrayList<Enemy>();
		random = new Random();
		player = new Player();
		clock = new Clock();
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the currentRoom
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	// den kommer att anropas av en kontroller
	public void goToRoom(String direction) {
		if(direction.equals("north"))
			System.out.println("Hello WORLD");
			
		Room nextRoom = currentRoom.getExit(direction);
		
		if (nextRoom != null) {
			Room oldRoom = currentRoom; 
			currentRoom = nextRoom;
			pcs.firePropertyChange("currentRoom", oldRoom, currentRoom);
			if(currentRoom== exit) {
				//create high score
				Highscore score = new Highscore();
			}
		}
	}

	public void createNewGame() {
		currentRoom = entryRoom;
		player.setHealth(player.getMaxHealth());
		randomizeItems();
		player.setInfected(true);
		clock.startTicking(5 * 60);
	}

	// read map from a file. If a bigger map needed just change in the file
	private void readMap(String fileName) {
		try {
			// open the file
			Scanner file = new Scanner(new File(fileName));
			while (file.hasNextLine()) {
				// get the type of the information to handle: room, exit, entry
				String dataType = file.nextLine();
				if (dataType.equals("exit")) {
					String roomFrom = file.nextLine();
					String direction = file.nextLine();
					String roomTo = file.nextLine();
					createExit(roomFrom, roomTo, direction);
					// need to know where player starts
				} else if (dataType.equals("entry")) {
					String roomName = file.nextLine();
					entryRoom = rooms.get(roomName);
				} else if (dataType.equals("mixing room")) {
	                    String roomName = file.nextLine();
					// DEBUG info
					System.out.println("Start at room " + roomName);
					mixingRoom = rooms.get(roomName);
				} else if (dataType.equals("room")) {
					String roomName = file.nextLine();
					String description = file.nextLine();
					String picture = file.nextLine();
					createRoom(roomName, description);
				}
			}
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "File couldn't open");
		}
	}

	private void createRoom(String roomName, String description) {
		// DEBUG info
		System.out.println("Creating a room called " + roomName + " : " + description);
		// create new room
		Room room = new Room(roomName);
		// put the room in to the map: (key is the name) to find entry room
		rooms.put(roomName, room);
	}

	// create exit between rooms
	private void createExit(String roomFrom, String roomTo, String direction) {
		// DEBUG info
		System.out.println("Creating an exit from " + roomFrom + "till " + roomTo);
		// get reference of the rooms to connect
		Room from = rooms.get(roomFrom);
		Room to = rooms.get(roomTo);
		if (from == null) {
			// DEBUG info
			System.out.println("Hittar inte from-rummet");
		}
		if (to == null) {
			// DEBUG info
			System.out.println("Hittar inte to-rummet");
		}
		// create exit. Method is in Room class
		from.setExit(direction, to);
	}

	// items into a file in the next version...
	private void createItems() {
		Item recipe = new Recipe("recept1", 5);
		Item beans = new Ingredient("rotten jelly beans", 1);
		Item pills = new Ingredient("alvedon pills", 1);
		Item acid = new Ingredient("hydrochloric acid", 1);
		Item soda = new Ingredient("caustic soda", 1);

		items.add(recipe);
		items.add(beans);
		items.add(pills);
		items.add(acid);
		items.add(soda);
	}

	// To do: Create enemies and place them randomly in different rooms
	private void createEnemies() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	// Randomize items without repeating the same item type in the same room
	private void randomizeItems() {
		// convert the hashmap to array
		Room[] allRooms = rooms.values().toArray(new Room[0]);
		int i = 0;
		// loop through items
		while (i < items.size()) {
			// an item is placed in a room. if it not already exists.
			int roomIndex = random.nextInt(allRooms.length);
			Room theroom = allRooms[roomIndex];

			if (!theroom.hasItem()) { // in the next version: hasItemOfthisType()
				theroom.addItem(items.get(i));
				i++;// go to the next item
				System.out.println(roomIndex);
			}
		}
	}
	//if exitRoom and max health go to high score.  
	public void toHighScore () {
		
	}

	// Clock that counts down second by second
	public class Clock extends Thread {
		
		private PropertyChangeSupport pcs;
		// number of seconds to live
		private int secondsLeft;
		// if the clock is started or not
		private boolean ticking;

		public Clock() {
			pcs = new PropertyChangeSupport(this);
			ticking = false;
			start();//when creating, thread runs
		}

		// use this to connect a view to this model
		public void addPropertyChangeListener(PropertyChangeListener l) {
			pcs.addPropertyChangeListener(l);
		}

		// call this method to start the clock-when a new game starts-
		public void startTicking(int initialSeconds) {
			updateSecondsLeft(initialSeconds);
			ticking = true;
		}

		// call this to stop the clock
		public void stopTicking() {
			ticking = false;
		}

		@Override
		// This is the thread loop
		public void run() {
			while (!isInterrupted()) {
				try {
					sleep(1000);
				} catch (InterruptedException ex) {
					break;
				}

				// om klockan är igång och ska räkna ner tid
				if (ticking) {
					updateSecondsLeft(secondsLeft - 1);
				}
			}
		}

		// update and fire property change
		private void updateSecondsLeft(int newValue) {
			int oldValue = secondsLeft;
			if (newValue < 0) {
				newValue = 0;
			}

			if (oldValue != newValue) {
				secondsLeft = newValue;
				// det är upp till GUI att visa "Du är död"-meddelande om
				// newValue är noll!
				pcs.firePropertyChange("secondsLeft", oldValue, newValue);
			}

			if (newValue == 0) {
				ticking = false;
			}
		}
	}

	public boolean canMixIngredients() {
		if (mixingRoom == currentRoom) {
            for(Item i: items){
                if(i instanceof Ingredient){
                   if(!player.getInventory().hasItem(i)){
                       return false; 
                   } 
                }
            }
             return true; 
		}
      else return false;
	}

	// To do: when entering a room, GameEngine handles fight rules.
	//inventory : controller
	//menu bar
	//gameengine kopplas ihop med high score och methoden som räknar när man är dör


}
