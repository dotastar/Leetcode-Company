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

	/**
	 * 1.ask left/right child for its greatest distance from leaf to itself
	 * 2.compare left and right path distance, update global diameter if it has
	 * both left and right children
	 * 3.return the greater path sum to its parent
	 */
	int globalDiameter = 0;

	public int diameter(TreeNode root) {
		updateDiameter(root);
		return globalDiameter;
	}

	public int updateDiameter(TreeNode root) {
		if (root == null)
			return 0;
		int leftPath = updateDiameter(root.left);
		int rightPath = updateDiameter(root.right);
		if (root.left != null && root.right != null)
			globalDiameter = Math.max(leftPath + rightPath + 1, globalDiameter);
		return Math.max(leftPath, rightPath) + 1;
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
