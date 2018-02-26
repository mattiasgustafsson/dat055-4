package zombieinfection.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import zombieinfection.model.GameEngine;
import zombieinfection.view.GUI.ItemPickUpPopup;
import zombieinfection.view.GUI.Map;

/**
 * The NavigationController class handles what to do when buttons in the
 * navigation panel are pressed and also keyboard input.
 *
 * @author Daniel Duvanå
 * @version 2018-02-23
 */
public class NavigationController implements KeyListener {

    /**
     * Handles what to do when supported keyboard keys are pressed.
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                northButtonController();
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                southButtonController();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                westButtonController();
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                eastButtonController();
                break;
            case KeyEvent.VK_M:
                mapButtonController();
                break;
            case KeyEvent.VK_X:
                mixButtonController();
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_P:
                pickUpButtonController();
                break;
        }
    }

    /**
     * Is called when the "North" button is pressed on the navigation panel in
     * the game. Moves the player in the given direction if possible.
     */
    public void northButtonController() {
        GameEngine.getInstance().goToRoom("north");
    }

    /**
     * Is called when the "West" button is pressed on the navigation panel in
     * the game. Moves the player in the given direction if possible.
     */
    public void westButtonController() {
        GameEngine.getInstance().goToRoom("west");
    }

    /**
     * Is called when the "South" button is pressed on the navigation panel in
     * the game. Moves the player in the given direction if possible.
     */
    public void southButtonController() {
        GameEngine.getInstance().goToRoom("south");
    }

    /**
     * Is called when the "East" button is pressed on the navigation panel in
     * the game. Moves the player in the given direction if possible.
     */
    public void eastButtonController() {
        GameEngine.getInstance().goToRoom("east");
    }

    /**
     * Is called when the "Map" button is pressed on the navigation panel in the
     * game. Brings up a map of the Rooms in the game.
     */
    public void mapButtonController() {
        if (GameEngine.getInstance().getGameOver()) {
            return;
        }
        Map map = new Map(GameEngine.getInstance().getEntryRoom(),
                GameEngine.getInstance().getCurrentRoom(),
                GameEngine.getInstance().getEndRoom());
        JOptionPane.showMessageDialog(GameEngine.getInstance().getGui(), map, "Map", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Is called when the "Pick up items" button is pressed on the navigation
     * panel in the game. Brings up a menu of the Items in the Room and let's
     * the player choose which to pick up.
     */
    public void pickUpButtonController() {
        if (GameEngine.getInstance().getGameOver()) {
            return;
        }
        if(!GameEngine.getInstance().getCurrentRoom().hasItem()) {
            return;
        }
        @SuppressWarnings("unused")
        ItemPickUpPopup ipup = new ItemPickUpPopup();
    }

    /**
     * Is called when the "Mix ingredients" button is pressed on the navigation
     * panel in the game. Checks if the Player has all the ingredients needed,
     * and if that's true it gives the Player full health and cured status.
     */
    public void mixButtonController() {
        if (GameEngine.getInstance().getGameOver()) {
            return;
        }
        GameEngine.getInstance().getPlayer().mixIngredients();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

}
