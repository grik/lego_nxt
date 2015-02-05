package speculatrix_main;

import java.util.Random;

public class states {

	/*
	 * 
	 * ground_check() checks what is beneath the robot
	 * 
	 */
	static int randomedInt;
	static boolean state_ground;
	public static boolean ground_check() {

		// temporarily use of the Random
		Random randomGenerator = new Random();
		randomedInt = randomGenerator.nextInt(100);

		if (randomedInt % 2 == 0) {
			state_ground = true;
		} else {
			state_ground = false;
		}
		System.out.println(state_ground);

		return state_ground;
	}

}
