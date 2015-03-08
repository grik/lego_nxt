package robocik;

//import java.util.ArrayList;
//import java.util.Random;

import lejos.nxt.Button;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MoveListener;
//import lejos.nxt.Motor;
//import lejos.nxt.Sound;
//import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Pose;

public class SpeculatrixGRIK {

	/**
	 * 
	 * @author Mikolaj "Jesmasta" Buchwald
	 * @param args
	 * @throws InterruptedException
	 * 
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		System.out.println("Press any button, please");
		Button.waitForAnyPress();
		System.out.println("I'm ready to proceed");

		motors.initLegacyNavi();
		// motors.pilot.travel(20, false);
		// System.out.println("X: " + motors.nav_leg.getX());
		// motors.nav_leg.goTo(40, 40, true);
		Boolean decision = true;
		while (true) {
			if (decision) {
				motors.nav_leg.goTo(80, 80, true);
			}
			System.out.println(sensors.jakiKolorPola() + " " + map.square_side);
			System.out.println("x:" + map.get_location()[0] + "; y:"
					+ map.get_location()[1]);
			Thread.sleep(2000);
			if (map.get_location()[0] > 40 && decision) {
				motors.nav_leg.goTo(-20, -60, true);
				decision = false;
			}

		}

		// System.out.println("X: " + motors.nav_leg.getX());
		// Thread.sleep(2000);
		// System.out.println("X: " + motors.nav_leg.getX());
		// System.out.println("finito");

		// Button.waitForAnyPress();

	}

}
