package interview.epi.chapter10_binary_trees;

import static org.junit.Assert.*;
import interview.AutoTestUtils;
import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.Test;

public class Q14_Form_A_Linked_List_From_The_Leaves_Of_A_Binary_Tree {
	static Class<?> c = Q14_Form_A_Linked_List_From_The_Leaves_Of_A_Binary_Tree.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Traverse the tree, if meet a leave, add it to the list.
	 */
	public static <T> ArrayList<BinaryTree<T>> connectLeaves(BinaryTree<T> n) {
		ArrayList<BinaryTree<T>> res = new ArrayList<>();
		Stack<BinaryTree<T>> stk = new Stack<>();
		stk.push(n);
		while (!stk.isEmpty()) {
			BinaryTree<T> node = stk.pop();
			if (node != null) {
				if (node.getLeft() == null && node.getRight() == null)
					res.add(node);
				else {
					stk.push(node.getRight());
					stk.push(node.getLeft());
				}
			}
		}
		return res;
	}

	@Test
	public void test1() {
		// 3
		// 2 5
		// 1 4 6
		BinaryTree<Integer> root = new BinaryTree<Integer>(3, null, null);
		root.setLeft(new BinaryTree<Integer>(2, null, null));
		root.getLeft().setLeft(new BinaryTree<Integer>(1, null, null));
		root.setRight(new BinaryTree<Integer>(5, null, null));
		root.getRight().setLeft(new BinaryTree<Integer>(4, null, null));
		root.getRight().setRight(new BinaryTree<Integer>(6, null, null));
		// should output 1, 4, 6
		ArrayList<BinaryTree<Integer>> L = connectLeaves(root);
		ArrayList<Integer> output = new ArrayList<Integer>();
		for (BinaryTree<Integer> l : L) {
			output.add(l.getData());
			System.out.print(l.getData()+"\t");
		}
		System.out.println();
		assertTrue(output.size() == 3);
		assertTrue(output.get(0).equals(1) && output.get(1).equals(4)
				&& output.get(2).equals(6));
	}

	@Test
	public void test2() {
		//  3
		// 4 _
		// _ 5
		BinaryTree<Integer> root = new BinaryTree<Integer>(3, null, null);
		root.setLeft(new BinaryTree<Integer>(4, null, null));
		root.getLeft().setRight(new BinaryTree<Integer>(5, null, null));
		// should output 5
		ArrayList<BinaryTree<Integer>> L = connectLeaves(root);
		ArrayList<Integer> output = new ArrayList<Integer>();
		for (BinaryTree<Integer> l : L) {
			output.add(l.getData());
			System.out.print(l.getData()+"\t");
		}
		System.out.println();
		assertTrue(output.size() == 1);
		assertTrue(output.get(0).equals(5));

	}

	@Test
	public void test3() {
		// 3
		// 4 5
		// _ 2 _ 6
		// _ _ 0 _ _ 8 9
		BinaryTree<Integer> root = new BinaryTree<Integer>(3, null, null);
		root.setLeft(new BinaryTree<Integer>(4, null, null));
		root.setRight(new BinaryTree<Integer>(5, null, null));

		root.getLeft().setRight(new BinaryTree<Integer>(2, null, new BinaryTree<Integer>(0)));
		root.getRight().setRight(new BinaryTree<Integer>(6, new BinaryTree<Integer>(8), new BinaryTree<Integer>(9)));

		// should output 0, 8, 9
		ArrayList<BinaryTree<Integer>> L = connectLeaves(root);
		ArrayList<Integer> output = new ArrayList<Integer>();
		for (BinaryTree<Integer> l : L) {
			output.add(l.getData());
			System.out.print(l.getData()+"\t");
		}
		System.out.println();
		assertTrue(output.size() == 3);
		assertTrue(output.get(0).equals(0) && output.get(1).equals(8)
				&& output.get(2).equals(9));
	}
}
