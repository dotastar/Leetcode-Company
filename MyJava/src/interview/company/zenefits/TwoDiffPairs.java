package interview.company.zenefits;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Zenefits
 * Skype onsite
 * 
 * Given N unique positive integers, we want to count the total pairs of numbers
 * whose difference is K. The solution should minimize computational time
 * complexity to the best of your ability
 * 
 * Input Format:
 * 1st line contains N and K, which are integers separated by a space.
 * 2nd line contains N integers that form the set.
 * 
 * Constraints
 * 0 ≤ N ≤ 105,
 * All N numbers are distinct and can be represented by 32 bit signed integer.
 * 0 < K <= 109
 * 
 * Output Format:
 * One integer, the number of pairs of numbers that have difference K.
 * 
 * Sample Input #1:
 * 5 2
 * 1 5 3 4 2
 * 
 * Sample Output #1: 3
 * Explanation:
 * The possible pairs are (5,3), (4,2) and (3,1).
 * 
 * Sample Input #2:
 * 10 1363374326 364147530 61825163 1073065718 1281246024 1399469912 428047635
 * 491595254 879792181 1069262793
 * 
 * Sample Output #2: 0
 * Explanation:
 * There are no pairs with a difference of 1.
 * Read input from STDIN and write output to STDOUT.
 * 
 * @author yazhoucao
 *
 */
public class TwoDiffPairs {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(TwoDiffPairs.class);
	}

	/**
	 * HashMap<Number, Count>, precomputation
	 * 
	 * num[i] - num[j] = k || num[j] - num[i] = k
	 * 
	 * num[i]- k = num[j] || k + num[i] = num[j]
	 * 
	 */
	public int numOfPairsDiffK(int[] A, int k) {
		int cnt = 0;
		Map<Integer, Integer> visited = new HashMap<>();
		for (int i = 0; i < A.length; i++) {
			if (visited.containsKey(A[i] - k))
				cnt += visited.get(A[i] - k);
			if (visited.containsKey(k + A[i]))
				cnt += visited.get(k + A[i]);
			Integer cntAi = visited.get(A[i]);
			if (cntAi == null)
				cntAi = 0;
			visited.put(A[i], cntAi + 1);
		}
		return cnt;
	}

	@Test
	public void test1() {
		int[] A = { 1, 5, 3, 4, 2 };
		int k = 2;
		assertEquals(3, numOfPairsDiffK(A, k));
	}

	@Test
	public void test2() {
		int[] A = { 1, 5, 3, 4, 2 };
		int k = 3;
		assertEquals(2, numOfPairsDiffK(A, k));
	}
}
