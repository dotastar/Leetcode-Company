package interview.leetcode;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 Find all possible combinations of k numbers that add up to a number n, given
 * that only numbers from 1 to 9 can be used and each combination should be a
 * unique set of numbers.
 * 
 * Ensure that numbers within the set are sorted in ascending order.
 * 
 * Example 1:
 * 
 * Input: k = 3, n = 7
 * 
 * Output:
 * 
 * [[1,2,4]]
 * 
 * 
 * Example 2:
 * 
 * Input: k = 3, n = 9
 * 
 * Output:
 * 
 * [[1,2,6], [1,3,5], [2,3,4]]
 * 
 * @author yazhoucao
 *
 */
public class Combination_Sum_III {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Combination_Sum_III.class);
	}

	/**
	 * DFS
	 * 
	 * C^k_n
	 */
	public List<List<Integer>> combinationSum3(int k, int n) {
		List<List<Integer>> res = new ArrayList<>();
		combine(k, n, 1, new ArrayList<Integer>(), res);
		return res;
	}

	/**
	 * Time: 2^(depth), depth = max(k, sum/avg(1~9))
	 */
	private void combine(int k, int sum, int i, List<Integer> curr,
			List<List<Integer>> res) {
		if (k == 0 || i > 9 || sum <= 0) {
			if (k == 0 && sum == 0)
				res.add(new ArrayList<Integer>(curr));
			return;
		}
		// not choose current
		combine(k, sum, i + 1, curr, res);
		// choose current
		curr.add(i);
		combine(k - 1, sum - i, i + 1, curr, res);
		curr.remove(curr.size() - 1);
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
