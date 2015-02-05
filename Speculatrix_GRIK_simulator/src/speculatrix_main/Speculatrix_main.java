package speculatrix_main;

import java.util.*;

public class Speculatrix_main {

	public static void main(String[] args) {

		// final boolean state_ground;
		Timer timer = new Timer();

		timer.schedule(new TimerTask() {
			int life_energy = 100;
			boolean state_ground;

			public void run() {

				state_ground = states.ground_check();
				if (state_ground) {
					life_energy -= 2;
				} else {
					life_energy -= 1;
				}

				System.out.println(life_energy);

			}
		}, 1 * 1 * 500, 1 * 1 * 500);

		// static public tools.Timer timer = new tools.Timer(4 * 60 - 5);
		double a;
		// a = motors.odleglosc(14, 12, 10, 5);
		// System.out.println(a);

	}
}
