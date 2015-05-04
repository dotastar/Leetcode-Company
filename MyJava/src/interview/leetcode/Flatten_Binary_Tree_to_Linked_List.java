package interview.leetcode;

import java.util.Stack;

/**
 * Given a binary tree, flatten it to a linked list in-place.
 * 
 * For example, Given
 * 
 * 
 * 1
 * / \
 * 2 5
 * / \ \
 * 3 4 6
 * 
 * The flattened tree should look like:
 * 1
 * \
 * 2
 * \
 * 3
 * \
 * 4
 * \
 * 5
 * \
 * 6
 * Hints:
 * 
 * If you notice carefully in the flattened tree, each node's
 * right child points to the next node of a pre-order traversal.
 * 
 * @author yazhoucao
 * 
 */
public class Flatten_Binary_Tree_to_Linked_List {

	public static void main(String[] args) {
		Flatten_Binary_Tree_to_Linked_List o = new Flatten_Binary_Tree_to_Linked_List();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		o.flatten_Iter1(root);
	}

	/**
	 * Very clear and concise, Time: O(n)
	 * Iteratively preorder traversal, connect the prev to current
	 */
	public void flatten_Iter1(TreeNode root) {
		Stack<TreeNode> stk = new Stack<>();
		stk.push(root);
		TreeNode prev = null;
		while (!stk.isEmpty()) {
			TreeNode curr = stk.pop();
			if (curr != null) {
				stk.push(curr.right);
				stk.push(curr.left);
				if (prev != null) {
					prev.left = null;
					prev.right = curr;
				}
				prev = curr;
			}
		}
	}

	/**
	 * Divide & Conquer, Time: O(n)
	 * Solve and Merge
	 * Flatten left subtree, right subtree to LinkedList respectively
	 * Connect them in this way: root -> left subtree -> right subtree
	 */
	public void flatten2(TreeNode root) {
		flat(root);
	}

	private TreeNode flat(TreeNode root) {
		if (root == null)
			return null;
		TreeNode leftTail = flat(root.left);
		TreeNode rightTail = flat(root.right);
		if (leftTail != null)
			leftTail.right = root.right;
		if (root.left != null) {
			root.right = root.left;
			root.left = null;
		}

		if (rightTail != null)
			return rightTail;
		else if (leftTail != null)
			return leftTail;
		else
			return root;
	}

	/**
	 * Recursion solution2, preorder traversal
	 */
	TreeNode prev;

	public void flatten_Recur(TreeNode root) {
		preorder(root);
	}

	private void preorder(TreeNode node) {
		if (node == null)
			return;
		TreeNode left = node.left, right = node.right;
		if (prev != null) {
			prev.left = null;
			prev.right = node;
		}
		prev = node;
		preorder(left);
		preorder(right);
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
