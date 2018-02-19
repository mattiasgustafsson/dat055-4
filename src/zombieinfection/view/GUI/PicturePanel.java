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

	private BufferedImage background;
	private BufferedImage overlay;

	public PicturePanel() {
		GameEngine.getInstance().addPropertyChangeListener(this);
	}

	public void changeBackground(String pic) throws IOException {
		URL picture = getClass().getClassLoader().getResource("image/" + pic);
		background = ImageIO.read(picture);
		repaint();
	}

	// if null, no overlay
	public void changeOverlay(String pic) throws IOException {
		if (pic != null) {
			URL picture = getClass().getClassLoader().getResource("image/" + pic);
			overlay = ImageIO.read(picture);
		} else {
			overlay = null;
		}
		repaint();
	}

	// paint into the window background first and then overlay
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 1200, 600, null);
		g.drawImage(overlay, 0, 0, 1200, 600, null);
	}

	//when a overlay shows fires a PropertyChange with a name of "changeOverlay"
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("changePicture")) {
			try {
				changeBackground((String) evt.getNewValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (evt.getPropertyName().equals("changeOverlay")) {
			try {
				changeOverlay((String) evt.getNewValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
