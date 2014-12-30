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
		return getHeight(root) >= 0;
	}

	/**
	 * Get Height Difference between left and right of subtree
	 */
	public static int getHeight(TreeNode node) {
		if (node == null)
			return 0;
		int left = getHeight(node.left);
		int right = getHeight(node.right);

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

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}
}
