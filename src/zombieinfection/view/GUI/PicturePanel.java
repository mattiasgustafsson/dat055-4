package zombieinfection.view.GUI;
import java.awt.*;
import javax.swing.*;

import zombieinfection.model.GameEngine;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
		URL url = new URL("https://static-cdn.sr.se/sida/images/4969/3686230_2048_1152.jpg");
		myPicture = ImageIO.read(url);
	}
	
	
	public void changeBackground(String URL) throws IOException{
		URL url = new URL(URL);
		myPicture = ImageIO.read(url);
		revalidate();
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
				System.out.println("heheheeh NU ÄR VI HÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄR");
				changeBackground((String)evt.getNewValue());
				} catch (IOException e) {
					System.out.println("heheheeh NU ÄR VI där");
				e.printStackTrace();
			}
			
		}
		
	}
	
}



