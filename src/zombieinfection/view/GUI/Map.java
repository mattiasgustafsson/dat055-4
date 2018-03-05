package zombieinfection.view.GUI;

import java.awt.*;
import java.util.HashSet;
import javax.swing.JPanel;
import zombieinfection.model.Room;

/**
 * Draws the map of all rooms in the game.
 *
 * @author Elena Marzi
 * @version 2018-02-17
 */
public class Map extends JPanel {

    private Room entry;
    private Room currentRoom;
    private Room endRoom;

    private static final int ROOM_SIZE = 40;
    private static final int EXIT_SIZE = 8;
    private static final int ROOM_DISTANCE = ROOM_SIZE + EXIT_SIZE;
    private static final int EXIT_DISTANCE = (ROOM_SIZE - EXIT_SIZE) / 2;
    private static final Color CURRENT_COLOR = Color.decode("#FF0040");
    private static final Color END_COLOR = Color.decode("#04B45F");

    /**
     * Creates a new Map panel.
     *
     * @param entry the first room in the map
     * @param currentRoom the room where the player is
     * @param endRoom the end room
     */
    public Map(Room entry, Room currentRoom, Room endRoom) {
        this.entry = entry;
        this.currentRoom = currentRoom;
        this.endRoom = endRoom;
        setPreferredSize(new Dimension(290, 350));
    }

    /**
     * Paints the content of the the map onto the panel. Draws the first room by
     * calling the recursive method drawRooms.
     *
     * @param g the Graphics object
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        HashSet<Room> drawnRooms = new HashSet<Room>();
        drawRooms(g, entry, 60, 260, drawnRooms);
    }

    /**
     * Method that draws one room, and all its neighbours, recursively. Keeps
     * track of what rooms have already been drawn, to not get stuck in an
     * endless recursion.
     *
     * @param g the Graphics object
     * @param room the room to draw
     * @param x the x coordinate to place the room in the window
     * @param y the y coordinate to place the room in the window
     * @param drawnRooms a set of rooms already drawn
     */
    private void drawRooms(Graphics g, Room room, int x, int y, HashSet<Room> drawnRooms) {
        // if this room is already drawn, exit immediately!
        if (drawnRooms.contains(room)) {
            return;
        }
        drawSingleRoom(room, g, x, y, drawnRooms);
        drawNeighbours(room, g, x, y, drawnRooms);
    }

    /**
     * Draws doorways between one room and all its neighbours, and recursively
     * draws all neighbour rooms.
     *
     * @param room the room to draw
     * @param g the Graphics object
     * @param x the x coordinate to place the room in the window
     * @param y the y coordinate to place the room in the window
     * @param drawnRooms a set of rooms already drawn
     */
    private void drawNeighbours(Room room, Graphics g, int x, int y, HashSet<Room> drawnRooms) {
        if (room.hasExit("north")) {
            g.fillRect(x + EXIT_DISTANCE, y - EXIT_SIZE, EXIT_SIZE, EXIT_SIZE);
            drawRooms(g, room.getExit("north"), x, y - ROOM_DISTANCE, drawnRooms);
        }
        if (room.hasExit("south")) {
            g.fillRect(x + EXIT_DISTANCE, y + ROOM_SIZE, EXIT_SIZE, EXIT_SIZE);
            drawRooms(g, room.getExit("south"), x, y + ROOM_DISTANCE, drawnRooms);
        }
        if (room.hasExit("east")) {
            g.fillRect(x + ROOM_SIZE, y + EXIT_DISTANCE, EXIT_SIZE, EXIT_SIZE);
            drawRooms(g, room.getExit("east"), x + ROOM_DISTANCE, y, drawnRooms);
        }
        if (room.hasExit("west")) {
            g.fillRect(x - EXIT_SIZE, y + EXIT_DISTANCE, EXIT_SIZE, EXIT_SIZE);
            drawRooms(g, room.getExit("west"), x - ROOM_DISTANCE, y, drawnRooms);
        }
    }

    /**
     * Draws one room in the window. If the room is the player's current room,
     * the room is drawn red. If the room is the end room, it is drawn green.
     * Otherwise, it is black.
     *
     * @param room the room to draw
     * @param g the Graphics object
     * @param x the x coordinate to place the room in the window
     * @param y the y coordinate to place the room in the window
     * @param drawnRooms a set of rooms already drawn
     */
    private void drawSingleRoom(Room room, Graphics g, int x, int y, HashSet<Room> drawnRooms) {
        if (room == currentRoom) {
            g.setColor(CURRENT_COLOR);
        } else if (room == endRoom) {
            g.setColor(END_COLOR);
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillRect(x, y, ROOM_SIZE, ROOM_SIZE);
        drawnRooms.add(room);
        g.setColor(Color.BLACK);
    }

}
