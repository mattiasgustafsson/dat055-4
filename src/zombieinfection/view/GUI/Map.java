package zombieinfection.view.GUI;

import java.awt.*;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import zombieinfection.model.Room;


	public class Map extends JPanel{
	    
	    private Room entry;
	    private Room currentRoom;
	    private Room endRoom;

	    public Map(Room entry, Room currentRoom, Room endRoom){
	        this.entry = entry;
	        this.currentRoom = currentRoom;
	        this.endRoom = endRoom;
	        
	        setPreferredSize(new Dimension(450,450));
	        
	    }
	    
	    @Override
	    public void paint(Graphics g){
	        super.paint(g);
	        g.setColor(Color.BLACK);
	        HashSet<Room> drawnRooms = new HashSet<Room>(); 
	        drawRooms(g, entry, 200, 300, drawnRooms);
	    }
        
        //draw one room and all the neighbours 
	    private void drawRooms(Graphics g, Room room, int x, int y, HashSet<Room> drawnRooms) {
	        // if this room is already drawn, exit immediately! otherwise: eternal loop...
	        if (drawnRooms.contains(room)) return;
	        
	        drawSingleRoom(room, g, x, y, drawnRooms); 
            drawNeighbours(room, g, x, y, drawnRooms);
	    }

       private void drawNeighbours(Room room, Graphics g, int x, int y, HashSet<Room> drawnRooms) {
        // for each possible direction, check if there is an exit
        if (room.hasExit("north")) {
            // draw the "door"(5 pixels)
            g.fillRect(x + 15, y - 5, 5, 5);
            // then call drawOneRoom recursively with the next room's position to drawn it 
            drawRooms(g, room.getExit("north"), x, y - 40, drawnRooms);
        }
        
        if (room.hasExit("south")) {
            g.fillRect(x + 15, y + 35, 5, 5);
            drawRooms(g, room.getExit("south"), x, y + 40, drawnRooms);
        }
        
        if (room.hasExit("east")) {
            g.fillRect(x + 35, y + 15, 5, 5);
            drawRooms(g, room.getExit("east"), x + 40, y, drawnRooms);
        }
        
        if (room.hasExit("west")) {
            g.fillRect(x - 5, y + 15, 5, 5);
            drawRooms(g, room.getExit("west"), x - 40, y, drawnRooms);
        }
    }

    private void drawSingleRoom(Room room, Graphics g, int x, int y, HashSet<Room> drawnRooms) throws NumberFormatException {
        // the current room is painted red, other rooms are painted black
        if (room == currentRoom) {
            g.setColor(Color.decode("#FF0040"));
        }
        else if (room == endRoom) {
            g.setColor(Color.decode("#04B45F"));
        }
        else {
            g.setColor(Color.BLACK);
        }
        // draw the room - just a simple square
        g.fillRect(x, y, 35, 35);
        // keep track of which rooms have been drawn
        drawnRooms.add(room);
        // draw the exits - start by setting a black color
        g.setColor(Color.BLACK);
    }

}
