package speculatrix_main;

public class robot {

	public static double[][] get_dist_robot_to_avail(int[][] avail_loc,
			int x_robot, int y_robot) {

		double[][] dist_robot_to_avail = new double[avail_loc.length][3];

		for (int i = 0; i < avail_loc.length; i++) {
			dist_robot_to_avail[i][0] = avail_loc[i][0];
			dist_robot_to_avail[i][1] = avail_loc[i][1];
			dist_robot_to_avail[i][2] = linear_path.distance(x_robot, y_robot,
					avail_loc[i][0], avail_loc[i][1]);
//			System.out.println(avail_loc[i][0] + " " + avail_loc[i][1] + " "
//					+ dist_robot_to_avail[i][2]);
		}

		return dist_robot_to_avail;
	}

}
