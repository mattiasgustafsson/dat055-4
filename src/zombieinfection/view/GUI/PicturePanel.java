package zombieinfection.view.GUI;
import java.awt.*;
import javax.swing.*;

import zombieinfection.model.GameEngine;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class PicturePanel extends JPanel implements PropertyChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8460419674102135030L;
	
	public static BufferedImage myPicture;
	
		
	public PicturePanel() throws IOException{
		GameEngine.getInstance().addPropertyChangeListener(this);
		URL entryPic = getClass().getClassLoader().getResource("image/entry.jpg");
		myPicture = ImageIO.read(entryPic);
	}
	
	
	public void changeBackground(String pic) throws IOException{
		URL picture = getClass().getClassLoader().getResource("image/"+ pic);
		myPicture = ImageIO.read(picture);
		repaint();
	}
	
	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        g.drawImage(myPicture, 0, 0, 1200, 600, null);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("changePicture")){
			try {
					changeBackground((String)evt.getNewValue());
				} catch (IOException e) {
					e.printStackTrace();
			}
			
		}
		
	}
	
}



