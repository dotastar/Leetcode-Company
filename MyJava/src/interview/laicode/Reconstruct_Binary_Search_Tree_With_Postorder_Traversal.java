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

	private TreeNode reconstruct(int[] post, int l, int r) {
		if (l > r)
			return null;

		TreeNode root = new TreeNode(post[r]);
		int rightStart = r;
		for (int i = r - 1; i >= l && post[i] > root.key; i--) {
			rightStart = i;
		}

		root.left = reconstruct(post, l, rightStart - 1);
		root.right = reconstruct(post, rightStart, r - 1);
		return root;
	}
}
