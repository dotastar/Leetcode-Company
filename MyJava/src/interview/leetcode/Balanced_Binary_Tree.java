package interview.leetcode;

/**
 * Given a binary tree, determine if it is height-balanced.
 * 
 * For this problem, a height-balanced binary tree is defined as a binary tree
 * in which the depth of the two subtrees of every node never differ by more
 * than 1.
 * 
 * @author yazhoucao
 * 
 */
public class Balanced_Binary_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(2);

		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(3);
		root.right.left = new TreeNode(3);
		root.right.right = new TreeNode(3);

		root.left.left.left = new TreeNode(4);
		root.left.left.right = new TreeNode(4);
		root.left.right.left = new TreeNode(4);
		root.left.right.right = new TreeNode(4);
		root.right.left.left = new TreeNode(4);
		root.right.left.right = new TreeNode(4);
		// root.right.right.left = new TreeNode(4);
		// root.right.right.right = new TreeNode(4);

		root.left.right.left.left = new TreeNode(5);
		root.left.right.left.right = new TreeNode(5);

		System.out.println(isBalanced(root));
	}

	public static boolean isBalanced(TreeNode root) {
		return getHeightDiff(root) >= 0;
	}

	/**
	 * Get Height Difference between left and right of subtree
	 */
	public static int getHeightDiff(TreeNode node) {
		if (node == null)
			return 0;
		int left = getHeightDiff(node.left);
		int right = getHeightDiff(node.right);

		if (left == -1 || right == -1)
			return -1;

		if (Math.abs(left - right) > 1)
			return -1;

		return 1 + Math.max(left, right);
	}

	/**
	 * Second time practice
	 * if it's not balanced, return -1;
	 * otherwise return the height of the node.
	 */
	public static int height(TreeNode node) {
		if (node == null)
			return 0;
		int left = height(node.left);
		if (left < 0)
			return left;
		int right = height(node.right);
		if (right < 0)
			return right;

		if (Math.abs(left - right) > 1)
			return -1;
		else
			return left > right ? left + 1 : right + 1;
	}

	/**
	 * Naive solution
	 * Time: O(nlogn)
	 * Each level need O(n) time and there are total O(logn) levels.
	 */
	public boolean isBalanced_naive(TreeNode root) {
		if (root == null)
			return true;
		int leftH = getHeight(root.left);
		int rightH = getHeight(root.right);
		// current left subtree and right subtree height difference is greater
		// than one, left subtree and right subtree are also balanced.
		if (Math.abs(leftH - rightH) > 1)
			return false;
		else
			return isBalanced(root.left) && isBalanced(root.right);
	}

	/**
	 * Time: O(n) (traverse all the nodes)
	 */
	private int getHeight(TreeNode root) {
		if (root == null)
			return 0;
		int left = getHeight(root.left);
		int right = getHeight(root.right);
		return left > right ? left + 1 : right + 1;
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}
}
