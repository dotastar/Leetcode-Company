package interview.company.zenefits;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Zenefits
 * Phone
 * 
 * Given an array consists of 1 and 0, you can only choose one segment and flip
 * all the 0 and 1 in it, what's the maximum number of 1s you can get?
 * 
 * You are given an array a of size N. The elements of the array area[0],
 * a[1], ... a[N - 1], where each a is either 0 or 1. You can perform one
 * transformation on the array: choose any two integers L,and R, and flip all
 * the elements between (and including) the Lth and Rth bits. In other words, L
 * and R represent the left-most and the right-most index demarcating the
 * boundaries of the segment whose bits you will decided to flip. ('Flipping' a
 * bit means, that a 0 is transformed to a 1 and a 1 is transformed to a 0.)
 * 
 * What is the maximum number of '1'-bits (indicated by S) which you can obtain
 * in the final bit-string?
 * 
 * Input Format:
 * The first line has a single integerN
 * The next N lines contains the Nelements in the array,a[0], a[1], ... a[N -
 * 1], one per line.
 * Note: Feel free to re-use the input-output code stubs provided.
 * 
 * Output format:
 * Return a single integer that denotes the maximum number of 1-bits which can
 * be obtained in the final bit string
 * 
 * Constraints:
 * 1 ≤ N ≤ 100,000
 * d can be either 0 or 1. It cannot be any other integer.
 * 0 ≤ L ≤ R < N
 * 
 * Sample Input:
 * 8
 * 1 0 0 1 0 0 1 0
 * 
 * Sample Output:
 * 6
 * 
 * Explanation:
 * We can get a maximum of 6 ones in the given binary array by performing either
 * of the following operations:
 * Flip [1, 5] ⇒ 1 1 1 0 1 1 1 0
 * or
 * Flip [1, 7] ⇒ 1 1 1 0 1 1 0 1
 * 
 * 
 * 
 * @author yazhoucao
 *
 */
public class SegmentFlip {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(SegmentFlip.class);
	}

	/**
	 * Goal is to find the segment that has the max # of 0s
	 * Replace 0 with 1, 1 with -1 -> max sub-array sum
	 * 
	 * The final result = max + # of 1s in original A
	 */
	public int maxLength(int[] A) {
		int max = 0, sum = 0, oneCnt = 0;
		for (int i = 0; i < A.length; i++) {
			if (A[i] == 1)
				oneCnt++;
			int val = A[i] == 0 ? 1 : -1;
			sum = sum + val > 0 ? sum + val : 0;
			max = sum > max ? sum : max;
		}
		return oneCnt + max;
	}

	@Test
	public void test1() {
		int[] A = { 1, 0, 0, 1, 0, 0, 1, 0 };
		int ans = 6;
		int res = maxLength(A);
		assertEquals(ans, res);
	}
}
