package interview.epi.chapter15_bst;

import static org.junit.Assert.*;
import interview.AutoTestUtils;
import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.junit.Test;

/**
 * Find the top-k elements in a BST.
 * 
 * @author yazhoucao
 * 
 */
public class Q4_Find_The_K_Largest_Elements_In_A_BST {

	static Class<?> c = Q4_Find_The_K_Largest_Elements_In_A_BST.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Perform a reverse inorder traversal
	 */
	public List<Integer> findKLargestInBST(BinaryTree<Integer> root, int k) {
		List<Integer> res = new ArrayList<>();
		Stack<BinaryTree<Integer>> stk = new Stack<>();
		BinaryTree<Integer> p = root;
		while (k > 0 && (p != null || !stk.isEmpty())) {
			if (p != null) {
				stk.push(p);
				p = p.getRight();
			} else { // reach the end of right
				p = stk.pop();
				res.add(p.getData());
				k--;
				p = p.getLeft();
			}
		}
		return res;
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
		Random r = new Random();
		int k = r.nextInt(6) + 1;
		System.out.println("k = " + k);
		List<Integer> ans = findKLargestInBST(root, k);
		System.out.println(ans);
		for (int i = 1; i < ans.size(); ++i) {
			assertTrue(ans.get(i - 1) >= ans.get(i));
		}
	}
}
