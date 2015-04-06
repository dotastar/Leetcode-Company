package interview.laicode;

import java.util.HashMap;
import java.util.Map;

/**
 * Majority Number I
 * Easy
 * Data Structure
 * 
 * Given an integer array of length L, find the number that occurs more than 0.5
 * * L times.
 * 
 * Assumptions
 * 
 * The given array is not null or empty
 * It is guaranteed there exists such a majority number
 * 
 * Examples
 * 
 * A = {1, 2, 1, 2, 1}, return 1
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Majority_Number_I {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Moore’s Voting Algorithm, O(n), notice: this only work if majority>50%
	 * This is a two step process.
	 * 1. Get an element occurring most of the time in the array. (it may not be
	 * exceeded 50%.)
	 * 2. Check if the element obtained from above step is above 50%.
	 * 
	 * The algorithm for first phase that works in O(n) is known as Moore’s
	 * Voting Algorithm. Basic idea of the algorithm is if we cancel out each
	 * occurrence of an element e with all the other elements that are different
	 * from e then e will exist till end if it is a majority element.
	 * 
	 */
	public int majority2(int[] A) {
		int major = A[0], cnt = 1;
		for (int i = 1; i < A.length; i++) {
			if (A[i] != major) {
				cnt--;
				if (cnt < 0) {
					major = A[i];
					cnt = 1;
				}
			} else
				cnt++;
		}
		cnt = 0;
		for (int a : A) {
			if (a == major)
				cnt++;
		}
		return 2 * cnt >= A.length ? major : 0;
	}

	/**
	 * Naive solution, use HashMap to record the count, if ever a number exceeds
	 * the threshold (0.5 * L), return the number.
	 * 
	 * Time: O(n), Space: O(n).
	 */
	public int majority(int[] A) {
		double threshold = 0.5 * A.length;
		Map<Integer, Integer> cntMap = new HashMap<>();
		for (int a : A) {
			Integer cnt = cntMap.get(a);
			if (cnt == null)
				cnt = 0;
			cntMap.put(a, ++cnt);
			if (cnt > threshold)
				return a;
		}
		return 0;
	}
}
