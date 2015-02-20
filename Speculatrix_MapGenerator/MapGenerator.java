package speculatrix_main;

import java.util.Random;

public class MapGenerator {

	// map size in positions in 2d array
	static int[][] map_speculatrix = new int[10][10];

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The
	 * difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	/**
	 * Prints the Speculatrix map to the console
	 * 
	 * @param nothing
	 * @return nothing - it's a void
	 */

	public static void map_show() {
		for (int i = 0; i < map_speculatrix.length; i++) {
			for (int j = 0; j < map_speculatrix[i].length; j++) {
				System.out.print(map_speculatrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Prints the food_cluster_size to the console
	 * 
	 * @param cluster
	 *            of food (with sizes) array
	 * @return nothing - it's a void
	 */

	public static void food_cluster_size_show(int[][] cluster) {
		for (int i = 0; i < cluster.length; i++) {
			for (int j = 0; j < cluster[i].length; j++) {
				System.out.print("food_index: " + i + " ### cluster size: "
						+ cluster[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Creates the array for adding fields with food around the core food pixel
	 * The map has to be specified in order to
	 * 
	 * @param core_pixel
	 *            the core food pixel
	 * @param map_generated
	 *            Map taken under consideration
	 * @return surrounding_fields places available for putting the additional
	 *         food fields
	 */

	public static int[][] add_surrounding_food_pixel(int[] core_pixel,
			int[][] map_generated) {
		int[][] surrounding_fields = new int[8][2];

		// fill surrounding_fields array with -1. Because of coordinates
		// conflict. Coordinates may happen to be (0,0)
		for (int i = 0; i < surrounding_fields.length; i++) {
			for (int j = 0; j < surrounding_fields[0].length; j++) {
				surrounding_fields[i][j] = -1;
			}
		}

		// Check surrounding field's availability
		for (int i = 0; i < 8; i++) {

			if (core_pixel[0] - 1 >= 0 && core_pixel[1] - 1 >= 0) {
				if (map_generated[core_pixel[0] - 1][core_pixel[1] - 1] < 1) {
					surrounding_fields[0][0] = core_pixel[0] - 1;
					surrounding_fields[0][1] = core_pixel[1] - 1;
				}
			}

			if (core_pixel[0] - 1 >= 0) {
				if (map_generated[core_pixel[0] - 1][core_pixel[1]] < 1) {
					surrounding_fields[1][0] = core_pixel[0] - 1;
					surrounding_fields[1][1] = core_pixel[1];
				}
			}

			if (core_pixel[0] - 1 >= 0
					&& core_pixel[1] + 1 <= map_generated[0].length - 1) {
				if (map_generated[core_pixel[0] - 1][core_pixel[1] + 1] < 1) {
					surrounding_fields[2][0] = core_pixel[0] - 1;
					surrounding_fields[2][1] = core_pixel[1] + 1;
				}
			}

			if (core_pixel[1] + 1 <= map_generated[0].length - 1) {
				if (map_generated[core_pixel[0]][core_pixel[1] + 1] < 1) {
					surrounding_fields[3][0] = core_pixel[0];
					surrounding_fields[3][1] = core_pixel[1] + 1;
				}
			}

			if (core_pixel[0] + 1 <= map_generated.length - 1
					&& core_pixel[1] + 1 <= map_generated[0].length - 1) {
				if (map_generated[core_pixel[0] + 1][core_pixel[1] + 1] < 1) {
					surrounding_fields[4][0] = core_pixel[0] + 1;
					surrounding_fields[4][1] = core_pixel[1] + 1;
				}
			}

			if (core_pixel[0] + 1 <= map_generated.length - 1) {
				if (map_generated[core_pixel[0] + 1][core_pixel[1]] < 1) {
					surrounding_fields[5][0] = core_pixel[0] + 1;
					surrounding_fields[5][1] = core_pixel[1];
				}
			}

			if (core_pixel[0] + 1 <= map_generated.length - 1
					&& core_pixel[1] - 1 >= 0) {
				if (map_generated[core_pixel[0] + 1][core_pixel[1] - 1] < 1) {
					surrounding_fields[6][0] = core_pixel[0] + 1;
					surrounding_fields[6][1] = core_pixel[1] - 1;
				}
			}

			if (core_pixel[1] - 1 >= 0) {
				if (map_generated[core_pixel[0]][core_pixel[1] - 1] < 1) {
					surrounding_fields[7][0] = core_pixel[0];
					surrounding_fields[7][1] = core_pixel[1] - 1;
				}
			}
		}
		return surrounding_fields;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// amount of clusters with food
		int food_amount = 5;

		// define how big the single cluster of food will be: e.g.: from 1 to 4
		// food_size[0] = 1;
		// food_size[1] = 4;
		int[] food_size = new int[2];
		food_size[0] = 1;
		food_size[1] = 4;

		// create array to store info about the size of the particular clusters
		int[][] food_cluster_size = new int[food_amount][1];

		int[] field_randomized = new int[2];
		int[] field_being_changed = new int[2];

		// randomize the size of the clusters
		for (int i = 0; i < food_amount; i++) {
			food_cluster_size[i][0] = randInt(food_size[0], food_size[1]);
			System.out.println(food_cluster_size[i][0]);
		}

		// randomize the field and put there a core food pixel (as number 1)
		for (int i = 0; i < food_amount; i++) {

			// check how many fields are available
			int fields_available = 0;
			for (int j = 0; j < map_speculatrix.length; j++) {
				for (int k = 0; k < map_speculatrix[j].length; k++) {
					if (map_speculatrix[j][k] == 0) {
						fields_available += 1;
					}

				}
				System.out.print("\n");
			}

			// if there is no field available then break the main loop
			if (fields_available == 0) {
				System.out
						.println("\nThere are no fields available. Breaking.\n");
				break;
			}

			int col = (int) Math.floor(Math.random() * map_speculatrix.length);
			int row = (int) Math.floor(Math.random()
					* map_speculatrix[0].length);
			// if statement to avoid randomizing the same fields
			if (map_speculatrix[row][col] < 1) {
				map_speculatrix[row][col] = 1;
				field_randomized[0] = row;
				field_randomized[1] = col;
				System.out.println(row + " " + col);
			} else {
				i -= 1;
				System.out.println("field not allowed, randomizing again");
			}

			int[][] surrounding_food_available = new int[8][2];

			surrounding_food_available = add_surrounding_food_pixel(
					field_randomized, map_speculatrix);

			System.out.println("coordinates: (" + field_randomized[0] + ", "
					+ field_randomized[1] + ")");

			// changes the avaliable field to number 2
			for (int j = 0; j < surrounding_food_available.length; j++) {
				for (int k = 0; k < surrounding_food_available[0].length; k++) {
					System.out.print(surrounding_food_available[j][k] + " ");
					field_being_changed[k] = surrounding_food_available[j][k];
				}
				if (field_being_changed[0] >= 0) {
					map_speculatrix[field_being_changed[0]][field_being_changed[1]] = 2;
					fields_available += 1;
				}
				System.out.println("\n");
			}
			// if (fields_available != 0){
			//
			// }

			// if cluster is bigger than 1 it has to be placed near "core"
			// field
			// there are at least two ways to place the cluster:
			// 1. Center-oriented
			// 2. Peripheral-oriented (worm-like)
			// Here I use the centered like. Grass (food for the turtles)
			// usually grows in all directions from one point.
			if (food_cluster_size[i][0] != 1) {
				// random places around the core pixel
				// NOTICE
				int food_pixel_new = randInt(1, 9);
			}

		}

		map_show();
		food_cluster_size_show(food_cluster_size);

		// // tmp for for checking
		// for (int i = 0; i < 50; i++) {
		// int food_pixel_new = randInt(1, 9);
		// System.out.println(food_pixel_new);
		// }
	}
}
