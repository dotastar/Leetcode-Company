package interview.company.others;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * “Find the immediate right neighbor of the given node, with parent links
 * given, but without root node”
 * 
 * Excerpt From: Yichao, Xiami, XiaoXiao & Fei Dong. “程序员面试白皮书.” iBooks.
 * https://itun.es/us/zcOJ6.l
 * 
 * @author yazhoucao
 * 
 */
public class Find_The_Next_Right_Neighbor_Node {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Find_The_Next_Right_Neighbor_Node.class);
	}

	/**
	 * Search next right neighbor at each level at a time,
	 * Notice: only check if is was from left subtree,
	 * if it's from right subtree it can only go up.
	 */
	public TreeNode findNextRight(TreeNode node) {
		int level = 1;
		TreeNode parent = node.parent;
		while (parent != null) {
			if (parent.left == node) { // only check if is was from left subtree
				TreeNode res = findNextRight(parent.right, level - 1);
				if (res != null)
					return res;
			}
			level++;
			node = parent;
			parent = parent.parent;
		}
		return null;
	}

	/**
	 * DFS Search the node at level 0, always check left first
	 */
	private TreeNode findNextRight(TreeNode node, int level) {
		if (node == null || level == 0)
			return node;
		TreeNode leftRes = findNextRight(node.left, level - 1);
		if (leftRes != null)
			return leftRes;
		TreeNode rightRes = findNextRight(node.right, level - 1);
		if (rightRes != null)
			return rightRes;
		return null;
	}

	@Test
	public void test1() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2, root);
		root.right = new TreeNode(3, root);

		TreeNode ans = root.right;
		TreeNode res = findNextRight(root.left);
		assertTrue("Wrong answer: " + res, ans == res);
	}

	@Test
	public void test2() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2, root);
		root.right = new TreeNode(3, root);

		TreeNode ans = null;
		TreeNode res = findNextRight(root.right);
		assertTrue("Wrong answer: " + res, ans == res);
	}

	/**
	 ***** 1
	 **** / \
	 *** 2 _ 3
	 ** / ___ \
	 * 4 _____ 5
	 */
	@Test
	public void test3() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2, root);
		root.right = new TreeNode(3, root);
		root.left.left = new TreeNode(4, root.left);
		root.right.right = new TreeNode(5, root.right);
		TreeNode ans = root.right.right;
		TreeNode res = findNextRight(root.left.left);
		assertTrue("Wrong answer: " + res, ans == res);
	}

	/**
	 ***** 1
	 **** / \
	 *** 2 _ 3
	 ** / __ /
	 * 4 __ 5
	 */
	@Test
	public void test4() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2, root);
		root.right = new TreeNode(3, root);
		root.left.left = new TreeNode(4, root.left);
		root.right.left = new TreeNode(5, root.right);
		TreeNode ans = root.right.left;
		TreeNode res = findNextRight(root.left.left);
		assertTrue("Wrong answer: " + res, ans == res);
	}

	public static class TreeNode {
		public int val;
		public TreeNode parent;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int key) {
			this.val = key;
		}

		public TreeNode(int key, TreeNode father) {
			this.val = key;
			this.parent = father;
		}

		public String toString() {
			return String.valueOf(val);
		}
	}
}
