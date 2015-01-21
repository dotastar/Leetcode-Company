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
		int[] S = { 1, 2, 3, 4, 5 };
		List<List<Integer>> res = generateSubsets(S);
		for (List<Integer> subset : res)
			System.out.println(subset.toString());
	}

	/**
	 * Alternative solution, Time: O(n*(2^n))
	 * Realizing that for a given ordering of length n and the set of all
	 * subsets of S, there exists a one-to-one correspondence between the 2^n
	 * bit arrays of length n and the set of all subsets of S --- the 1s in the
	 * n-length bit array v indicate the elements of S in the subset
	 * corresponding to v.
	 */
	private static final double LOG_2 = Math.log(2);

	public static List<List<Integer>> generateSubsets(int[] S) {
		Arrays.sort(S);
		List<List<Integer>> res = new ArrayList<>();
		for (int i = 0; i < (1 << S.length); ++i) {
			List<Integer> subset = new ArrayList<Integer>();
			int x = i;
			while (x != 0) {
				// x & ~(x - 1): remain only the lowest bit that is 1
				// log.e.x / log.e.2 = log.2.(x)
				int tar = (int) (Math.log(x & ~(x - 1)) / LOG_2);
				subset.add(S[tar]);
				x &= x - 1; // remove the lowest bit that is 1
			}
			res.add(subset);
		}
		return res;
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
