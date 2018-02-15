/*
 * This class is responsible for the rules of the game and it keeps track of all objects in the game world.
 * The class creates random objects to pick, enemies to fight with. 
 */
package zombieinfection.model;

import java.awt.Font;
import java.beans.*;
import java.io.*;
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
    // private Collection<Enemy> enemies;
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

    // singleton
    private GameEngine() {
        createInstances();
        // read the map from the file
        readMap("map.txt");
        createItems();
        // createEnemies();
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
       if(gameOver)return; 
        Room oldRoom = currentRoom;
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom != null) {
        	if((nextRoom == endRoom) && (!getPlayer().getInventory().containsItem("key"))){
        		return;
        	
        	}
            currentRoom = nextRoom;
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
        clock.startTicking(5 * 60);
        gameOver = false;
        pcs.firePropertyChange("gameOver", true, false);
        pcs.firePropertyChange("currentRoom", null, currentRoom);
        pcs.firePropertyChange("changePicture", null, currentRoom.getPicture());
    }

    // read map from a file. If a bigger map needed just change in the file
    private void readMap(String fileName) {
        try {
            // open the file
            Scanner file = new Scanner(new File(fileName));
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
        } catch (FileNotFoundException ex) {
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
        Item recipe = new Recipe("recept1", 5);
        Item beans = new Ingredient("rotten jelly beans", 1);
        Item pills = new Ingredient("alvedon pills", 1);
        Item acid = new Ingredient("hydrochloric acid", 1);
        Item soda = new Ingredient("caustic soda", 1);
        Item key = new Key("key",0);

        items.add(recipe);
        items.add(beans);
        items.add(pills);
        items.add(acid);
        items.add(soda);
        items.add(key);
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



    private void checkWinGame() {
        if (currentRoom == endRoom && !player.isInfected()) {
            clock.stopTicking();
            int sec = 60*5 - clock.getSecondsLeft(); 
            gameOver = true; 
            pcs.firePropertyChange("gameOver", false, true);
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
            start();// when creating, thread runs
        }

        public int getSecondsLeft() {
			return secondsLeft; 
			
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
                if (ticking) {
                    updateSecondsLeft(secondsLeft - 1);
                    if(getPlayer().isInfected()){
                    getPlayer().setHealth(getPlayer().getHealth()-1);
                    }
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
                
                pcs.firePropertyChange("secondsLeft", oldValue, newValue);
            }
            
            
            if (newValue == 0) {
                ticking = false;
                gameOver = true;
                pcs.firePropertyChange("gameOver", false, true);
                if(showLoserMsg()) {;
                new Highscore(0);
                }
            
            }
        }

		
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
		ImageIcon icon = new ImageIcon(getClass().getResource("skull.png"));
		int answer = JOptionPane.showOptionDialog
				(null, label, "Game Over!",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
				 icon, new Object[] {"Show highscore"}, "Show highscore");
		if (answer == 0) {
			return true; 
		}
		else return false; 
	}

	

   

    // To do: when entering a room, GameEngine handles fight rules.
    // inventory : controller
    // menu bar
    // gameengine kopplas ihop med high score och methoden som räknar när man är dör
}
