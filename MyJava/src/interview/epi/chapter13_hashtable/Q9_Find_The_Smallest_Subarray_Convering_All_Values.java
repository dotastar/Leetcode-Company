package interview.epi.chapter13_hashtable;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Given two array A and Q, find smallest [i,j] such that A[i..j] includes all
 * elements of Q.
 * 
 * The same as Minimum Window Substring of LeetCode
 * 
 * Follow up:
 * Suppose A is presented in streaming fashion, i.e. elements are read one at a
 * time, and you cannot read earlier entries. How would you modify your
 * solution for this case ?
 * 
 * @author yazhoucao
 * 
 */
public class Q9_Find_The_Smallest_Subarray_Convering_All_Values {

	static Class<?> c = Q9_Find_The_Smallest_Subarray_Convering_All_Values.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Sliding window
	 * Map<String, Integer> dict is important
	 * The Integer in dict is a count, count how many words there are need to match
	 */
	public int[] findRange(String[] A, String[] Q) {
		Map<String, Integer> dict = new HashMap<>();
		for (String q : Q) {
			if (dict.containsKey(q))
				dict.put(q, dict.get(q) + 1);
			else
				dict.put(q, 1);
		}

		int min = A.length;
		int left = 0;
		int restToMatch = Q.length;
		for (int l = 0, r = 0; r < A.length; r++) {
			if (dict.containsKey(A[r])) {
				Integer rcnt = dict.get(A[r]);	// right count
				dict.put(A[r], --rcnt);
				if (rcnt >= 0)
					restToMatch--;	// important! only decrement when cnt <= 0
				
				// move left forward
				if (restToMatch == 0) {
					while (restToMatch == 0) {
						Integer lcnt = dict.get(A[l]);	// left count
						if (lcnt != null) {
							dict.put(A[l], ++lcnt);
							if (lcnt > 0)
								restToMatch++;	// important! only increment when lcnt > 0
						}
						l++;
					} // when while ends, condition broke,
						// the l should be backward one
					if ((r - l + 2) < min) {
						left = l - 1; // left - 1
						min = r - l + 2;
					}
				}
			}
		} // min is length, the index 'end' should be length - 1
		return new int[] { left, (left + min - 1) };
	}

	@Test
	public void test1() {
		String[] A = "I like to play basketball and also watch TV and play video game".split(" ");
		String[] Q = { "basketball", "video", "game" };
		int[] res = findRange(A, Q);
		int[] ans = { 4, 12 };
		assertTrue(Arrays.equals(res, ans));
	}

	@Test
	public void test2() {
		String[] A = "I like to play basketball and also watch TV and play video game".split(" ");
		String[] Q = { "like", "play" };
		int[] res = findRange(A, Q);
		int[] ans = { 1, 3 };
		assertTrue(Arrays.equals(res, ans));
	}

	@Test
	public void test3() {
		String[] A = "I like to play basketball and also watch TV and play video game".split(" ");
		String[] Q = { "TV" };
		int[] res = findRange(A, Q);
		int[] ans = { 8, 8 };
		assertTrue(Arrays.equals(res, ans));
	}

	@Test
	public void test4() {
		String[] A = "I like to watch a big TV and watch the TV and watch TV and TV".split(" ");
		String[] Q = { "watch", "TV" };
		int[] res = findRange(A, Q);
		int[] ans = { 12, 13 };
		assertTrue(Arrays.equals(res, ans));
	}
}
