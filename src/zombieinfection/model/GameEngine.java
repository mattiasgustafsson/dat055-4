/*
 * This class is responsable for the roles of the game and it keeps track of all object i the game world.
 * The class creates random object to pick, enemy to fight with. 
 */
package zombieinfection.model;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Elena Marzi
 */
public class GameEngine {
    private HashMap<String,Room> rooms; 
    private ArrayList<Item> items; 
    //private Collection<Enemy> enemies; 
    private Player player; 
    private Room currentRoom; 
    private int clock; 
    private Room entryRoom; 
    private Random random; 
    
    public GameEngine(){
        createInstances(); 
        createMap();
        createItems(); 
        //createEnemies();
        
    }

    private void createInstances() {
        rooms = new HashMap<String,Room>();
        items = new ArrayList<Item>();
        //enemies= new ArrayList<Enemy>();
        random = new Random();
        player= new Player();
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
    public void goToRoom(String direction){
         Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom != null){
            currentRoom = nextRoom; 
        }
            
       
    }
 
    public void createNewGame(){
        currentRoom = entryRoom; 
        player.setHealth(10);
        randomizeItems();
        player.setInfected(true);
        
    }
    
    //read map from a file. If a bigger map needed just change in the file
    private void readMap(String fileName){
        try {
            Scanner file = new Scanner(new File (fileName));
            while(file.hasNextLine()){
                String dataType = file.nextLine(); 
                if(dataType.equals("exit")){
                    String roomFrom = file.nextLine();
                    String direction = file.nextLine(); 
                    String roomTo = file.nextLine();
                    createExit(roomFrom, roomTo, direction);
                }
                else if(dataType.equals("entry")){
                    String roomName = file.nextLine(); 
                    System.out.println("Start at room "+ roomName);
                    entryRoom = rooms.get(roomName);
                }
                else if(dataType.equals("room")){
                   String roomName = file.nextLine(); 
                   String description = file.nextLine(); 
                   String picture = file.nextLine(); 
                    createRoom(roomName, description);
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"File couldn't open");
        }     
    }

    private void createRoom(String roomName, String description) {
        System.out.println("Creating a room called "+ roomName + " : " + description);
        //create new room
        Room room = new Room(description);
        //put the room in to the map
        rooms.put(roomName,room);
    }

    private void createExit(String roomFrom, String roomTo, String direction) {
        System.out.println("Creating an exit from "+ roomFrom + "till " + roomTo);
        //create exit between rooms
        Room from = rooms.get(roomFrom);
        Room to = rooms.get(roomTo);
        if (from == null) System.out.println("Hittar inte from-rummet");
        if (to == null) System.out.println("Hittar inte to-rummet");
        rooms.get(roomFrom).setExit(direction, rooms.get(roomTo));
    }
    
    
    //read the map from a file
     private void createMap(){
         readMap("map.txt"); 
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

    private void createEnemies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   //slumpa utan att upprepa
    private void randomizeItems() {
        //convert the hashmap to array 
        Room[] allRooms = rooms.values().toArray(new Room[0]);
        int i =0; 
        while(i<items.size()){ 
            //an item placed in a room. if it not already exists. 
            int roomIndex = random.nextInt(allRooms.length);
            if(!allRooms[roomIndex].hasItem()){  //in the next version: hasItemOfthisType() 
                placeItem(allRooms[roomIndex],items.get(i)); 
                i++; 
                System.out.println(roomIndex);
            }    
        }
    }
    
    //att göra: när man går till ett rum ska GameEngine sköta attcken
    //uppdatera UML class: GameEngine och lägga till rätt pilar till diagramm

    private void placeItem(Room room, Item it) {
        room.addItem(it); 
    }
    
}
