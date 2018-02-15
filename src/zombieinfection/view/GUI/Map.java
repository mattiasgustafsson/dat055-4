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
	        drawOneRoom(g, entry, 200, 300, drawnRooms);
	    }

		private void drawOneRoom(Graphics g, Room room, int x, int y, HashSet<Room> drawnRooms) {
			 // if this room is already drawn, exit immediately!
			if (drawnRooms.contains(room));
			
			//change color for the current room
			if (room == currentRoom) {
				g.setColor(Color.RED);
			}
			
			//change color for the end room
			else if (room == endRoom) {
				g.setColor(Color.GREEN);
			}
			else {
				g.setColor(Color.BLACK);
			}
			//recursive method to paint rooms
			
			
			
			
		}

}
