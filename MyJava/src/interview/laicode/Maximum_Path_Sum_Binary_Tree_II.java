package interview.laicode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Maximum Path Sum Binary Tree II
 * Hard
 * Recursion
 * 
 * Given a binary tree in which each node contains an integer number. Find the
 * maximum possible sum from any node to any node (the start node and the end
 * node can be the same).
 * 
 * Assumptions
 * 
 * ​The root of the given binary tree is not null
 * 
 * Examples
 * 
 * -1
 * 
 * / \
 * 
 * 2 11
 * 
 * / \
 * 
 * 6 -14
 * 
 * one example of paths could be -14 -> 11 -> -1 -> 2
 * 
 * another example could be the node 11 itself
 * 
 * The maximum path sum in the above binary tree is 6 + 11 + (-1) + 2 = 18
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
public class Maximum_Path_Sum_Binary_Tree_II {

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Maximum_Path_Sum_Binary_Tree_II.class);
	}

	private int globalMax;

	public int maxPathSum(TreeNode root) {
		globalMax = Integer.MIN_VALUE;
		maxPathSumHelper(root);
		return globalMax;
	}

	public int maxPathSumHelper(TreeNode root) {
		if (root == null)
			return 0;
		int leftPath = maxPathSumHelper(root.left);
		int rightPath = maxPathSumHelper(root.right);
		int pathSum = max(leftPath + root.key, rightPath + root.key, root.key);
		int localMax = max(pathSum, leftPath + rightPath + root.key);
		if (localMax > globalMax)
			globalMax = localMax;
		return pathSum;
	}

	private int max(int... values) {
		int max = values[0];
		for (int val : values)
			if (val > max)
				max = val;
		return max;
	}

	/**
	 * 3
	 * -8 9
	 * 4 10 2 -5
	 * 1 -2
	 * 
	 */
	@Test
	public void test1() {
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(-8);
		root.right = new TreeNode(9);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(10);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(-5);
		root.left.left.left = new TreeNode(1);
		root.left.left.right = new TreeNode(-2);

		int res = maxPathSum(root);
		int ans = 16;
		assertTrue("Wrong: " + res, res == ans);
	}

	/**
	 * 3
	 * -8 -9
	 * 4 10 2 -5
	 * 10 20
	 * 
	 */
	@Test
	public void test2() {
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(-8);
		root.right = new TreeNode(-9);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(10);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(-5);
		root.left.left.left = new TreeNode(10);
		root.left.left.right = new TreeNode(20);

		int res = maxPathSum(root);
		int ans = 34;
		assertTrue("Wrong: " + res, res == ans);
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
