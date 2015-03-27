package speculatrix_main;

public class linear {

	// distance = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	private static double distance(double x1, double x2, double y1, double y2) {
		double distance;
		distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return distance;
	}

	// (x2-x1)(y-y1)=(y2-y1)(x-x1)
	// public static void two_points(double x1, double y1, double x2, double y2)
	// {
	// double x = 0;
	// double y = 0;
	// System.out.println("|" + x1 + " - " + x2 + "| = " + Math.abs(x1 - x2));
	//
	// System.out.println("x");
	// for (int i = 0; i < Math.abs(x1 - x2) - 1; i++) {
	// if (x1 <= x2) {
	// x = x1 + i + 1;
	// }
	// if (x2 <= x1) {
	// x = x1 - i - 1;
	// }
	//
	// y = Math.floor((((y2 - y1) * (x - x1)) / (x2 - x1)) + y1);
	// // y = (((y2 - y1) * (x - x1)) / (x2 - x1)) + y1;
	//
	// System.out.print(x + " " + y);
	// System.out.print("; " + distance(x1, x2, x, y) + "\n");
	// }
	//
	// System.out.println("y");
	//
	// for (int i = 0; i < Math.abs(y1 - y2) - 1; i++) {
	// if (y1 <= y2) {
	// y = y1 + i + 1;
	// }
	// if (y2 <= y1) {
	// y = y1 - i - 1;
	// }
	//
	// x = Math.floor((((x2 - x1) * (y - y1)) / (y2 - y1)) + x1);
	// // x = (((x2 - x1) * (y - y1)) / (y2 - y1)) + x1;
	//
	// System.out.print(x + " " + y);
	// System.out.print("; " + distance(x1, x2, x, y) + "\n");
	// }
	// }

	public static void path(int[][] map_walk, int[][] map_specul, double x1,
			double y1, double x2, double y2) {
		double x = 0;
		double y = 0;
		double distance_x = 0;
		double distance_y = 0;
		int counter = 0;
		System.out.println("|" + x1 + " - " + x2 + "| = " + Math.abs(x1 - x2));
		System.out.println("|" + y1 + " - " + y2 + "| = " + Math.abs(y1 - y2));

		System.out.println("x");
		for (int i = 0; i < Math.abs(x1 - x2) * 8; i++) {
			if (i % 8 == 0 && i > 0) {
				counter += 1;
			}

			if (x1 <= x2) {
				x = x1 + counter + ((double) i % 8) / 8;
				// System.out.println(((double) i % 4) / 4);
			}
			if (x2 <= x1) {
				x = x1 - counter - ((double) i % 8) / 8;
			}

			y = (((y2 - y1) * (x - x1)) / (x2 - x1)) + y1;

			System.out.print(x + " " + y);
			System.out.print("; " + distance(x1, x2, x, y) + "\n");

			y = Math.floor((((y2 - y1) * (x - x1)) / (x2 - x1)) + y1);

			if (map_walk[(int) x][(int) y] == 2) {
				if (map_specul[(int) x][(int) y] == 0) {
					map_walk[(int) x][(int) y] = 0;
				}
				if (map_specul[(int) x][(int) y] == 1) {
					map_walk[(int) x][(int) y] = 1;
					distance_x = distance(x1, x2, x, y);
					break;
				}
			}
		}

		System.out.println("y");
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

			System.out.print(x + " " + y);
			System.out.print("; " + distance(x1, x2, x, y) + "\n");

			x = Math.floor((((x2 - x1) * (y - y1)) / (y2 - y1)) + x1);
			
			if (map_walk[(int) x][(int) y] == 2) {
				if (map_specul[(int) x][(int) y] == 0) {
					map_walk[(int) x][(int) y] = 0;
				}
				if (map_specul[(int) x][(int) y] == 1) {
					map_walk[(int) x][(int) y] = 1;
					distance_y = distance(x1, x2, x, y);
					break;
				}
			}
		}
		if (distance_x > distance_y){
			System.out.print("distance X is greater");
		}
		else{
			System.out.print("distance Y is greater");
		}
		
	}
}
