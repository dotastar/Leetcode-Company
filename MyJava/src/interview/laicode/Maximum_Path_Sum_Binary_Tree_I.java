package interview.laicode;


/**
 * 
 * Maximum Path Sum Binary Tree I
 * Fair
 * Recursion
 * 
 * Given a binary tree in which each node contains an integer number. Find the
 * maximum possible sum from one leaf node to another leaf node. If there is no
 * such path available, return Integer.MIN_VALUE(Java)/INT_MIN (C++).
 * 
 * Examples
 * 
 * -15
 * 
 * / \
 * 
 * 2 11
 * 
 * / \
 * 
 * 6 14
 * 
 * The maximum path sum is 6 + 11 + 14 = 31.
 * 
 * How is the binary tree represented?
 * 
 * We use the level order traversal sequence with a special symbol "#" denoting
 * the null node.
 * 
 * For Example:
 * 
 * The sequence [1, 2, 3, #, #, 4] represents the following binary tree:
 * 
 * 1
 * 
 * / \
 * 
 * 2 3
 * 
 * /
 * 
 * 4
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Maximum_Path_Sum_Binary_Tree_I {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private int globalMax;

	public int maxPathSum(TreeNode root) {
		globalMax = Integer.MIN_VALUE;
		if (root != null)
			findPathSum(root);
		return globalMax;
	}

	private int findPathSum(TreeNode root) {
		if (root == null)
			return 0;
		int leftPath = findPathSum(root.left);
		int rightPath = findPathSum(root.right);
		int sum = leftPath + rightPath + root.key;
		if (sum > globalMax && root.left != null && root.right != null)
			globalMax = sum;

		if (root.left == null)
			return rightPath + root.key;
		else if (root.right == null)
			return leftPath + root.key;
		else
			return Math.max(rightPath, leftPath) + root.key;
	}

	public static class TreeNode {
		public int key;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int key) {
			this.key = key;
		}
	}
}
