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
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		System.out.println("Press any button, please");
		Button.waitForAnyPress();
		System.out.println("I'm ready to proceed");
	
		motors.initNavi();
		//motors.navigator.getPoseProvider();
//		System.out.println(motors.pp.getPose());
//		motors.pilot.travel(40);
//		System.out.println(motors.pp.getPose());
//		motors.pilot.rotate(90);
//		motors.pilot.travel(40);
//		System.out.println(motors.pp.getPose());
//		motors.navigator.goTo(0,0);
//		motors.navigator.rotateTo(90);
		motors.navigator.goTo(40,-40);
		motors.navigator.singleStep(true);
		System.out.println(motors.navigator.getWaypoint().getPose());
		motors.navigator.goTo(0,0);
//		motors.navigator.rotateTo(0);
//		System.out.println(motors.navigator.getWaypoint());
//		System.out.println(motors.navigator.getPoseProvider());
//		System.out.println(motors.navigator.getMoveController());
//		motors.navigator.goTo(0,0);
		System.out.println(motors.pp.getPose());
//		motors.navigator.goTo(50,0);
//		System.out.println(motors.pp.getPose());
	    
		
//		motors.pp.goTo(start);
//		goTo(start);
//		motors.pilot.goTo(start);
//		motors.pilot.

		Button.waitForAnyPress();
		
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
