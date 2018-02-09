package zombieinfection.model;

import javax.swing.Icon;

public class FoodSlot {
	private Food food;
	private int placement; // From left to right in GUI
	
	public FoodSlot() {} // Do not use this constructor
	
	public FoodSlot(Food food, int placement) {
		this.food = food;
		this.placement = placement;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int getPlacement() {
		return placement;
	}

	public void setPlacement(int placement) {
		this.placement = placement;
	}
}
