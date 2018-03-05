package zombieinfection.view.GUI;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.*;

import zombieinfection.controller.NavigationController;

/**
 * This class is responsible for the main JFrame that cointains the different
 * panels for the game.
 *
 * @author David.S
 * @version 2018-02-23
 */
public class MainFrame extends JFrame {

    /**
     * Creates the main frame containing all the panels.
     */
    public MainFrame() {
        MakeFrame();

    }

    /**
     * Adds different panels to the main JFrame as well as sets the size and
     * scaling for some of them.
     */
    private void MakeFrame() {
        setTitle("Zombie infection");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(800, 550));
        addKeyListener(new NavigationController());

        JLayeredPane centrePane = new JLayeredPane();
        centrePane.setPreferredSize(new Dimension(1200, 605));

        PicturePanel roomPicture = new PicturePanel();

        //create a clock display
        ClockPanel clock = new ClockPanel();
        //clock is shown above the picture panel
        centrePane.add(clock, new Integer(1));
        centrePane.add(roomPicture, new Integer(0));

        clock.setBounds(0, 0, 100, 40);

        centrePane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = centrePane.getSize();
                roomPicture.setBounds(0, 0, size.width, size.height - 5);
            }
        });

        InteractionPanel ip = new InteractionPanel();
        ip.setPreferredSize(new Dimension(1200, 215));

        this.add(ip, BorderLayout.SOUTH);
        this.add(centrePane, BorderLayout.CENTER);

        setJMenuBar(new BarMenu());
        pack();
        setVisible(true);

    }

}
