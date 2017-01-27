package interview.epi.chapter17_dynamic_programming;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Let array A be an array of n positive integers. Entry A[i] is the value of
 * the i-th item. Design an algorithm that computes S which is a subset of A
 * such that the differences between the value of subset S and the value of rest
 * of the set (A-S) is minimized
 * 
 * @author yazhoucao
 * 
 */
public class Q10_Divide_The_Spoils_Fairly {

	public static void main(String[] args) {
		Q10_Divide_The_Spoils_Fairly o = new Q10_Divide_The_Spoils_Fairly();
		Random r = new Random();
		int n = r.nextInt(15);
		List<Integer> A = new ArrayList<>();
		for (int i = 0; i < n; ++i) {
			A.add(r.nextInt(20));
		}
		System.out.println(A);
		int sum = 0;
		for (int a : A) {
			sum += a;
		}
		System.out.println(sum);
		System.out.println("minimum difference = " + o.minimizeDifference(A));
	}

	/**
	 * Enumerate all the subset sum that is less than sum/2
	 * Key: Use previous subset sum to calculate current subset sum.
	 * Time: O(n*sum), Space: O(sum).
	 */
	public int minimizeDifference(List<Integer> A) {
		int sum = 0;
		for (int a : A)
			sum += a;

		Set<Integer> isOk = new HashSet<>();
		isOk.add(0); // contains all the subset sum that less than sum/2
		for (int item : A) {
			for (int v = sum / 2; v >= item; --v) {
				// if contains, then it is valid subset sum
				if (isOk.contains(v - item)) {
					isOk.add(v); // v = old_subset_sub + item (current)
				}
			}
		}
		// Finds the first i from middle where isOk[i] == true.
		for (int i = sum / 2; i > 0; --i) {
			if (isOk.contains(i)) {
				return (sum - i) - i;
			}
		}
		return sum; // One thief takes all.
	}
}
