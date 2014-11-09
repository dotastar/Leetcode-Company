package interview.leetcode;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Given a binary tree, find its minimum depth.
 * 
 * The minimum depth is the number of nodes along the shortest path from the
 * root node down to the nearest leaf node.
 * 
 * @author yazhoucao
 * 
 */
public class Minimum_Depth_of_Binary_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * BFS to the first leaf node, it is the shortest path
	 * 
	 */
    public int minDepth(TreeNode root) {
    	if(root==null) return 0;
    	Queue<TreeNode> q = new LinkedList<TreeNode>();
    	q.add(root);
    	int depth=0;
    	while(!q.isEmpty()){
    		depth++;
    		int size = q.size();
    		for(int i=0; i<size; i++){
    			TreeNode node = q.poll();
    			if(node.left==null&&node.right==null)
    				return depth;
    			if(node.left!=null)
    				q.add(node.left);
    			if(node.right!=null)
    				q.add(node.right);
    		}
    	}
    	return depth;
    }
    
    /**
     * Recursion, BFS is better for minimum depth.
     * 
     */
    public int minDepth2(TreeNode root) {
        if(root==null)  
            return 0;
        else
            return minDepth(root, 1);
    }
    
    private int minDepth(TreeNode node, int depth){
        if(node.left==null && node.right==null) return depth;
        
        if(node.left!=null && node.right==null)
            return minDepth(node.left, depth+1);
        else if(node.right!=null && node.left==null)
            return minDepth(node.right, depth+1);
        else{
            int leftMin = minDepth(node.left, depth+1);
            int rightMin = minDepth(node.right, depth+1);
            return leftMin<rightMin?leftMin:rightMin;    
        }
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
