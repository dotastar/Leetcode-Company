package interview.epi.chapter13_hashtable;

import static org.junit.Assert.*;
import interview.AutoTestUtils;
import interview.epi.chapter10_binary_trees.BinaryTreeWithParentPrototype.BinaryTree;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Lowest common ancestor - TreeNode has a parent pointer
 * Alternative solution for problem 10.5
 * Design an algorithm for computing the findLCA of a and b that has time
 * O(max(da-dl, db-dl)), where dl is the depth of findLCA of a and b.
 * 
 * @author yazhoucao
 * 
 */
public class Q5_Compute_LCA {

	static Class<?> c = Q5_Compute_LCA.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Record visited parent node
	 */
	public <T> BinaryTree<T> findLCA(BinaryTree<T> a, BinaryTree<T> b) {
		Set<BinaryTree<T>> visited = new HashSet<>();
		while (a != null || b != null) {
			if (a != null) {
				if (!visited.add(a.getParent()))
					return a.getParent();
				a = a.getParent();
			}
			if (b != null) {
				if (!visited.add(b.getParent()))
					return b.getParent();
				b = b.getParent();
			}
		}
		return null; // a and b are not in the same tree.
	}

	@Test
	public void test1() {
		// 3
		// 2 5
		// 1 4 6
		BinaryTree<Integer> root = new BinaryTree<Integer>(3, null, null);
		root.setLeft(new BinaryTree<Integer>(2, null, null));
		root.getLeft().setParent(root);
		root.getLeft().setLeft(new BinaryTree<Integer>(1, null, null));
		root.getLeft().getLeft().setParent(root.getLeft());
		root.setRight(new BinaryTree<Integer>(5, null, null));
		root.getRight().setParent(root);
		root.getRight().setLeft(new BinaryTree<Integer>(4, null, null));
		root.getRight().getLeft().setParent(root.getRight());
		root.getRight().setRight(new BinaryTree<Integer>(6, null, null));
		root.getRight().getRight().setParent(root.getRight());
		// should output 3
		assertTrue (findLCA(root.getLeft(), root.getRight()).getData().equals(3));
		System.out.println(findLCA(root.getLeft(), root.getRight()).getData());
		// should output 5
		assertTrue (findLCA(root.getRight().getLeft(), root.getRight().getRight()).getData().equals(5));
		System.out.println(findLCA(root.getRight().getLeft(), root.getRight().getRight()).getData());
	}
}
