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
	 * @author Mikolaj "Jesmasta" Buchwald
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
		
		// TODO Auto-generated method stub
		
		map.map_positions = map.create_map_positions();
		map.map_main = map.fill_map_main();

		System.out.println("Press any button, please");
		Button.waitForAnyPress();
		System.out.println("I'm ready to proceed");
		
		timer.setDaemon(true);
		timer.start();
		System.out.println("timer start");

		motors.initLegacyNavi();
		
		int[] randomed = new int[2];
		
		for (int i=0; i<4; i++){
			randomed = tools.randInt(0,5);
			rand_x = tools.randInt(0, 5)[0];
			rand_y = tools.randInt(0, 5)[1];
			System.out.println("x: " + rand_x + " y: " + rand_y);
			motors.nav_leg.goTo(map.map_positions[rand_x][rand_y][0], map.map_positions[rand_x][rand_y][1], false);
		}
		
		
//		motors.nav_leg.goTo(map.map_positions[0][3][0], map.map_positions[0][3][1], false);
//		motors.nav_leg.travel(16*6, false);

//		while (true) {
//
//			motors.nav_leg.goTo(80, 80, false);
//
////			System.out.println(sensors.jakiKolorPola() + " " + map.square_side);
////			System.out.println("x:" + map.get_location()[0] + "; y:"
////					+ map.get_location()[1]);
//			Thread.sleep(2000);
//
//		}
		System.out.println("Press any button, please");
		Button.waitForAnyPress();
	}

}

// }
