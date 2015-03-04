package interview.leetcode;

import java.util.Stack;

/**
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 * 
 * Assume a BST is defined as follows:
 * 
 * The left subtree of a node contains only nodes with keys less than the node's
 * key.
 * 
 * The right subtree of a node contains only nodes with keys greater than the
 * node's key.
 * 
 * Both the left and right subtrees must also be binary search trees.
 * 
 * @author yazhoucao
 * 
 */
public class Validate_Binary_Search_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// {10,5,15,#,#,6,20}
		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(5);
		root.right = new TreeNode(15);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(20);
		System.out.println(isValidBST(root));

		TreeNode node = new TreeNode(0);
		System.out.println(isValidBST(node));
	}

	/**
	 * Recursion solution, based on the definition, every node will have value
	 * range based on its father and ancestors.
	 * 
	 * Notice: this solution uses long to resolve the overflow problem!
	 * E.g. A valid tree like below will return false if use int instead of
	 * long:
	 * Integer.MIN_VALUE
	 * / \
	 * null Integer.MAX_VALUE
	 * 
	 * Time: O(n), traverse all the nodes
	 * 
	 * Space: O(lg(n)), recursion stack
	 * 
	 */
	public static boolean isValidBST(TreeNode root) {
		return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	private static boolean isValidBST(TreeNode node, long min, long max) {
		if (node == null)
			return true;
		if (node.val <= min || node.val >= max)
			return false;
		return isValidBST(node.left, min, node.val)
				&& isValidBST(node.right, node.val, max);
	}

	/**
	 * Iterative solution, make use of the property that in-order traversing
	 * binary search tree will have a sorted sequence of keys (ascending order).
	 * 
	 * So, the idea is inorder traverse and see if it is in ascending order.
	 * 
	 * Time: O(n), have to traverse all the nodes
	 * 
	 * Space: O(lg(n)), need a stack to do iterative traversal
	 */
	public boolean isValidBST2(TreeNode root) {
		Stack<TreeNode> stk = new Stack<TreeNode>();
		TreeNode p = root, prev = null;
		while (!stk.isEmpty() || p != null) {
			if (p != null) {
				stk.push(p);
				p = p.left;
			} else {
				p = stk.pop(); // parent
				if (prev != null && prev.val >= p.val)
					return false;
				prev = p;
				p = p.right;
			}
		}
		return true;
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
