package interview.laicode;

import interview.laicode.utils.TreeNode;

/**
 * 
 Delete In Binary Search Tree
 * Fair
 * Data Structure
 * 
 * Delete the target key K in the given binary search tree if the binary search
 * tree contains K. Return the root of the binary search tree.
 * 
 * Find your own way to delete the node from the binary search tree, after
 * deletion the binary search tree's property should be maintained.
 * 
 * Assumptions
 * 
 * There are no duplicate keys in the binary search tree
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Delete_In_Binary_Search_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Three cases when deleting a node in BST:
	 * 1.The deleted node has no child, leaf node
	 * 2.The deleted node has only one child
	 * 3.The deleted node has both children
	 * 
	 * Case 1 and 2 can be solved together by set the deleted node to null
	 * (if no child) or the other non-null child node(has only one child).
	 * 
	 * Case 3 is harder, find its successor(it's the smallest node in the right
	 * subtree), set the deleted node's value to its successor's value, and then
	 * recursively delete the successor(the successor must be a leaf node).
	 * (We can also use predecessor, then recursively left subtree)
	 */
	public TreeNode delete(TreeNode root, int key) {
		if (root == null) // sanity check
			return null;

		if (key < root.key) {
			root.left = delete(root.left, key);
		} else if (key > root.key) {
			root.right = delete(root.right, key);
		} else { // root.key == key
			if (root.left == null) {
				return root.right;
			} else if (root.right == null)
				return root.left;
			// has both left and right child
			TreeNode successor = root.right; // smallest in the right subtree
			while (successor.left != null)
				successor = successor.left;
			root.key = successor.key;
			root.right = delete(root.right, successor.key);
		}

		return root;
	}
}
