package speculatrix_main;

import java.util.*;
import speculatrix_main.tools;

public class Speculatrix_main {

	public static void main(String[] args) throws InterruptedException {

		// creating the empty main reference map (the revealed one)
		int[][] map_specul = new int[MapGenerator.map_speculatrix.length][MapGenerator.map_speculatrix[0].length];

		// creating the empty main reference map (the revealed one)
		int[][] map_explored = map_specul;

		int steps = map_explored.length * map_explored.length;
		
		int[] rand_loc = new int[2];

		// MapGenerator.randInt(0, 5);

		// create the map, generate one with the MapGenerator class
		map_specul = MapGenerator.create_map();

		// the starting point is the middle of 2D-odd-numbered array
		// later on these variables stores the info about the current location
		int x_current = map_specul.length / 2;
		int y_current = map_specul.length / 2;
		
		int x_rand;
		int y_rand;

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");

		// some init functions, show the generated map
		MapGenerator.map_fill(map_explored, 2);
		MapGenerator.map_set_starting(map_explored);
		MapGenerator.map_show(map_specul);

		for (int i = 0; i < steps; i++) {
			System.out.println("#########################################");
			System.out.println("# STEP NUMBER: " + i + " # HP: " + tools.hp);
			System.out.println("##################\n");
			// randomize the locations: x and y
//			x_rand = rand.randIntArray(0, map_explored.length - 1)[0];
//			y_rand = rand.randIntArray(0, map_explored.length - 1)[1];

			rand_loc = rand.randLoc(map_explored);
			
			x_rand = rand_loc[0];
			y_rand = rand_loc[1];
			
			if (x_rand == 7 && y_rand == 7){
				break;
			}
			
			// prints some fancy localization feedback
			System.out.println("[x_current, y_current] => [x_rand, y_rand]");
			System.out.println("[" + x_current + ", " + y_current + "]"
					+ " => " + "[" + x_rand + ", " + y_rand + "]");

			// craete the coords to follow when walking the map in the current
			// step
			int[][] coords = linear_path.path(map_specul, x_current + 0.5,
					y_current + 0.5, x_rand + 0.5, y_rand + 0.5);

//			System.out.println("\nCoords taken during this step: ");
//			System.out.println(coords.length + "\n");

			// walk the map to the randomized point
			map_explored = linear_path.walk(map_explored, map_specul, coords,
					x_current, y_current);

			System.out.println("\n" + tools.hp + " = " + tools.hp + " - ("
					+ tools.dist_to_hp(linear_path.dist) + " + "
					+ tools.rota_to_hp(linear_path.rota) + ") + "
					+ tools.food_to_hp(linear_path.food));

			// hp loss or addition handeling
			tools.hp = tools.hp
					- (tools.dist_to_hp(linear_path.dist) + tools
							.rota_to_hp(linear_path.rota))
					+ tools.food_to_hp(linear_path.food);
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

			// show the step on the map
			MapGenerator.map_show(map_explored);

			// get the current location from linear_path class
			x_current = linear_path.x_found;
			y_current = linear_path.y_found;

			System.out
					.println("#########################################\n\n\n");
		}

	}
}
