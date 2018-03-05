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

/**
 * This class is responsible for drawing the background picture as well as the
 * zombie overlay picture.
 *
 * @author David.S
 * @version 2018-02-23
 */
public class PicturePanel extends JPanel implements PropertyChangeListener {

    private static final long serialVersionUID = -8460419674102135030L;

    private BufferedImage background;
    private BufferedImage overlay;

    /**
     * Adds property change listener to this class.
     */
    public PicturePanel() {
        GameEngine.getInstance().addPropertyChangeListener(this);
    }

    /**
     * Reads the background picture from its path and then repaints it.
     *
     * @param pic the relative path of the background picture.
     * @throws IOException
     */
    public void changeBackground(String pic) throws IOException {
        URL picture = getClass().getClassLoader().getResource("image/" + pic);
        background = ImageIO.read(picture);
        repaint();
    }

    /**
     * Reads the overlay picture from its path and then repaints it.
     *
     * @param pic the relative path of the overlay picture.
     * @throws IOException
     */
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

    /**
     * Paints the background and overlay picture, scales to screen size.
     *
     * @param Gets a BufferedImage.
     */
    // paint into the window background first and then overlay
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paint graphics the actual size of the picture panel
        Dimension size = this.getSize();
        g.drawImage(background, 0, 0, size.width, size.height, null);
        g.drawImage(overlay, 0, 0, size.width, size.height, null);
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
