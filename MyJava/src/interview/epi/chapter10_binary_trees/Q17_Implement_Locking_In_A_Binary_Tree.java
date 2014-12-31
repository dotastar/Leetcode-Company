package interview.epi.chapter10_binary_trees;

import static org.junit.Assert.*;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Problem 10.17
 * This problem is concerned with the design of an API for setting the state of
 * nodes in a binary tree to lock or unlock. A node's state cannot be set to
 * lock if any of its descendants or ancestors are in lock. Changing a node's
 * state to lock does not change the state of any other nodes. For example, all
 * leaves may simultaneously be in the state lock. (If this is the case, no
 * nonleaf nodes can be in state lock.)
 * 
 * @author yazhoucao
 * 
 */
public class Q17_Implement_Locking_In_A_Binary_Tree {
	static Class<?> c = Q17_Implement_Locking_In_A_Binary_Tree.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	public static class BinaryTree<T> {
		private BinaryTree<T> left, right, parent;
		private boolean locked;
		private int numChildrenLocks;

		/**
		 * O(1)
		 */
		public boolean isLock() {
			return locked;
		}

		/**
		 * O(h)
		 */
		public boolean lock() {
			if (numChildrenLocks == 0 && !locked) {
				BinaryTree<T> p = parent;
				while (p != null) { // Make sure all parents do not lock.
					if (p.isLock())
						return false;
					p = p.parent;
				}

				p = parent;
				locked = true; // Lock itself and update its parents.
				while (p != null) {
					p.numChildrenLocks++;
					p = p.parent;
				}

				return true;
			} else
				return false;
		}

		/**
		 * O(h)
		 */
		public void unlock() {
			if (locked) { // Unlock itself and update its parents.
				BinaryTree<T> p = parent;
				while (p != null) {
					p.numChildrenLocks--;
					p = p.parent;
				}
				locked = false;
			}
		}

		public BinaryTree<T> getLeft() {
			return left;
		}

		public void setLeft(BinaryTree<T> left) {
			this.left = left;
		}

		public BinaryTree<T> getRight() {
			return right;
		}

		public void setRight(BinaryTree<T> right) {
			this.right = right;
		}

		public void setParent(BinaryTree<T> parent) {
			this.parent = parent;
		}
	}

	@Test
	public void test1() {
		BinaryTree<Integer> root = new BinaryTree<Integer>();
		root.setLeft(new BinaryTree<Integer>());
		root.getLeft().setParent(root);
		root.setRight(new BinaryTree<Integer>());
		root.getRight().setParent(root);
		root.getLeft().setLeft(new BinaryTree<Integer>());
		root.getLeft().getLeft().setParent(root.getLeft());
		root.getLeft().setRight(new BinaryTree<Integer>());
		root.getLeft().getRight().setParent(root.getLeft());
		// Should output false.
		assertTrue(!root.isLock());
		System.out.println(root.isLock());
		root.lock();
		// Should output true.
		assertTrue(root.isLock());
		System.out.println(root.isLock());
		root.unlock();
		root.getLeft().lock();
		root.lock();
		// Should output false.
		assertTrue(!root.isLock());
		System.out.println(root.isLock());
		root.getRight().lock();
		// Should output true.
		assertTrue(root.getRight().isLock());
		System.out.println(root.getRight().isLock());
	}
}
