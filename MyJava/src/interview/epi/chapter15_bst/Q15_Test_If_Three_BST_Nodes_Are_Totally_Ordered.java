package interview.epi.chapter15_bst;

import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

/**
 * Let r, s, and m be distinct nodes in a BST. In this BST, nodes do not have
 * pointers to their parents and all keys are unique. Write a function which
 * returns true if m has both an ancestor and a descendant in the set {r,s}.
 * 
 * @author yazhoucao
 * 
 */
public class Q15_Test_If_Three_BST_Nodes_Are_Totally_Ordered {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Perform the searches from s for r.key and from r for s.key in an
	 * interleaved way.
	 */
	public static boolean isRSDescendantAncestorOfM(BinaryTree<Integer> r,
			BinaryTree<Integer> s, BinaryTree<Integer> m) {
		BinaryTree<Integer> curR = r;
		BinaryTree<Integer> curS = s;
		// Interleaving searches from r and s.
		while (curR != null && curR != s && curR != m && curS != null
				&& curS != r && curS != m) {
			curR = curR.getData() > m.getData() ? curR.getLeft() : curR.getRight();
			curS = curS.getData() > m.getData() ? curS.getLeft() : curS.getRight();
		}
		// Reach the other before reaching m.
		if (curR == s || curS == r || (curR != m && curS != m)) {
			return false;
		}
		// Try to search m before reaching the other.
		return searchTarget(m, s) || searchTarget(m, r);
	}

	private static boolean searchTarget(BinaryTree<Integer> p, BinaryTree<Integer> t) {
		while (p != null && p != t) {
			p = p.getData() > t.getData() ? p.getLeft() : p.getRight();
		}
		return p == t;
	}
}
