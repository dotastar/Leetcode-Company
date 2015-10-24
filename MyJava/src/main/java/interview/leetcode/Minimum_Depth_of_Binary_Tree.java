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
	 * BFS is better for minimum depth.
	 */
	public int minDepth(TreeNode root) {
		if (root == null)
			return 0;
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		q.add(root);
		int depth = 0;
		while (!q.isEmpty()) {
			depth++;
			int size = q.size();
			for (int i = 0; i < size; i++) {
				TreeNode node = q.poll();
				if (node.left == null && node.right == null)
					return depth;
				if (node.left != null)
					q.add(node.left);
				if (node.right != null)
					q.add(node.right);
			}
		}
		return depth;
	}

	/**
	 * Recursion
	 */
	public int minDepth2(TreeNode root) {
		if (root == null)
			return 0;
		else if (root.left == null && root.right == null)
			return 1;
		else if (root.left == null && root.right != null)
			return minDepth(root.right) + 1;
		else if (root.left != null && root.right == null)
			return minDepth(root.left) + 1;
		else
			return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
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
