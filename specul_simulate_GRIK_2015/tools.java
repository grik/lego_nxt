package speculatrix_main;

public class tools {

	public static int hp_init = 100;
	public static int hp = hp_init;

	// distance traveled and rotation performed cost
//	public static double dist_cost = 1.7;
//	public static double rota_cost = 0.02777;
	public static double dist_cost = 3;
	public static double rota_cost = 0.04875;

	public static int hp_food = 20;

	public static double dist_total = 0;
	public static double rota_total = 0;
	public static int food_found = 0;

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
		if (food) {
			hp_add += hp_food;
		}
		return hp_add;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static double[][] sort_by_index(double[][] params, int column) {
		double[] storage = new double[params[0].length];

		for (int i = 0; i < params.length - 1; i++) {
			for (int j = 0; j < params.length - 1; j++) {
				if (params[j][column] < params[j + 1][column]) {
					for (int k = 0; k < params[0].length; k++) {
						storage[k] = params[j][k];
						params[j][k] = params[j + 1][k];
						params[j + 1][k] = storage[k];
					}
				}
			}
		}
		return params;
	}

}
