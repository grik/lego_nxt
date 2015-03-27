package speculatrix_main;

public class tools {
	
	public static int hp_init = 100;
	public static int hp = hp_init;
	
	// distance traveled and rotation performed cost
	public static double dist_cost = 4;
	public static double rota_cost = 0.1;
	
	public static int hp_food = 30;
	
	// method to calculate distance to hp loss
	// each centimeter passed costs some amount of time => hp loss
	public static int dist_to_hp(double dist) {
		double hp_loss = 0;
		hp_loss = dist * dist_cost;
		return (int) hp_loss;
	}
	
	public static int rota_to_hp(double rota) {
		double hp_loss = 0;
		hp_loss = rota * rota_cost;
		return (int) hp_loss;
	}
	
	public static int food_to_hp(Boolean food) {
		int hp_add = 0;
		if (food){
			hp_add += hp_food;
		}
		return hp_add;
	}

}
