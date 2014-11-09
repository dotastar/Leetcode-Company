package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the zigzag level order traversal of its nodes'
 * values. (ie, from left to right, then right to left for the next level and
 * alternate between).
 * 
 * For example: Given binary tree {3,9,20,#,#,15,7},
 * 
    3
   / \
  9  20
    /  \
   15   7
 * 
 * return its zigzag level order traversal as:
[
  [3],
  [20,9],
  [15,7]
]
 * @author yazhoucao
 * 
 */
public class Binary_Tree_Zigzag_Level_Order_Traversal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeNode root = new TreeNode(0);
		root.left = new TreeNode(1);
		root.right = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(6);
		root.left.left.right = new TreeNode(7);
		root.right.left.right = new TreeNode(8);
		
		List<List<Integer>> res = zigzagLevelOrder(root);
		for(List<Integer> list : res)
			System.out.println(list.toString());
	}
	
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if(root==null)  return res;
        Stack<TreeNode> stk = new Stack<TreeNode>();
        stk.push(root);
        boolean reversed = true;
        List<TreeNode> nextRow = new ArrayList<TreeNode>();
        while(!stk.isEmpty()){
            int size = stk.size();
            List<Integer> row = new ArrayList<Integer>(size);
            nextRow.clear();
            for(int i=0; i<size; i++){
                TreeNode node = stk.pop();
                if(node!=null){
                    row.add(node.val);
                    if(reversed){
                    	nextRow.add(node.left);
                    	nextRow.add(node.right);
                    }else{
                    	nextRow.add(node.right);
                    	nextRow.add(node.left);
                    }
                }
            }
            
            reversed = !reversed;
            stk.addAll(nextRow);
            if(row.size()>0)
                res.add(row);
        }
        return res;
    }

    
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
		public String toString(){
			return Integer.toString(val);
		}
	}
}
