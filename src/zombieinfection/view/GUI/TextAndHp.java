package zombieinfection.view.GUI;

import java.awt.*;
import java.beans.*;
import javax.swing.*;

import zombieinfection.model.*;

/**
 * The TextAndHp class represents the text output panel and health points bar of
 * the GUI
 *
 * @author Daniel Duvanå
 * @version 2018-02-23
 */
public class TextAndHp extends JPanel implements PropertyChangeListener {

    private static final long serialVersionUID = 7875681010024987618L;
    private JProgressBar pb = new JProgressBar();
    private JTextArea text = new JTextArea();

    /**
     * Creates the TextAndHp object.
     */
    public TextAndHp() {
        this.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(Color.BLACK);

        //Progress bar
        pb.setValue(100);
        pb.setMinimum(0);
        pb.setMaximum(100);
        pb.setForeground(Color.red);

        northPanel.add(pb, BorderLayout.CENTER);

        southPanel.setBackground(Color.GREEN);
        //TextArea 
        text = new JTextArea("");
        text.setFont(new Font("Verdena", 0, 16));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setFocusable(false);
        //ScrollPane
        JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setPreferredSize(new Dimension(100, 195));

        southPanel.add(scroll);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);

        GameEngine.getInstance().addPropertyChangeListener(this);
        GameEngine.getInstance().getPlayer().addPropertyChangeListener(this);

        for (Enemy enemy : GameEngine.getInstance().getEnemies()) {
            enemy.addPropertyChangeListener(this);
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("currentRoom")) {
            Room room = (Room) evt.getNewValue();
            text.setText(room.getDescription() + "\n");
        } else if (evt.getPropertyName().equals("pickedUpItem")) {
            String itemName = (String) evt.getNewValue();
            if (!(itemName.equals("Recipe"))) {
                text.setText(text.getText() + "\n \n" + "You picked up " + itemName + " and placed it in your inventory.");
            }
        } else if (evt.getPropertyName().equals("mixing")) {
            if ((Boolean) evt.getNewValue() == true) {
                text.setText(text.getText() + "\n \n" + "You successfully made a potion to cure yourself. You're now healthy and immune to the virus!");
            } else if ((Boolean) evt.getNewValue() == false) {
                text.setText(text.getText() + "\n \n" + "Hmm... You seem to be missing some ingredients from the recipie");
            }
        } else if (evt.getPropertyName().equals("health")) {
            int health = (Integer) evt.getNewValue();
            pb.setValue(health);
        } else if (evt.getPropertyName().equals("health")) {
            int health = (Integer) evt.getNewValue();
            pb.setValue(health);
        } else if (evt.getPropertyName().equals("eatingFood")) {
            int healthGained = (Integer) evt.getNewValue();
            String foodItem = (String) evt.getOldValue();
            text.setText(text.getText() + "\n \n" + "You ate " + foodItem + " and gained " + healthGained + " health!");
        } else if (evt.getPropertyName().equals("eatingBadFood")) {
            int healthGained = (Integer) evt.getNewValue();
            String foodItem = (String) evt.getOldValue();
            text.setText(text.getText() + "\n \n" + "You ate " + foodItem + " and lost " + -healthGained + " health...");
        } else if (evt.getPropertyName().equals("eatingFoodDead")) {
            String foodItem = (String) evt.getOldValue();
            text.setText(text.getText() + "\n \n" + "You ate " + foodItem + " and lost so much health you died :(");
        } else if (evt.getPropertyName().equals("zombie")) {
            if ((int) evt.getOldValue() == 0) {
                text.setText(text.getText() + "\nThere's a zombie in the room!! ");
            } else if ((int) evt.getOldValue() == 1) {
                text.setText(text.getText() + "\nThe zombie attacks and deals " + evt.getNewValue() + " damage to you!");
            } else if ((int) evt.getOldValue() == 2) {
                text.setText(text.getText() + "\nBut it doesn't seem to notice you. Better keep quiet and move slowly...");
            }
        } else if (evt.getPropertyName().equals("showWinningText")) {
            text.setText("You are safe!!\nIt's your lucky day! Get to the chopper!");
        } else if (evt.getPropertyName().equals("itemTooHeavy")) {
            String itemName = (String) evt.getNewValue();
            text.setText(text.getText() + "\n \n" + "Inventory too full for item " + itemName);
        } else if (evt.getPropertyName().equals("lockedRoom")) {
            text.setText(text.getText() + "\n \n" + "You tried to enter the room, but the door is locked. Maybe there's a key somewhere...");
        } else if (evt.getPropertyName().equals("recipePrint")) {
            text.setText(text.getText() + "\n \n" + "You found a recipie! It says you need the following things: " + evt.getNewValue());
        }

    }

}
