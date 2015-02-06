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
		int[] candidates = new int[]{2,3,6,7};
		int target = 7;
		List<List<Integer>> res = combinationSum(candidates, target);
		for(List<Integer> comb : res)
			System.out.println(comb.toString());
	}
	
	
	public static List<List<Integer>> combinationSum(int[] candidates, int target) {
		Arrays.sort(candidates);
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		findComb(candidates, target, 0, new Stack<Integer>(), res);
		return res;
	}
	
	
	public static void findComb(int[] candidates, int target, int idx, Stack<Integer> stack, List<List<Integer>> res){
		if(target<0)
			return;
		if(target==0){
			List<Integer> comb = new ArrayList<Integer>(stack);
			res.add(comb);
			return;
		}
		
		for(int i=idx; i<candidates.length; i++){
			int num = candidates[i];
			stack.push(num);
			findComb(candidates, target-num, i, stack, res);
			stack.pop();
		}
	}
	
}
