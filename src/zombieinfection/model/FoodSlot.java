package zombieinfection.model;

public class FoodSlot {
	private Food food;
	private int placement; // From left to right in GUI
	private boolean isEmpty;
	
	public FoodSlot(Food food, int placement) {
		this.food = food;
		this.placement = placement;
		isEmpty = true;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
		isEmpty = false;
	}

	public int getPlacement() {
		return placement;
	}

	public void setPlacement(int placement) {
		this.placement = placement;
	}
	
	public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
