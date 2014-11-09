package interview.leetcode;

/**
 * Find the contiguous subarray within an array (containing at least one number)
 * which has the largest product.
 * 
 * For example, given the array [2,3,-2,4], the contiguous subarray [2,3] has
 * the largest product = 6.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Maximum_Product_Subarray {

	public static void main(String[] args) {
		System.out.print(maxProduct(new int[] { 0, -3, 1, 1 }) + "\t"); // 1
		System.out.print(maxProduct(new int[] { 1, 0, -1, 2, 3, -5, -2 })
				+ "\t"); // 60
		System.out.print(maxProduct(new int[] { -3, 0, 1, -2 }) + "\t"); // 1
		System.out.print(maxProduct(new int[] { -1, 0, 7, -9, 0, 9, -9, -1, 0,
				11, 4, 0 })
				+ "\t"); // 81
		System.out.print(maxProduct(new int[] { -1, -2, -3, 0 }) + "\t"); // 6
		System.out.print(maxProduct(new int[] { -2, 0, -1 }) + "\t"); // 0
		System.out.print(maxProduct(new int[] { 2, -5, -2, -4, 3 }) + "\t"); // 24
		System.out.print(maxProduct(new int[] { -1, -2, -9, -6 }) + "\t"); // 108
		System.out.print(maxProduct(new int[] { -1, 1, 2, 1 }) + "\t"); // 2
		System.out.print(maxProduct(new int[] { -2 }) + "\t"); // -2
		System.out.print(maxProduct(new int[] { -3, -4 }) + "\t"); // 12
		System.out.print(maxProduct(new int[] { -2, 3, -4 }) + "\t"); // 24
		System.out.print(maxProduct(new int[] { -1, -1 }) + "\t"); // 1

		System.out.println();
	}

	/**
	 * Brute force, Time O(n^2)
	 * 
	 * Time out
	 * 
	 */
	public static int maxProduct(int[] A) {
		int len = A.length;
		if (len == 0)
			return 0;
		int max = A[0];
		for (int i = 1; i < len; i++) {
			int currmax = A[i];
			int mult = A[i];
			for (int j = i - 1; j >= 0; j--) {
				if (A[j] == 0) {
					currmax = currmax < 0 ? 0 : currmax;
					break;
				}
				mult *= A[j];
				currmax = mult > currmax ? mult : currmax;
			}

			max = currmax > max ? currmax : max;
		}
		return max;
	}

	/**
	 * Dynamic Programming
	 * 
	 * Best solution, very neat, maintain a current max and min continuous
	 * product that ends at i, the max continuous product at point i could be
	 * either
	 * 
	 * oldmax(i-1)*A[i] if A[i]>0
	 * 
	 * or
	 * 
	 * oldmin(i-1)*A[i] if A[i]<=0
	 * 
	 * Because of the property of multiplication: one big negative number * one
	 * negative number could be a big positive number, so the max number could
	 * be either from two positive multiplication or two negative multiplication
	 * 
	 * max = max(A[i], max*A[i], min*A[i]),
	 * 
	 * min = min(A[i], max*A[i], min*A[i])
	 * 
	 * @return
	 */
	public static int maxProduct_DP(int[] A) {
		if (A == null || A.length == 0)
			return 0;
		int max_local = A[0];
		int min_local = A[0];
		int global = A[0];
		for (int i = 1; i < A.length; i++) {
			int max_copy = max_local;
			max_local = max(max(A[i], max_local * A[i]), A[i] * min_local);
			min_local = min(min(A[i], max_copy * A[i]), A[i] * min_local);
			global = max(global, max_local);
		}
		return global;
	}
    


	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static int min(int a, int b) {
		return a < b ? a : b;
	}
}
