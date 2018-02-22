package zombieinfection.view.GUI;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import zombieinfection.model.GameEngine;

/**
 * Displays a clock label formatted as minutes:seconds
 *
 * @author Elena Marzi
 * @version 2018-02-16
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

    /**
     * Listens to changed values for the secondsLeft property and shows the time
     * left, formatted as "MM:SS"
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("secondsLeft")) {
            int seconds = (Integer) evt.getNewValue();
            setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
        }
    }
}
