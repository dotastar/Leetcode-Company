package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Binary Tree Path Sum To Target
 * Fair
 * Data Structure
 * 
 * Given a binary tree in which each node contains an integer number. Determine
 * if there exists a path from any node to any node (the two nodes can be the
 * same node and they can only be on the path from root to one of the leaf
 * nodes), the sum of the numbers on the path is the given target number.
 * 
 * Examples
 * 
 *** 5
 * 
 ** / \
 * 
 * 2 __ 11
 * 
 ***** / \
 * 
 **** 6 _ 14
 * 
 * If target = 17, There exists a path 11 + 6, the sum of the path is target,
 * 
 * If target = 10, There does not exist any paths sum of which is target.
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
 *** 1
 * 
 ** / \
 * 
 * 2 _ 3
 * 
 **** /
 * 
 ** 4
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Binary_Tree_Path_Sum_To_Target {

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Binary_Tree_Path_Sum_To_Target.class);
	}

	public boolean exist(TreeNode root, int target) {
		return exist(root, target, target);
	}

	public boolean exist(TreeNode root, int target, int rest) {
		if (root == null)
			return false;
		return root.key == rest || exist(root.left, target, rest - root.key)
				|| exist(root.right, target, rest - root.key)
				|| exist(root.left, target, target)
				|| exist(root.right, target, target);
	}

	@Test
	public void test1() {
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(-8);
		root.right = new TreeNode(9);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(10);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(-5);
		root.left.left.left = new TreeNode(1);
		root.left.left.right = new TreeNode(-2);

		int target = -7;
		boolean res = exist(root, target, target);
		boolean ans = false;
		assertTrue(res == ans);
	}

	@Test
	public void test2() {
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(-8);
		root.right = new TreeNode(9);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(10);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(-5);
		root.left.left.left = new TreeNode(10);
		root.left.left.right = new TreeNode(20);

		int target = -3;
		boolean res = exist(root, target, target);
		boolean ans = true;
		assertTrue(res == ans);
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
