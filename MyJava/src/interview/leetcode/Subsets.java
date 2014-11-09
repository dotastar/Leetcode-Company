package interview.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Given a set of distinct integers, S, return all possible subsets.
 * 
 * Note:
 * 
 * 1) Elements in a subset must be in non-descending order.
 * 
 * 2) The solution set must not contain duplicate subsets.
 * 
 * If S = [1,2,3], a solution is:
 * 
 * [ [3], [1], [2], [1,2,3], [1,3], [2,3], [1,2], [] ]
 * 
 * @author yazhoucao
 * 
 */
public class Subsets {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Thoughts
	 * 
	 * Given a set S of n distinct integers, there is a relation between Sn and
	 * Sn-1. The subset of Sn-1 is the union of {subset of Sn-1} and {each
	 * element in Sn-1 + one more element}. Therefore, a Java solution can be
	 * quickly formalized.
	 * 
	 */
	public List<List<Integer>> subsets(int[] S) {
		Arrays.sort(S);
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		// build current subsets, which is previous subset + S[i]
		for (int i = 0; i < S.length; i++) {
			List<List<Integer>> newsets = new ArrayList<List<Integer>>();
			for (List<Integer> set : res) {
				List<Integer> copy = new ArrayList<Integer>(set);
				copy.add(S[i]);
				newsets.add(copy);
			}
			res.addAll(newsets);
			// add single S[i] as a subset to current
			List<Integer> single = new ArrayList<Integer>();
			single.add(S[i]);
			res.add(single);
		}

		res.add(new ArrayList<Integer>());
		return res;
	}

	/**
	 * Second time practice, recursion
	 */
	public List<List<Integer>> subsets2(int[] S) {
		Arrays.sort(S);
		List<List<Integer>> sets = new ArrayList<List<Integer>>();
		sets.add(new ArrayList<Integer>());
		subsets(S, 0, new Stack<Integer>(), sets);
		return sets;
	}

	public void subsets(int[] S, int idx, Stack<Integer> set,
			List<List<Integer>> sets) {
		if (idx == S.length) {
			return;
		}

		for (int i = idx; i < S.length; i++) {
			set.push(S[i]);
			sets.add(new ArrayList<Integer>(set));
			subsets(S, i + 1, set, sets);
			set.pop();
		}
	}
}
