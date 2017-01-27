package interview.epi.chapter17_dynamic_programming;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author yazhoucao
 * 
 */
public class Q15_Pick_Up_Coins_For_Maximum_Gain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Time: O(n^2)
	 * T[a][b] = the maximum value a player can get when the coins remaining on
	 * the table are at indices a to b.
	 */
	public int pickUpCoins(List<Integer> C) {
		int[][] T = new int[C.size()][C.size()];
		for (int[] t : T) {
			Arrays.fill(t, -1);
		}
		return pickUpCoins(C, 0, C.size() - 1, T);
	}

	private int pickUpCoins(List<Integer> C, int a, int b, int[][] T) {
		if (a > b)
			return 0; // Base condition.

		if (T[a][b] == -1) {
			T[a][b] = Math.max(
					C.get(a) + min(pickUpCoins(C, a + 2, b, T), pickUpCoins(C, a + 1, b - 1, T)),
					C.get(b) + min(pickUpCoins(C, a + 1, b - 1, T), pickUpCoins(C, a, b - 2, T)));
		}
		return T[a][b];
	}

	private int min(int a, int b) {
		return a < b ? a : b;
	}
}
