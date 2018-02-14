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
	        
//	        setBackground(Color.white);
	        setPreferredSize(new Dimension(450,450));
	        
	    }
	    
	    @Override
	    public void paint(Graphics g){
	        super.paint(g);
	        g.setColor(Color.BLACK);
	        drawOneRoom(g, entry, 200, 300);
	    }

		private void drawOneRoom(Graphics g, Room room, int x, int y) {
			// TODO Auto-generated method stub
			
		}

}
