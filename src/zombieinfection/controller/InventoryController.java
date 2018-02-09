package zombieinfection.controller;



public class InventoryController {
	
	public void foodSlotClicked(int nr) {
		
		if(nr == 1){
			System.out.println("mat1 konsumerad");
		}
		if(nr == 2){
			System.out.println("mat2 konsumerad");
		}
		if(nr == 3){
			System.out.println("mat3 konsumerad");
		}
		if(nr == 4){
			System.out.println("mat4 konsumerad");
		}
	}
}
