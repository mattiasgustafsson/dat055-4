/*
 *Create a clock
 */
package zombieinfection.view.GUI;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import zombieinfection.model.GameEngine;

/**
 *
 * @author Elena Marzi
 */
public class ClockPanel extends JLabel implements PropertyChangeListener {

    public ClockPanel() {
        GameEngine.getInstance().addPropertyChangeListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFont(new Font("Monospaced", Font.BOLD, 30));
        setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("secondsLeft")) {
            int seconds = (Integer) evt.getNewValue();
            setText(String.format("%02d:%02d", seconds/60, seconds%60));
        }
    }

}
