package zombieinfection.view.GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import zombieinfection.model.GameEngine;

public class ZombiePanel extends JPanel implements PropertyChangeListener {
    
    private BufferedImage zombieImage;

    public ZombiePanel() {
        try {
            zombieImage = ImageIO.read(new File("zombie.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(zombieImage, 0, 0, 120, 60, this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("zombieInRoom")) {
        }
    }
}
