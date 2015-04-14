package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

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
		AutoTestUtils.runTestClassAndPrint(Lowest_Common_Ancestor_III.class);
	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode one,
			TreeNode two) {
		if (exist(root, one) && exist(root, two))
			return lca(root, one, two);
		else
			return null;
	}

	private TreeNode lca(TreeNode root, TreeNode one, TreeNode two) {
		if (root == null || root == one || root == two)
			return root;
		TreeNode left = lca(root.left, one, two);
		TreeNode right = lca(root.right, one, two);
		if (left != null && right != null)
			return root;
		else if (left != null)
			return left;
		else
			return right;
	}

	private boolean exist(TreeNode root, TreeNode target) {
		if (root == null)
			return false;
		if (root == target)
			return true;
		return exist(root.left, target) || exist(root.right, target);
	}

	@Test
	public void test1() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right = new TreeNode(5);
		root.left.left.left = new TreeNode(6);

		TreeNode one = root.left.left.left;
		TreeNode two = new TreeNode(10);
		assertTrue(lowestCommonAncestor(root, one, two) == null);
	}

	@Test
	public void test2() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right = new TreeNode(5);
		root.left.left.left = new TreeNode(6);

		TreeNode one = root.left.left.left;
		TreeNode two = root.right;
		assertTrue(lowestCommonAncestor(root, one, two) == root);
	}

	@Test
	public void test3() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right = new TreeNode(5);
		root.left.left.left = new TreeNode(6);

		TreeNode one = root.left.left.left;
		TreeNode two = root.left;
		assertTrue(lowestCommonAncestor(root, one, two) == root.left);
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
