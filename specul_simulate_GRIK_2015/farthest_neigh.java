package speculatrix_main;

import java.util.Arrays;

public class farthest_neigh {

	/*
	 * gets the info about the particular available field 0 - x, 1 - y, 2 -
	 * mean_dist to food fields, 3 - dist to robot, 4 - dist to North side, 5 -
	 * dist to East side, 6 - dist to South side, 7 - dist to West side
	 */

	public static double[][] get_avail_info(int[][] map_explored,
			double[][][] side_dist, int x_robot, int y_robot) {
		int[][] avail_loc = map.get_avail_loc(map_explored);
		double[][] dist_avail_to_food = map.get_dist_avail_to_food(
				map_explored, avail_loc);
		double[][] dist_avail_to_expl = map.get_dist_avail_to_expl(
				map_explored, avail_loc);
		double[][] dist_robot_to_avail = robot.get_dist_robot_to_avail(
				avail_loc, x_robot, y_robot);
		// double[][][] side_dist = map.get_side_dist();

		double[][] avail_full_info = new double[avail_loc.length][9];
		for (double[] row : avail_full_info)
			Arrays.fill(row, 1.0);

		for (int i = 0; i < avail_loc.length; i++) {
			avail_full_info[i][0] = avail_loc[i][0];
			avail_full_info[i][1] = avail_loc[i][1];
			avail_full_info[i][2] = dist_avail_to_food[i][2];
			avail_full_info[i][3] = dist_robot_to_avail[i][2];
			avail_full_info[i][4] = side_dist[avail_loc[i][0]][avail_loc[i][1]][0];
			avail_full_info[i][5] = side_dist[avail_loc[i][0]][avail_loc[i][1]][1];
			avail_full_info[i][6] = side_dist[avail_loc[i][0]][avail_loc[i][1]][2];
			avail_full_info[i][7] = side_dist[avail_loc[i][0]][avail_loc[i][1]][3];
			avail_full_info[i][8] = dist_avail_to_expl[i][2];
			// for (int j = 0; j < 9; j++) {
			// System.out.print(avail_full_info[i][j] + " ");
			// }
			// System.out.print("\n");
		}
		return avail_full_info;
	}

	public static double[][] get_fn_params(double[][] avail_info) {
		double[][] fn_params = new double[avail_info.length][11];
		double x_short = 0;
		double y_short = 0;
		double mean_side = 0;
		double a_side = 0;
		double b_side = 0;
		double c_side = 0;

		for (int i = 0; i < avail_info.length; i++) {
			fn_params[i][0] = avail_info[i][0];
			fn_params[i][1] = avail_info[i][1];

			// System.out.println(avail_info[i][4] + " < " + avail_info[i][6]);
			if (avail_info[i][4] < avail_info[i][6]) {
				x_short = avail_info[i][4];
			} else {
				x_short = avail_info[i][6];
			}
			// System.out.println(avail_info[i][5] + " < " + avail_info[i][7]);
			if (avail_info[i][5] < avail_info[i][7]) {
				y_short = avail_info[i][5];
			} else {
				y_short = avail_info[i][7];
			}

			// System.out.println("x: " + avail_info[i][0] + "; y: "
			// + avail_info[i][1]);
			// System.out.println("x_short: " + x_short + "y_short: " +
			// y_short);
			// System.out.println("avail_info[i][2]: " + avail_info[i][2]);
			// System.out.println("Math.abs(avail_info[i][2] - x_short): "
			// + Math.abs(avail_info[i][2] - x_short));
			// System.out.println("Math.abs(avail_info[i][2] - y_short): "
			// + Math.abs(avail_info[i][2] - y_short));

			fn_params[i][2] = avail_info[i][2];
			fn_params[i][3] = avail_info[i][3];
			fn_params[i][4] = avail_info[i][8];
			if (!Double.isNaN(avail_info[i][2])) {
				// a_side = Math.abs(x_short - avail_info[i][2]);
				// b_side = Math.abs(y_short - avail_info[i][2]);
				// c_side = Math.abs(x_short - x_short);

				double in[] = { x_short, y_short, 1.2 * fn_params[i][2] };
				fn_params[i][5] = tools.round(stats.stdev(in), 2);
			} else {

				// System.out.println("x: " + fn_params[i][0] + "; y: "
				// + fn_params[i][1] + " || " + x_short + " " + y_short + " "
				// + fn_params[i][3]);
				double in[] = { x_short, y_short, 0.55 * fn_params[i][3] };
				// double in[] = { x_short, y_short, fn_params[i][3] };
				fn_params[i][5] = tools.round(stats.stdev(in), 2);

			}

			// taking mean of the sides has no sense. Just take side dist
			// separetly. Then you can compare meand_dist_to_fields to
			// dist_to_the_nearest_side seprately.

			// distance weight
			fn_params[i][6] = 0.3 * fn_params[i][2];

			// explored weight
			fn_params[i][7] = 1.7 * fn_params[i][4];

			// side weight
			fn_params[i][8] = 3.5 * fn_params[i][5];

			if (!Double.isNaN(fn_params[i][6])) {
				fn_params[i][9] = fn_params[i][6] - fn_params[i][7]
						+ fn_params[i][8];
			} else {
				fn_params[i][9] = fn_params[i][7] - fn_params[i][8];
			}
			// System.out.println(fn_params[i][9]);

			fn_params[i][10] = tools.round(fn_params[i][9] + 0.2
					* fn_params[i][3], 2);

			// fn_params[i][2] = tools.round(
			// Math.abs(avail_info[i][2] - mean_side), 2);
			// System.out.println(fn_params[i][2] + " = " + mean_side + " - "
			// + avail_info[i][2]);

			// fn_params[i][3] = tools.round(avail_info[i][3], 2);

			// for (int j = 0; j < fn_params[0].length; j++) {
			// System.out.print(fn_params[i][j] + " ");
			// }
			// System.out.print("\n");
		}
		return fn_params;
	}

	public static int[] get_fn_decision(double[][] fn_params, int column) {
		int[] fn_decision = new int[2];
		double highest = fn_params[0][column];

		for (int i = 0; i < fn_params.length; i++) {
			if (fn_params[i][column] >= highest) {
				// System.out.println("\n\n" +(int) fn_params[i][0] + " "
				// + (int) fn_params[i][1] + " "
				// + fn_params[i][column] + "\n\n");
				fn_decision[0] = (int) fn_params[i][0];
				fn_decision[1] = (int) fn_params[i][1];
				highest = fn_params[i][column];
			}
		}
		System.out.println("fn_decision: " + fn_decision[0] + " "
				+ fn_decision[1]);
		return fn_decision;
	}
}
