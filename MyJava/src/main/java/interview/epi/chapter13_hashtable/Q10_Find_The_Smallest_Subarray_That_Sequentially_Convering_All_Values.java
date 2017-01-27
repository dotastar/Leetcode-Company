package interview.epi.chapter13_hashtable;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Similar to Q9, but the difference is this time we should be ware of the order
 * between key words.
 * Given two array A and Q, find smallest [i,j] such that A[i..j] sequentially
 * cover all elements of Q.
 * 
 * @author yazhoucao
 * 
 */
public class Q10_Find_The_Smallest_Subarray_That_Sequentially_Convering_All_Values {

	static Class<?> c = Q10_Find_The_Smallest_Subarray_That_Sequentially_Convering_All_Values.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * To be continued, the solution has not been understood, from EPI.
	 * 
	 * Three important data structure to solve this problem:
	 * 
	 * 1.A HashMap that maps each keyword to its index in the keywords array.
	 * Map<String, Integer> keyToIdx
	 * 
	 * 2. An array L that maps Q[j]'s most recent occurrence in A to L[j]
	 * L = int[] latestOccur
	 * 
	 * 3. An array D, D[j] is the min length end at L[j] that covers All Q in A.
	 * D = int[] minLength
	 */
	public int[] findRange(String[] paragraph, String[] keywords) {
		int keysLen = keywords.length;
		Map<String, Integer> keyToIdx = new HashMap<>();
		int[] latestOccur = new int[keysLen];
		int[] minLength = new int[keysLen];

		// Initializes latestOccur, minLength, and keyToIdx.
		for (int i = 0; i < keysLen; ++i) {
			latestOccur[i] = -1;
			minLength[i] = Integer.MAX_VALUE;
			keyToIdx.put(keywords[i], i);
		}
		int[] res = new int[] { -1, -1 };
		for (int i = 0; i < paragraph.length; ++i) {
			Integer keyIdx = keyToIdx.get(paragraph[i]);
			if (keyIdx != null) {
				if (keyIdx == 0) { // First keyword.
					minLength[0] = 1;
				} else if (minLength[keyIdx - 1] != Integer.MAX_VALUE) {
					int distanceToPreviousKeyword = i - latestOccur[keyIdx - 1];
					minLength[keyIdx] = distanceToPreviousKeyword
							+ minLength[keyIdx - 1];
				}
				latestOccur[keyIdx] = i;
				if (keyIdx == keysLen - 1
						&& minLength[keysLen - 1] < res[1] - res[0] + 1) {
					res[0] = (i - minLength[keysLen - 1] + 1);
					res[1] = i;
				}
			}
		}
		return res;
	}

	@Test
	public void smallTest() {
		String[] a3 = new String[] { "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "2", "4", "6", "10", "10", "10", "3", "2", "1", "0" };
		String[] subseq4 = new String[] { "0", "2", "9", "4", "6" };
		int[] res = findRange(a3, subseq4);
		assertTrue(res[0] == 0 && res[1] == 12);
	}

}
