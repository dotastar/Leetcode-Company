package general.oo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.Test;

/**
 * External Iterator Design Pattern Implementation
 * 
 * Define in Java an external iterator for inorder traversal of a generic binary
 * search tree (BST), and use this iterator to define the equality of two BSTs.
 * The external iterator should be generic and should support two public
 * methods: a boolean function done(), which tests whether the iterator has any
 * more values to yield, and a function next(), which returns the next value if
 * the iterator is not yet done. It may contain other helper methods, which
 * should be declared private.
 * The equality test should repeatedly obtain one value from each tree, in
 * alternating fashion, and compare these values for equality. It should return
 * false if the values are not equal (mismatch). If there are no mismatches and
 * the iterators are done at the same time, the equality test should return
 * true.
 * 
 * @author yazhoucao
 * 
 * @param <T>
 */
// Tree implements the binary search tree property
class Tree<T extends Comparable<T>> {
	public Tree(T v) {
		value = v;
		left = null;
		right = null;
	}

	public void insert(T v) {
		if (value.compareTo(v) == 0)
			return;
		if (value.compareTo(v) > 0)
			if (left == null)
				left = new Tree<T>(v);
			else
				left.insert(v);
		else if (value.compareTo(v) < 0)
			if (right == null)
				right = new Tree<T>(v);
			else
				right.insert(v);
	}

	protected T value;
	protected Tree<T> left;
	protected Tree<T> right;
}

/**
 * External Iterator
 * 
 * @author yazhoucao
 */
class Iter<T extends Comparable<T>> {
	// define here the external iterator operations, done and next
	private Tree<T> p;
	private Stack<Tree<T>> stk = new Stack<Tree<T>>();

	public Iter(Tree<T> iterTree) {
		p = iterTree;
	}

	public Tree<T> next() {
		if (done())
			return null;
		Tree<T> res = null;
		while (res == null) {
			if (p != null) {
				stk.push(p);
				p = p.left;
			} else {
				p = stk.pop();
				res = p;
				p = p.right;
			}
		}
		return res;
	}

	public boolean done() {
		return p == null && stk.isEmpty();
	}
}

public class GenericTreeEquality {

	static <T extends Comparable<T>> boolean equals(Tree<T> tree1, Tree<T> tree2) {
		// define here the equality test
		Iter<T> iter1 = new Iter<T>(tree1);
		Iter<T> iter2 = new Iter<T>(tree2);
		while (!iter1.done()) {
			if (iter2.done())
				return false;
			Tree<T> nxt1 = iter1.next();
			Tree<T> nxt2 = iter2.next();
			// System.out.println(nxt1.value.toString()+" : "+nxt2.value.toString());
			if (nxt1.value.compareTo(nxt2.value) != 0)
				return false;
		}
		return iter2.done();
	}

	public static void main(String[] args) {

		Tree<Integer> tree1 = new Tree<Integer>(50);
		tree1.insert(70);
		tree1.insert(80);
		tree1.insert(90);
		tree1.insert(100);

		Tree<Integer> tree2 = new Tree<Integer>(100);
		tree2.insert(90);
		tree2.insert(80);
		tree2.insert(70);
		tree2.insert(50);

		System.out.println(equals(tree1, tree2));
	}

	
	
	
	/**
	 * For testing GenericTreeEquality.java
	 * 
	 * @author yazhoucao
	 * 
	 */
	public static class BSTester {

		/* INEQUALITY TEST */
		@Test
		public void test() {

			Tree<Integer> tree1 = new Tree<Integer>(50);
			tree1.insert(70);
			tree1.insert(80);
			tree1.insert(90);
			tree1.insert(100);

			Tree<Integer> tree2 = new Tree<Integer>(90);
			tree2.insert(80);
			tree2.insert(70);
			tree2.insert(50);

			assertFalse(GenericTreeEquality.equals(tree1, tree2));
		}

		/* EQUALITY */
		@Test
		public void test2() {

			Tree<Integer> tree1 = new Tree<Integer>(90);
			tree1.insert(80);
			tree1.insert(100);
			tree1.insert(70);
			tree1.insert(50);
			tree1.insert(75);
			tree1.insert(55);
			tree1.insert(53);
			tree1.insert(52);
			tree1.insert(54);
			tree1.insert(95);
			tree1.insert(120);

			Tree<Integer> tree2 = new Tree<Integer>(90);
			tree2.insert(70);
			tree2.insert(54);
			tree2.insert(95);
			tree2.insert(53);
			tree2.insert(52);
			tree2.insert(55);
			tree2.insert(120);
			tree2.insert(80);
			tree2.insert(100);
			tree2.insert(50);
			tree2.insert(75);

			assertTrue(GenericTreeEquality.equals(tree1, tree2));
		}

		/* SINGLE ELEMENT */
		@Test
		public void test3() {
			Tree<Integer> tree1 = new Tree<Integer>(90);
			Tree<Integer> tree2 = new Tree<Integer>(70);

			assertFalse(GenericTreeEquality.equals(tree1, tree2));

			tree1 = new Tree<Integer>(90);
			tree2 = new Tree<Integer>(90);

			assertTrue(GenericTreeEquality.equals(tree1, tree2));

		}

		/* NULL POINTER */
		@Test
		public void test4() {
			Tree<Integer> tree1 = new Tree<Integer>(90);
			Tree<Integer> tree2 = null;

			assertFalse(GenericTreeEquality.equals(tree1, tree2));

		}

		/* STRING TEST - EQUALITY & INEQUALITY */
		@Test
		public void test5() {

			Tree<String> tree1 = new Tree<String>("bar");
			tree1.insert("hello");
			tree1.insert("pardon");
			tree1.insert("james");
			tree1.insert("dean");

			Tree<String> tree2 = new Tree<String>("hello");
			tree2.insert("bar");
			tree2.insert("dean");
			tree2.insert("james");
			tree2.insert("pardon");

			assertTrue(GenericTreeEquality.equals(tree1, tree2));

			tree1 = new Tree<String>("bar");
			tree1.insert("hello");
			tree1.insert("pardon");

			tree2 = new Tree<String>("hello");
			tree2.insert("bar");
			tree2.insert("dean");
			tree2.insert("james");
			tree2.insert("pardon");

			assertFalse(GenericTreeEquality.equals(tree1, tree2));

		}

		/* DOUBLE TEST */
		@Test
		public void test6() {

			Tree<Double> tree1 = new Tree<Double>(6.2);
			tree1.insert(70.5);
			tree1.insert(80.2);
			tree1.insert(90.6);
			tree1.insert(100.7);

			Tree<Double> tree2 = new Tree<Double>(6.2);
			tree2.insert(100.7);
			tree2.insert(90.6);
			tree2.insert(80.2);
			tree2.insert(70.5);

			assertTrue(GenericTreeEquality.equals(tree1, tree2));
		}
	}
}
