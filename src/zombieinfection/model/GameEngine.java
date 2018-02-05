/*
 * This class is responsible for the rules of the game and it keeps track of all objects in the game world.
 * The class creates random objects to pick, enemies to fight with. 
 */
package zombieinfection.model;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * @author Elena Marzi
 */
public class GameEngine {

    private HashMap<String, Room> rooms;
    private ArrayList<Item> items;
    //private Collection<Enemy> enemies; 
    private Player player;
    private Room currentRoom;
    private int clock;
    private Room entryRoom;
    private Random random;

    public GameEngine() {
        createInstances();
        //read the map from the file
        readMap("map.txt");
        createItems();
        //createEnemies();

    }

    private void createInstances() {
        rooms = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        //enemies= new ArrayList<Enemy>();
        random = new Random();
        player = new Player();
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

    /**
     * @return the clock
     */
    public int getClock() {
        return clock;
    }

    //den kommer att anropas av en kontroller
    public void goToRoom(String direction) {
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom != null) {
            currentRoom = nextRoom;
        }
    }

    public void createNewGame() {
        currentRoom = entryRoom;
        player.setHealth(10);
        randomizeItems();
        player.setInfected(true);
    }

    //read map from a file. If a bigger map needed just change in the file
    private void readMap(String fileName) {
        try {
            //open the file
            Scanner file = new Scanner(new File(fileName));
            while (file.hasNextLine()) {
                //get the type of the information to handle: room, exit, entry
                String dataType = file.nextLine();
                if (dataType.equals("exit")) {
                    String roomFrom = file.nextLine();
                    String direction = file.nextLine();
                    String roomTo = file.nextLine();
                    createExit(roomFrom, roomTo, direction);
                    //need to know where player starts
                } else if (dataType.equals("entry")) {
                    String roomName = file.nextLine();
                    // DEBUG info
                    System.out.println("Start at room " + roomName);
                    entryRoom = rooms.get(roomName);
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
        //create new room
        Room room = new Room(description);
        //put the room in to the map: (key is the name) to find entry room
        rooms.put(roomName, room);
    }
    
     //create exit between rooms
    private void createExit(String roomFrom, String roomTo, String direction) {
        // DEBUG info
        System.out.println("Creating an exit from " + roomFrom + "till " + roomTo);
        //get reference of the rooms to connect
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
        //create exit. Method is in Room class
        from.setExit(direction, to);
    }

  

    //items into a file in the next version...
    private void createItems() {
        Item recipe = new Recipe();
        Item beans = new Ingredient("rotten jelly beans");
        Item pills = new Ingredient("alvedon pills");
        Item acid = new Ingredient("hydrochloric acid");
        Item soda = new Ingredient("caustic soda");

        items.add(recipe);
        items.add(beans);
        items.add(pills);
        items.add(acid);
        items.add(soda);
    }

    // To do: Create enemies and place them randomly in different rooms
    private void createEnemies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Randomize items without repeating the same item type in the same room
    private void randomizeItems() {
        //convert the hashmap to array 
        Room[] allRooms = rooms.values().toArray(new Room[0]);
        int i = 0;
        //loop through items
        while (i < items.size()) {
            //an item is placed in a room. if it not already exists. 
            int roomIndex = random.nextInt(allRooms.length);
            Room theroom = allRooms[roomIndex];
            
            if (!theroom.hasItem()) {  //in the next version: hasItemOfthisType() 
                theroom.addItem(items.get(i));
                i++;//go to the next item
                System.out.println(roomIndex);
            }
        }
    }

    //To do: when entering a room, GameEngine handles fight rules.
    //clock: thread. class clock som ärvar fråm Thread i metoden run loopa
    //while (!isInterrupted()){
    //sleep (1000); minska instans variabel med 1 och om time left = 0 är död meddela och uppdatera gui()
    //kanske inner klass
   
}
