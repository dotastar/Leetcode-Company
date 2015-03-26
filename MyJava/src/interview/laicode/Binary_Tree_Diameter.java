package interview.laicode;

/**
 * 
 * Binary Tree Diameter
 * Fair
 * Recursion
 * 
 * Given a binary tree in which each node contains an integer number. The
 * diameter is defined as the longest distance from one leaf node to another
 * leaf node. The distance is the number of nodes on the path.
 * 
 * If there does not exist any such paths, return 0.
 * 
 * Examples
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
 * The diameter of this tree is 4 (2 → 5 → 11 → 14)
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
public class Binary_Tree_Diameter {

	public static void main(String[] args) {

	}

	private int globalMax;

	public int diameter(TreeNode root) {
		globalMax = 0;
		diameterHelper(root);
		return globalMax;
	}

	private int diameterHelper(TreeNode root) {
		if (root == null)
			return 0;

		int leftLen = diameterHelper(root.left);
		int rightLen = diameterHelper(root.right);
		int currDiameter = leftLen + rightLen + 1;
		if (root.left != null && root.right != null && currDiameter > globalMax)
			globalMax = currDiameter;

		if (root.left == null)
			return rightLen + 1;
		else if (root.right == null)
			return leftLen + 1;
		else
			return Math.max(leftLen, rightLen) + 1;
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
