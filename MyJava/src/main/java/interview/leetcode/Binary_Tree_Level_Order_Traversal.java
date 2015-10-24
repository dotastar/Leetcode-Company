package interview.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree, return the level order traversal of its nodes' values.
 * (ie, from left to right, level by level).
 * 
 * For example: Given binary tree {3,9,20,#,#,15,7},
    3
   / \
  9  20
    /  \
   15   7
 * return its level order traversal as:
[
  [3],
  [9,20],
  [15,7]
]
 * 
 * @author yazhoucao
 * 
 */
public class Binary_Tree_Level_Order_Traversal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * BFS
	 */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<List<Integer>>();
    	if(root==null)
        	return lists;
    	Queue<TreeNode> q = new LinkedList<TreeNode>();
    	q.add(root);
    	while(!q.isEmpty()){
    		int len = q.size();
    		List<Integer> list = new ArrayList<Integer>();
    		for(int i=0; i<len; i++){
    			TreeNode node = q.poll();
    			list.add(node.val);
    			if(node.left!=null)
    				q.add(node.left);
    			if(node.right!=null)
    				q.add(node.right);
    		}
    		lists.add(list);
    	}
    	return lists;
    }
    
    /**
     * Same solution, another way to write it
     * 
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        int sizeCount = 1;
        List<Integer> row =  new ArrayList<Integer>();
        while(!q.isEmpty()){
            if(sizeCount==0){
                sizeCount = q.size();
                res.add(row);
                row = new ArrayList<Integer>();
            }

            TreeNode node = q.poll();
            if(node!=null){
                row.add(node.val);
                q.offer(node.left);
                q.offer(node.right);
            }
            sizeCount--;
        }
        return res;
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
