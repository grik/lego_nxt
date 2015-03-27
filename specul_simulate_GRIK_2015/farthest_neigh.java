package speculatrix_main;

public class farthest_neigh {

	public static double[] dist_side(double x_curr, double y_curr) {
		// side distance: 0 - north, 1 - east, 2 - south, 3 - west
		double[] side_dist = new double[4];
		side_dist[0] = linear_path.distance(x_curr, y_curr, 0, y_curr);
		side_dist[1] = linear_path.distance(x_curr, y_curr, x_curr,
				MapGenerator.map_speculatrix[0].length - 1);
		side_dist[2] = linear_path.distance(x_curr, y_curr,
				MapGenerator.map_speculatrix.length - 1, y_curr);
		side_dist[3] = linear_path.distance(x_curr, y_curr, x_curr, 0);
		return side_dist;
	}
	
//	public static double[] dist_food(double x_curr, double y_curr, int[][] map) {
		
//		for(int i=0; i< ;i++){
//			
//		}
//		
//		if(i<x_curr){
//			nearest_north = ;
//		}
//		if(i>x_curr){
//			nearest_south = ;
//		}
//		if(j>x_curr){
//			nearest_south = ;
//		}
		
		// side distance: 0 - north, 1 - east, 2 - south, 3 - west
//		double[] side_dist = new double[4];
//		side_dist[0] = linear_path.distance(x_curr, y_curr, 0, y_curr);
//		side_dist[1] = linear_path.distance(x_curr, y_curr, x_curr,
//				MapGenerator.map_speculatrix[0].length - 1);
//		side_dist[2] = linear_path.distance(x_curr, y_curr,
//				MapGenerator.map_speculatrix.length - 1, y_curr);
//		side_dist[3] = linear_path.distance(x_curr, y_curr, x_curr, 0);
//		return side_dist;
//	}

}
