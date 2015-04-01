package speculatrix_main;

import java.util.Random;

public class MapGenerator {

	// map size in positions in 2d array
	public static int[][] map_speculatrix = new int[15][15];
	public static int food_max = 0;

	/**
	 * Prints the Speculatrix map to the console
	 * 
	 * @param nothing
	 * @return nothing - it's a void
	 */

	public static void map_show(int[][] map_showed) {
		System.out.print("\n");
		for (int i = 0; i < map_showed.length; i++) {
			for (int j = 0; j < map_showed[i].length; j++) {
				System.out.print(map_showed[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	/**
	 * Fills the given map with the given number
	 * 
	 * @param int[][] map_to_be_filled, int number to fill
	 * @return int[][] filled_map
	 */

	public static int[][] map_fill(int[][] map_filling, int num_fill) {
		for (int i = 0; i < map_filling.length; i++) {
			for (int j = 0; j < map_filling[i].length; j++) {
				map_filling[i][j] = num_fill;
			}
		}
		return map_filling;
	}

	/**
	 * Clear map to ones only
	 * 
	 * @param map_to_clean
	 * @return a cleaned map
	 */

	/**
	 * Fills the given map with the given number
	 * 
	 * @param int[][] map_to_be_filled, int number to fill
	 * @return int[][] filled_map
	 */

	public static int[][] map_set_starting(int[][] map_init) {
		map_init[map_init.length / 2][map_init.length / 2] = 0;
		return map_init;
	}

	/**
	 * Clear map to ones only
	 * 
	 * @param map_to_clean
	 * @return a cleaned map
	 */

	public static int[][] map_clean(int[][] map_to_clean) {
		for (int i = 0; i < map_to_clean.length; i++) {
			for (int j = 0; j < map_to_clean[i].length; j++) {
				if (map_to_clean[i][j] == 2 || map_to_clean[i][j] == 3) {
					map_to_clean[i][j] = 0;
				}
			}
		}
		return map_to_clean;
	}

	/**
	 * Prints the filelds available for putting core food pixel array to the
	 * console
	 * 
	 * @param array_locations
	 * @return nothing - it's a void
	 */

	public static void fields_aval_show(int[][] array_locations) {
		for (int j = 0; j < array_locations.length; j++) {
			System.out.print("which field?: " + j + "  - ");
			for (int k = 0; k < array_locations[j].length; k++) {
				if (k == 0) {
					System.out.print(" (x): ");
				} else {
					System.out.print(" (y): ");
				}
				System.out.print(array_locations[j][k]);
				System.out.print(" ");
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
			System.out.println("food_index: " + i + " ### cluster size: "
					+ cluster[i][0] + " ");
		}
		System.out.print("\n");
	}

	/**
	 * Sorts the food_cluster by size
	 * 
	 * @param cluster
	 *            of food (with sizes) array
	 * @return sorted cluster
	 */

	public static int[][] food_cluster_sort(int[][] cluster) {
		int store_cluster_info;
		for (int i = 0; i < cluster.length - 1; i++) {
			for (int j = 0; j < cluster.length - 1; j++) {
				if (cluster[j][0] < cluster[j + 1][0]) {
					store_cluster_info = cluster[j][0];
					cluster[j][0] = cluster[j + 1][0];
					cluster[j + 1][0] = store_cluster_info;
				}
			}
		}
		return cluster;
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
		int[][] surrounding_fields = new int[8][3];

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
				// if (map_generated[core_pixel[0] - 1][core_pixel[1] - 1] < 1)
				// {
				surrounding_fields[0][0] = core_pixel[0] - 1;
				surrounding_fields[0][1] = core_pixel[1] - 1;
				surrounding_fields[0][2] = map_generated[core_pixel[0] - 1][core_pixel[1] - 1];
				// }
			}

			if (core_pixel[0] - 1 >= 0) {
				// if (map_generated[core_pixel[0] - 1][core_pixel[1]] < 1)
				// {
				surrounding_fields[1][0] = core_pixel[0] - 1;
				surrounding_fields[1][1] = core_pixel[1];
				surrounding_fields[1][2] = map_generated[core_pixel[0] - 1][core_pixel[1]];
				// }
			}

			if (core_pixel[0] - 1 >= 0
					&& core_pixel[1] + 1 <= map_generated[0].length - 1) {
				// if (map_generated[core_pixel[0] - 1][core_pixel[1] + 1] < 1)
				// {
				surrounding_fields[2][0] = core_pixel[0] - 1;
				surrounding_fields[2][1] = core_pixel[1] + 1;
				surrounding_fields[2][2] = map_generated[core_pixel[0] - 1][core_pixel[1] + 1];
				// }
			}

			if (core_pixel[1] + 1 <= map_generated[0].length - 1) {
				// if (map_generated[core_pixel[0]][core_pixel[1] + 1] < 1) {
				surrounding_fields[3][0] = core_pixel[0];
				surrounding_fields[3][1] = core_pixel[1] + 1;
				surrounding_fields[3][2] = map_generated[core_pixel[0]][core_pixel[1] + 1];
				// }
			}

			if (core_pixel[0] + 1 <= map_generated.length - 1
					&& core_pixel[1] + 1 <= map_generated[0].length - 1) {
				// if (map_generated[core_pixel[0] + 1][core_pixel[1] + 1] < 1)
				// {
				surrounding_fields[4][0] = core_pixel[0] + 1;
				surrounding_fields[4][1] = core_pixel[1] + 1;
				surrounding_fields[4][2] = map_generated[core_pixel[0] + 1][core_pixel[1] + 1];
				// }
			}

			if (core_pixel[0] + 1 <= map_generated.length - 1) {
				// if (map_generated[core_pixel[0] + 1][core_pixel[1]] < 1) {
				surrounding_fields[5][0] = core_pixel[0] + 1;
				surrounding_fields[5][1] = core_pixel[1];
				surrounding_fields[5][2] = map_generated[core_pixel[0] + 1][core_pixel[1]];
				// }
			}

			if (core_pixel[0] + 1 <= map_generated.length - 1
					&& core_pixel[1] - 1 >= 0) {
				// if (map_generated[core_pixel[0] + 1][core_pixel[1] - 1] < 1)
				// {
				surrounding_fields[6][0] = core_pixel[0] + 1;
				surrounding_fields[6][1] = core_pixel[1] - 1;
				surrounding_fields[6][2] = map_generated[core_pixel[0] + 1][core_pixel[1] - 1];
				// }
			}

			if (core_pixel[1] - 1 >= 0) {
				// if (map_generated[core_pixel[0]][core_pixel[1] - 1] < 1) {
				surrounding_fields[7][0] = core_pixel[0];
				surrounding_fields[7][1] = core_pixel[1] - 1;
				surrounding_fields[7][2] = map_generated[core_pixel[0]][core_pixel[1] - 1];
				// }
			}
		}
		return surrounding_fields;
	}

	public static int[][] create_map() {

		// set starting point: the middle of the array
		// put there 2 to prevent putting there a food field
		int starting_field_row = (int) Math.floor(map_speculatrix.length / 2);
		int starting_field_col = (int) Math
				.floor(map_speculatrix[0].length / 2);
		map_speculatrix[starting_field_row][starting_field_col] = 2;

		// amount of clusters with food
		int food_amount = 10;

		// define how big the single cluster of food will be: e.g.: from 1 to 4
		// food_size[0] = 1;
		// food_size[1] = 4;
		int[] food_size = new int[2];
		food_size[0] = 0;
		food_size[1] = 4;

		// create array to store info about the size of the particular clusters
		int[][] food_cluster_size = new int[food_amount][1];

		int[][] surrounding_food_available = new int[8][2];

		int field_randomized;
		int[] field_randomized_location = new int[2];

		int fields_available = 0;
		int fields_avail_corner = 0;

		int[] field_being_changed = new int[2];
		int fields_changed_available = 0;
		Boolean break_order = false;

		int field_to_feed = 0;
		int[] surr_zeros_to_two = new int[2];

		// randomize the size of the clusters
		for (int i = 0; i < food_amount; i++) {
			food_cluster_size[i][0] = rand.randInt(food_size[0], food_size[1]);
			System.out.println(food_cluster_size[i][0]);
		}
		food_cluster_sort(food_cluster_size);

		/**
		 * 
		 * 
		 * ################## MAIN LOOP ##################
		 * 
		 * 
		 */

		// randomize the field and put there a core food pixel (as number 1)
		for (int i = 0; i < food_amount; i++) {
			// System.out.println("\n");

			// check how many fields are available
			for (int j = 0; j < map_speculatrix.length; j++) {
				for (int k = 0; k < map_speculatrix[j].length; k++) {
					if (map_speculatrix[j][k] == 0) {
						fields_available += 1;
					}
					// System.out.print("\n");
				}
			}

			// if there is no field available then break the main loop
			if (fields_available == 0) {
				System.out.println(i + " core pixels put.");
				System.out
						.println("There are no more available fields. Breaking.\n");
				break;
			}

			// create the array with as many rows as many fields_available
			// there are
			int[][] fields_aval_locations = new int[fields_available][2];

			// reset fields_available counter
			// it will be used to enumerate fields_aval_locations[][]
			// counter
			System.out.print("Fields avaliable: " + fields_available);
			System.out.print("\n");
			fields_available = 0;

			for (int j = 0; j < map_speculatrix.length; j++) {
				for (int k = 0; k < map_speculatrix[j].length; k++) {
					if (map_speculatrix[j][k] == 0) {
						fields_aval_locations[fields_available][0] = j;
						fields_aval_locations[fields_available][1] = k;
						fields_available += 1;
					}
				}
			}

			fields_available = 0;

			// fields_aval_show(fields_aval_locations);

			// randomize some field from fields_aval_locations[][]
			// and assign its locations
			field_randomized = rand
					.randInt(0, fields_aval_locations.length - 1);
			field_randomized_location[0] = fields_aval_locations[field_randomized][0];
			field_randomized_location[1] = fields_aval_locations[field_randomized][1];

			System.out.println("Field randomized:");
			System.out.println(field_randomized_location[0] + " "
					+ field_randomized_location[1]);
			System.out.println("");

			surrounding_food_available = add_surrounding_food_pixel(
					field_randomized_location, map_speculatrix);

			// check if cluster will have enough space around the randomized
			// field
			// see: cornered core pixels have at most 3 fields available.
			// If cluster size i e.g. 4 - it won't fit in
			for (int j = 0; j < surrounding_food_available.length; j++) {
				if (surrounding_food_available[j][2] == 0
						|| surrounding_food_available[j][2] == 3) {
					fields_avail_corner += 1;
				}
			}
			if (food_cluster_size[i][0] <= fields_avail_corner) {

				// changes the randomized location on the map to 1 (a food
				// field)
				map_speculatrix[field_randomized_location[0]][field_randomized_location[1]] = 1;

				// changes the available field around the core pixel to number 2
				for (int j = 0; j < surrounding_food_available.length; j++) {
					for (int k = 0; k < surrounding_food_available[0].length; k++) {
						if (k == 2) {
							break;
						}
						field_being_changed[k] = surrounding_food_available[j][k];
					}

					for (int l = 0; l < surrounding_food_available.length; l++) {
						if (surrounding_food_available[l][2] == 2) {
							break_order = true;
						}
					}

					if (break_order) {
						i -= 1;
						map_speculatrix[field_randomized_location[0]][field_randomized_location[1]] = 3;
						System.out.println("Field not allowed. Braking.");
						break_order = false;
						break;
					}

					// if the surrounding field being checked is outside the map
					// then it's 0 and 1 are -1's

					if (field_being_changed[0] >= 0) {
						map_speculatrix[field_being_changed[0]][field_being_changed[1]] = 2;
						fields_changed_available += 1;
					}

				}
			} else {
				System.out
						.println("No place to put cluster of that size with this core pixel.");
				System.out
						.println("Lookin for another field to put this core pixel.");
				i -= 1;
			}

			// if cluster is bigger than 1, the additional pixels have to be
			// placed near "core" field
			//
			// there are at least two ways to place the cluster:
			// 1. Center-oriented
			// 2. Peripheral-oriented (worm-like)
			// Here I use the centered like. Grass (food for the turtles)
			// usually grows in all directions from one point.
			if (fields_changed_available != 0) {
				System.out.println("fields_changed_available: "
						+ fields_changed_available);
				System.out.println("food_cluster_size_index: " + i + " "
						+ food_cluster_size[i][0]);

				for (int j = 0; j < food_cluster_size[i][0]; j++) {

					surrounding_food_available = add_surrounding_food_pixel(
							field_randomized_location, map_speculatrix);
					int counter_available = 0;
					for (int k = 0; k < surrounding_food_available.length; k++) {
						if (surrounding_food_available[k][2] == 2) {
							counter_available += 1;
						}
					}
					System.out.println("counter_available: "
							+ counter_available);

					int[][] fields_around_core = new int[counter_available][2];

					int counter = counter_available;
					for (int k = 0; k < surrounding_food_available.length; k++) {
						// System.out.println("counter: " + counter);
						if (surrounding_food_available[k][2] == 2) {
							if (counter != 0) {
								fields_around_core[counter - 1][0] = surrounding_food_available[k][0];
								fields_around_core[counter - 1][1] = surrounding_food_available[k][1];
								counter -= 1;
							}
						}
					}
					for (int l = 0; l < fields_around_core.length; l++) {
						System.out.println("x: " + fields_around_core[l][0]
								+ ";    y: " + fields_around_core[l][1]);
					}

					field_to_feed = rand.randInt(0,
							fields_around_core.length - 1);
					map_speculatrix[fields_around_core[field_to_feed][0]][fields_around_core[field_to_feed][1]] = 1;

					System.out.println(counter_available + " "
							+ fields_around_core[field_to_feed][0] + " "
							+ fields_around_core[field_to_feed][1]);
					System.out.println("\n");

					surr_zeros_to_two[0] = fields_around_core[field_to_feed][0];
					surr_zeros_to_two[1] = fields_around_core[field_to_feed][1];

					surrounding_food_available = add_surrounding_food_pixel(
							surr_zeros_to_two, map_speculatrix);

					for (int k = 0; k < surrounding_food_available.length; k++) {
						if (surrounding_food_available[k][2] == 0) {
							map_speculatrix[surrounding_food_available[k][0]][surrounding_food_available[k][1]] = 2;
						}
					}

				}

				surrounding_food_available = add_surrounding_food_pixel(
						field_randomized_location, map_speculatrix);

			}

			fields_changed_available = 0;

			/*
			 * 
			 * To be able to see map during generating uncomment the line below
			 */
			// map_show(map_speculatrix);

		}

		// map_show();
		food_cluster_size_show(food_cluster_size);
		map_clean(map_speculatrix);
		map_show(map_speculatrix);

		int count = 0;
		for (int i = 0; i < map_speculatrix.length; i++) {
			for (int j = 0; j < map_speculatrix[i].length; j++) {
				if (map_speculatrix[i][j] == 1) {
					count += 1;
				}
			}
		}
		System.out.println("There are :      " + count
				+ " pixels with the food.");

		food_max = count;
		count = 0;

		for (int i = 0; i < food_cluster_size.length; i++) {
			count += food_cluster_size[i][0];
		}
		count += food_amount;
		System.out.println("There should be: " + count
				+ " pixels with the food.");

		return map_speculatrix;

	}
}