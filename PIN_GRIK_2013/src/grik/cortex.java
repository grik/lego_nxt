package grik;


import java.util.ArrayList;
import java.util.Random;

import lejos.util.Matrix;

public class cortex {

	// nie możemy uzywac nic z virtual!

	public static ArrayList<Integer> pin = new ArrayList<Integer>();

	public static Random generator = new Random();

	public static void znany_pin() {
		//wedlug regulaminu!
		pin.clear();
		pin.add(1);
		pin.add(2);
		pin.add(5);
		pin.add(4);
		pin.add(3);
		pin.add(1);

	}
	
	public static void losuj_pin() {
		cortex.pin.clear();
		ArrayList<Integer> cyfry=new ArrayList<Integer>();
		cyfry.add(1);
		cyfry.add(2);
		cyfry.add(3);
		cyfry.add(4);
		cyfry.add(5);
		
		for (int i=5;i>0;i--)
		{
			int hmm=cortex.generator.nextInt(i);
			cortex.pin.add(cyfry.get(hmm));
			cyfry.remove(hmm);
		}
		int hmm=cortex.pin.get(0);
		cortex.pin.add(hmm);

	}
	

	public static Matrix pamiec = new Matrix(5, 5);

	enum ruchy {
		UP, DOWN, LEFT, RIGHT, STAND, BEEP, END;
	}

	enum orientacja {
		GORA, DOL, PRAWO, LEWO
	}

	// liczymy od lewego dolnego, zaczynamy w prawym dolnym, a wszedzie mamy
	// zera

	public static pole pozycja = new pole(0, 4);
	public static orientacja skierowanie = orientacja.GORA;

	public static ruchy[] sztywne = new ruchy[] { ruchy.UP, ruchy.UP, ruchy.UP,
			ruchy.UP, ruchy.LEFT, ruchy.LEFT, ruchy.LEFT, ruchy.LEFT,
			ruchy.DOWN, ruchy.DOWN, ruchy.DOWN, ruchy.DOWN, ruchy.RIGHT,
			ruchy.RIGHT, ruchy.RIGHT, ruchy.UP, ruchy.UP, ruchy.UP, ruchy.LEFT,
			ruchy.LEFT, ruchy.DOWN, ruchy.DOWN, ruchy.RIGHT, ruchy.UP,
			ruchy.END };

	@SuppressWarnings("deprecation")
	public static ArrayList<ruchy> sztywnaSekwencja = new ArrayList<ruchy>(
			sztywne);

	// public static void wypelnij {}

	static ArrayList<pole> doZebrania = new ArrayList<pole>(); // lista pol,
																// ktore trzeba
																// przejechac (i
																// na nich
																// piknac!) //
																// ruchow

	static ArrayList<pole> doPrzejechania = new ArrayList<pole>();

	public static ArrayList<ArrayList<pole>> numerki = new ArrayList<ArrayList<pole>>();

	static ArrayList<ruchy> sekwencja = new ArrayList<ruchy>(); // zawiera
												// sekwencje
																// ruchow

	public static ArrayList<pole> gdzieTeNumerki(double i) {
		ArrayList<pole> tu = new ArrayList<pole>();
		for (int w = 0; w < 5; w++) {
			for (int k = 0; k < 5; k++) {
				if (pamiec.get(w, k) == (int) i)
					tu.add(new pole(w, k));

			}
		}

		return tu;

	}

	public static void generujKandydatow() {
		numerki.clear();
		for (int i = 0; i < pin.size(); i++) {
			numerki.add(gdzieTeNumerki(pin.get(i)));

		}

	}

	public static ArrayList<pole> najlepszaDoZebrania() {
		// potem ja trzeba jeszcze uzupelnic, minimalizujac zakrecanie
		ArrayList<pole> kandydat = new ArrayList<pole>();
		int prog = 1000000;
		int ilu_kandydatow = 1;
		for (int i = 0; i < numerki.size(); i++) {

			ilu_kandydatow *= numerki.get(i).size();
		}

		for (int licznik = 0; licznik < ilu_kandydatow * 10; licznik++) {
			ArrayList<pole> tymczasowa = new ArrayList<pole>();
			for (int i = 0; i < numerki.size(); i++) {
				int losowy = generator.nextInt(numerki.get(i).size());
				tymczasowa.add(numerki.get(i).get(losowy));

			}
			tymczasowa.add(0, pozycja);
			int wynik = dlugosc_trasy(tymczasowa) + ilosc_zakretow(tymczasowa);
			if (wynik < prog) {
				kandydat = tymczasowa;
				prog = wynik;
			}

		}

		return kandydat;

	}

	static ArrayList<pole> zPolaDoPola(pole a, pole b) {
		ArrayList<pole> trasunia = new ArrayList<pole>();
		pole tymczasowe = a;
		if (a.w == b.w && a.k == b.k)
			trasunia.add(a);

		if (tymczasowe.w > b.w) {
			while (tymczasowe.w != b.w) {
				tymczasowe = new pole(tymczasowe.w - 1, tymczasowe.k);
				trasunia.add(tymczasowe);
			}
		}

		if (tymczasowe.w < b.w) {
			while (tymczasowe.w != b.w) {
				tymczasowe = new pole(tymczasowe.w + 1, tymczasowe.k);
				trasunia.add(tymczasowe);
			}
		}

		if (tymczasowe.k > b.k) {
			while (tymczasowe.k != b.k) {
				tymczasowe = new pole(tymczasowe.w, tymczasowe.k - 1);
				trasunia.add(tymczasowe);
			}
		}

		if (tymczasowe.k < b.k) {
			while (tymczasowe.k != b.k) {
				tymczasowe = new pole(tymczasowe.w, tymczasowe.k + 1);
				trasunia.add(tymczasowe);
			}
		}
		return trasunia;

	}

	static void aleJakDoPrzejechania()

	{
		doPrzejechania.clear();
		doPrzejechania.add(doZebrania.get(0));
		for (int i = 1; i < doZebrania.size(); i++) {
			doPrzejechania.addAll(zPolaDoPola(doZebrania.get(i - 1),
					doZebrania.get(i)));
		}
	}

	static void aleJakaSekwencjaRuchow() {
		sekwencja.clear();

		int szukana = 0;

		for (int i = 1; i < doPrzejechania.size(); i++) {
			pole a = doPrzejechania.get(i - 1);
			pole b = doPrzejechania.get(i);
			if (pamiec.get(a.w, a.k) == pin.get(szukana)) {
				sekwencja.add(ruchy.BEEP);
				szukana++;
			}
			if ((a.w == b.w) && (a.k == b.k)) {
				sekwencja.add(ruchy.STAND);
			}

			if ((a.w < b.w) && (a.k == b.k)) {
				sekwencja.add(ruchy.UP);
			}

			if ((a.w > b.w) && (a.k == b.k)) {
				sekwencja.add(ruchy.DOWN);
			}

			if ((a.w == b.w) && (a.k > b.k)) {
				sekwencja.add(ruchy.LEFT);
			}

			if ((a.w == b.w) && (a.k < b.k)) {
				sekwencja.add(ruchy.RIGHT);
			}
		}
		sekwencja.add(ruchy.BEEP);
		sekwencja.add(ruchy.END);
	}

	public static boolean czyJuzMamywymagane()
	// wymagane cyferki w pamieci do złozenia pinu :)
	{
		ArrayList<Integer> liczebnosci = new ArrayList<Integer>();
		for (int i = 0; i < pin.size(); i++) {
			liczebnosci.add(0);
			int pinek = pin.get(i);
			for (int w = 4; w > -1; w--) {
				for (int k = 0; k < 5; k++)
					if (pamiec.get(w, k) == pinek) {
						liczebnosci.set(i, liczebnosci.get(i) + 1);
					}
			}
		}
		if (liczebnosci.contains(0))
			return false;
		else
			return true;

	}

	public static void piszpamiec() {
		for (int w = 4; w > -1; w--) {
			for (int k = 0; k < 5; k++) {
				System.out.print((int) pamiec.get(w, k));
				System.out.print(" ");
			}
			System.out.println("");
		}

	}

	public static double odleglosc(float x1, float y1, float x2, float y2) { // z
																				// pitagorasa,
																				// oczywiście.
		return Math.abs(Math.sqrt(((x1 - x2) * (x1 - x2))
				+ ((y1 - y2) * (y1 - y2))));
		// niepotrzebne w pinie!
	}

	static double[] milimetry = { 0, 2, 4, 9, 12, 17, 26, 32, 42, 51, 61, 71,
			84, 88, 100, 112, 121, 129, 132 };

	static double MiliToKat(double mili) {
		if (mili < 0)
			return 0.0;
		if (mili > 135)
			return -90.0;
		double mniejsza = 0.0;
		double wieksza = 0.0;
		int i = 0;
		for (i = 0; i < 19; i++) {
			if (milimetry[i] <= mili)
				mniejsza = i;
			else
				break;

		}

		for (i = 0; i < 19; i++) {
			if (milimetry[i] >= mili) {
				wieksza = i;
				break;
			}

		}

		return ((wieksza + mniejsza) / 2.0) * -5.0;

	}

	static double KatToMili(double kat)

	{

		if (kat < -90)
			return 135.0;
		if (kat > 0)
			return 0.0;

		return (milimetry[(int) kat / -5]);

	}

	public static void czyscPamiec() {
		pamiec = new Matrix(5, 5);

	}

	public static int dlugosc_trasy(ArrayList<pole> jakiej) {
		int dlug = 0;
		for (int i = 1; i < jakiej.size(); i++) {
			dlug += Math.abs(jakiej.get(i).w - jakiej.get(i - 1).w)
					+ Math.abs(jakiej.get(i).k - jakiej.get(i - 1).k);

		}
		return dlug;

	}

	public static int ilosc_zakretow(ArrayList<pole> jakiej) {
		int ile = 0;

		if (jakiej.size() < 2)
			return 0;
		for (int i = 2; i < jakiej.size(); i++) {
			if ((jakiej.get(i).w != jakiej.get(i - 2).w)
					&& (jakiej.get(i).k != jakiej.get(i - 2).k))
				ile++;

		}
		return ile;

	}

}
