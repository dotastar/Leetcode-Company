package interview.epi.chapter15_bst;

import static org.junit.Assert.*;

import org.junit.Test;

import interview.AutoTestUtils;
import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

/**
 * Design an algorithm that takes a BST T of size n and height h, nodes s and b,
 * return the LCA of s and b. Assume s.key < b.key.
 * The algorithm should run in O(h) time and O(1) space.
 * 
 * @author yazhoucao
 * 
 */
public class Q5_Compute_LCA_In_A_BST {

	static Class<?> c = Q5_Compute_LCA_In_A_BST.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Compare root's value with s.key and b.key.
	 * The LCA must be the first node that s.key < node.key < b.key
	 */
	public BinaryTree<Integer> findLCA(BinaryTree<Integer> root,
			BinaryTree<Integer> s, BinaryTree<Integer> b) {
		while (root != null) {
			if (root.getData() > b.getData())
				root = root.getLeft();
			else if (root.getData() < s.getData())
				root = root.getRight();
			else
				break;	// s.key < root.key < b.key
		}
		return root;
	}

	@Test
	public void test1() {
		// 3
		// 2 5
		// 1 4 6
		BinaryTree<Integer> root = new BinaryTree<>(3);
		root.setLeft(new BinaryTree<>(2));
		root.getLeft().setLeft(new BinaryTree<>(1));
		root.setRight(new BinaryTree<>(5));
		root.getRight().setLeft(new BinaryTree<>(4));
		root.getRight().setRight(new BinaryTree<>(6));
		assertTrue(3 == findLCA(root, root.getLeft().getLeft(), root.getRight().getLeft()).getData());
		assertTrue(5 == findLCA(root, root.getRight().getLeft(), root.getRight().getRight()).getData());
		assertTrue(2 == findLCA(root, root.getLeft().getLeft(), root.getLeft()).getData());
	}
}
