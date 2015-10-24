package interview.epi.chapter15_bst;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;
import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

import org.junit.Test;

/**
 * Write a function that takes a BST T and a key k, and returns the first entry
 * that larger than k that would appear in an inorder traversal. If k is absent
 * or no key larger than k, then return null.
 * 
 * @author yazhoucao
 * 
 */
public class Q3_Find_The_First_Key_Larger_Than_k_In_A_BST {

	static Class<?> c = Q3_Find_The_First_Key_Larger_Than_k_In_A_BST.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	public BinaryTree<Integer> findFirstLargerK(BinaryTree<Integer> n, Integer k) {
		while (n != null) {
			if (n.getData() == k) {
				if (n.getRight() == null)
					return null;
				else if (n.getRight().getData() > k)
					return n.getRight();
				else
					n = n.getRight();
			} else if (n.getData() < k)
				n = n.getRight();
			else { // n.getData()>k
				if (n.getLeft() != null && n.getLeft().getData() == k)
					return n;
				else
					n = n.getLeft();
			}

		}
		return n;
	}

	@Test
	public void test1() {
		// 3
		// 2 5
		// 1 4 7
		BinaryTree<Integer> root = new BinaryTree<>(3);
		root.setLeft(new BinaryTree<>(2));
		root.getLeft().setLeft(new BinaryTree<>(1));
		root.setRight(new BinaryTree<>(5));
		root.getRight().setLeft(new BinaryTree<>(4));
		root.getRight().setRight(new BinaryTree<>(7));
		assertTrue(findFirstLargerK(root, 1) == root.getLeft());
		assertTrue(findFirstLargerK(root, 5) == root.getRight().getRight());
		assertTrue(findFirstLargerK(root, 6) == null);
		assertTrue(findFirstLargerK(root, 7) == null);
	}
}
