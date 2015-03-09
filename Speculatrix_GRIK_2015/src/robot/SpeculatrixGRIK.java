package robot;

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
	 * @author Mikolaj "Cereberus" Buchwald
	 * @param args
	 * @throws InterruptedException
	 * 
	 * 
	 */
	
	static public tools.Timer timer = new tools.Timer(1 * 12);
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {
		int rand_x = 0;
		int rand_y = 0;
		
        // initiate the map
        // map_positions() sets the centimeters for every point in the map's 2D array
		map.map_positions = map.create_map_positions();
        // fill_main() fills the map with 2's (except from the starting point 0,0)
		map.map_main = map.fill_map_main();

		System.out.println("Press any button, please");
		Button.waitForAnyPress();
		System.out.println("I'm ready to proceed");
		
        // Set up the timer and prompt the info about it.
		timer.setDaemon(true);
		timer.start();
		System.out.println("timer start");

		motors.initLegacyNavi();
		
        // A loop thats sends robot to some random places.
        // This is the very beginning of the control condition.
		for (int i=0; i<4; i++){
			rand_x = tools.randInt(0, 5)[0];
			rand_y = tools.randInt(0, 5)[1];
			System.out.println("x: " + rand_x + " y: " + rand_y);
			motors.nav_leg.goTo(map.map_positions[rand_x][rand_y][0], map.map_positions[rand_x][rand_y][1], false);
		}
		
        // wait for the final button press before exiting the program
		System.out.println("Press any button, please");
		Button.waitForAnyPress();
	}

}
