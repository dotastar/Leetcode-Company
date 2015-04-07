package interview.laicode;

import java.util.Stack;

import org.junit.Test;

import interview.laicode.utils.TreeNode;

/**
 * Reconstruct Binary Search Tree With Level Order Traversal
 * Fair
 * Data Structure
 * 
 * Given the levelorder traversal sequence of a binary search tree, reconstruct
 * the original tree.
 * 
 * Assumptions
 * 
 * The given sequence is not null
 * There are no duplicate keys in the binary search tree
 * 
 * Examples
 * 
 * levelorder traversal = {5, 3, 8, 1, 4, 11}
 * 
 * the corresponding binary search tree is
 * 
 ** 5
 * 
 * / \
 * 
 * 3 8
 * 
 * / \ \
 * 
 * 1 4 11
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
 *** 1
 * 
 ** / \
 * 
 * 2 _ 3
 * 
 *** /
 * 
 * 4
 * 
 * @author yazhoucao
 * 
 */
public class Reconstruct_Binary_Search_Tree_With_Level_Order_Traversal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Time: O(n), n = number of nodes (level.length)
	 */
	public TreeNode reconstruct2(int[] level) {
		return reconstruct(level, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Because the nature of level ordered array, the first entry that sits in
	 * the range will be the next node to construct.
	 * 5 3 8 1 4 11
	 * ^
	 */
	private TreeNode reconstruct(int[] level, int idx, int min, int max) {
		// find the first index that within the range(min, max)
		while (idx < level.length && (level[idx] < min || level[idx] > max))
			idx++;
		if (idx >= level.length)
			return null;
		// use it for current root
		TreeNode root = new TreeNode(level[idx]);
		// update range with root.key
		root.left = reconstruct(level, idx + 1, min, root.key);
		root.right = reconstruct(level, idx + 1, root.key, max);
		return root;
	}

	/************************** Naive Solution **************************/

	/**
	 * Naive solution,
	 * insert node one by one, time: O(n^2) worst case
	 */
	public TreeNode reconstruct(int[] level) {
		if (level.length == 0)
			return null;
		TreeNode root = new TreeNode(level[0]);
		for (int i = 1; i < level.length; i++) {
			insert(root, level[i]);
		}
		return root;
	}

	/**
	 * 5 3 8 1 4 11
	 * ^
	 * 5
	 * 3 8
	 * 
	 */
	private TreeNode insert(TreeNode root, int value) {
		if (root == null)
			return new TreeNode(value);

		if (value < root.key)
			root.left = insert(root.left, value);
		else if (value > root.key)
			root.right = insert(root.right, value);

		return root;
	}

	@Test
	public void test1() {
		int[] pre = { 5, 3, 8, 1, 4, 11 };
		TreeNode root = reconstruct2(pre);
		printBST(root);
		System.out.println();
	}

	public void printBST(TreeNode root) {
		Stack<TreeNode> stk = new Stack<>();
		TreeNode p = root;
		while (!stk.isEmpty() || p != null) {
			if (p != null) {
				stk.push(p);
				p = p.left;
			} else {
				p = stk.pop();
				System.out.print(p + "\t");
				p = p.right;
			}
		}
	}
}
