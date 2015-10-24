package interview.epi.chapter17_dynamic_programming;

import java.util.Arrays;

/**
 * You need to test the design of a protective case. Specifically, the case can
 * protect the enclosed device from a fall from up to some number of floors, and
 * you want to determine what that number of floor is. You want to achieve this
 * using no more than c cases. An additional constraint is that you can perform
 * only d drops before the building supervisor stops you.
 * 
 * The ground floor is numbered zero, and it is given that the case will not
 * break if dropped from the ground floor.
 * 
 * Given c cases and d drops, what is the maximum number of floors that you can
 * test in the worst-case?
 * 
 * @author yazhoucao
 * 
 */
public class Q13_Determin_The_Critical_Height {

	public static void main(String[] args) {
		Q13_Determin_The_Critical_Height o = new Q13_Determin_The_Critical_Height();
		for (int c = 1; c < 10; c++) {
			System.out.print("c=" + c + ":\t");
			for (int d = 1; d < c + 10; d++)
				System.out.print(o.getHeight(c, d) + "\t");
			System.out.println();
		}
	}

	/**
	 * F(c,d): the max number of floors we can test with c cases and d drops.
	 * Key: F(c,d) = F(c-1,d-1) + 1 + F(c,d-1).
	 * F(c-1,d-1): the c-th case breaks and uses one drop.
	 * F(c,d-1): the c-th case doesn't break and uses one drop.
	 * 
	 * Time: O(cd), Space: O(cd).
	 */
	public int getHeight(int c, int d) {
		int[][] F = new int[c + 1][d + 1];
		for (int[] element : F) {
			Arrays.fill(element, -1);
		}
		return getHeightHelper(F, c, d);
	}

	private int getHeightHelper(int[][] F, int c, int d) {
		if (c == 0 || d == 0) {
			return 0;
		} else if (c == 1) {
			return d;
		} else {
			if (F[c][d] == -1) {
				F[c][d] = getHeightHelper(F, c, d - 1)
						+ getHeightHelper(F, c - 1, d - 1) + 1;
			}
			return F[c][d];
		}
	}
}
