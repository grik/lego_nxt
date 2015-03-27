package speculatrix_main;

public class map {
	public static int count_avail(int[][] map_explored) {
		int avail_num = 0;
		for (int i = 0; i < map_explored.length; i++) {
			for (int j = 0; j < map_explored[0].length; j++) {
				if (map_explored[i][j] == 2) {
					avail_num += 1;
				}
			}
		}
		return avail_num;
	}

	public static int[][] get_avail_loc(int[][] map_explored) {
		int[][] avail_loc = new int[count_avail(map_explored)][2];
		int avail_count = 0;
		for (int i = 0; i < map_explored.length; i++) {
			for (int j = 0; j < map_explored[0].length; j++) {
				if (map_explored[i][j] == 2) {
					avail_loc[avail_count][0] = i;
					avail_loc[avail_count][1] = j;
					avail_count += 1;
				}
			}
		}
		return avail_loc;
	}

}
