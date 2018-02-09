package zombieinfection.view.GUI;
import java.awt.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;


public class PicturePanel extends JPanel implements PropertyChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8460419674102135030L;
	public static BufferedImage myPicture;
	
	public PicturePanel() throws IOException{
		changeBackground("https://static-cdn.sr.se/sida/images/4969/3686230_2048_1152.jpg");
			
	}
	
	public void changeBackground(String URL) throws IOException{
		URL url = new URL(URL);
		myPicture = ImageIO.read(url);
		//JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		
		
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
				changeBackground("http://hawaiipizzeria.com/uploads/images/pizza_large.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}



