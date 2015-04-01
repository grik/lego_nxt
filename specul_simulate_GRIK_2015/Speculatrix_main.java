package speculatrix_main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import speculatrix_main.tools;

public class Speculatrix_main {
	
	public Speculatrix_main(){
		
	}

	public static void main(String[] args) {

		// creating the empty main reference map (the revealed one)
		int[][] map_specul = new int[MapGenerator.map_speculatrix.length][MapGenerator.map_speculatrix[0].length];

		// creating the empty main reference map (the revealed one)
		int[][] map_explored = map_specul;

		int steps = map_explored.length * map_explored.length;
		int steps_counter = 0;

		int[] rand_loc = new int[2];
		int[] decide_loc = new int[2];

		double[][] food_loc_dist;
		double[][] fn_params;

		// interface parameter (Printing)
		String header = "|.......|.......|.......|.......|.......|.......|.......|.......|.......|.......|.......|\n";
		header += "| x  .0 | y  .1 | mf .2 | rd .3 | me .4 | sp .5 | dw .6 | ew .7 | sw .8 | fp .9 | s .10 |\n";
		header += "|.......|.......|.......|.......|.......|.......|.......|.......|.......|.......|.......|";
		NumberFormat formatter = new DecimalFormat("#00.00");

		double[][][] side_dist = map.get_side_dist();
		String out_filename = "";

		int food_cost = 0;

		// MapGenerator.randInt(0, 5);

		// create the map, generate one with the MapGenerator class
		map_specul = MapGenerator.create_map();

		// the starting point is the middle of 2D-odd-numbered array
		// later on these variables stores the info about the current location
		int x_current = map_specul.length / 2;
		int y_current = map_specul.length / 2;

		int x_dest;
		int y_dest;

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");

		// some init functions, show the generated map
		MapGenerator.map_fill(map_explored, 2);
		MapGenerator.map_set_starting(map_explored);
		MapGenerator.map_show(map_specul);

		for (int i = 0; i < steps; i++) {
			System.out.println("#########################################");
			System.out.println("# STEP NUMBER: " + i + " # HP: " + tools.hp);
			System.out.println("##################\n");

			food_loc_dist = farthest_neigh.get_avail_info(map_explored,
					side_dist, x_current, y_current);

			// for (int j = 0; j < food_loc_dist.length; j++) {
			// for (int k = 0; k < food_loc_dist[0].length; k++) {
			// System.out.print(food_loc_dist[j][k] + " ");
			// }
			// System.out.print("\n");
			// }

			fn_params = farthest_neigh.get_fn_params(food_loc_dist);

			/*
			 * The main algorithm
			 */
			decide_loc = farthest_neigh.get_fn_decision(fn_params, 10);
			x_dest = decide_loc[0];
			y_dest = decide_loc[1];
			out_filename = "spec.csv";

			System.out.println("\nfields_available: " + fn_params.length + "\n");

			fn_params = tools.sort_by_index(fn_params, 9);

//			System.out.println(header);
//			for (int j = 0; j < fn_params.length; j++) {
//				System.out.print("| ");
//				for (int k = 0; k < fn_params[0].length; k++) {
//					System.out.print(formatter.format(tools.round(
//							fn_params[j][k], 2)) + " | ");
//				}
//				System.out.print("\n");
//				if (j != 0 && j % 19 == 0) {
//					System.out.println(header);
//				} 
//			}
//			System.out.print("\n");

			// randomize the locations: x and y
			// x_rand = rand.randIntArray(0, map_explored.length - 1)[0];
			// y_rand = rand.randIntArray(0, map_explored.length - 1)[1];

			/*
			 * The tabu-like random algorithm
			 */
			// rand_loc = rand.randLoc(map_explored);
			// x_dest = rand_loc[0];
			// y_dest = rand_loc[1];
			// out_filename = "rand.csv";

			if (x_dest == 7 && y_dest == 7) {
				break;
			}

			// prints some fancy localization feedback
			System.out.println("[x_current, y_current] => [x_rand, y_rand]");
			System.out.println("[" + x_current + ", " + y_current + "]"
					+ " => " + "[" + x_dest + ", " + y_dest + "]");

			// craete the coords to follow when walking the map in the current
			// step
			int[][] coords = linear_path.path(map_specul, x_current + 0.5,
					y_current + 0.5, x_dest + 0.5, y_dest + 0.5);

			// System.out.println("\nCoords taken during this step: ");
			// System.out.println(coords.length + "\n");

			// walk the map to the randomized point
			map_explored = linear_path.walk(map_explored, map_specul, coords,
					x_current, y_current);

			food_cost = tools.hp
					- (tools.dist_to_hp(linear_path.dist) + tools
							.rota_to_hp(linear_path.rota))
					+ tools.food_to_hp(linear_path.food);

			System.out.println("\n" + food_cost + " = " + tools.hp + " - ("
					+ tools.dist_to_hp(linear_path.dist) + " + "
					+ tools.rota_to_hp(linear_path.rota) + ") + "
					+ tools.food_to_hp(linear_path.food));

			// hp loss or addition handeling
			tools.hp = food_cost;

			if (tools.hp > 100) {
				tools.hp = 100;
			}
			System.out.println("HP: " + tools.hp + " <=====================\n");

			if (tools.hp < 0) {
				System.out.println("################");
				System.out.println("STARVED TO DEATH");
				System.out.println("################");
				break;
			}

			tools.dist_total += linear_path.dist;
			tools.rota_total += linear_path.rota;
			if (linear_path.food) {
				tools.food_found += 1;
				System.out.println("FOOD HP: " + 30 + "; food_found: "
						+ tools.food_found);
			}

			// show the step on the map
			MapGenerator.map_show(map_explored);

			// get the current location from linear_path class
			x_current = linear_path.x_found;
			y_current = linear_path.y_found;

			steps_counter += 1;

			System.out
					.println("#########################################\n\n\n");
		}

		System.out.println("");
		System.out.println("#################");
		System.out.println("#  S C O R E S  #");
		System.out.println("#################");
		System.out.println("food_got: " + tools.food_found);
		System.out.println("food_max: " + MapGenerator.food_max);
		System.out.println("steps_made: " + steps_counter);
		System.out.println("distance: " + tools.round(tools.dist_total, 2));
		System.out.println("rotation: " + tools.round(tools.rota_total, 2));
		System.out.println("time_ela: "
				+ tools.round(tools.dist_total * tools.dist_cost
						+ tools.rota_total * tools.rota_cost, 2));
		System.out.println(tools.food_found
				+ ", "
				+ MapGenerator.food_max
				+ ", "
				+ steps_counter
				+ ", "
				+ tools.round(tools.dist_total, 2)
				+ ", "
				+ tools.round(tools.rota_total, 2)
				+ ", "
				+ tools.round(tools.dist_total * tools.dist_cost
						+ tools.rota_total * tools.rota_cost, 2));

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(out_filename, true)));
			out.println(tools.food_found
					+ ", "
					+ MapGenerator.food_max
					+ ", "
					+ steps_counter
					+ ", "
					+ tools.round(tools.dist_total, 2)
					+ ", "
					+ tools.round(tools.rota_total, 2)
					+ ", "
					+ tools.round(tools.dist_total * tools.dist_cost
							+ tools.rota_total * tools.rota_cost, 2));
			out.close();
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}

	}
}
