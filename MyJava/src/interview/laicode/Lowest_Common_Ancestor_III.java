package interview.laicode;


/**
 * 
 * Lowest Common Ancestor III
 * Hard
 * Recursion
 * 
 * Given two nodes in a binary tree, find their lowest common ancestor (the
 * given two nodes are not guaranteed to be in the binary tree).
 * 
 * Return null If any of the nodes is not in the tree.
 * 
 * Assumptions
 * 
 * There is no parent pointer for the nodes in the binary tree
 * 
 * The given two nodes are not guaranteed to be in the binary tree
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
 * The lowest common ancestor of 2 and 14 is 5
 * 
 * The lowest common ancestor of 2 and 9 is 9
 * 
 * The lowest common ancestor of 2 and 8 is null (8 is not in the tree)
 * 
 * @author yazhoucao
 * 
 */
public class Lowest_Common_Ancestor_III {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode one,
			TreeNode two) {
		if (searchLCA(root, one, one) != null
				&& searchLCA(root, two, two) != null)
			return searchLCA(root, one, two);
		else
			return null;
	}

	public TreeNode searchLCA(TreeNode root, TreeNode one, TreeNode two) {
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
