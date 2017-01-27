package interview.epi.chapter10_binary_trees;

import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;
import interview.epi.utils.Pair;

/**
 * Define a node in a binary tree to be k-balanced if the difference in the
 * number of nodes in its left and right subtrees is no more than k.
 * 
 * Problem 10.2
 * Design an algorithm that takes as input a binary tree and positive integer k,
 * and returns a node u in the binary tree such that u is not k-balanced, but
 * all of u's descendants are k-balanced. If no such node exists, return null.
 * 
 * @author yazhoucao
 * 
 */
public class Q2_Find_K_Unbalanced_Nodes {

	public static void main(String[] args) {

	}

	public static <T> BinaryTree<T> findNonKBalancedNode(BinaryTree<T> n, int k) {
		return helper(n, k).getFirst();
	}

	private static <T> Pair<BinaryTree<T>, Integer> helper(BinaryTree<T> n,
			int k) {
		if (n == null)
			return new Pair<BinaryTree<T>, Integer>(null, 0);
		Pair<BinaryTree<T>, Integer> left = helper(n.getLeft(), k);
		if (left.getFirst() != null)
			return left;

		Pair<BinaryTree<T>, Integer> right = helper(n.getRight(), k);
		if (right.getFirst() != null)
			return right;

		// # of nodes in n.
		int nodeNum = left.getSecond() + right.getSecond() + 1;
		if (Math.abs(left.getSecond() - right.getSecond()) >= k)
			return new Pair<BinaryTree<T>, Integer>(n, nodeNum);
		else
			return new Pair<BinaryTree<T>, Integer>(null, nodeNum);
	}

}
