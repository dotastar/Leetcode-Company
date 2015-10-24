package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the inorder traversal of its nodes' values.
 * 
 * For example: Given binary tree {1,#,2,3}, 
   1
    \
     2
    /
   3
 * return [1,3,2].
 * 
 * Note: Recursive solution is trivial, could you do it iteratively? 
 * 
 * OJ's Binary Tree Serialization:
 * The serialization of a binary tree follows a level order traversal, 
 * where '#' signifies a path terminator where no node exists below.
 * 
 * Here's an example:
   1
  / \
 2   3
    /
   4
    \
     5
 * The above binary tree is serialized as "{1,2,3,#,#,4,#,#,5}".
 * @author yazhoucao
 * 
 */
public class Binary_Tree_Inorder_Traversal {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.left.right = new TreeNode(5);
		
		inorderRecur(root);
		System.out.println();
		List<Integer> res = inorderTraversal(root);
		System.out.println(res.toString());
	}
	
	
	
	/**
	 * Iterative fasion
	 * @param root
	 * @return
	 */
	 public static List<Integer> inorderTraversal(TreeNode root) {
	        List<Integer> res = new ArrayList<Integer>();
	        Stack<TreeNode> stack = new Stack<TreeNode>();
	        TreeNode p = root;
	        while(!stack.empty() || p!=null){
	        	 // if it is not null, push to stack
	            //and go down the tree to left
	        	if(p!=null){
	        		stack.push(p);
	        		p = p.left;
	        	}else{
	                // if no left child
	                // pop stack, process the node
	                // then let p point to the right
	        		TreeNode parent = stack.pop();
	        		res.add(parent.val);
	        		p = parent.right;
	        	}
	        }
	        return res;
	 }
	 
	 /**
	  * Recursive fasion
	  * @param node
	  */
	 public static void inorderRecur(TreeNode node){
		 if(node==null)
			 return;
		 
		 inorderRecur(node.left);
		 System.out.print(node.val + " ");
		 inorderRecur(node.right);
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
