package interview.epi.chapter15_bst;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;
import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

import org.junit.Test;

/**
 * Given BST T, write recursive and iterative versions of a function that takes
 * a BST T, a key k, and returns the node containing k that would appear first
 * in an inorder traversal. If k absent, return null. The tree may contains
 * duplicate keys.
 * 
 * @author yazhoucao
 * 
 */
public class Q2_Find_The_First_Occurrentce_Of_K_In_A_BST {

	static Class<?> c = Q2_Find_The_First_Occurrentce_Of_K_In_A_BST.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Recursive
	 */
	public BinaryTree<Integer> findFirstEqualK(BinaryTree<Integer> node, int k) {
		if (node == null)
			return null;
		if (k == node.getData() && (node.getLeft() == null || node.getLeft().getData() != k))
			return node;
		else if (k <= node.getData())
			return findFirstEqualK(node.getLeft(), k);
		else
			return findFirstEqualK(node.getRight(), k);
	}

	/**
	 * Iterative
	 */
	public BinaryTree<Integer> findFirstEqualK2(BinaryTree<Integer> node,int k) {
		BinaryTree<Integer> p = node;
		while(p!=null){
			if(p.getData()==k && (p.getLeft()==null || p.getLeft().getData()!=k))
				return p;
			else if(k<=p.getData())
				p = p.getLeft();
			else
				p = p.getRight();
		}
		return p;
	}
	
	@Test
	public void test1(){
		 // 3
		// 2 6
		// 1 4 6
		BinaryTree<Integer> root = new BinaryTree<>(3);
		root.setLeft(new BinaryTree<>(2));
		root.getLeft().setLeft(new BinaryTree<>(1));
		root.setRight(new BinaryTree<>(6));
		root.getRight().setLeft(new BinaryTree<>(4));
		root.getRight().setRight(new BinaryTree<>(6));
		assertTrue (findFirstEqualK(root, 7) == null);
		assertTrue (findFirstEqualK(root, 6).getData().equals(6) && findFirstEqualK(root, 6).getRight().getData().equals(6));
		
		assertTrue (findFirstEqualK2(root, 7) == null);
		assertTrue (findFirstEqualK2(root, 6).getData().equals(6) && findFirstEqualK(root, 6).getRight().getData().equals(6));
	}
	
	
	@Test
	public void test2(){
		 // 3
		// 2 5
		// 1 4 6
		BinaryTree<Integer> root = new BinaryTree<>(3);
		root.setLeft(new BinaryTree<>(2));
		root.getLeft().setLeft(new BinaryTree<>(1));
		root.setRight(new BinaryTree<>(5));
		root.getRight().setLeft(new BinaryTree<>(4));
		root.getRight().setRight(new BinaryTree<>(6));
		assertTrue (findFirstEqualK(root, 7) == null);
		assertTrue (findFirstEqualK(root, 6).getData().equals(6));
		
		assertTrue (findFirstEqualK2(root, 7) == null);
		assertTrue (findFirstEqualK2(root, 6).getData().equals(6));
	}
}
