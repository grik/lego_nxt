package speculatrix_main;

import java.util.Arrays;

public class stats {
	public static double stdev(double[] data) {
		double size;
		double mean;
		double var;
		double stdev;

		size = data.length;

		double sum = 0.0;
		for (double a : data)
			sum += a;
		mean = sum / size;

		double temp = 0;
		for (double a : data)
			temp += (mean - a) * (mean - a);
		var = temp / size;

		stdev = Math.sqrt(var);

		return stdev;
	}
	// double[] data;
	// double size;
	//
	// public stats(double[] data)
	// {
	// this.data = data;
	// size = data.length;
	// }
	//
	// double getMean()
	// {
	// double sum = 0.0;
	// for(double a : data)
	// sum += a;
	// return sum/size;
	// }
	//
	// double getVariance()
	// {
	// double mean = getMean();
	// double temp = 0;
	// for(double a :data)
	// temp += (mean-a)*(mean-a);
	// return temp/size;
	// }
	//
	// double getStdDev()
	// {
	// return Math.sqrt(getVariance());
	// }
	//
	// public double median()
	// {
	// double[] b = new double[data.length];
	// System.arraycopy(data, 0, b, 0, b.length);
	// Arrays.sort(b);
	//
	// if (data.length % 2 == 0)
	// {
	// return (b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0;
	// }
	// else
	// {
	// return b[b.length / 2];
	// }
	// }
}
