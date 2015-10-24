package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;
import interview.utils.TreeNode;

import org.junit.Test;

/**
 * Given a complete binary tree, count the number of nodes.
 * 
 * Definition of a complete binary tree from Wikipedia:
 * In a complete binary tree every level, except possibly the last, is
 * completely filled, and all nodes in the last level are as far left as
 * possible. It can have between 1 and 2h nodes inclusive at the last level h.
 * 
 * @author yazhoucao
 *
 */
public class Count_Complete_Tree_Nodes {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Count_Complete_Tree_Nodes.class);
	}

	/**
	 * Out goal is to binary search the last node on the last tree row.
	 * 
	 * The height of a tree can be found by just going left.
	 * 
	 * We can decide which subtree to search by checking whether the height of
	 * the right subtree is just one less than that of the whole tree, meaning
	 * left and right subtree have the same height.
	 * 
	 * If yes, then the last node is in the right subtree and the left subtree
	 * is a full tree of height h-1.
	 * So we take the 2^h-1 nodes of the left subtree plus the 1 root node.
	 * 
	 * If no, then the last node is in the left subtree and the right subtree is
	 * a full tree of height h-2.
	 * So we take the 2^(h-1)-1 nodes of the right subtree plus the 1 root node.
	 * 
	 * Repeat the steps until find the last node.
	 * 
	 * Since I halve the tree in every step, I have O(log(n)) steps.
	 * Finding a height costs O(log(n)). So overall O(log(n)^2).
	 */

	/**
	 * Idea of binary search
	 * Time: O(h^2) or O((logn)^2), h: height of tree, n: total # nodes
	 */
	public int countNodes_Iterative(TreeNode root) {
		int height = getDepth(root);
		int cnt = 0;
		TreeNode curr = root;
		while (curr != null) {
			int depth = getDepth(curr.right);
			if (depth == height - 1) { // last node is in right subtree
				// add # of left subtree plus sub-root node
				cnt += 1 << (height - 1);
				curr = curr.right;
			} else { // last node is in left subtree
				// add # of right subtree plus sub-root node
				cnt += 1 << (height - 2);
				curr = curr.left;
			}
			height--;
		}
		return cnt;
	}

	/**
	 * Recursive fashion
	 */
	public int countNodes(TreeNode root) {
		int h = getDepth(root);
		return h == 0 ? 0 : getDepth(root.right) == h - 1 ? (1 << h - 1) + countNodes(root.right) : (1 << h - 2) + countNodes(root.left);
	}

	private int getDepth(TreeNode node) {
		return node == null ? 0 : getDepth(node.left) + 1;
	}

	@Test
	public void test1() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		root.left.left.left = new TreeNode(8);
		int res = countNodes_Iterative(root);
		int ans = 8;
		assertEquals(ans, res);
	}

	@Test
	public void test2() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		root.left.left.left = new TreeNode(8);
		root.left.left.right = new TreeNode(9);
		root.left.right.left = new TreeNode(10);
		root.left.right.right = new TreeNode(11);

		int res = countNodes_Iterative(root);
		int ans = 11;
		assertEquals(ans, res);
	}

	@Test
	public void test3() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		root.left.left.left = new TreeNode(8);
		root.left.left.right = new TreeNode(9);
		root.left.right.left = new TreeNode(10);
		root.left.right.right = new TreeNode(11);
		root.right.left.left = new TreeNode(12);
		int res = countNodes_Iterative(root);
		int ans = 12;
		assertEquals(ans, res);
	}
}
