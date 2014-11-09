package interview.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's
 * sum equals the given sum.
 * 
 * For example: Given the below binary tree and sum = 22,
 * 
              5
             / \
            4   8
           /   / \
          11  13  4
         /  \    / \
        7    2  5   1 
 * 
 * return [ [5,4,11,2], [5,8,4,5] ]
 * 
 * @author yazhoucao
 * 
 */
public class Path_Sum_II {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeNode root = new TreeNode(-2);
		root.right = new TreeNode(-3);
		List<List<Integer>> res = pathSum(root, -2);
		for(List<Integer> l : res)
			System.out.println(l.toString());
	}

    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> paths = new LinkedList<List<Integer>>();
    	addSum(root, sum, new Stack<Integer>(), paths);
    	return paths;
    }

    public static void addSum(TreeNode node, int sum, Stack<Integer> stack, List<List<Integer>> paths){
    	if(node==null)
    		return;
    	sum -= node.val;
    	if(node.left==null && node.right==null && sum==0){	//leaf
    		List<Integer> path = new LinkedList<Integer>(stack);
    		path.add(node.val);
    		paths.add(path);
    		return;
    	}

		stack.push(node.val);
		addSum(node.left, sum, stack, paths);
		addSum(node.right, sum, stack, paths);
    	stack.pop();
    }
    
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}

		public String toString() {
			return Integer.toString(val);
		}
	}
}
