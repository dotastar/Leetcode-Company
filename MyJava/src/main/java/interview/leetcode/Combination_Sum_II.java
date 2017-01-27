package interview.leetcode;

import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Given a collection of candidate numbers (C) and a target number (T), find all
 * unique combinations in C where the candidate numbers sums to T.
 * 
 * Each number in C may only be used once in the combination.
 * 
 * Note: All numbers (including target) will be positive integers. Elements in a
 * combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤
 * … ≤ ak). The solution set must not contain duplicate combinations. For
 * example, given candidate set 10,1,2,7,6,1,5 and target 8,
 * 
 * A solution set is: [1, 7] [1, 2, 5] [2, 6] [1, 1, 6]
 * 
 * @author yazhoucao
 * 
 */
public class Combination_Sum_II {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Combination_Sum_II.class);
	}

	public List<List<Integer>> combinationSum2(int[] num, int target) {
		Arrays.sort(num);
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		combSum(num, 0, target, new ArrayList<Integer>(), res);
		return res;
	}

	private void combSum(int[] num, int idx, int sum, List<Integer> comb,
			List<List<Integer>> res) {
		if (sum <= 0) {
			if (sum == 0)
				res.add(new ArrayList<Integer>(comb));
			return;
		}

		for (int i = idx; i < num.length; i++) {
			if (i > idx && num[i] == num[i - 1])
				continue;
			comb.add(num[i]);
			combSum(num, i + 1, sum - num[i], comb, res);
			comb.remove(comb.size() - 1);
		}
	}

	@Test
	public void test1() {
		int[] candidates = new int[] { 1, 2, 2, 2 };
		int target = 4;
		List<List<Integer>> res = combinationSum2(candidates, target);
		for (List<Integer> comb : res)
			System.out.println(comb.toString());
	}
}
