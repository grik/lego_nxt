package speculatrix_main;

public class map {
	public static int count_avail(int[][] map_explored) {
		int avail_num = 0;
		for (int i = 0; i < map_explored.length; i++) {
			for (int j = 0; j < map_explored[0].length; j++) {
				if (map_explored[i][j] == 2) {
					avail_num += 1;
				}
			}
		}
		return avail_num;
	}

	public static int[][] get_avail_loc(int[][] map_explored) {
		int[][] avail_loc = new int[count_avail(map_explored)][2];
		int avail_count = 0;
		for (int i = 0; i < map_explored.length; i++) {
			for (int j = 0; j < map_explored[0].length; j++) {
				if (map_explored[i][j] == 2) {
					avail_loc[avail_count][0] = i;
					avail_loc[avail_count][1] = j;
					avail_count += 1;
				}
			}
		}
		return avail_loc;
	}

	public static int[][] get_food_loc(int[][] map_explored) {
		int food_detected = 0;
		for (int i = 0; i < map_explored.length; i++) {
			for (int j = 0; j < map_explored[0].length; j++) {
				if (map_explored[i][j] == 3) {
					food_detected += 1;
				}
			}
		}
		int food_count = 0;
		int[][] food_loc = new int[food_detected][2];
		for (int i = 0; i < map_explored.length; i++) {
			for (int j = 0; j < map_explored[0].length; j++) {
				if (map_explored[i][j] == 3) {
					food_loc[food_count][0] = i;
					food_loc[food_count][1] = j;
					// System.out.println(i + " " + j);
					food_count += 1;
				}
			}
		}
		return food_loc;
	}

	public static int[][] get_expl_loc(int[][] map_explored) {
		int epxl_detected = 0;
		for (int i = 0; i < map_explored.length; i++) {
			for (int j = 0; j < map_explored[0].length; j++) {
				if (map_explored[i][j] != 2) {
					epxl_detected += 1;
//					System.out.println(epxl_detected + ": epxl_detected");
				}
			}
		}
		int epxl_count = 0;
		int[][] epxl_loc = new int[epxl_detected][2];
		for (int i = 0; i < map_explored.length; i++) {
			for (int j = 0; j < map_explored[0].length; j++) {
				if (map_explored[i][j] != 2) {
					epxl_loc[epxl_count][0] = i;
					epxl_loc[epxl_count][1] = j;
//					 System.out.println(i + " " + j);
					epxl_count += 1;
				}
			}
		}

		return epxl_loc;
	}

	public static double[][][] get_side_dist() {
		int[][] map_speculatrix = MapGenerator.map_speculatrix;
		double[][][] side_dist = new double[map_speculatrix.length][map_speculatrix.length][4];
		double dist_max = linear_path.distance(0, 0, 0,
				map_speculatrix.length - 1);
		System.out.println("dist_max: " + dist_max);

		for (int i = 0; i < map_speculatrix.length; i++) {
			for (int j = 0; j < map_speculatrix[0].length; j++) {
				side_dist[i][j][0] = linear_path.distance(i, j, 0, j);
				side_dist[i][j][1] = linear_path.distance(i, j, i,
						map_speculatrix.length - 1);
				side_dist[i][j][2] = dist_max - side_dist[i][j][0];
				side_dist[i][j][3] = dist_max - side_dist[i][j][1];
				// System.out.println("\nx: " + i + "y: " + j);
				// System.out.println("dist_North: " + side_dist[i][j][0]);
				// System.out.println("dist_East: " + side_dist[i][j][1]);
				// System.out.println("dist_South: " + side_dist[i][j][2]);
				// System.out.println("dist_West: " + side_dist[i][j][3]);
			}
		}
		return side_dist;
	}

	public static double get_dist_mean(int[][] food_loc, int x, int y) {
		double sum_dist = 0;
		double mean_dist = 0;

		for (int i = 0; i < food_loc.length; i++) {
			sum_dist += linear_path.distance(x, y, food_loc[i][0],
					food_loc[i][1]);
		}
		mean_dist = sum_dist / food_loc.length;
		// System.out.println("mean distance");
		// System.out.println(mean_dist + " = " + sum_dist + " / "
		// + food_loc.length);

		return mean_dist;
	}

	public static double[][] get_dist_avail_to_food(int[][] map_explored,
			int[][] avail_loc) {
		int[][] food_loc = get_food_loc(map_explored);

		double[][] dist_avail_to_food = new double[avail_loc.length][3];

		for (int i = 0; i < avail_loc.length; i++) {
			dist_avail_to_food[i][0] = avail_loc[i][0];
			dist_avail_to_food[i][1] = avail_loc[i][1];
			dist_avail_to_food[i][2] = get_dist_mean(food_loc, avail_loc[i][0],
					avail_loc[i][1]);
			// System.out.println(avail_loc[i][0] + " " + avail_loc[i][1] + " "
			// + dist_avail_to_food[i][2]);
		}

		return dist_avail_to_food;
	}

	public static double[][] get_dist_avail_to_expl(int[][] map_explored,
			int[][] avail_loc) {
		int[][] expl_loc = get_expl_loc(map_explored);

//		for (int i = 0; i < expl_loc.length; i++) {
//			for (int j = 0; j < expl_loc[0].length; j++) {
//				System.out.print(expl_loc[i][j] + " ");
//			}
//			System.out.print("\n");
//		}

		double[][] dist_avail_to_expl = new double[avail_loc.length][3];

		for (int i = 0; i < avail_loc.length; i++) {
			dist_avail_to_expl[i][0] = avail_loc[i][0];
			dist_avail_to_expl[i][1] = avail_loc[i][1];
			dist_avail_to_expl[i][2] = get_dist_mean(expl_loc, avail_loc[i][0],
					avail_loc[i][1]);
//			for (int j = 0; j < dist_avail_to_expl[0].length; j++) {
//				System.out.print(dist_avail_to_expl[i][j] + " ");
//			}
//			System.out.print("\n");
		}

		return dist_avail_to_expl;
	}
}
