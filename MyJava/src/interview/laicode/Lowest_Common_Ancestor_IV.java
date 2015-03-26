package interview.laicode;

import java.util.List;

/**
 * 
 * Lowest Common Ancestor IV
 * Hard
 * Recursion
 * 
 * Given K nodes in a binary tree, find their lowest common ancestor.
 * 
 * Assumptions
 * 
 * K >= 2
 * 
 * There is no parent pointer for the nodes in the binary tree
 * 
 * The given two nodes are guaranteed to be in the binary tree
 * 
 * Examples
 * 
 * 5
 * 
 * / \
 * 
 * 9 12
 * 
 * / \ \
 * 
 * 2 3 14
 * 
 * The lowest common ancestor of 2, 3, 14 is 5
 * 
 * The lowest common ancestor of 2, 3, 9 is 9
 * 
 * @author yazhoucao
 * 
 */
public class Lowest_Common_Ancestor_IV {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Second solution, a little faster than the first solution, because it will
	 * stop traversing down when it meets the highest node in the tree.
	 * 
	 * This solution has an assumption:
	 * All the nodes are assumed in the same tree.
	 */
	public TreeNode lowestCommonAncestor(TreeNode root, List<TreeNode> nodes) {
		if (root == null)
			return root;
		for (TreeNode node : nodes) {
			if (node == root)
				return root;
		}
		
		TreeNode leftRes = lowestCommonAncestor(root.left, nodes);
		TreeNode rightRes = lowestCommonAncestor(root.right, nodes);

		if (leftRes != null && rightRes != null)
			return root;
		else if (leftRes != null)
			return leftRes;
		else
			return rightRes;
	}

	/**
	 * First solution
	 * Use searchLCA as a subroutine
	 */
	public TreeNode lowestCommonAncestor2(TreeNode root, List<TreeNode> nodes) {
		TreeNode one = nodes.get(0);
		for (int i = 1; i < nodes.size(); i++) {
			one = searchLCA(root, one, nodes.get(i));
		}
		return one;
	}

	private TreeNode searchLCA(TreeNode root, TreeNode one, TreeNode two) {
		if (root == null || root == one || root == two)
			return root;

		TreeNode leftRes = searchLCA(root.left, one, two);
		TreeNode rightRes = searchLCA(root.right, one, two);

		if (leftRes != null && rightRes != null)
			return root;
		else if (leftRes != null)
			return leftRes;
		else
			return rightRes;
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
