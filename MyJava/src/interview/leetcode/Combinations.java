package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given two integers n and k, return all possible combinations of k numbers out
 * of 1 ... n.
 * 
 * For example, If n = 4 and k = 2, a solution is: [ [2,4], [3,4], [2,3], [1,2],
 * [1,3], [1,4], ]
 * 
 * @author yazhoucao
 * 
 */
public class Combinations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Recursion, Time: Exponential, Space: O(lg(n)).
	 * Cn(k)
	 */
	public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        combine(n, k, 1, new Stack<Integer>(), res);
        return res;
    }
    
    public void combine(int n, int k, int start, Stack<Integer> comb, List<List<Integer>> combs){
        if(k==0){
            List<Integer> c = new ArrayList<Integer>(comb);
            combs.add(c);
            return;
        }
        
        for(int i=start; i<=n; i++){
            comb.push(i);
            combine(n, k-1, i+1, comb, combs);
            comb.pop();
        }
    }
}
