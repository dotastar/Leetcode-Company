package interview.leetcode;

/**
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path
 * such that adding up all the values along the path equals the given sum.
 * 
 * For example: Given the below binary tree and sum = 22,
 * 5
 * / \
 * 4 8
 * / / \
 * 11 13 4
 * / \ \
 * 7 2 1
 * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 * 
 * @author yazhoucao
 * 
 */
public class Path_Sum {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		// root.right = new TreeNode(3);
		System.out.println(hasPathSum(root, 1));
	}

	/**
	 * Traverse to the leaf, add the node value along the path
	 * Notice: the leaf is the node that its left and right child are both null.
	 * Time: O(n)
	 */
	public static boolean hasPathSum(TreeNode root, int sum) {
		if (root == null)
			return false;
		sum -= root.val;
		if (root.left == null && root.right == null)
			return sum == 0 ? true : false;
		return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
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
