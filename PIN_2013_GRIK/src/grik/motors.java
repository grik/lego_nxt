package grik;

import lejos.nxt.*;

import lejos.robotics.navigation.*;
import lejos.robotics.localization.*;

import lejos.robotics.RegulatedMotor;
import lejos.util.Delay;

/**
 * Simple class that provides motor methods.
 * 
 * @author Piotr "Nex" Janus, Patryk "pp106" Piesiak
 * @version 0.3 pin 2013
 * 
 */
public class motors {

	public static int def_speed = 20;
    public static int push_speed = 10;
	private static final int turn_speed = 30; // predkosc skretu jest w
												// stopniach , jazdy w
												// centrymetrach na sekunde
	
	public static final int wysuniecie_oczka=9; //o ile nam wystaje od srodka gasienic

	private static final float arm_speed = 90.0f;

	private static final float wheelDiameter = 3.21f;
	private static final float trackWidth = 18.4f; // gasienice po skosie
    private static final int kat_kalibracji = 10;
	private static final RegulatedMotor leftMotor = Motor.A;
	private static final RegulatedMotor rightMotor = Motor.B;
	private static final boolean reverse = true;
	private static final float startx = 0.0f;
	private static final float starty = 0.0f;
	private static final float startkat = 0.0f;
	public static final float pole_bok = 20.0f;
	public static final float line_width = 2.0f;
	public static final float step = pole_bok + line_width;
	public static final DifferentialPilot pilot = new DifferentialPilot(
			wheelDiameter, trackWidth, leftMotor, rightMotor, reverse);
	public static Navigator navigator = new Navigator(pilot);
	public static OdometryPoseProvider pp = new OdometryPoseProvider(pilot);
	public static Pose pose = new Pose(startx, starty, startkat);

	public static void initNavi() {
		pilot.addMoveListener(pp);
		navigator.setPoseProvider(pp);
		pp.setPose(pose);
		pilot.setTravelSpeed(def_speed);
		pilot.setRotateSpeed(turn_speed);
	}

	public static void initArm() {
		Motor.C.setSpeed(arm_speed);
		Motor.C.rotate(90);
		Motor.C.resetTachoCount();
	}
	
    public static double odleglosc(float x1, float y1, float x2, float y2) { //z pitagorasa, oczywiście.
        return Math.abs(Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2))));
    }
	
	 public static double cofajDoLinii() {
		 
		 	Motor.C.rotateTo((int) cortex.MiliToKat(67));

	        pilot.setTravelSpeed(push_speed);

	        Pose start = pp.getPose();

	        pilot.backward();
	        while (sensors.czyLinia() == 0) {
	        }
	        pilot.stop();

	        Pose end = pp.getPose();

	        float x1 = start.getX();
	        float y1 = start.getY();
	        float x2 = end.getX();
	        float y2 = end.getY();
	        return odleglosc(x1, y1, x2, y2);


	    }
	 

	public static void doPrzodu() {
		if (PIN_2013.etap_swobodny)
			pilot.travel(step);
		else
			pilot.travel(step - 9.5); // magiczna stala skanowania... i pol korekty!

	}

	public static void doTylu() {
		pilot.travel(-1 * step); //nigdy sie nie cofamy w fazie na sztywno

	}

	public static void wPrawo() {
		Motor.C.rotateTo((int) cortex.MiliToKat(67));
		motors.pilot.setTravelSpeed(motors.push_speed);
		pilot.travel(2);
		motors.pilot.setTravelSpeed(motors.def_speed);
		pilot.rotate(90);
	}

	public static void wLewo() {
		Motor.C.rotateTo((int) cortex.MiliToKat(67));
		motors.pilot.setTravelSpeed(motors.push_speed);
		pilot.travel(2);
		motors.pilot.setTravelSpeed(motors.def_speed);
		pilot.rotate(-90);
	}

	public static void czekaj() {
		Delay.msDelay(500);
	}

	public static void bipnij() {
		pilot.travel(5);
		Sound.beep();
		//czekaj(); //nie musimy czekac, gdyz jezdzimy jak debile, bo nie umiem tego finezyjnie rozwiazac 
		pilot.travel(-5);
	}

	public static void skoncz() {
		tools.koniec();
	}

	public static void zrobRuch(cortex.ruchy jaki) {

		if (jaki == cortex.ruchy.BEEP) {
			bipnij();
		}

		if (jaki == cortex.ruchy.STAND) {
			czekaj();
		}

		if (jaki == cortex.ruchy.END) {
			skoncz();
		}

		if (jaki == cortex.ruchy.UP) {
			if (cortex.skierowanie == cortex.orientacja.GORA) {
				doPrzodu();
			}

			if (cortex.skierowanie == cortex.orientacja.DOL) {
				doTylu();
			}

			if (cortex.skierowanie == cortex.orientacja.PRAWO) {
				wLewo();
				doPrzodu();
			}

			if (cortex.skierowanie == cortex.orientacja.LEWO) {
				wPrawo();
				doPrzodu();
			}

			cortex.pozycja = cortex.pozycja.jednoUp();
			if (cortex.skierowanie == cortex.orientacja.DOL)
				;
			else
				cortex.skierowanie = cortex.orientacja.GORA;
		}

		if (jaki == cortex.ruchy.DOWN) {
			if (cortex.skierowanie == cortex.orientacja.GORA) {
				doTylu();
			}

			if (cortex.skierowanie == cortex.orientacja.DOL) {

				doPrzodu();
			}

			if (cortex.skierowanie == cortex.orientacja.PRAWO) {
				wPrawo();
				doPrzodu();
			}

			if (cortex.skierowanie == cortex.orientacja.LEWO) {
				wLewo();
				doPrzodu();
			}

			cortex.pozycja = cortex.pozycja.jednoDown();
			if (cortex.skierowanie == cortex.orientacja.GORA)
				;
			else
				cortex.skierowanie = cortex.orientacja.DOL;

		}

		if (jaki == cortex.ruchy.LEFT) {
			if (cortex.skierowanie == cortex.orientacja.GORA) {
				wLewo();
				doPrzodu();
			}

			if (cortex.skierowanie == cortex.orientacja.DOL) {
				wPrawo();
				doPrzodu();
			}

			if (cortex.skierowanie == cortex.orientacja.PRAWO)

			{
				doTylu();
			}

			if (cortex.skierowanie == cortex.orientacja.LEWO) {
				doPrzodu();
			}
			cortex.pozycja = cortex.pozycja.jednoLeft();
			if (cortex.skierowanie == cortex.orientacja.PRAWO)
				;
			else
				cortex.skierowanie = cortex.orientacja.LEWO;
		}

		if (jaki == cortex.ruchy.RIGHT) {
			if (cortex.skierowanie == cortex.orientacja.GORA) {
				wPrawo();
				doPrzodu();
			}

			if (cortex.skierowanie == cortex.orientacja.DOL) {
				wLewo();
				doPrzodu();
			}

			if (cortex.skierowanie == cortex.orientacja.PRAWO) {
				doPrzodu();
			}

			if (cortex.skierowanie == cortex.orientacja.LEWO) {
				doTylu();
			}
			cortex.pozycja = cortex.pozycja.jednoRight();
			if (cortex.skierowanie == cortex.orientacja.LEWO)
				;
			else
				cortex.skierowanie = cortex.orientacja.PRAWO;
		}
	}
	

	 public static double jedzDoLinii() {
		 
		 	Motor.C.rotateTo((int) cortex.MiliToKat(67));

	        pilot.setTravelSpeed(push_speed);

	        Pose start = pp.getPose();

	        pilot.forward();
	        while (sensors.czyLinia() == 0) {
	        }
	        pilot.stop();

	        Pose end = pp.getPose();

	        float x1 = start.getX();
	        float y1 = start.getY();
	        float x2 = end.getX();
	        float y2 = end.getY();
	        return odleglosc(x1, y1, x2, y2);	    }
	 
	 public static void calibratePole() { //TODO: czy to dziala? -> nie, bo mamy przesów oczka

	        pilot.setTravelSpeed(push_speed);
			pilot.rotate(kat_kalibracji, false);

	        double a1 = jedzDoLinii();

	        pilot.travel(-1*a1, false);

	        pilot.rotate(-2 * kat_kalibracji, false);

	        double a2 = jedzDoLinii();


	        pilot.travel(-1*a2, false);
	        pilot.rotate(kat_kalibracji);
	        boolean wlewo = true; //strona, w która jestesmy oidchyleni od "pionu"...
	        a1=a1+wysuniecie_oczka;
	        a2=a2+wysuniecie_oczka;
	        
	        if (a2 < a1) { //...dla uproszczenia obliczeń i rozważań
	            wlewo = false;
	            double a;
	            a = a1;
	            a1 = a2;
	            a2 = a;
	        }

	        //System.out.println("a1: " + a1 + "a2: " + a2 + "wlewo: " + wlewo);

	        double alfa = Math.toRadians(2 * kat_kalibracji);

	        double pole1 = 0.5 * a1 * a2 * Math.sin(alfa);
	        double a3 = Math.sqrt((a1 * a1) + (a2 * a2) - 2 * a1 * a2 * Math.cos(alfa)); //z twierdzenia cosinusów

	        //System.out.println("a3: " + a3);

	        double gamma = Math.asin(2 * pole1 / (a2 * a3)); //użyty już wzór na pole dla innego kata
	        double beta = Math.PI - (alfa + gamma); //bezpieczne liczenie bety jest naokoło, to gamma jest na pewno ostry w wyjściowym przypadku
	        double odchyl = (beta - Math.PI / 2) + (alfa / 2);
//	        System.out.println("odchyl: " + odchyl);
//	        System.out.println("h: " + h);

	        if (wlewo) { //korekta kąta...
	            pilot.rotate(Math.toDegrees(odchyl), false);
	        } else {
	            pilot.rotate(-1 * Math.toDegrees(odchyl), false);
	        }

	        jedzDoLinii();
	        pilot.travel(-5);
	        pilot.setTravelSpeed(def_speed);

	    }
}
