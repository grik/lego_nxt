package robot;

import java.util.*;

public class map {

	public static int map_size = 6;
	public static int square_side = 16;

	static float[][][] map_positions = new float[map_size][map_size][2];
	static int[][] map_main = new int[map_size][map_size];
	
	/*
	 * 0 - food has never been
	 * 1 - food was but is no more
	 * 2 - i wasn't there but there can be some food
	 */
	public static int[][] fill_map_main() {
		for (int i = 0; i < map_main.length; i++) {
			for (int j = 0; j < map_main[i].length; j++) {
				map_main[i][j] = 2;
			}
		}
		map_main[0][0] = 0;
		return map_main;
	}

	public static float[][][] create_map_positions() {
		for (int i = 0; i < map_positions.length; i++) {
			for (int j = 0; j < map_positions[i].length; j++) {
				map_positions[i][j][0] = (i) * square_side + (square_side);
				map_positions[i][j][1] = (j) * square_side + (square_side);
			}
		}
		return map_positions;
	}

//	public static float[] pos(int x, int y) {
//		return map_positions[x][y];
//	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@SuppressWarnings("deprecation")
	public static int[] get_location() {
		int[] location = new int[2];
		location[0] = (int) Math.floor(motors.nav_leg.getX() / square_side);
		location[1] = (int) Math.floor(motors.nav_leg.getY() / square_side);
		return location;
	}

	@SuppressWarnings("deprecation")
	public static double[] get_location_exact() {
		double[] location = new double[2];
		location[0] = round(motors.nav_leg.getX(), 2);
		location[1] = round(motors.nav_leg.getY(), 2);
		return location;
	}

}
