package robocik;

import lejos.nxt.*;
import java.util.*;

import lejos.robotics.navigation.*;
import lejos.robotics.localization.*;

import lejos.robotics.RegulatedMotor;

/**
 * Simple class that provides motor methods.
 *
 * @author Mikolaj "Jesmasta" Buchwald
 * @version 0.1
 *
 *          I use ann to check if color sensor receive black or white input
 *
 * 
 */

public class motors {
	// motor settings

	public static int def_speed = 10;
	public static int push_speed = 10;
	private static final int turn_speed = 40; // predkosc skretu jest w
												// stopniach , jazdy w
												// sekindach.
	private static final float wheelDiameter = 8.0f;
	private static final float trackWidth = 9.8f; // gasienice po skosie
	private static final RegulatedMotor leftMotor = Motor.B;
	private static final RegulatedMotor rightMotor = Motor.A;
	private static final boolean reverse = false;

	private static final float startx = 0.0f;
	private static final float starty = 0.0f;
	private static final float startkaft = 0.0f;

	// map settings
	public static final float pole_bok = 20.0f;
	public static final float line_width = 2.0f;
	public static final float step = pole_bok + line_width;

	public static final DifferentialPilot pilot = new DifferentialPilot(
			wheelDiameter, trackWidth, leftMotor, rightMotor, reverse);
	
	public static OdometryPoseProvider pp = new OdometryPoseProvider(pilot);
	public static Navigator navigator = new Navigator(pilot, pp);
	private static float startkat = 0;
	public static Pose pose = new Pose(startx, starty, startkat);

	public static double odleglosc(float x1, float y1, float x2, float y2) { // z
																				// pitagorasa
		return Math.abs(Math.sqrt(((x1 - x2) * (x1 - x2))
				+ ((y1 - y2) * (y1 - y2))));
	}

	public static void initNavi() {
		pilot.addMoveListener(pp);
		navigator.setPoseProvider(pp);
		pp.setPose(pose); // pilot służy kierowaniu, navigator pilnuje
							// koordynatów, pp obsługuje pozycję bieżaca;
							// wiązemy te obiekty tu
		pilot.setTravelSpeed(def_speed);
		pilot.setRotateSpeed(turn_speed);

	}
	
//	public static void 

	public static double cofajDoLinii() {

		pilot.setTravelSpeed(push_speed);

		Pose start = pp.getPose();

		pilot.backward();
		while (sensors.jakiKolorPola() != 0) {
		}
		pilot.stop();

		Pose end = pp.getPose();

		float x1 = start.getX();
		float y1 = start.getY();
		float x2 = end.getX();
		float y2 = end.getY();
		return odleglosc(x1, y1, x2, y2);

	}

	public static double jedzDoLinii() {
		pilot.setTravelSpeed(push_speed);

		Pose start = pp.getPose();

		pilot.forward();
		while (sensors.jakiKolorPola() != 0) {
		}
		pilot.stop();

		Pose end = pp.getPose();

		float x1 = start.getX();
		float y1 = start.getY();
		float x2 = end.getX();
		float y2 = end.getY();
		return odleglosc(x1, y1, x2, y2);
	}
}
