package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the preorder traversal of its nodes' values.
 * 
 * For example: 
 * Given binary tree {1,#,2,3}, 
 *  1 
 *   \ 
 *    2
 *   / 
 *  3 
 * return [1,2,3].
 * 
 * Note: Recursive solution is trivial, could you do it iteratively?
 * 
 * @author yazhoucao
 * 
 */
public class Binary_Tree_Preorder_Traversal {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.left.right = new TreeNode(5);

		List<Integer> res = preorderTraversal(root);
		System.out.println(res.toString());
		List<Integer> res2 = new ArrayList<Integer>();
		preorderRecur(root, res2);
		System.out.println(res2.toString());
	}

	/*
	 * Test case: 1 / \ 2 3 / 4 \ 5
	 */
	/**
	 * Iterative fasion
	 * 
	 * @param root
	 * @return
	 */
	public static List<Integer> preorderTraversal(TreeNode root) {
		List<Integer> res = new ArrayList<Integer>();
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);

		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			if (node == null)
				continue;
			res.add(node.val);
			stack.push(node.right); // notice: push right first
			stack.push(node.left);
		}

		return res;
	}

	/**
	 * Recursive fasion
	 * 
	 * @param node
	 * @param list
	 */
	public static void preorderRecur(TreeNode node, List<Integer> list) {
		if (node == null)
			return;

		list.add(node.val);
		preorderRecur(node.left, list);
		preorderRecur(node.right, list);
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
