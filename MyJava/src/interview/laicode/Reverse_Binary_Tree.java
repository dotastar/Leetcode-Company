package interview.laicode;

import interview.AutoTestUtils;
import interview.laicode.utils.TreeNode;

import org.junit.Test;

/**
 * Reverse Binary Tree
 * Fair
 * Data Structure
 * 
 * Given a binary tree where all the right nodes are leaf nodes, flip it upside
 * down and turn it into a tree with left leaf nodes as the root.
 * 
 * Examples
 * 
 ** 1
 * 
 * / \
 * 
 * 2 5
 * 
 * / \
 * 
 * 3 4
 * 
 * is reversed to
 * 
 * 3
 * 
 * / \
 * 
 * 2 4
 * 
 * / \
 * 
 * 1 5
 * 
 * How is the binary tree represented?
 * 
 * We use the pre order traversal sequence with a special symbol "#" denoting
 * the null node.
 * 
 * For Example:
 * 
 * The sequence [1, 2, #, 3, 4, #, #, #] represents the following binary tree:
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
public class Reverse_Binary_Tree {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Reverse_Binary_Tree.class);
	}

	/**
	 * Variant of Reverse linked list
	 */
	public TreeNode reverse(TreeNode root) {
		if (root == null || root.left == null)
			return root;
		TreeNode newroot = reverse(root.left);
		root.left.right = root.right;
		root.left.left = root;
		root.left = null;
		root.right = null;
		return newroot;
	}

	@Test
	public void test1() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);

		TreeNode res = reverse(root);
		System.out.println(res.toString());
	}

	@Test
	public void test2() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(5);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);

		TreeNode res = reverse(root);
		System.out.println(res.toString());
	}
}
