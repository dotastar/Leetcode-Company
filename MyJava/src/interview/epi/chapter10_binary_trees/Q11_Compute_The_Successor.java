package interview.epi.chapter10_binary_trees;

import interview.AutoTestUtils;
import interview.epi.chapter10_binary_trees.BinaryTreeWithParentPrototype.BinaryTree;

/**
 * The successor of a node n in a binary tree is the node s that appears
 * immediately after n in an inorder traversal.
 * 
 * Problem 10.12
 * Design an algorithm that takes a node n in a binary tree, and returns its
 * successor. Assume that each node has a parent field; the parent field of root
 * is null.
 * 
 * @author yazhoucao
 * 
 */
public class Q11_Compute_The_Successor {
	static Class<?> c = Q11_Compute_The_Successor.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Next node is in the right sub-tree,
	 * O(h), h is the height from node to the leave
	 */
	public static BinaryTree<Integer> findSuccessor(BinaryTree<Integer> node) {
		if (node == null)
			return null;
		if (node.getRight() != null) {
			BinaryTree<Integer> p = node.getRight();
			while (p.getLeft() != null)
				p = p.getLeft();
			return p;
		} else
			return node.getParent();
	}

	
	
}
