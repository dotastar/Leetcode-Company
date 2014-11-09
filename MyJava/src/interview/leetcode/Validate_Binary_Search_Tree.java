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
	}

	/**
	 * Recursion solution, based on the definition, every node will have value
	 * range based on its father and ancestors.
	 * 
	 * Time: O(n), traverse all the nodes
	 * 
	 * Space: O(lg(n)), recursion stack
	 * 
	 */
	public static boolean isValidBST(TreeNode root) {
		return isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public static boolean isValidBST(TreeNode root, int min, int max) {
		if (root == null)
			return true;
		if (root.left != null
				&& (root.left.val >= root.val || root.left.val <= min))
			return false;
		if (root.right != null
				&& (root.right.val <= root.val || root.right.val >= max))
			return false;

		return isValidBST(root.left, min, root.val)
				&& isValidBST(root.right, root.val, max);
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
		TreeNode p = root;
		int lastVal = Integer.MIN_VALUE;
		while (!stk.isEmpty() || p != null) {
			if (p != null) {
				stk.push(p);
				p = p.left;
			} else {
				TreeNode node = stk.pop();
				if (node.val > lastVal) {
					lastVal = node.val;
				} else
					return false;

				p = node.right;
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
