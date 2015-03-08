package robocik;

public class map {
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public static int square_side = 10;

	@SuppressWarnings("deprecation")
	public static double[] get_location() {
		double [] location = new double[2];
		location[0] = round(motors.nav_leg.getX(), 2);
		location[1] = round(motors.nav_leg.getY(), 2);
		return location;

	}

}
