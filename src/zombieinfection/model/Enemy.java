package zombieinfection.model;

public class Enemy {
    private String name;
    private Player player;
    private int strength;
    // TODO Image
    
    public Enemy(String name, int strength) {
        this.name = name;
        this.strength = strength; // TODO Random?
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
        System.out.println("AN ENEMY IS IN THIS ROOM");
        // TODO Add random attack
        // Version 1: Zombie always attacks if it is in the current room
        if (true /* TODO Random */) {
            attack();
        }
    }

    /**
     * Zombie attacks player and does damage to players health.
     */
    // Version 1: The zombie always does damage
    // The damage the zombie does is its strength
    private void attack() {
        player = GameEngine.getInstance().getPlayer();
        player.setHealth(player.getHealth() - strength);
    }
}
