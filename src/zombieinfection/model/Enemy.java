package zombieinfection.model;

public class Enemy {
    private Player player;
    private int strength;
    
    public Enemy() {
        player = GameEngine.getInstance().getPlayer();
        strength = 20; // TODO Random?
    }
    
    public void enemyInteraction() {
        // TODO Add random attack
    }

    // In the first version the zombie always does damage if it attacks
    // The damage the zombie does is its strength
    private void attack() {
        player.setHealth(player.getHealth() - strength);
    }
}
