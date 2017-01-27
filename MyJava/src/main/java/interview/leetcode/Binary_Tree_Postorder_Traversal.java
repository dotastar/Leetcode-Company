package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the postorder traversal of its nodes' values.
 * 
 * For example: Given binary tree {1,#,2,3}, return [3,2,1].
 * 
 * Note: Recursive solution is trivial, could you do it iteratively?
 * 
 * @author yazhoucao
 * 
 */
public class Binary_Tree_Postorder_Traversal {
	/*
	 * Test case:
	   1
	  / \
	 2   3
	    /
	   4
	    \
	     5
	 */
	
	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.left.right = new TreeNode(5);

		List<Integer> res = postorderTraversal_Iter(root);
		System.out.println(res.toString());
		List<Integer> res2 = postorderTraversal_Recur(root);
		System.out.println(res2.toString());
	}

	/**
	 * The key to to iterative postorder traversal is the following:
	 * Find the relation between the previously visited node and the current
	 * node.Use a stack to track nodes
	 */
	public static List<Integer> postorderTraversal_Iter(TreeNode root) {
		List<Integer> res = new ArrayList<Integer>();
		if (root == null)
			return res;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);
		TreeNode prev = null;
		while (!stack.isEmpty()) {
			TreeNode curr = stack.peek();
			if(prev==null || prev.left==curr || prev.right==curr){
				//go down the tree.
	            //check if current node is leaf, if so, process it and pop stack,
	            //otherwise, keep going down
				if(curr.left!=null)
					stack.push(curr.left);
				else if(curr.right!=null)
					stack.push(curr.right);
				else
					res.add(stack.pop().val);
			}else if(curr.left==prev){
				//just went up the tree from left node    
	            //need to check if there is a right child
	            //if yes, push it to stack
	            //otherwise, process parent and pop stack
				if(curr.right!=null)
					stack.push(curr.right);
				else
					res.add(stack.pop().val);
			}else if(curr.right==prev)
	            //just went up the tree from right node 
	            //after coming back from right node, process parent node and pop stack. 
				res.add(stack.pop().val);

			prev = curr;
		}
		return res;
	}
	
	/**
	 * The same as above, iterative fashion, more concise, remove comments
	 */
    public List<Integer> postorderTraversal_Concise(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root==null) 
            return res;
        Stack<TreeNode> stk = new Stack<>();
        TreeNode prev = null;
        stk.push(root);
        while(!stk.isEmpty()){
            TreeNode curr = stk.peek();
            if(prev==null || prev.left==curr || prev.right==curr){ //down
                if(curr.left!=null) //down left
                    stk.push(curr.left);
                else if(curr.right!=null){  //down right
                    stk.push(curr.right);
                }else   // leave
                    res.add(stk.pop().val);
            }else if(prev==curr.left){  // up from left
                if(curr.right==null)
                    res.add(stk.pop().val);
                else
                    stk.push(curr.right);
            }else 
                res.add(stk.pop().val);	 // up from right
                
            prev = curr;
        }
        return res;
    }
	
	/**
	 * Same solution as above, it is just added more readable comments
	 * 
	 * Basically the idea is to differentiate three situations: 
	 * 
	 * (or you can also call it the moving directions)
	 * 
	 * 1.go down, move down from parent, the path could be a right branch
	 * or a left branch or an initialization situation (first enter the loop).
	 * Then check if it has left or right son to keep going down or it is the
	 * leaf node which should be visited.
	 * 
	 * 2.go up from a left son, then check if it has a right son, it yes, then
	 * go down to its right son, otherwise(it doesn't have a right son), visit
	 * current node which is the parent (this case can be merged with below 
	 * condition)  
	 * 
	 * 3.go up from a right son, it is the time to visit the current node which 
	 * is the parent
	 * 
	 */
	public static List<Integer> postorderTraversal2(TreeNode root){
        List<Integer> res = new ArrayList<Integer>();
        if(root==null)  return res;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        TreeNode prev = null;
        while(!stack.isEmpty()){
            TreeNode curr = stack.peek();
            if(prev==null || curr==prev.left || curr==prev.right){
            	//go down, from parent, curr could be either left or right son
                if(curr.left!=null){
                	stack.push(curr.left);	//down left
                }else if(curr.right!=null){
                	stack.push(curr.right);	//down right if left is null
                }else{	
                	res.add(curr.val);//leaf node if both branches are null
                	stack.pop();
                }
            }else if(curr.left == prev && curr.right!=null){
            	//go up, back to parent from left son, and its right son is null
            	stack.push(curr.right);	
            }else{	//curr.right==prev || curr.right==null
            	//go up, back to parent from its right son or 
            	//could be back from left son and its right son is null
                res.add(curr.val);
                stack.pop();
            }
            prev = curr;
        }
        return res;
	}

	public static List<Integer> postorderTraversal_Recur(TreeNode root) {
		List<Integer> res = new ArrayList<Integer>();
		postorder_Recur(root, res);
		return res;
	}

	public static void postorder_Recur(TreeNode node, List<Integer> res) {
		if (node == null)
			return;
		postorder_Recur(node.left, res);
		postorder_Recur(node.right, res);
		res.add(node.val);
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
