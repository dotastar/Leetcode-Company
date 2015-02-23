package interview.leetcode;

import java.util.Stack;

/**
 * Implement an iterator over a binary search tree (BST). Your iterator will be
 * initialized with the root node of a BST.
 * 
 * Calling next() will return the next smallest number in the BST.
 * 
 * Note: next() and hasNext() should run in average O(1) time and uses O(h)
 * memory, where h is the height of the tree.
 * 
 * @author yazhoucao
 * 
 */
public class Binary_Search_Tree_Iterator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Basically it's a stateful iteratively inorder traversal of BST
	 * 
	 * @author yazhoucao
	 * 
	 */
	public static class BSTIterator {

		private Stack<TreeNode> stk;
		private TreeNode p;

		public BSTIterator(TreeNode root) {
			stk = new Stack<TreeNode>();
			p = root;
		}

		/** @return whether we have a next smallest number */
		public boolean hasNext() {
			return (!stk.isEmpty() || p != null);
		}

		/** @return the next smallest number */
		public int next() {
			int res = 0;
			while (hasNext()) {
				if (p != null) {
					stk.push(p);
					p = p.left;
				} else {
					p = stk.pop();
					res = p.val; // visit p
					p = p.right;
					break;
				}
			}
			return res;
		}
	}

	/**
	 * Your BSTIterator will be called like this:
	 * BSTIterator i = new BSTIterator(root);
	 * while (i.hasNext()) v[f()] = i.next();
	 */

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
