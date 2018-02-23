package zombieinfection.model;

import java.awt.Font;
import java.beans.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import zombieinfection.MusicPlayer;
import zombieinfection.view.GUI.*;
import zombieinfection.view.highscore.Highscore;

/**
 * This class is responsible for the rules of the game and it keeps track of all
 * objects in the game world. The class creates random objects to pick, enemies
 * to fight with, food and armour, and creates the map.
 *
 * @author Elena Marzi
 * @version 2018-02-21
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
    private final int timer = 5 * 60;
    private boolean guiLocked;
    private MainFrame gui;

    private static final int MIN_ZOMBIE_COUNT = 3;
    private static final int MAX_ZOMBIE_COUNT = 6;

    private GameEngine() {
        createInstances();
        URL mapUrl = getClass().getClassLoader().getResource("txt/map.txt");
        readMap(mapUrl);
        createItems();
        createEnemies(MAX_ZOMBIE_COUNT);
        pcs = new PropertyChangeSupport(this);
        gameOver = true;
        guiLocked = false;
    }

    /**
     * Adds a PropertyChangeListener to the GameEngine object, and to all other
     * model objects. One call is sufficient to start listening to everything in
     * the model objects.
     *
     * @param listener the PropertyChangeListener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
        clock.addPropertyChangeListener(listener);
        for (Room room : rooms.values()) {
            room.addPropertyChangeListener(listener);
        }
        player.getInventory().addPropertyChangeListener(listener);
    }

    /**
     * @return the GameEngine singleton instance
     */
    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }
    
    /**
     * Creates the main collections and game objects.
     */
    private void createInstances() {
        rooms = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        enemies = new ArrayList<Enemy>();
        random = new Random();
        player = new Player();
        clock = new Clock();
    }

    /**
     * @param frame a reference to the main window
     */
    public void setGui(MainFrame frame) {
        gui = frame;
    }

    /**
     * @return the main window object
     */
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
     * @return the room that the player is currently in
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Moves the player to an adjacent room.
     * 
     * @param direction the direction to go, "north", "south", "east" or "west"
     */
    public void goToRoom(String direction) {
        if (gameOver || guiLocked) {
            return;
        }
        Room oldRoom = currentRoom;
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            handleRoomRules(nextRoom, oldRoom);
        }
    }
    
    /**
     * Handles all the rules for one room. It checks if the player has a key to
     * go in a locked room, it changes the picture, clears the overlay, handles
     * an enemy if there is one, and checks if the game is won.
     * 
     * @param nextRoom the room the player is moving to
     * @param oldRoom the room the player is moving from
     */
    private void handleRoomRules(Room nextRoom, Room oldRoom) {
        if (!canEnterRoom(nextRoom)) {
            pcs.firePropertyChange("lockedRoom", 0, 1);
            return;
        }
        currentRoom = nextRoom;
        pcs.firePropertyChange("currentRoom", oldRoom, currentRoom);
        pcs.firePropertyChange("changeOverlay", null, null); //remove any overlay
        handleEnemyInRoom();
        pcs.firePropertyChange("changePicture", oldRoom.getPicture(), currentRoom.getPicture());
        checkWinGame();
        return;
    }

    /**
     * @param nextRoom the room that the player is entering
     * @return true, if the room is unlocked or the player has a key. otherwise false
     */
    private boolean canEnterRoom(Room nextRoom) {
        if ((nextRoom == endRoom) && (!getPlayer().getInventory().containsItem("key"))) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Checks if the room has an enemy, shows the picture of
     * the enemy, and notifies the GUI about what text to display.
     */
    private void handleEnemyInRoom() {
        if (currentRoom.hasEnemy()) {
            pcs.firePropertyChange("zombie", 0, 2);
            String enemyPic = currentRoom.getEnemy().getName() + ".png";
            pcs.firePropertyChange("changeOverlay", null, enemyPic);
            if (currentRoom.getEnemy().interact()) {
                pcs.firePropertyChange("zombie", 1, currentRoom.getEnemy().getStrength());
            } else {
                pcs.firePropertyChange("zombie", 2, 0);
            }
        }
    }

    /**
     * Starts a thread that handles an attacking zombie by showing a picture
     * and playing a sound effect. During the time of the thread, the GUI is
     * locked.
     * 
     * @param enemy the enemy that attacks the player
     * @param firstDelay number of milliseconds before showing the attack picture
     * @param lastDelay number of milliseconds that the attack picture is shown
     */
    public void startAttackThread(Enemy enemy, int firstDelay, int lastDelay) {
        guiLocked = true;
        //anonymous thread class
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(firstDelay);
                    //show a picture of an attacking zombie
                    pcs.firePropertyChange("changeOverlay", null, enemy.getName() + "attack.png"); // no old value exists
                    MusicPlayer.getInstance().playEffect("growl");
                    enemy.attack();
                    sleep(lastDelay);
                    //take away the picture
                    pcs.firePropertyChange("changeOverlay", null, null);
                } catch (Exception e) {
                } finally {
                    guiLocked = false;
                }
            }
        };
        thread.start();
    }

    /**
     * Resets all game state and prepares a new game to be played.
     * 
     * @param firstGame true, if this is the first game played. Otherwise false.
     */
    public void createNewGame(boolean firstGame) {
        gameOver = false;
        currentRoom = entryRoom;
        resetPlayer();
        randomizeItems();
        placeEnemies();
        fireNewGameProperties();
        if (firstGame) {
            showStartGamePanel();
        }
        clock.startTicking(timer);
    }

    /**
     * Shows the first panel with information about the game.
     */
    private void showStartGamePanel() {
        StartGamePanel start = new StartGamePanel();
        JOptionPane.showOptionDialog(gui, start, "Welcome to ZOMBIE INFECTION GAME",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{
                    "Start game"}, "startGame");
    }

    /**
     * Notifies the GUI about all state changes for a new game.
     */
    private void fireNewGameProperties() {
        pcs.firePropertyChange("gameOver", true, false);
        pcs.firePropertyChange("currentRoom", null, currentRoom);
        pcs.firePropertyChange("changePicture", null, currentRoom.getPicture());
        pcs.firePropertyChange("changeOverlay", null, null);
        pcs.firePropertyChange("secondsLeft", null, timer);
    }

    /**
     * Resets the player object for a new game.
     */
    private void resetPlayer() {
        player.setHealth(player.getMaxHealth());
        player.setInfected(true);
        player.getInventory().removeAll();
    }

    /**
     * Reads the map from a file. This methods create all rooms, exits between
     * rooms, and checks which rooms are the entry room, mix room and the end.
     * 
     * @param fileName url of the text file to parse
     */
    private void readMap(URL fileName) {
        try {
            Scanner file = new Scanner(fileName.openStream());
            while (file.hasNextLine()) {
                String dataType = file.nextLine();
                if (dataType.equals("exit")) {
                    readAndCreateExit(file);
                } else if (dataType.equals("entry")) {
                    readAndSetEntryRoom(file);
                } else if (dataType.equals("mixing room")) {
                    readAndSetMixingRoom(file);
                } else if (dataType.equals("end room")) {
                    readAndSetEndRoom(file);
                } else if (dataType.equals("room")) {
                    readAndCreateRoom(file);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File couldn't open");
        }
    }

    /**
     * Reads three lines from the file: name, description and name of picture
     * file, and create a new room
     * 
     * @param file scanner object for reading from the file
     */
    private void readAndCreateRoom(Scanner file) {
        String roomName = file.nextLine();
        String description = file.nextLine();
        String picture = file.nextLine();
        createRoom(roomName, description, picture);
    }

    /**
     * Reads the name of the room used as the end room.
     * 
     * @param file scanner object for reading from the file
     */
    private void readAndSetEndRoom(Scanner file) {
        String roomName = file.nextLine();
        endRoom = rooms.get(roomName);
    }

    /**
     * Reads the name of the room used as the mixing room.
     * 
     * @param file scanner object for reading from the file
     */
    private void readAndSetMixingRoom(Scanner file) {
        String roomName = file.nextLine();
        mixingRoom = rooms.get(roomName);
    }

    /**
     * Reads the name of the room used as the entry room.
     * 
     * @param file scanner object for reading from the file
     */
    private void readAndSetEntryRoom(Scanner file) {
        String roomName = file.nextLine();
        entryRoom = rooms.get(roomName);
    }

    /**
     * Reads three lines from the file: name of source room, direction to go,
     * name of target room, and create an exit between the rooms
     * 
     * @param file scanner object for reading from the file
     */
    private void readAndCreateExit(Scanner file) {
        String roomFrom = file.nextLine();
        String direction = file.nextLine();
        String roomTo = file.nextLine();
        createExit(roomFrom, roomTo, direction);
    }

    /**
     * Creates a room object and adds it to the map.
     * 
     * @param roomName name of the room
     * @param description description to show the player
     * @param picture filename of room's background picture
     */
    private void createRoom(String roomName, String description, String picture) {
        Room room = new Room(roomName);
        room.setDescription(description);
        room.setPicture(picture);
        rooms.put(roomName, room);
    }

    /**
     * Creates an exit from one room to another
     * 
     * @param roomFrom name of source room
     * @param roomTo name of destination room
     * @param direction direction to go
     */
    private void createExit(String roomFrom, String roomTo, String direction) {
        Room from = rooms.get(roomFrom);
        Room to = rooms.get(roomTo);
        from.setExit(direction, to);
    }

    /**
     * Creates all in-game objects for the player to pick up and interact with.
     */
    private void createItems() {
        createRecipe();
        createIngredients();
        createKey();
        createArmours();
        createFood();
    }

    /**
     * Creates all different types of armour, and adds them to the list of
     * all items.
     */
    private void createArmours() {
        Item shield = new Armour("Shield", 15, 5);
        Item leatherjacket = new Armour("Leather jacket", 5, 8);
        Item helmet = new Armour("Hockey helmet", 1, 12);
        Item platemail = new Armour("Plate mail", 30, 30);
        items.add(platemail);
        items.add(leatherjacket);
        items.add(helmet);
        items.add(shield);
    }

    /**
     * Creates all different types of food, and adds them to the list of
     * all items.
     */
    private void createFood() {
        Food hawaii = new Food("Pizza", 10, 50);
        Food catFood = new Food("Cat food", 10, 8);
        Food haggis = new Food("Haggis", 40, 70);
        Food kebab = new Food("Moldy kebab", 16, -20);
        Food pie = new Food("Pie", 16, 30);
        Food beer = new Food("Porter beer", 2, 20);
        Food poison = new Food("Rat posion", 4, -50);
        items.add(hawaii);
        items.add(catFood);
        items.add(haggis);
        items.add(kebab);
        items.add(beer);
        items.add(poison);
        items.add(pie);
    }

    /**
     * Creates the key, and adds it to the list of all items.
     */
    private void createKey() {
        Item key = new Key("key", 2);
        items.add(key);
    }

    /**
     * Creates all different types of ingredients, and adds them to the list of
     * all items.
     */
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

    /**
     * Creates the recie, and adds it to the list of all items.
     */
    private void createRecipe() {
        Item recipe = new Recipe("Recipe", 0);
        items.add(recipe);
    }

    /**
     * Chooses a random number of enemies between a minimum and a maximum number
     * and places them in random rooms. First removes all enemies from the map.
     */
    private void placeEnemies() {
        int actualNoOfEnemies = random.nextInt(MAX_ZOMBIE_COUNT - MIN_ZOMBIE_COUNT) + MIN_ZOMBIE_COUNT;
        Room[] allRooms = rooms.values().toArray(new Room[0]);
        removeAllEnemies(allRooms);
        randomizeEnemies(actualNoOfEnemies, allRooms);
    }

    /**
     * Removes enemies from all rooms in the map.
     * 
     * @param allRooms array of all rooms
     */
    private void removeAllEnemies(Room[] allRooms) {
        for (Room room : allRooms) {
            room.setHasEnemy(false);
        }
    }

    /**
     * Places a given number of enemies into randomly selected rooms.
     * The Entry room and the End room can not have an enemy.
     * 
     * @param actualNoOfEnemies number of enemies to place
     * @param allRooms array of all rooms
     */
    private void randomizeEnemies(int actualNoOfEnemies, Room[] allRooms) {
        int i = 0;
        while (i < actualNoOfEnemies) {
            Room theroom = getRandomRoom(allRooms);
            if (!theroom.hasEnemy() && theroom != entryRoom && theroom != endRoom) {
                theroom.setEnemy(new Enemy("zombie" + (i % 3)));
                i++;
            }
        }
    }

    /**
     * @param allRooms array of all rooms in the map
     * @return a randomly selected room from the array
     */
    private Room getRandomRoom(Room[] allRooms) {
        int roomIndex = random.nextInt(allRooms.length);
        Room theroom = allRooms[roomIndex];
        return theroom;
    }

    /**
     * Creates a number of enemies to be placed in rooms later. Each enemy is
     * given a name, that is used to choose two pictures to show when attacking.
     * 
     * @param numberOfEnemies the number of enemies to create
     */
    private void createEnemies(int numberOfEnemies) {
        for (int i = 0; i < numberOfEnemies; i++) {
            enemies.add(new Enemy("zombie" + Integer.toString(i % 3)));
        }
    }

    /**
     * Places all available items into different rooms, without repeating the
     * same item type in the same room. The key cannot be inside the locked
     * room (end room).
     */
    private void randomizeItems() {
        Room[] allRooms = rooms.values().toArray(new Room[0]);
        clearItems(allRooms);
        int i = 0;
        while (i < items.size()) {
            Room theroom = getRandomRoom(allRooms);
            Item theItem = items.get(i);
            if (theroom == endRoom && theItem instanceof Key) {
                continue;
            }
            if (!theroom.hasItemOfthisType(theItem)) {
                theroom.addItem(theItem);
                i++;
            }
        }
    }

    /**
     * Removes all items from all rooms.
     * 
     * @param allRooms array of all rooms in the map
     */
    private void clearItems(Room[] allRooms) {
        for (Room room : allRooms) {
            room.removeItems();
        }
    }

    /**
     * If the player has won the game, stop the game, notify the GUI to show
     * the end picture, play a sound effect and show the Highscore window. The
     * player wins when they are in the End room, and is not infected.
     */
    private void checkWinGame() {
        if (currentRoom == endRoom && !player.isInfected()) {
            clock.stopTicking();
            int score = timer - clock.getSecondsLeft();
            gameOver = true;
            pcs.firePropertyChange("gameOver", false, true);
            pcs.firePropertyChange("changeOverlay", null, "GETTOTHACHOPPA.jpg");
            pcs.firePropertyChange("showWinningText", 0, 1);
            MusicPlayer.getInstance().playEffect("chopper");
            new Highscore(score);
        }
    }

    /**
     * @return true, if the player is in the mixing room, has the recipe and
     * all the ingredients. Otherwise, false
     */
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

    /**
     * @return true if the game is over, for any reason
     */
    public boolean getGameOver() {
        return gameOver;
    }

    /**
     * @return the entry room
     */
    public Room getEntryRoom() {
        return entryRoom;
    }

    /**
     * @return the end room
     */
    public Room getEndRoom() {
        return endRoom;
    }

    /**
     * @return the list of available enemies
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Ends the game and the clock
     */
    public void setGameOver() {
        gameOver = true;
        clock.stopTicking();
        pcs.firePropertyChange("gameOver", false, true);
    }

    /**
     * Shows the "Game over" message for when the player dies. Lets the player
     * select whether or not to show the Highscore window.
     * 
     * @return true, if player wants to show Highscore window
     */
    public boolean showLoserMsg() {
        JLabel label = new JLabel("You lose!");
        label.setBorder(new EmptyBorder(30, 30, 30, 30));
        label.setFont(new Font("Dialog", 0, 30));
        ImageIcon icon = new ImageIcon("resources/image/skull.png");
        MusicPlayer.getInstance().playEffect("laugh");
        int answer = JOptionPane.showOptionDialog(null, label, "Game Over!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                icon, new Object[]{"Show highscore"}, "Show highscore");
        if (answer == 0) {
            return true;
        } else {
            return false;
        }
    }
}
