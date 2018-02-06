package zombieinfection.model;

public class Food extends Item {
	private int healthGained;
	
	public Food(String name, int weight, int healthGained){
		super(name, weight);
		this.healthGained = healthGained;
	}
	
	public int getHealthGained(){
		return healthGained;
	}
	
	public void eat(){
		
	}

}
