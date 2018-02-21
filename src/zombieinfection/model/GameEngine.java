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

import zombieinfection.MusicPlayer;
import zombieinfection.view.GUI.MainFrame;
import zombieinfection.view.GUI.StartGamePanel;
import zombieinfection.view.highscore.Highscore;

/**
 * @author Elena Marzi
 */
public class GameEngine {

    private HashMap<String, Room> rooms;
    private ArrayList<Item> items;
    private ArrayList<Enemy> enemies;
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
	private boolean guiLocked;           
	private MainFrame gui;
	
	
	private static final int MAX_ZOMBIE_COUNT = 5;

    // singleton
    private GameEngine() {
        createInstances();
        // read the map from the file
        URL mapUrl = getClass().getClassLoader().getResource("txt/map.txt");
        readMap(mapUrl);
        createItems();
        createEnemies(MAX_ZOMBIE_COUNT);
        pcs = new PropertyChangeSupport(this);
        gameOver = true; 
        guiLocked = false; 
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
    
    public void setGui(MainFrame frame) {
    	gui = frame; 
    }
    
    public MainFrame getGui() {
    	return gui; 
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

   
    public void goToRoom(String direction) {
        if(gameOver || guiLocked)return; 
         Room oldRoom = currentRoom;
         Room nextRoom = currentRoom.getExit(direction);
         
         if (nextRoom != null) {
         	if((nextRoom == endRoom) && (!getPlayer().getInventory().containsItem("key"))){
         		pcs.firePropertyChange("lockedRoom", 0, 1);
         		return;
         	}
             currentRoom = nextRoom;
             pcs.firePropertyChange("currentRoom", oldRoom, currentRoom);
             pcs.firePropertyChange("changeOverlay", null, null); //remove any overlay
             if (currentRoom.hasEnemy()) {
            	 pcs.firePropertyChange("zombie", 0, 2);
            	 String enemyPic = currentRoom.getEnemy().getName() + ".png";
                 pcs.firePropertyChange("changeOverlay", null, enemyPic);
                 
                 if (currentRoom.getEnemy().interact())
                	 pcs.firePropertyChange("zombie", 1, currentRoom.getEnemy().getStrength());
                 else
                	 pcs.firePropertyChange("zombie", 2, 0);
             }
             pcs.firePropertyChange("changePicture", oldRoom.getPicture(), currentRoom.getPicture());
             checkWinGame();
         }

     }
    //gui is locked when zombie attacks and a growl plays
    public void startAttackThread(String filename, int firstDelay, int lastDelay){
        guiLocked = true; 
        //anonymous thread class
        Thread thread = new Thread (){
            @Override
          public void run(){
              try{
                  //paus
                  sleep(firstDelay);
                  //show a picture of an attacking zombie
                  pcs.firePropertyChange("changeOverlay", null, filename); // no old value exists
                  //play sound effect
                  MusicPlayer.getInstance().playEffect("growl");
                  //paus again
                  sleep(lastDelay);
                  //take away the picture
                  pcs.firePropertyChange("changeOverlay", null, null);
              }catch(Exception e){
              }
              finally{
                  guiLocked = false; 
              }
              
          }  
        };
        thread.start();
    }

    public void createNewGame(boolean firstGame) {
    	 
        currentRoom = entryRoom;
        player.setHealth(player.getMaxHealth());
        randomizeItems();
        placeEnemies(MAX_ZOMBIE_COUNT);
        player.setInfected(true);
        gameOver = false;
        pcs.firePropertyChange("gameOver", true, false);
        pcs.firePropertyChange("currentRoom", null, currentRoom);
        pcs.firePropertyChange("changePicture", null, currentRoom.getPicture());
        pcs.firePropertyChange("changeOverlay", null, null);
        pcs.firePropertyChange("secondsLeft", null, 5*60);
        //MusicPlayer.getInstance().playEffect("breathing");
        player.getInventory().removeAll(); 
        if (firstGame) {
        StartGamePanel start = new StartGamePanel();
        JOptionPane.showOptionDialog(gui, start, "Welcome to ZOMBIE INFECTION GAME",
        		JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, new Object[] {
        				"Start game"}, "startGame");
        
        
        }
        clock.startTicking(timer);
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
           
        // create endRoom. Method is in Room class
        from.setExit(direction, to);
    }

    // items into a file in the next version...
    private void createItems() {
        createRecipe();
        createIngredients();
        createKey();
        createArmours();
        createFood();
    }
    
    private void createArmours() {
        Item platemail = new Armour("Plate mail", 30, 30);
        Item leatherjacket = new Armour("Leather jacket", 5, 8);
        Item wifebeater = new Armour("Wife beater", 1, 12);
        Item mail = new Armour("Mail", 15, 5);
        
        items.add(platemail);
        items.add(leatherjacket);
        items.add(wifebeater);
        items.add(mail);
    }
    
    private void createFood(){
        Food hawaii = new Food("Pizza", 10,50);
        Food catFood = new Food("Cat food",10,8);
        Food haggis = new Food("Haggis", 40,70);
        Food kebab = new Food ("Moldy kebab", 16, -20);
        Food pie = new Food ("Pie", 16,30);
        Food beer = new Food("Porter beer", 2, 20);
        Food mushroom = new Food("Rat posion", 4, -50);
       
        items.add(hawaii);
        items.add(catFood);
        items.add(haggis);
        items.add(kebab);
        items.add(beer);
        items.add(mushroom);
        items.add(pie);
    }

	private void createKey() {
		Item key = new Key("key",2);
        items.add(key);
	}

	private void createIngredients() {
		Item beans = new Ingredient("Rotten jelly beans", 5);
        Item pills = new Ingredient("Alvedon pills", 1);
        Item acid = new Ingredient("Hydrochloric acid", 3);
        Item soda = new Ingredient("Caustic soda", 8);
        items.add(beans);
        items.add(pills);
        items.add(acid);
        items.add(soda);
	}

	private void createRecipe() {
		Item recipe = new Recipe("Recipe", 0);
        items.add(recipe);
	}
    
    /**
     * Creates a random number of enemies between 1 and the given maximum number
     * of enemies and adds the enemies to random rooms.
     * A room can at most 1 enemy.
     * @param maxNoOfEnemies
     */
    private void placeEnemies(int maxNoOfEnemies) {
        // Randomize the actual number of enemies
        int actualNoOfEnemies = random.nextInt(maxNoOfEnemies - 1) + 2;
        
        // Create array from values of HashMap rooms
        Room[] allRooms = rooms.values().toArray(new Room[0]);
        // Remove all enemies first
        for (Room room : allRooms) {
        	room.setHasEnemy(false);
        }
        int i = 0; // Iteration variable
        while (i < actualNoOfEnemies) {
            // Pick a random room
            int roomIndex = random.nextInt(allRooms.length);
            Room theroom = allRooms[roomIndex];
            // If the random room does not have an enemy -> Add enemy
            // Else -> Pick a new random room.
            // The Entry room can not have an enemy
            if (!theroom.hasEnemy() && !(theroom.getName() == "Entry")) {
                theroom.setEnemy(new Enemy("zombie" + (i % 3)));
                
                i++;
            }
        }
    }

	private void createEnemies(int maxNoOfEnemies) {
		// Create the maximum number of enemies and add to list of enemies
        for (int i = 0; i < maxNoOfEnemies; i++) {
            enemies.add(new Enemy("zombie" + Integer.toString(i%3)));
        }
	}

    // Randomize items without repeating the same item type in the same room
    private void randomizeItems() {
  
        // convert the hashmap to array
        Room[] allRooms = rooms.values().toArray(new Room[0]);
        for(Room room: allRooms) {
        	room.removeItems();
        }
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
            pcs.firePropertyChange("showWinningText",0, 1);
            MusicPlayer.getInstance().playEffect("chopper");
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
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
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
		MusicPlayer.getInstance().playEffect("laugh");
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
