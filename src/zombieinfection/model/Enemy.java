package zombieinfection.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

import zombieinfection.MusicPlayer;

/**
 * This class represents enemies.
 *
 * @author Mattias Gustafsson
 * @version 2018-02-22
 *
 */
public class Enemy {

    private String name;
    private Player player;
    private int strength;
    private Random r;
    private PropertyChangeSupport pcs;
    private final int MIN_ENEMY_STRENGTH;
    private final int MAX_ENEMY_STRENGTH;

    /**
     * Creates an object of the enemy class.
     *
     * @param name the name this enemy object should have
     */
    public Enemy(String name) {
        this.name = name;
        r = new Random();
        MIN_ENEMY_STRENGTH = 15;
        MAX_ENEMY_STRENGTH = 35;
        // Set strength to random number [MIN_ENEMY_STRENGTH..MAX_ENEMY_STRENGTH]
        this.strength = r.nextInt(MAX_ENEMY_STRENGTH + 1) + MIN_ENEMY_STRENGTH;
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * Returns the name of this enemy object.
     *
     * @return the name of this enemy object.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the strength of this enemy object.
     *
     * @return the strength of this enemy object.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Used to "interact" with an enemy and decides if the enemy will attack or
     * not when the player interacts with it. If the enemy decides to attack the
     * attack-method is called.
     *
     * @return <code>true</code> if the enemy attacks; <code>false</code>
     * otherwise
     */
    public boolean interact() {
        if (r.nextBoolean()) { // 50/50 chance the zombie attacks
            // attack method is called from the thread, to time the health bar with the
            // attack picture
            GameEngine.getInstance().startAttackThread(this, 800, 800);
            return true;
        } else {
            MusicPlayer.getInstance().playEffect("breathing");
            return false;
        }
    }

    /**
     * Used to make enemy attack player and do damage to player's health. The
     * damage the enemy does to the players health is its strength minus the
     * damage of the strongest armour in the players inventory.
     */
    public void attack() {
        player = GameEngine.getInstance().getPlayer();
        int strongestArmour = player.getInventory().getStrongestArmour();
        // If enemy is stronger than the strongest armour the zombie will do damage to
        // the player
        if (strength > strongestArmour) {
            player.setHealth(player.getHealth() - (strength - strongestArmour));
        }
        // if the armour is stronger than zombie, the zombie dies
        GameEngine.getInstance().getCurrentRoom().setHasEnemy(false);
    }

    /**
     * Add a PropertyChangeListener to the listener pcl.
     *
     * @param pcl the listener object
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

}
