package zombieinfection.model;

import java.util.Random;

public class Enemy {
    private String name;
    private Player player;
    private int strength;
    private Random r;
    // TODO Image
    
    public Enemy(String name) {
        this.name = name;
        r = new Random();
        // Set strength to random number [20..40]
        this.strength = r.nextInt(21) + 15;
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
     * method.
     */
    public void interact() {
        System.out.println("A ZOMBIE " + getName() + " IS IN THIS ROOM");
        if (r.nextBoolean()) { // 50/50 chance the zombie attacks
            attack();
            GameEngine.getInstance().startAttackThread(name + "attack.png", 800, 800);
        }
        else {
            MusicPlayer.getInstance().playEffect("breathing");
            System.out.println("ZOMBIE DOES NOTHING");
        }
    }

    /**
     * Zombie attacks player and does damage to players health. The damage the
     * zombie does to the players health is its strength minus the damage of the
     * strongest weapon in the players inventory.
     */
    private void attack() {
        System.out.println("ZOMBIE ATTACKS WITH " + getStrength() + " DAMAGE");
        player = GameEngine.getInstance().getPlayer();
        int strongestWeaponDamage = player.getInventory().getStrongestWeaponDamage();
        //if zombie is stronger than the strongest weapon the zombie will injure
        if (strength > strongestWeaponDamage) {
        player.setHealth(player.getHealth() - (strength - strongestWeaponDamage));
        }
        //if the weapon is stronger than zombie, the zombie dies
        GameEngine.getInstance().getCurrentRoom().setHasEnemy(false);
    }
}
