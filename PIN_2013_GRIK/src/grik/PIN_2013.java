package grik;
import grik.sieci.zLSiec;

import java.util.ArrayList;

//TODO: szybki skan po L
//TODO: optymalizacja czasu miedzy poczatkiem a koncem (pelen skan?) 
//TODO: kalibracja kata na samych odczytach
//TODO: nie jezdzic tu i tam do bipniecia
//TODO: ramie na srodek przed skretami, by nie rzucalo
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.util.Delay;

public class PIN_2013 {
	static ArrayList<Double> ostatniSkan = new ArrayList<Double>();
	static ArrayList<Double> zeSkanu = new ArrayList<Double>();
	
	static zLSiec zLnn=new zLSiec();
	
	//TODO: plan ostateczny, jak wypadniemy

	static void LSkanPlus() {
		ostatniSkan.clear();
		zeSkanu.clear();
		// motors.pilot.travel(-9);
		// czesc wprost...
		Motor.C.setSpeed(90.0f);
		Motor.C.rotateTo(-90);
		Motor.C.setSpeed(20.0f);

		motors.pilot.setTravelSpeed(motors.push_speed);
		int jakdaleko = 9;
		motors.pilot.travel(jakdaleko, true);
		while (motors.pilot.isMoving()) {
			ostatniSkan.add(sensors.czyLinia());
			Delay.msDelay(50);
		}

		while (ostatniSkan.size() > 9) {
			ostatniSkan.remove(0);

		}

		double suma = 0;
		for (int j = ostatniSkan.size() - 9; j < ostatniSkan.size(); j++) {
			suma += ostatniSkan.get(j);
		}
		double srednio1 = suma / 9.0; // to wynika z rozmiaru skanu...
		ostatniSkan.add(srednio1);
		zeSkanu.add(srednio1);

		// czesc wbok...
		Motor.C.setSpeed(90.0f);
		Motor.C.setSpeed(20.0f);
		Motor.C.rotateTo(0, true);
		for (int i2 = 95; i2 > 10; i2 = i2 - 10) {
			int pozycja = Motor.C.getPosition();
			while (Motor.C.getPosition() == pozycja) {
			}
			while (Motor.C.getPosition() < (int) cortex.MiliToKat((double) i2)) {
			}
			ostatniSkan.add(sensors.czyLinia());
		}

		suma = 0;
		for (int j = ostatniSkan.size() - 9; j < ostatniSkan.size(); j++) {
			suma += ostatniSkan.get(j);
		}
		double srednio2 = suma / 9.0; // to wynika z rozmiaru skanu...
		ostatniSkan.add(srednio2);
		zeSkanu.add(srednio2);

		ostatniSkan.add((srednio2 + srednio1) / 2.0);
		zeSkanu.add((srednio2 + srednio1) / 2.0);

		double przejscia_prosto = 0.0;
		double przejscia_bokiem = 0.0;

		for (int i = 1; i < 9; i++) {
			double pierwsza = ostatniSkan.get(i - 1);
			double druga = ostatniSkan.get(i);
			if (pierwsza != druga)
				przejscia_prosto += 0.125;
		}

		for (int i = 11; i < 19; i++) {
			double pierwsza = ostatniSkan.get(i - 1);
			double druga = ostatniSkan.get(i);
			if (pierwsza != druga)
				przejscia_bokiem += 0.125;
		}

		ostatniSkan.add(przejscia_prosto);
		zeSkanu.add(przejscia_prosto);
		ostatniSkan.add(przejscia_bokiem);
		zeSkanu.add(przejscia_bokiem);
		
		suma = 0;
		for (int j = 2; j < 7; j++) {
			suma += ostatniSkan.get(j);
		}
		suma /= 5.0; // to wynika z rozmiaru skanu...
		ostatniSkan.add(suma);
		zeSkanu.add(suma);
		
		suma = 0;
		for (int j = 12; j < 17; j++) {
			suma += ostatniSkan.get(j);
		}
		suma /= 5.0; // to wynika z rozmiaru skanu...
		ostatniSkan.add(suma);
		zeSkanu.add(suma);}
	
	static int JakiePoleL() {
		
		//TODO: żeby działało!

		// TODO: w2 ersje do wjeżdżania z przodu i zboku
		double[] odczyty = new double[zeSkanu.size()];

		for (int i = 0; i < zeSkanu.size(); i++) {
			odczyty[i] = zeSkanu.get(i);

		}

		return zLnn.getClass(odczyty) + 1;

		// 1: pole kreskowane ukosnie
		// 2: pole pasy pionowe
		// 3: pole pasy poziome
		// 4: pole krzyż
		// 5: puste
	}
	

	public static boolean etap_swobodny = false;

		static public tools.Timer timer = new tools.Timer(4 * 60 - 5); // lekka
																	// korekta,
																	// na
																	// wszelki
																	// wypadek

	public static void main(String[] args) throws Exception {

		// pamietac o tym, że najpierw jedziemy na sztywno, az mamy
		// wszystko do pinu, a wtedy już adaptacyjnie
		int ile_za_nami = 0;
		int ostatnia_kalibracja = 0;
		timer.setDaemon(true);
		timer.start();

		System.out.println("GRIK PIN Krakrobot 2013...");
		motors.initNavi();

		motors.initArm();// zaczynamy z lapka na plasko, przekrecona na prawo!
							// bo wymiary.
		
		motors.pilot.rotateRight();
		
		while (motors.pilot.isMoving()){};
		
		Motor.C.rotateTo((int) cortex.MiliToKat(67));
		etap_swobodny = false;
		motors.pilot.setTravelSpeed(motors.push_speed);
		motors.pilot.travel(1);

		motors.pilot.travel(-10); // bo poczatek poruszania się definiuje
									// kierunek robota ^^
		motors.pilot.setTravelSpeed(motors.def_speed);
		//cortex.znany_pin(); // TODO: ustawic wlasciwy!
		Sound.beep();
		cortex.losuj_pin();
		//cortex.czyscPamiec();
		System.out.println(cortex.pin);
		System.out.println("");

		for (int i = 0; i < cortex.sztywnaSekwencja.size(); i++) {
			int wiersz = cortex.pozycja.w;
			int kolumna = cortex.pozycja.k;

			if (cortex.pamiec.get(wiersz, kolumna) == 0.0) {				
				LSkanPlus();				
				int cotujest = JakiePoleL();
				System.out.println(cotujest);
				cortex.pamiec.set(wiersz, kolumna, cotujest);
				ile_za_nami++;
				if (cotujest == 5) {
					if ((ile_za_nami - ostatnia_kalibracja) > 3) {
						motors.calibratePole();
						ostatnia_kalibracja = ile_za_nami;
					}

				}
			}
			while (cortex.pamiec.get(wiersz, kolumna) == cortex.pin.get(0)) {
				motors.bipnij();
				cortex.pin.remove(0);
				// motors.czekaj(); nie potrzebujemy, bo bip nam podsuwa sie i
				// cofa
			}
			if (cortex.czyJuzMamywymagane())
				break;
			motors.zrobRuch(cortex.sztywnaSekwencja.get(i));
		}

		etap_swobodny = true;

		System.out.println("odslonilimilimy");
		System.out.println(cortex.pozycja);
		System.out.println(cortex.skierowanie);
		cortex.generujKandydatow();
		cortex.doZebrania = cortex.najlepszaDoZebrania();
		cortex.aleJakDoPrzejechania();
		cortex.aleJakaSekwencjaRuchow();

		for (int i = 0; i < cortex.sekwencja.size(); i++) {
			motors.zrobRuch(cortex.sekwencja.get(i));
			
			ile_za_nami++;
			int cotujest=(int) cortex.pamiec.get(cortex.pozycja.w, cortex.pozycja.k);
			if (cotujest == 5) {
				if ((ile_za_nami - ostatnia_kalibracja) > 3) {
					motors.calibratePole();
					ostatnia_kalibracja = ile_za_nami;
				}}
		}

		tools.koniec();
	}
}
