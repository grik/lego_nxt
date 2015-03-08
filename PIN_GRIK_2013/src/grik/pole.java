package grik;

public class pole {
	public int w, k;

	public pole(int wiersz, int kolumna) {
		w = wiersz;
		k = kolumna;
	}

	public boolean mozeUp() {
		if (w == 4)
			return false;
		else
			return true;
	}

	public boolean mozeDown() {
		if (w == 0)
			return false;
		else
			return true;

	}

	public boolean mozeLeft() {
		if (k == 0)
			return false;
		else
			return true;

	}

	public boolean mozeRight() {
		if (k == 4)
			return false;
		else
			return true;

	}

	public boolean czySkanowano() {
		if (coTuJest() == 0.0)
			return false;
		else
			return true;
	}

	public pole jednoUp() {
		return new pole(w + 1, k);
	}

	public pole jednoDown() {
		return new pole(w - 1, k);
	}

	public pole jednoLeft() {
		return new pole(w, k - 1);
	}

	public pole jednoRight() {
		return new pole(w, k + 1);
	}

	public double coTuJest() {
		return cortex.pamiec.get(w, k);

	}

	public String toString() {
		return ("pole: " + Integer.toString(w) + " " + Integer.toString(k));

	}

}
