package interview.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

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
		// TODO Auto-generated method stub
		int[] candidates = new int[]{1,1,2,5,6,7,10};
		int target = 8;
		List<List<Integer>> res = combinationSum2(candidates, target);
		for(List<Integer> comb : res)
			System.out.println(comb.toString());
	}

    public static List<List<Integer>> combinationSum2(int[] num, int target) {
    	Arrays.sort(num);
    	List<List<Integer>> res = new ArrayList<List<Integer>>();
    	addCombSum(num, 0, target, new Stack<Integer>(), res);
    	return res;
    }
    
    public static void addCombSum(int[] num, int start, int sum, Stack<Integer> comb, List<List<Integer>> res){
    	if(sum==0){
    		res.add(new ArrayList<Integer>(comb));
    		return;
    	}
    	if(sum<0)
    		return;
    	
    	for(int i=start; i<num.length; i++){
    		if(num[i]>sum||(i>start && num[i]==num[i-1]))
    			continue;
    		comb.push(num[i]);
    		addCombSum(num, i+1, sum-num[i], comb, res);	
    		comb.pop();
    	}
    }
}
