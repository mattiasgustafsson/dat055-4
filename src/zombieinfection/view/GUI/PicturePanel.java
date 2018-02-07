package GUI;
import java.awt.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class PicturePanel extends JPanel {
	
	public static BufferedImage myPicture;
	
	public PicturePanel() throws IOException{
		URL url = new URL("https://static-cdn.sr.se/sida/images/4969/3686230_2048_1152.jpg");
		myPicture = ImageIO.read(url);
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		//this.add(picLabel);
		//this.add(new JButton("TOMAT"));
	}
	
	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        g.drawImage(myPicture, 0, 0, 1200, 600, null);
	}
	
}



