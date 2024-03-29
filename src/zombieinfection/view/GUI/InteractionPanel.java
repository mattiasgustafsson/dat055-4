package zombieinfection.view.GUI;

import java.awt.*;
import javax.swing.*;

import zombieinfection.model.*;
import zombieinfection.controller.*;

/**
 * The InteractionPanel class represents the interaction panel of the GUI
 *
 * @author Daniel Duvanå
 * @version 2018-02-23
 */
public class InteractionPanel extends JPanel {

    private static final long serialVersionUID = 7584029056674325609L;

    /**
     * Creates the InteractionPanel
     */
    public InteractionPanel() {
        Inventory inv = GameEngine.getInstance().getPlayer().getInventory();
        this.setLayout(new GridLayout(1, 3));
        this.setBounds(0, 0, 800, 120);
        this.setPreferredSize(new Dimension(800, 120));
        this.setBackground(Color.BLACK);
        InventoryPanel ip = new InventoryPanel(new InventoryController(inv));
        inv.addPropertyChangeListener(ip);
        this.add(ip);
        this.add(new TextAndHp());
        this.add(new NavigationPanel());
    }
}
