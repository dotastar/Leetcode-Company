package interview.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Given a set of candidate numbers (C) and a target number (T), find all unique
 * combinations in C where the candidate numbers sums to T.
 * 
 * The same repeated number may be chosen from C unlimited number of times.
 * 
 * Note: All numbers (including target) will be positive integers. Elements in a
 * combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤
 * … ≤ ak). The solution set must not contain duplicate combinations.
 * 
 * For example, given candidate set 2,3,6,7 and target 7, A solution set is:
 * 
 * [7] [2, 2, 3]
 * 
 * @author yazhoucao
 * 
 */
public class Combination_Sum {
	public static void main(String[] args) {
		int[] candidates = new int[] { 2, 3, 6, 7 };
		int target = 7;
		List<List<Integer>> res = combinationSum2(candidates, target);
		for (List<Integer> comb : res)
			System.out.println(comb.toString());
	}

	public static List<List<Integer>> combinationSum2(int[] candidates,
			int target) {
		List<List<Integer>> res = new ArrayList<>();
		combination(res, new int[candidates.length], candidates, target, 0);
		return res;
	}

	/**
	 * DFS (Backtracking)
	 * another version of DFS
	 * In each level we try to add the same kind of candidate in different
	 * times.
	 * In each recursion level, there are many states:
	 * 1.add candidates[idx] zero times.
	 * 2.add candidates[idx] one time.
	 * 3.add candidates[idx] two time.
	 * 4.add candidates[idx] three time.
	 * ...
	 * Unil remains<0 which means it can't be added any more.
	 * 
	 * The depth of the recursion tree will be candids.length.
	 * In each level, there are remains/candids[idx] branches.
	 */
	private static void combination(List<List<Integer>> res, int[] comb,
			int[] candids, int remains, int idx) {
		if (idx == candids.length || remains <= 0) {
			if (remains == 0)
				System.out.println(Arrays.toString(comb));
			// res.add(); // add to solution
			return;
		}
		for (int i = 0; candids[idx] * i <= remains; i++) { // i : times to add
			comb[idx] = i;
			combination(res, comb, candids, remains - candids[idx] * i, idx + 1);
		}
	}

	/**
	 * DFS (Backtracking)
	 * In each recursion level, we try to just add 1 element.
	 * In each recursion level, there are just two states:
	 * add candidates[idx] or do not add
	 * 
	 * (assume candids[0] is the smallest)
	 * The depth of the recursion tree will be remains/candids[0].
	 * In each level, there are candids.length branches.
	 */
	public static List<List<Integer>> combinationSum(int[] candidates,
			int target) {
		Arrays.sort(candidates);
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		findComb(candidates, target, 0, new Stack<Integer>(), res);
		return res;
	}

	public static void findComb(int[] candidates, int target, int idx,
			Stack<Integer> comb, List<List<Integer>> res) {
		if (target < 0)
			return;
		if (target == 0) {
			List<Integer> combination = new ArrayList<Integer>(comb);
			res.add(combination);
			return;
		}

		for (int i = idx; i < candidates.length; i++) {
			int num = candidates[i];
			comb.push(num);
			findComb(candidates, target - num, i, comb, res);
			comb.pop();
		}
	}

}
