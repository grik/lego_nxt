package grik;

import java.util.ArrayList;

import grik.sieci.krawedz_nn;
import lejos.nxt.ColorSensor;

import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor.Color;

public class sensors {

	static ColorSensor sensorek = new ColorSensor(SensorPort.S1);
	static Color barwy = new Color(0, 0, 0, 0, 0);
	static krawedz_nn siec_krawedz = new krawedz_nn();

	static double czyLinia() {
		barwy = sensorek.getColor();
		double red = (barwy.getRed()) / 255.0;
		double green = (barwy.getGreen()) / 255.0;
		double blue = (barwy.getBlue()) / 255.0;
		double background = (barwy.getBackground()) / 255.0;

		double[] odczyty = new double[] { red, green, blue, background };

		int wyszlo = siec_krawedz.getClass(odczyty);

		if (wyszlo == 0)
			return 1.0;
		else
			return 0.0;

	}

	public static ArrayList<Integer> odczytyOczka() {
		ArrayList<Integer> zwrot = new ArrayList<Integer>();
		barwy = sensorek.getColor();
		zwrot.add(barwy.getRed());
		zwrot.add(barwy.getGreen());
		zwrot.add(barwy.getBlue());
		zwrot.add(barwy.getBackground());

		return zwrot;
	}

}
