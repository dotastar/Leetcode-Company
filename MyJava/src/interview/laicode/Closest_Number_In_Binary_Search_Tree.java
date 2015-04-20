package interview.laicode;

import interview.laicode.utils.TreeNode;

/**
 * Closest Number In Binary Search Tree
 * Fair
 * Data Structure
 * 
 * In a binary search tree, find the node containing the closest number to the
 * given target number.
 * 
 * Assumptions:
 * 
 * The given root is not null.
 * There are no duplicate keys in the binary search tree.
 * 
 * Examples:
 * 
 * 5
 * 
 * / \
 * 
 * 2 11
 * 
 * / \
 * 
 * 6 14
 * 
 * closest number to 4 is 5
 * 
 * closest number to 10 is 11
 * 
 * closest number to 6 is 6
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
 * @author yazhoucao
 * 
 */
public class Closest_Number_In_Binary_Search_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	public int closest(TreeNode root, int target) {
		if (root == null)
			return Integer.MAX_VALUE;
		int left = closest(root.left, target);
		int right = closest(root.right, target);
		int curr = Math.abs(root.key - target);
		if (right == Integer.MAX_VALUE || (left != Integer.MAX_VALUE && Math.abs(left - target) < Math.abs(right - target)))
			return Math.abs(left - target) < curr ? left : root.key;
		else
			return Math.abs(right - target) < curr ? right : root.key;
	}
}
