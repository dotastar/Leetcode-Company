package interview.leetcode;

import java.util.Stack;

/**
 * Two elements of a binary search tree (BST) are swapped by mistake.
 * 
 * Recover the tree without changing its structure.
 * 
 * Note: A solution using O(n) space is pretty straight forward. Could you
 * devise a constant space solution?
 * 
 * @author yazhoucao
 * 
 */
public class Recover_Binary_Search_Tree {
	static void main(String[] args) {
		// 4 and 7 switched
		TreeNode root = new TreeNode(7);
		root.left = new TreeNode(2);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);
		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(4);

		printTree(root);
		recoverTree(root);
		System.out.println();
		printTree(root);
	}

	/**
	 * Same solution, just change to iterative inorder BST traversal
	 * 
	 */
	public static void recoverTree2(TreeNode root) {
		Stack<TreeNode> stk = new Stack<>();
		TreeNode p = root, prev = null, first = null, second = null;
		while (!stk.isEmpty() || p != null) {
			if (p != null) {
				stk.push(p);
				p = p.left;
			} else {
				p = stk.pop();
				// visit p
				if (prev != null && prev.val > p.val) {
					if (first == null)
						first = prev;
					second = p;
				}
				prev = p;
				p = p.right;
			}
		}
		if (first != null) {
			int temp = first.val;
			first.val = second.val;
			second.val = temp;
		}
	}

	/**
	 * Make use of the property of inorder traversing BST will get an ascending
	 * order sequence of keys. (Recursive)
	 * 
	 * 1,2,3,4,5,6,7 //normal case
	 * 
	 * There are two different cases that it could be swapped:
	 * 
	 * 1,3,2,4,5,6,7 //swapped the neighbor values
	 * 
	 * 1,2,6,4,5,3,7 //swapped the non-neighbor values
	 * 
	 * We can use first, second, pre to handle these two cases.
	 */
	static TreeNode pre, first, second;

	public static void recoverTree(TreeNode root) {
		pre = first = second = null;
		inorder(root);
		swap(first, second);
	}

	/**
	 * Time: O(n), Space: O(lg(n))
	 */
	public static void inorder(TreeNode node) {
		if (node == null)
			return;

		inorder(node.left);

		if (pre != null && node.val < pre.val) {
			if (first == null)
				first = pre;
			second = node;
		}
		pre = node;

		inorder(node.right);
	}

	private static void swap(TreeNode n1, TreeNode n2) {
		int tmp = n1.val;
		n1.val = n2.val;
		n2.val = tmp;
	}

	public static void printTree(TreeNode node) {
		if (node == null)
			return;

		printTree(node.left);
		System.out.print(node.val + " ");
		printTree(node.right);
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}

		public String toString() {
			return Integer.toString(val);
		}
	}
}
