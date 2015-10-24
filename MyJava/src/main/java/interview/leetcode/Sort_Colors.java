package interview.leetcode;

/**
 * Given an array with n objects colored red, white or blue, sort them so that
 * objects of the same color are adjacent, with the colors in the order red,
 * white and blue.
 * 
 * Here, we will use the integers 0, 1, and 2 to represent the color red, white,
 * and blue respectively.
 * 
 * Note: You are not suppose to use the library's sort function for this
 * problem.
 * 
 * click to show follow up.
 * 
 * Follow up: A rather straight forward solution is a two-pass algorithm using
 * counting sort. First, iterate the array counting number of 0's, 1's, and 2's,
 * then overwrite array with total number of 0's, then 1's and followed by 2's.
 * 
 * Could you come up with an one-pass algorithm using only constant space?
 * 
 * @author yazhoucao
 * 
 */
public class Sort_Colors {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * One-pass algorithm, swap in place, like remove duplicates in Array
	 * Imagine it has three parts, move red to the first part, blue to the last
	 * part, white in the middle which is done by swapping other two colors in
	 * position.
	 * p1=red=0, p2=white=1, p3=blue=2
	 */
	public void sortColors2(int[] A) {
		int p1 = 0, p3 = A.length - 1;
		for (int p2 = 0; p2 <= p3; p2++) {
			if (A[p2] == 0) {
				swap(A, p1, p2);
				p1++;
			} else if (A[p2] == 2) {
				swap(A, p2, p3);
				p3--;
				p2--; // stay put
			}
		}
	}

	private void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}

	/**
	 * Counting sort, 2 passes
	 */
	public void sortColors(int[] A) {
		int[] counts = new int[3];
		for (int a : A)
			counts[a]++;
		for (int color = 0, i = 0; color < 3; color++) {
			while (i < A.length && color < counts.length && counts[color] > 0) {
				A[i] = color;
				counts[color]--;
				i++;
			}
		}
	}
}
