package interview.epi.chapter10_binary_trees;

import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

/**
 * Any two nodes in a binary tree have a common ancestor, namely the root. The
 * lowest common ancestor LCA of any two nodes in a binary tree is the node
 * furthest from the root that is an ancestor of both nodes.
 * 
 * Problem 10.4
 * Design an efficient algorithm for computing the LCA of nodes a and b in a
 * binary tree in which nodes do not have a parent pointer.
 * 
 * @author yazhoucao
 * 
 */
public class Q4_Compute_The_LCA_In_A_Binary_Tree {

	public static void main(String[] args) {

	}

	/**
	 * Naive solution, see if a,b are in the same side, if is then dive in,
	 * otherwise it is the LCA.
	 * 
	 * Time: O(2^1 + 2^2 + 2^3 + ... + 2^h), h is the height of the tree.
	 */
	public static <T> BinaryTree<T> LCA(BinaryTree<T> n, BinaryTree<T> a,
			BinaryTree<T> b) {
		if (n == null)
			return null;
		boolean aAtLeft = ancestorOf(n.getLeft(), a);
		boolean bAtLeft = ancestorOf(n.getLeft(), b);
		if (aAtLeft && bAtLeft)
			return LCA(n.getLeft(), a, b);
		else if (!aAtLeft && !bAtLeft)
			return LCA(n.getRight(), a, b);
		else
			return n;
	}

	public static <T> boolean ancestorOf(BinaryTree<T> parent,
			BinaryTree<T> node) {
		if (parent == null)
			return false;
		if (parent == node)
			return true;
		else
			return ancestorOf(parent.getLeft(), node)
					|| ancestorOf(parent.getRight(), node);
	}

	/**
	 * Recursively search the two nodes, if find one of them then return the
	 * node; otherwise return null.
	 * There are two cases while searching:
	 * 1.they are both found in the same branch, then dive in keep searching.
	 * 2.they are found in different branches, then it is the LCA.
	 * 
	 * Structurally similar to a recursive preorder traversal.
	 * Time: O(n), Space: O(lgn).
	 */
	public static <T> BinaryTree<T> LCA_Improved(BinaryTree<T> curr,
			BinaryTree<T> a, BinaryTree<T> b) {
		if (curr == null)
			return null;
		if (a == curr || b == curr)
			return curr;
		BinaryTree<T> left = LCA_Improved(curr.getLeft(), a, b);
		BinaryTree<T> right = LCA_Improved(curr.getRight(), a, b);
		if (left != null && right != null)
			return curr;
		else
			return left != null ? left : right;
	}
}
