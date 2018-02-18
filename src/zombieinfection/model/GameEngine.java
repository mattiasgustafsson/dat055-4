/*
 * This class is responsible for the rules of the game and it keeps track of all objects in the game world.
 * The class creates random objects to pick, enemies to fight with. 
 */
package zombieinfection.model;

import java.awt.Font;
import java.beans.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import zombieinfection.view.highscore.Highscore;

/**
 * @author Elena Marzi
 */
public class GameEngine {

    private HashMap<String, Room> rooms;
    private ArrayList<Item> items;
    private List<Enemy> enemies;
    private Player player;
    private Room currentRoom;
    private Clock clock;
    private Room entryRoom;
    private Random random;
    private static GameEngine instance = null;
    private PropertyChangeSupport pcs;
    private Room mixingRoom;
    private Room endRoom;
    private boolean gameOver;
    private final int timer = 6*50;

    // singleton
    private GameEngine() {
        createInstances();
        // read the map from the file
        URL mapUrl = getClass().getClassLoader().getResource("txt/map.txt");
        readMap(mapUrl);
        createItems();
        createAndPlaceEnemies(3);
        pcs = new PropertyChangeSupport(this);
        gameOver = true; 
    }

    //when a view adds itself as a listener to GameEngine, it gets
    //added to all the other model objects by GameEngine. 
    //one call is sufficient to start listening to everything in the model objects.
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
        clock.addPropertyChangeListener(l);
        for (Room room : rooms.values()) { //every room notify changes
            room.addPropertyChangeListener(l);
        }
        player.getInventory().addPropertyChangeListener(l);
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
        random = new Random();
        player = new Player();
        enemies = new ArrayList<Enemy>();
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
       if(gameOver)return; 
        Room oldRoom = currentRoom;
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom != null) {
        	if((nextRoom == endRoom) && (!getPlayer().getInventory().containsItem("key"))){
        		return;
        	}
            currentRoom = nextRoom;
            pcs.firePropertyChange("changeOverlay", null, null); //remove any overlay
            if (currentRoom.hasEnemy()) {
                currentRoom.getEnemy().interact(); 
            }
            pcs.firePropertyChange("currentRoom", oldRoom, currentRoom);
            pcs.firePropertyChange("changePicture", oldRoom.getPicture(), currentRoom.getPicture());
            checkWinGame();
        }

    }

    public void createNewGame() {
        currentRoom = entryRoom;
        player.setHealth(player.getMaxHealth());
        randomizeItems();
        player.setInfected(true);
        clock.startTicking(timer);
        gameOver = false;
        pcs.firePropertyChange("gameOver", true, false);
        pcs.firePropertyChange("currentRoom", null, currentRoom);
        pcs.firePropertyChange("changePicture", null, currentRoom.getPicture());
    }

    // read map from a file. If a bigger map needed just change in the file
    private void readMap(URL fileName) {
        try {
            // open the file
            Scanner file = new Scanner(fileName.openStream());
            while (file.hasNextLine()) {
                // get the type of the information to handle: room, endRoom, entry
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
                    mixingRoom = rooms.get(roomName);
                } else if (dataType.equals("end room")) {
                    String roomName = file.nextLine();
                    endRoom = rooms.get(roomName);
    
                } else if (dataType.equals("room")) {
                    String roomName = file.nextLine();
                    String description = file.nextLine();
                    String picture = file.nextLine();
                    createRoom(roomName, description, picture);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File couldn't open");
		}
    }

    private void createRoom(String roomName, String description, String picture) {
        Room room = new Room(roomName);
        // set the description of the room
        room.setDescription(description);
        // set the picture of the room
        room.setPicture(picture);        
        // put the room in to the map: (key is the name) to find entry room
        rooms.put(roomName, room);
        
    }

    // create endRoom between rooms
    private void createExit(String roomFrom, String roomTo, String direction) {
  
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
        // create endRoom. Method is in Room class
        from.setExit(direction, to);
    }

    // items into a file in the next version...
    private void createItems() {
        createRecipe();
        createIngredients();
        createKey();
        createWeapons();
        createFood();
    }
    
    private void createWeapons() {
        Item axe = new Weapon("Battle axe", 20, 10);
        Item knife = new Weapon("Knife", 5, 2);
        Item shuriken = new Weapon("Shuriken", 1, 8);
        
        items.add(axe);
        items.add(knife);
        items.add(shuriken);
    }
    
    private void createFood(){
        Food hawaii = new Food("Hawaii pizza", 30,50);
        Food catFood = new Food("Cat food",10,4);
        Food haggis = new Food("Stinky haggis", 40,70);
        Food kebab = new Food ("A moldy kebab", 16,10);
        Food beer = new Food("A Carnegie Porter beer", 2, -15);
       
        items.add(hawaii);
        items.add(catFood);
        items.add(haggis);
        items.add(kebab);
        items.add(beer);
    }

	private void createKey() {
		Item key = new Key("key",0);
        items.add(key);
	}

	private void createIngredients() {
		Item beans = new Ingredient("Rotten jelly beans", 1);
        Item pills = new Ingredient("Alvedon pills", 1);
        Item acid = new Ingredient("Hydrochloric acid", 1);
        Item soda = new Ingredient("Caustic soda", 1);
        items.add(beans);
        items.add(pills);
        items.add(acid);
        items.add(soda);
	}

	private void createRecipe() {
		Item recipe = new Recipe("Recipe", 5);
        items.add(recipe);
	}
    
    /**
     * Creates a random number of enemies between 1 and the given maximum number
     * of enemies and adds the enemies to random rooms.
     * A room can at most 1 enemy.
     * @param maxNoOfEnemies
     */
    private void createAndPlaceEnemies(int maxNoOfEnemies) {
        // Randomize the actual number of enemies
        int actualNoOfEnemies = random.nextInt(maxNoOfEnemies) + 1;
        // Create the randomized number of enemies and add to list of enemies
        for (int i = 0; i < actualNoOfEnemies; i++) {
            enemies.add(new Enemy("zombie" + Integer.toString(i)));
        }
        System.out.println("The enemies are:");
        for (Enemy e : enemies) {
            System.out.println(e.getName() + " with strength: " + e.getStrength());
        }
        // Create array from values of HashMap rooms
        Room[] allRooms = rooms.values().toArray(new Room[0]);
        int i = 0; // Iteration variable
        while (i < actualNoOfEnemies) {
            // Pick a random room
            int roomIndex = random.nextInt(allRooms.length);
            Room theroom = allRooms[roomIndex];
            // If the random room does not have an enemy -> Add enemy
            // Else -> Pick a new random room.
            // The Entry room can not have an enemy
            if (!theroom.hasEnemy() && !(theroom.getName() == "Entry")) {
                theroom.setEnemy(enemies.get(i));
                i++;
            }
        }
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
            Item theItem = items.get(i);
            Room theroom = allRooms[roomIndex];
            //the key can't be in the locked room!
            if (theroom == endRoom && theItem instanceof Key) {
                continue;
            }

            if (!theroom.hasItemOfthisType(theItem)) { // in the next version: hasItemOfthisType()
                theroom.addItem(theItem);
                i++;// go to the next item
                //DEBUG
                System.out.println(theItem.getName()+ "is placed in "+ theroom.getName());
            }
        }
    }

    private void checkWinGame() {
        if (currentRoom == endRoom && !player.isInfected()) {
            clock.stopTicking();
            int sec = timer - clock.getSecondsLeft(); 
            gameOver = true; 
            pcs.firePropertyChange("gameOver", false, true);
            pcs.firePropertyChange("changeOverlay",null,"GETTOTHACHOPPA.jpg");//change picture in the end
            pcs.firePropertyChange("showWinningText",null, null);
            Highscore score = new Highscore(sec);
        }
    }
    
    public boolean canMixIngredients() {
        if (mixingRoom == currentRoom) {
            for (Item i : items) {
                if (i instanceof Ingredient || i instanceof Recipe) {
                    if (!player.getInventory().hasItem(i)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    public boolean getGameOver() {
		return gameOver; 
	}

	public Room getEntryRoom() {
		return entryRoom; 
	}

	public Room getEndRoom() {
		return endRoom;
	}

	public void setGameOver() {
		gameOver = true;
		clock.stopTicking();
	}
	
	public boolean showLoserMsg() {
		JLabel label = new JLabel("You lose!");
		label.setBorder(new EmptyBorder(30,30,30,30));
		label.setFont(new Font("Dialog",0,30));
		ImageIcon icon = new ImageIcon("resources/image/skull.png");
		int answer = JOptionPane.showOptionDialog
				(null, label, "Game Over!",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
				 icon, new Object[] {"Show highscore"}, "Show highscore");
		if (answer == 0) {
			return true; 
		}
		else return false; 
	}
}
