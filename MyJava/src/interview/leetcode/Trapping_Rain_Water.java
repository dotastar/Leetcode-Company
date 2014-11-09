package interview.leetcode;


/**
 * Given n non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it is able to trap after raining.
 * 
 * For example, Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Trapping_Rain_Water {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A0 = new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
		int[] A1 = new int[] { 2, 0, 2 };
		System.out.println(trap(A0));
		System.out.println(trap2(A1));
	}

	/**
	 * Two scans, one scan get the left max at each point, second scan get the
	 * right max and at the same time calculate the current water volume,
	 * volume = min( leftMax[i], rightMax[i] ) - A[i]
	 * 
	 * Time: O(n) = 2n
	 */
	public static int trap(int[] A) {
		if (A.length <= 1)
			return 0;
		int len = A.length;
		int[] leftMax = new int[len];
		int max = 0;
		// from left to right, calc left max
		for (int i = 0; i < len; i++) {
			if (A[i] > max)
				max = A[i];
			leftMax[i] = max;
		}

		// from right to left, calc right max and
		// the volume of each unit
		int volume = 0;
		max = 0;
		for (int i = len - 1; i >= 0; i--) {
			if (A[i] > max)
				max = A[i];
			volume += (max < leftMax[i] ? max - A[i] : leftMax[i] - A[i]);
		}
		return volume;
	}

	/**
	 * Two pointers start at the beginning and the end respectively, the higher
	 * height pointer fixed, only the lower height pointer move, to calculate
	 * the volume at each bar on that side, we first record the current height
	 * as the higher height as 'height'. The volume can have water only if
	 * the next bar is lower than height.
	 * So the volume of the bar = height - A[l]/A[r];
	 * 
	 * Time: O(n) = n
	 */
	public static int trap2(int[] A) {
		int water = 0;
		int l = 0;
		int r = A.length - 1;
		while (l < r) {
			if (A[l] < A[r]) {
				int height = A[l];
				l++;
				while (l < r && A[l] < height) {
					water += height - A[l];
					l++;
				}
			} else {
				int height = A[r];
				r--;
				while (l < r && A[r] < height) {
					water += height - A[r];
					r--;
				}
			}
		}
		return water;
	}
}
