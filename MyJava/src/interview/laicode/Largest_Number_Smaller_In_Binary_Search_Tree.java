package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Largest Number Smaller In Binary Search Tree
 * Fair
 * Data Structure
 * 
 * In a binary search tree, find the node containing the largest number smaller
 * than the given target number.
 * 
 * If there is no such number, return INT_MIN.
 * 
 * Assumptions:
 * 
 * The given root is not null.
 * There are no duplicate keys in the binary search tree.
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
 * largest number smaller than 1 is null
 * 
 * largest number smaller than 10 is 6
 * 
 * largest number smaller than 6 is 5
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
public class Largest_Number_Smaller_In_Binary_Search_Tree {

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Largest_Number_Smaller_In_Binary_Search_Tree.class);
	}

	/**
	 * Ask children nodes for largestSmaller, if find a result, return it,
	 * otherwise return Int.MIN
	 * Cases:
	 * 0.Base case, node is null, return Int.MIN
	 * 1.If current value < target, return max(current, rightchild's result)
	 * 2.Else, current >= target, return leftChild's
	 * 
	 * Use the property of BST
	 */
	public int largestSmaller(TreeNode root, int target) {
		if (root == null)
			return Integer.MIN_VALUE;
		if (root.key < target)
			return Math.max(root.key, largestSmaller(root.right, target));
		else
			return largestSmaller(root.left, target);
	}

	@Test
	public void test1() {
		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(5);
		root.right = new TreeNode(15);
		root.left.left = new TreeNode(2);
		root.left.right = new TreeNode(9);
		root.right.left = new TreeNode(11);
		root.right.right = new TreeNode(23);
		root.left.left.left = new TreeNode(1);
		root.left.right.left = new TreeNode(6);
		root.right.left.right = new TreeNode(12);
		root.right.right.left = new TreeNode(21);
		root.right.right.right = new TreeNode(25);

		int res = largestSmaller(root, 30);
		int ans = 25;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(5);
		root.right = new TreeNode(15);
		root.left.left = new TreeNode(2);
		root.left.right = new TreeNode(9);
		root.right.left = new TreeNode(11);
		root.right.right = new TreeNode(23);
		root.left.left.left = new TreeNode(1);
		root.left.right.left = new TreeNode(6);
		root.right.left.right = new TreeNode(12);
		root.right.right.left = new TreeNode(21);
		root.right.right.right = new TreeNode(25);

		int res = largestSmaller(root, 20);
		int ans = 15;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test3() {
		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(5);
		root.right = new TreeNode(15);
		root.left.left = new TreeNode(2);
		root.left.right = new TreeNode(9);
		root.right.left = new TreeNode(11);
		root.right.right = new TreeNode(23);
		root.left.left.left = new TreeNode(1);
		root.left.right.left = new TreeNode(6);
		root.right.left.right = new TreeNode(12);
		root.right.right.left = new TreeNode(21);
		root.right.right.right = new TreeNode(25);

		int res = largestSmaller(root, 11);
		int ans = 10;
		assertTrue("Wrong: " + res, res == ans);
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
