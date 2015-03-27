package speculatrix_main;

import java.util.Random;

public class rand {

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
	 * Returns a pseudo-random array with numbers between min and max,
	 * inclusive. The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Array with two integers between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int[] randIntArray(int min, int max) {

		int[] randArray = new int[2];
		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		randArray[0] = rand.nextInt((max - min) + 1) + min;
		randArray[1] = rand.nextInt((max - min) + 1) + min;

		return randArray;
	}

	/**
	 * Returns a pseudo-random array containing a location of one of the
	 * available points. Available means not yet explored field. Here: number 2.
	 *
	 * @param map_explored
	 * @return Array with the available location [0] = x and [1] = y
	 * @see java.util.Random#nextInt(int)
	 */
	public static int[] randLoc(int[][] map_explored) {

		int[][] avail_loc = map.get_avail_loc(map_explored);
		int[] randArrayLoc = new int[2];
		int rand_loc = -1;
		
		System.out.println("locations_avail: " + avail_loc.length);
		
		if (avail_loc.length > 0) {
			rand_loc = randInt(0, avail_loc.length - 1);
			randArrayLoc[0] = avail_loc[rand_loc][0];
			randArrayLoc[1] = avail_loc[rand_loc][1];
		}
		else{
			randArrayLoc[0] = 7;
			randArrayLoc[1] = 7;
			System.out.println("NO MORE AVAILABLE LOCATIONS!");
			System.out.println("Staying at the middle");
		}

		return randArrayLoc;
	}
}
