package zombieinfection.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

import zombieinfection.MusicPlayer;

public class Enemy {
    private String name;
    private Player player;
    private int strength;
    private Random r;
    private PropertyChangeSupport pcs;
    // TODO Image
    
    public Enemy(String name) {
        this.name = name;
        r = new Random();
        // Set strength to random number [20..40]
        this.strength = r.nextInt(21) + 15;
        pcs = new PropertyChangeSupport(this);
    }

    public String getName() {
        return name;
    }
    
    public int getStrength() {
        return strength;
    }
    
    /**
     * This method is used to "interact" with a zombie. It decides whether the
     * zombie attacks or not. If the zombie attacks this method runs the attack
     * method.Returns true if the zombie attacks, false if it doesn't. 
     */
    public boolean interact() {
    	if (r.nextBoolean()) { // 50/50 chance the zombie attacks
            // attack method is called from the thread, to time the health bar with the attack picture
            GameEngine.getInstance().startAttackThread(this, 800, 800);
            return true;
    	}
        else {
            MusicPlayer.getInstance().playEffect("breathing");
            return false;
        }
    }

    /**
     * Zombie attacks player and does damage to players health. The damage the
     * zombie does to the players health is its strength minus the damage of the
     * strongest armour in the players inventory.
     */
    public void attack() {
    	player = GameEngine.getInstance().getPlayer();
        int strongestArmour = player.getInventory().getStrongestArmour();
        //if zombie is stronger than the strongest armour the zombie will injure
        if (strength > strongestArmour) {
            player.setHealth(player.getHealth() - (strength - strongestArmour));
        }
        //if the armour is stronger than zombie, the zombie dies
        GameEngine.getInstance().getCurrentRoom().setHasEnemy(false);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l){
		pcs.addPropertyChangeListener(l);
	}
    
}
