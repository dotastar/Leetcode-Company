package interview.laicode;

import interview.laicode.utils.TreeNode;

/**
 * Reconstruct Binary Search Tree With Postorder Traversal
 * Fair
 * Data Structure
 * 
 * Given the postorder traversal sequence of a binary search tree, reconstruct
 * the original tree.
 * 
 * Assumptions
 * 
 * The given sequence is not null
 * There are no duplicate keys in the binary search tree
 * 
 * Examples
 * 
 * postorder traversal = {1, 4, 3, 11, 8, 5}
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
public class Reconstruct_Binary_Search_Tree_With_Postorder_Traversal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Time: O(n)
	 */
	private int index;

	public TreeNode reconstruct2(int[] post) {
		// postorder is not null, no duplicates
		index = post.length - 1;
		return helper(post, Integer.MIN_VALUE);
	}

	/**
	 * Do a reversely PreOrder traversal,
	 * Use a min to decide current node exists or not.
	 */
	private TreeNode helper(int[] postorder, int min) {
		if (index < 0 || postorder[index] <= min) {
			return null;
		}
		TreeNode root = new TreeNode(postorder[index--]);
		root.right = helper(postorder, root.key);
		root.left = helper(postorder, min);
		return root;
	}

	/**
	 * Time: O(n^2) worst case,
	 * O(nlogn) average case (if the tree has log(n) height)
	 * 
	 * 1 4 3 11 8 5
	 * |__ ^ |__^ ^
	 * 
	 */
	public TreeNode reconstruct(int[] post) {
		return reconstruct(post, 0, post.length - 1);
	}

	/**
	 * Use the property of BST, split post[] to left and right part by its root
	 * value which is post[r], recursively construct the tree.
	 */
	private TreeNode reconstruct(int[] post, int l, int r) {
		if (l > r)
			return null;

		TreeNode root = new TreeNode(post[r]);
		int leftEnd = r - 1;
		while (leftEnd >= l && post[leftEnd] > root.key) {
			leftEnd--;
		}
		root.left = reconstruct(post, l, leftEnd);
		root.right = reconstruct(post, leftEnd + 1, r - 1);
		return root;
	}
}
