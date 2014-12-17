package robocik;

//import java.util.ArrayList;
//import java.util.Random;

import lejos.nxt.Button;
//import lejos.nxt.Motor;
//import lejos.nxt.Sound;
//import lejos.robotics.navigation.Pose;

public class SpeculatrixGRIK {

	/**
	 * 
	 * @author Mikolaj "Jesmasta" Buchwald
	 * @param args
	 * @throws InterruptedException
	 * 
	 * 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		motors.initNavi();
		System.out.println("Press any button, please");
		Button.waitForAnyPress();
		System.out.println("I'm ready to proceed");
		int odl;

		while (true) {
			odl = sensors.ucho.getDistance();
			if (odl < 10) {
				motors.pilot.travel(-10);
				break;
			}

			// if (sensors.prawy_palec.isPressed()) {
			// motors.pilot.travel(20);
			// }
			// Thread.sleep(1000);
			//
			// if (sensors.lewy_palec.isPressed()) {
			// motors.pilot.travel(-20);
			// }

		}

	}

}