package interview.leetcode;

/**
 * Given a binary tree, find its maximum depth. The maximum depth is the number
 * of nodes along the longest path from the root node down to the farthest leaf
 * node.
 * 
 * @author yazhoucao
 * 
 */
public class Maximum_Depth_of_Binary_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Super neat version
	 * 
	 * Notice: this solution won't work on minimum depth because the max()
	 * function always choose the max, won't stop at the node that has only one
	 * branch, but min() cannot automatically ignore the case that
	 * root.left!=null && root.right==null or root.right!=null &&
	 * root.left==null, min() will stop at here and return the false value.
	 */
	public int maxDepth(TreeNode root) {
		if (root == null)
			return 0;
		return max(maxDepth(root.left), maxDepth(root.right)) + 1;
	}

	public int max(int a, int b) {
		return a > b ? a : b;
	}

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

}
