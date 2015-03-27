package speculatrix_main;

public class linear_path {

	public static int x_found = -1;
	public static int y_found = -1;
	public static double dist = 0;
	public static double rota = 0;
	public static boolean food = false;
	public static double degree_previous = 0;

	// distance = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	public static double distance(double x1, double y1, double x2, double y2) {
		double distance;
		distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return distance;
	}

	private static int get_axis_len(double coord1, double coord2) {
		int axis_len = (int) Math.abs(coord1 - coord2);
		return axis_len;
	}

	public static int[][] create_coord_array(double x1, double y1, double x2,
			double y2) {
		int x_y_len = (int) (Math.abs(x1 - x2) + Math.abs(y1 - y2));
		int[][] coord_array = new int[x_y_len][3];
		return coord_array;
	}

	public static double degree(double x1, double y1, int x2, int y2) {
		double degree = 0;
		double term = 0;
		// a = tan(Alpha);
		term = (y2 - y1) / (x2 - x1);
		System.out.println("term: " + term);
		degree = Math.toDegrees(Math.atan(term));
		return degree;
	}

	public static int[][] path(int[][] map_specul, double x1, double y1,
			double x2, double y2) {
		double x = 0;
		double y = 0;
		int x_len = get_axis_len(x1, x2);
		int y_len = get_axis_len(y1, y2);
		int[][] coordinates = new int[(x_len + y_len) * 8][3];
		double distance_x = 0;
		double distance_y = 0;
		int counter = 0;

		/*
		 * "X" axis analysis search for the trespassed fields by the X axis
		 */
		for (int i = 0; i < Math.abs(x1 - x2) * 8; i++) {
			if (i % 8 == 0 && i > 0) {
				counter += 1;
			}
			if (x1 <= x2) {
				x = x1 + counter + ((double) i % 8) / 8;
			}
			if (x2 <= x1) {
				x = x1 - counter - ((double) i % 8) / 8;
			}

			y = (((y2 - y1) * (x - x1)) / (x2 - x1)) + y1;

			y = Math.floor((((y2 - y1) * (x - x1)) / (x2 - x1)) + y1);

			coordinates[i][0] = (int) x;
			coordinates[i][1] = (int) y;
			coordinates[i][2] = map_specul[(int) x][(int) y];
		}

		/*
		 * "Y" axis analysis search for the trespassed fields by the Y axis
		 */
		counter = 0;

		for (int i = 0; i < Math.abs(y1 - y2) * 8; i++) {
			if (i % 8 == 0 && i > 0) {
				counter += 1;
			}

			if (y1 <= y2) {
				y = y1 + counter + ((double) i % 8) / 8;
			}
			if (y2 <= y1) {
				y = y1 - counter - ((double) i % 8) / 8;
			}

			x = (((x2 - x1) * (y - y1)) / (y2 - y1)) + x1;

			x = Math.floor((((x2 - x1) * (y - y1)) / (y2 - y1)) + x1);

			coordinates[x_len * 8 + i][0] = (int) x;
			coordinates[x_len * 8 + i][1] = (int) y;
			coordinates[x_len * 8 + i][2] = map_specul[(int) x][(int) y];
		}
		return coordinates;
	}

	public static int[][] walk(int[][] map_walk, int[][] map_specul,
			int[][] coords, double x1, double y1) {
		int food_detected = 0;
		int x_detected = (int) x1;
		int y_detected = (int) y1;
		int coord_count = 0;

		double degree_current = 0;

		// maximal distance on the map.
		double near_dist = distance(0, 0, map_specul.length - 1,
				map_specul.length - 1);

		// counting how many coord picks there are outside the first field
		for (int i = 0; i < coords.length; i++) {
			if (coords[i][0] != x1 || coords[i][1] != y1) {
				coord_count += 1;
			}
		}

		int[][] coord_correct = new int[coord_count][3];

		int coord_corr_count = 0;
		for (int i = 0; i < coords.length; i++) {
			if (coords[i][0] != x1 || coords[i][1] != y1) {
				// System.out.println("coords_length: " + coords.length
				// + "; coord_correct: " + coord_correct.length);
				coord_correct[coord_corr_count][0] = coords[i][0];
				coord_correct[coord_corr_count][1] = coords[i][1];
				coord_correct[coord_corr_count][2] = coords[i][2];
				coord_corr_count += 1;
			}
		}

		// check how many food fields there are in the path
		for (int i = 0; i < coord_correct.length; i++) {
			if (coord_correct[i][2] == 1) {
				food_detected += 1;
			}
		}

		// if there is at least one food field check which of them is the
		// nearest to the starting point
		if (food_detected != 0) {

			// get the food locations
			int[][] food_loc = new int[food_detected][2];
			for (int i = 0; i < coord_correct.length; i++) {
				if (coord_correct[i][2] == 1) {
					food_loc[food_detected - 1][0] = coord_correct[i][0];
					food_loc[food_detected - 1][1] = coord_correct[i][1];
					food_detected -= 1;
				}
			}
			// iterate trough the food locations, check which one is the nearest
			for (int i = 0; i < food_loc.length; i++) {
				if (near_dist > distance(x1, y1, (double) food_loc[i][0],
						(double) food_loc[i][1])) {
					near_dist = distance(x1, y1, (double) food_loc[i][0],
							(double) food_loc[i][1]);

					x_detected = food_loc[i][0];
					y_detected = food_loc[i][1];

					x_found = x_detected;
					y_found = y_detected;
					food = true;
				}
			}
			System.out.println("x_detected: " + x_detected + "\ny_detected: "
					+ y_detected + "\n");

			// walk the path until you find the food field
			for (int i = 0; i < coord_correct.length; i++) {
				if (distance(x1, y1, coord_correct[i][0], coord_correct[i][1]) < near_dist) {
					map_walk[coord_correct[i][0]][coord_correct[i][1]] = coord_correct[i][2];
				}
				if (distance(x1, y1, coord_correct[i][0], coord_correct[i][1]) == near_dist) {
					map_walk[coord_correct[i][0]][coord_correct[i][1]] = 3;
				}
			}
		}
		// if there are no food fields just walk the path
		else {
			for (int i = 0; i < coord_correct.length; i++) {
				map_walk[coord_correct[i][0]][coord_correct[i][1]] = coord_correct[i][2];
			}
			x_found = coord_correct[coord_correct.length - 1][0];
			y_found = coord_correct[coord_correct.length - 1][1];

			food = false;
		}

		dist = distance(x1, y1, x_found, y_found);
		 
		degree_current = degree(x1, y1, x_found, y_found);
		System.out.println("degree_previous: " + degree_previous);
		System.out.println("degree_current: " + degree_current);
		
		//TODO: to get rota the direction is needed
//		rota = degree_previous - degree_current;
		rota = Math.abs(degree_previous - degree_current);
		degree_previous = degree_current;
		
		System.out.println("rota: " + rota);
		return map_walk;
	}
}
