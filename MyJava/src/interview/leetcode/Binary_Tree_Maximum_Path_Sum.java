package interview.leetcode;

/**
 * Given a binary tree, find the maximum path sum.
 * 
 * The path may start and end at any node in the tree.
 * 
 * For example: Given the below binary tree,
 * 
 *      1
 *     / \
 *    2   3
 * 
 * Return 6.
 * 
 * @author yazhoucao
 * 
 */
public class Binary_Tree_Maximum_Path_Sum {

	public static void main(String[] args) {
		Binary_Tree_Maximum_Path_Sum obj1 = new Binary_Tree_Maximum_Path_Sum();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		System.out.println(obj1.maxPathSum(root));

		Binary_Tree_Maximum_Path_Sum obj2 = new Binary_Tree_Maximum_Path_Sum();
		TreeNode root1 = new TreeNode(2);
		root1.left = new TreeNode(-1);
		System.out.println(obj2.maxPathSum(root1));
	}

	/**
	 * Basically it is to find two path from the same subroot that have
	 * the max path sum, which is left + right + sum is maximum.
	 * 
	 * We can recursively solve it.
	 * 
	 * Check every node's path sum if it is max, and also pass the max
	 * one-path-sum to the parent, the one-path-sum could be either the
	 * node.val, or node.val+left, or node.val+right.
	 * 
	 * @param root
	 * @return
	 */
	// instead of use global value, we can also use
	// int[] max as a local variable pass in the function
	int max;

	public int maxPathSum(TreeNode root) {
		max = Integer.MIN_VALUE;
		traversePathSum(root);
		return max;
	}

	public int traversePathSum(TreeNode root) {
		if (root == null)
			return 0;
		int leftSum = traversePathSum(root.left);
		int rightSum = traversePathSum(root.right);

		updateMax(root.val, leftSum, rightSum);

		// as a one-path-sum, pass up to parent node
		int max = leftSum > rightSum ? leftSum : rightSum;
		max = max < 0 ? 0 : max;
		int pathSum = root.val + max;

		return pathSum;
	}

	/**
	 * As a complete path, update max value
	 */
	public void updateMax(int root, int left, int right) {
		if (left > 0)
			root += left;
		if (right > 0)
			root += right;
		if (root > max)
			max = root;
	}

	/**
	 * A more compact version of solution
	 */
	int sum;

	public int maxPathSum2(TreeNode root) {
		sum = Integer.MIN_VALUE;
		maxps(root);
		return sum;
	}

	public int maxps(TreeNode root) {
		if (root == null)
			return 0;

		int maxLeft = maxps(root.left);
		int maxRight = maxps(root.right);

		int sumboth = maxLeft + maxRight + root.val;
		sum = sumboth > sum ? sumboth : sum;

		int max = maxLeft > maxRight ? maxLeft : maxRight;
		max = max <= 0 ? root.val : max + root.val;
		sum = max > sum ? max : sum;

		return max;
	}

	/**
	 * Third time practice, use Array to store max, pass as reference
	 */
	public int maxPathSum3(TreeNode root) {
		int[] sum = new int[1];
		sum[0] = root == null ? 0 : root.val;
		maxPathSumHelper(root, sum);
		return sum[0];
	}

	private int maxPathSumHelper(TreeNode root, int[] maxSum) {
		if (root == null)
			return 0;
		int left = maxPathSumHelper(root.left, maxSum);
		int right = maxPathSumHelper(root.right, maxSum);
		int pathSum = max(left, right);
		pathSum = pathSum < 0 ? root.val : root.val + pathSum;
		updateMax(maxSum, root.val, left, right);
		return pathSum;
	}

	private void updateMax(int[] maxSum, int root, int left, int right) {
		int currMax = root;
		if (left > 0)
			currMax += left;
		if (right > 0)
			currMax += right;

		if (currMax > maxSum[0])
			maxSum[0] = currMax;
	}

	private int max(int a, int b) {
		return a > b ? a : b;
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
