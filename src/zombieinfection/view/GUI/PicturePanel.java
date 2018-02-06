package GUI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PicturePanel extends JPanel {
	public PicturePanel() throws IOException{
		URL url = new URL("https://static-cdn.sr.se/sida/images/4969/3686230_2048_1152.jpg?preset=768x432");
		BufferedImage myPicture = ImageIO.read(url);
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		this.add(picLabel);
	}
}
