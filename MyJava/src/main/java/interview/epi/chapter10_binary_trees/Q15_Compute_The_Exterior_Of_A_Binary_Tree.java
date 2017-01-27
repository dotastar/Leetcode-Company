package interview.epi.chapter10_binary_trees;

import interview.AutoTestUtils;
import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

import java.util.Stack;

import org.junit.Test;

public class Q15_Compute_The_Exterior_Of_A_Binary_Tree {
	static Class<?> c = Q15_Compute_The_Exterior_Of_A_Binary_Tree.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	public static <T> void exteriorBinaryTree(BinaryTree<T> root) {
		System.out.print(root.getData() + "\t");
		printLeft(root.getLeft());
		printLeaves(root);
		printRight(root.getRight());
		System.out.println();
	}

	private static <T> void printLeft(BinaryTree<T> node) {
		if (node == null)
			return;
		while (node.getLeft() != null) {
			System.out.print(node.getData() + "\t");
			node = node.getLeft();
		}
	}

	private static <T> void printLeaves(BinaryTree<T> node) {
		Stack<BinaryTree<T>> stk = new Stack<>();
		stk.push(node);
		while (!stk.isEmpty()) {
			BinaryTree<T> n = stk.pop();
			if (n != null) {
				if (n.getLeft() == null && n.getRight() == null)
					System.out.print(n.getData()+"\t");
				else {
					stk.push(n.getRight());
					stk.push(n.getLeft());
				}
			}
		}
	}

	private static <T> void printRight(BinaryTree<T> node) {
		if (node == null)
			return;
		while (node.getRight() != null) {
			System.out.print(node.getData() + "\t");
			node = node.getRight();
		}
	}

	@Test
	public void test1() {
		// 3
		// 2 5
		// 1 0 4 6
		// -1 -2
		BinaryTree<Integer> root = new BinaryTree<Integer>(3, null, null);
		root.setLeft(new BinaryTree<Integer>(2, null, null));
		root.getLeft().setRight(new BinaryTree<Integer>(0, null, null));
		root.getLeft().getRight().setLeft(new BinaryTree<Integer>(-1, null, null));
		root.getLeft().getRight().setRight(new BinaryTree<Integer>(-2, null, null));
		root.getLeft().setLeft(new BinaryTree<Integer>(1, null, null));
		root.setRight(new BinaryTree<Integer>(5, null, null));
		root.getRight().setLeft(new BinaryTree<Integer>(4, null, null));
		root.getRight().setRight(new BinaryTree<Integer>(6, null, null));
		// should output 3 2 1 -1 -2 4 6 5
		exteriorBinaryTree(root);
	}
}
