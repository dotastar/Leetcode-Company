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

	public void sortColors(int[] A) {
		int[] color = new int[3];
		for (int i = 0; i < A.length; i++) {
			if (A[i] == 0)
				color[0]++;
			else if (A[i] == 1)
				color[1]++;
			else
				color[2]++;
		}
		int i = 0;
		while (color[0] > 0) {
			A[i++] = 0;
			color[0]--;
		}
		while (color[1] > 0) {
			A[i++] = 1;
			color[1]--;
		}
		while (color[2] > 0) {
			A[i++] = 2;
			color[2]--;
		}
	}

	/**
	 * One-pass algorithm, like Quicksort, swap pivot
	 * Red move to the beginning, Blue move to the end, White wait for to be
	 * swapped.
	 * red=0, white=1, blue=2
	 */
	public void sortColors2(int[] A) {
		int red = 0;
		int blue = A.length - 1;
		for (int white = 0; white <= blue; white++) {
			if (A[white] == 0) {
				swap(A, red, white);
				red++;
			} else if (A[white] == 2) {
				swap(A, blue, white);
				blue--;
				white--; // stay put
			}
		}
	}

	private void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}
}
