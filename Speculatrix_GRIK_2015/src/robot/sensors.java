package robocik;

import sieci.nn_kolor_pola;
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.Color;
//import lejos.nxt.LCD;

/**
 * Simple class that provides sensor methods.
 * 
 * @author Mikolaj "Jesmasta" Buchwald
 * @version 0.1
 * 
 * 
 */

public class sensors {

	static ColorSensor ks = new ColorSensor(SensorPort.S1);

	static UltrasonicSensor ucho = new UltrasonicSensor(SensorPort.S2);

	static TouchSensor prawy_palec = new TouchSensor(SensorPort.S3);
	static TouchSensor lewy_palec = new TouchSensor(SensorPort.S4);

	static Color kolor;
	private static double red;
	private static double green;
	private static double blue;
	static nn_kolor_pola siec_kolor = new nn_kolor_pola(); // -1: nic, 0:czarny,
															// 1:pole

	// additional variable to count distance/touch/color ticks
	static int number_of_ticks = 0;

	public static String odczytyKoloru() {
		kolor = ks.getColor();

		return (kolor.getRed() + " " + kolor.getGreen() + " " + kolor.getBlue());
	}

	public static int jakiKolorPola() { // jak w neurosorterze, siecią.
		kolor = ks.getColor();
		red = (double) kolor.getRed() / 255.0;
		green = (double) kolor.getGreen() / 255.0;
		blue = (double) kolor.getBlue() / 255.0;
		double[] wektor = new double[] { red, green, blue };
		int odczyt = siec_kolor.getClass(wektor);
		return (odczyt);
	}

	public static void ultraDistance() {

		// just counting how many times did i getDistance
		number_of_ticks++;
		System.out.println("iteration: " + number_of_ticks);

		System.out.println("distance: " + ucho.getDistance());
		System.out.println("---");

	}

	// public static void ultraDistance() {
	// LCD.clear();
	// LCD.drawString(ucho.getVendorID(), 0, 0);
	// LCD.drawString(ucho.getProductID(), 0, 1);
	// LCD.drawString(ucho.getVersion(), 0, 2);
	// LCD.drawInt(ucho.getDistance(), 0, 3);
	//
	// }

}
