package interview.leetcode;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;
import interview.utils.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Test;

/**
 * Invert a binary tree.
 * 
 ****** 4
 **** / * \
 *** 2 *** 7
 ** / \ * / \
 * 1 * 3 6 * 9
 * 
 * to
 * 
 ****** 4
 **** / * \
 *** 7 *** 2
 ** / \ * / \
 * 9 * 6 3 * 1
 * 
 * @author yazhoucao
 *
 */
public class Invert_Binary_Tree {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Invert_Binary_Tree.class);
	}

	/**
	 * Recursive
	 */
	public TreeNode invertTree(TreeNode root) {
		if (root != null) {
			TreeNode left = root.left;
			TreeNode right = root.right;
			root.left = right;
			root.right = left;
			invertTree(root.left);
			invertTree(root.right);
		}
		return root;
	}

	/**
	 * Iterative (Preorder)
	 */
	public TreeNode invertTree_Iterative(TreeNode root) {
		if (root == null)
			return null;

		Deque<TreeNode> stk = new LinkedList<>();
		stk.push(root);
		while (!stk.isEmpty()) {
			TreeNode curr = stk.pop();
			TreeNode left = curr.left;
			TreeNode right = curr.right;
			curr.left = right;
			curr.right = left;
			if (right != null)
				stk.push(right);
			if (left != null)
				stk.push(left);
		}
		return root;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
