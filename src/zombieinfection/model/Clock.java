package zombieinfection.model;

import java.beans.*;
import zombieinfection.view.highscore.Highscore;

/**
 * Clock thread that counts down second by second.
 *
 * @author Elena Marzi
 * @version 2018-02-19
 */
public class Clock extends Thread {

    private PropertyChangeSupport pcs;
    private int secondsLeft;
    private boolean ticking;

    public Clock() {
        pcs = new PropertyChangeSupport(this);
        ticking = false;
        start();// when creating, thread runs
    }

    /**
     * @return the number of seconds left on the clock
     */
    public int getSecondsLeft() {
        return secondsLeft;

    }

    /**
     * Adds a PropertyChangeListener to this object.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Starts the clock at a given number of seconds. This is called when a new
     * game is started.
     *
     * @param initialSeconds number of seconds to start the clock with
     */
    public void startTicking(int initialSeconds) {
        setSecondsLeft(initialSeconds);
        ticking = true;
    }

    /**
     * Stops the clock ticking. Does not stop the thread.
     */
    public void stopTicking() {
        ticking = false;
    }

    /**
     * Loops until the application is closed, waiting one second and if the
     * timer is ticking, updates the number of seconds left.
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                break;
            }
            if (ticking) {
                setSecondsLeft(secondsLeft - 1);
                handlePlayerHealth();
            }
        }
    }

    /**
     * If the player is infected, decreases their health points by one.
     */
    private void handlePlayerHealth() {
        Player player = GameEngine.getInstance().getPlayer();
        if (player.isInfected()) {
            player.setHealth(player.getHealth() - 1);
        }
    }

    /**
     * Sets the number of seconds left on the clock. If the new value is zero or
     * less, stops the game, shows the "Game over" message and the Highscore
     * window if the user wants to see the highscores.
     *
     * @param newValue new number of seconds left
     */
    private void setSecondsLeft(int newValue) {
        int oldValue = secondsLeft;
        if (newValue < 0) {
            newValue = 0;
        }
        if (oldValue != newValue) {
            secondsLeft = newValue;
            pcs.firePropertyChange("secondsLeft", oldValue, newValue);
        }
        if (newValue == 0) {
            ticking = false;
            GameEngine.getInstance().setGameOver();
            if (GameEngine.getInstance().showLoserMsg()) {
                new Highscore(0);
            }
        }
    }
}
