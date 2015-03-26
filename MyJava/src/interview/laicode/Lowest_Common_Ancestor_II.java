package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 Lowest Common Ancestor II
 * Fair
 * Data Structure
 * 
 * Given two nodes in a binary tree (with parent pointer available), find their
 * lowest common ancestor.
 * 
 * Assumptions
 * 
 * There is parent pointer for the nodes in the binary tree
 * 
 * The given two nodes are not guaranteed to be in the binary tree
 * 
 * Examples
 * 
 * 5
 * 
 * / \
 * 
 * 9 12
 * 
 * / \ \
 * 
 * 2 3 14
 * 
 * The lowest common ancestor of 2 and 14 is 5
 * 
 * The lowest common ancestor of 2 and 9 is 9
 * 
 * The lowest common ancestor of 2 and 8 is null (8 is not in the tree)
 * 
 * @author yazhoucao
 * 
 */
public class Lowest_Common_Ancestor_II {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Lowest_Common_Ancestor_II.class);
	}

	public TreeNodeP lowestCommonAncestor(TreeNodeP one, TreeNodeP two) {
		int depth1 = getDepth(one);
		int depth2 = getDepth(two);
		int diff = depth1 - depth2;
		// make depth1 > depth2, one is always the deeper node
		if (diff < 0) {
			TreeNodeP temp = one;
			one = two;
			two = temp;
			diff = -diff;
		}
		for (int i = 0; i < diff && one != null; i++)
			one = one.parent;
		while (one != two && one != null && two != null) {
			one = one.parent;
			two = two.parent;
		}
		return one == two ? one : null;
	}

	private int getDepth(TreeNodeP node) {
		int depth = 0;
		while (node != null) {
			node = node.parent;
			depth++;
		}
		return depth;
	}

	@Test
	public void test1() {
		TreeNodeP root = new TreeNodeP(5, null);
		root.left = new TreeNodeP(9, root);
		root.right = new TreeNodeP(12, root);
		root.left.left = new TreeNodeP(2, root.left);
		root.left.right = new TreeNodeP(3, root.left);
		root.right.right = new TreeNodeP(14, root.right);
		TreeNodeP res = lowestCommonAncestor(root.left.right, root.left.left);
		TreeNodeP ans = root.left;
		assertTrue(res == ans);
	}

	@Test
	public void test2() {
		TreeNodeP root = new TreeNodeP(5, null);
		root.left = new TreeNodeP(9, root);
		root.right = new TreeNodeP(12, root);
		root.left.left = new TreeNodeP(2, root.left);
		root.left.right = new TreeNodeP(3, root.left);
		root.right.right = new TreeNodeP(14, root.right);
		TreeNodeP res = lowestCommonAncestor(root.left.right, root.right.right);
		TreeNodeP ans = root;
		assertTrue(res == ans);
	}

	@Test
	public void test3() {
		TreeNodeP root = new TreeNodeP(5, null);
		root.left = new TreeNodeP(9, root);
		root.right = new TreeNodeP(12, root);
		root.left.left = new TreeNodeP(2, root.left);
		root.left.right = new TreeNodeP(3, root.left);
		root.right.right = new TreeNodeP(14, root.right);
		TreeNodeP res = lowestCommonAncestor(root.left.right, root);
		TreeNodeP ans = root;
		assertTrue(res == ans);
	}

	@Test
	public void test4() { // different tree
		TreeNodeP root = new TreeNodeP(5, null);
		root.left = new TreeNodeP(9, root);
		root.right = new TreeNodeP(12, root);
		root.left.left = new TreeNodeP(2, root.left);
		root.left.right = new TreeNodeP(3, root.left);
		root.right.right = new TreeNodeP(14, root.right);

		TreeNodeP root2 = new TreeNodeP(-1, null);
		root2.left = new TreeNodeP(9, root2);
		root2.left.left = new TreeNodeP(2, root2.left);
		root2.left.left.left = new TreeNodeP(2, root2.left.left);
		root2.left.left.left.left = new TreeNodeP(2, root2.left.left.left);

		TreeNodeP res = lowestCommonAncestor(root.left,
				root2.left.left.left.left);
		TreeNodeP ans = null;
		assertTrue(res == ans);
	}

	public class TreeNodeP {
		public int key;
		public TreeNodeP left;
		public TreeNodeP right;
		public TreeNodeP parent;

		public TreeNodeP(int key, TreeNodeP parent) {
			this.key = key;
			this.parent = parent;
		}
	}

}
