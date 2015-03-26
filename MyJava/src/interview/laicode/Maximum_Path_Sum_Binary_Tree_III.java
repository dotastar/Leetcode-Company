package interview.laicode;


/**
 * 
 * Maximum Path Sum Binary Tree III
 * Fair
 * Recursion
 * 
 * Given a binary tree in which each node contains an integer number. Find the
 * maximum possible path sum(both the starting and ending node should be on the
 * same path from root to one of the leaf nodes, and the path is allowed to
 * contain only one node).
 * 
 * Assumptions
 * 
 * The root of given binary tree is not null
 * 
 * Examples
 * 
 * -5
 * 
 * / \
 * 
 * 2 11
 * 
 * / \
 * 
 * 6 14
 * 
 * The maximum path sum is 11 + 14 = 25
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
public class Maximum_Path_Sum_Binary_Tree_III {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private int globalMax;

	public int maxPathSum(TreeNode root) {
		globalMax = Integer.MIN_VALUE;
		maxPathSumHelper(root);
		return globalMax;
	}

	private int maxPathSumHelper(TreeNode root) {
		if (root == null)
			return 0;
		int leftPath = maxPathSumHelper(root.left);
		int rightPath = maxPathSumHelper(root.right);
		int localMax = Math.max(Math.max(leftPath, rightPath) + root.key,
				root.key);
		if (localMax > globalMax)
			globalMax = localMax;
		return localMax;
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
