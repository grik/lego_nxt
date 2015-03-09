package robot;

import java.util.Random;

//import lejos.nxt.LCD;
//import lejos.nxt.Motor;
//import lejos.nxt.NXT;
import lejos.nxt.Sound;

public class tools {
	
	public static double hp = 100;
	static double hp_mov = 1;
	static double hp_still = 0.5;
	
    // randomize two ints (into the array)
    // for the random moves around the map
	public static int[] randInt(int min, int max){
		int[] randomInt = new int[2];
		Random rand = new Random();
		int num_01 = rand.nextInt((max - min) + 1) + min;
		int num_02 = rand.nextInt((max - min) + 1) + min;
		
		randomInt[0] = num_01;
		randomInt[1] = num_02;
	
		return randomInt;
	}

	// time interval between ground scans and cognitive processes sum up
	// in milliseconds
	// public static int time_tick_interval = 500;
	public static class Timer extends Thread { // to s³u¿y temu, aby robot nie
												// przekroczy³ czasu...

		private int time_left = 0;

		public Timer(int time_to_die) {
			time_left = time_to_die;
			System.out.println(time_to_die + " time_to_die");

		}

		// TODO: get time
		public void sztop() {
			time_left = 1000000;
			System.out.println(time_left + " time_left");

		}

		@SuppressWarnings("deprecation")
		public void run() {
			String field_color = "unknown";
			while (time_left > 0) {
				try {
					time_left--;
//					System.out.println(time_left + " time_left");
					if(sensors.jakiKolorPola() == 1){
//						field_color = "white";
//						map.map_main[map.get_location()[0]][map.get_location()[1]] = 0;
					}
					else{
//						field_color = "black";
//						map.map_main[map.get_location()[0]][map.get_location()[1]] = 1;
					}
//					System.out.println(field_color + "; at: "
//							+ map.square_side);
//					System.out.println("x:" + map.get_location()[0] + "; y:"
//							+ map.get_location()[1]);
//					System.out.println("x:" + map.get_location()[0] + "; y:"
//							+ map.get_location()[1]);
					
//					if (motors.nav_leg.isMoving()){
//						hp -= hp_mov;
//					}
//					else{
//						hp -= hp_still;
//					}
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			}

			koniec();
		}
	}

	public static void doWait(long wait_time) {
		try {
			Thread.sleep(wait_time);
		} catch (Exception e) {
		}
	}

	public static void koniec() {
		System.out.println("en fin");
		System.exit(1);
		// LCD.clearDisplay();
		// LCD.clear();
		//
		// PIN_2013.timer.sztop();
		// motors.pilot.stop();
		// motors.navigator.stop();
		// Motor.C.stop();
		// while (motors.pilot.isMoving()){}
		// while (motors.navigator.isMoving()){}
		// while (Motor.C.isMoving()){}
		// motors.pilot.stop();
		// motors.navigator.stop();
		// Motor.C.stop();
		Sound.twoBeeps();
		// NXT.shutDown();
	}

}
