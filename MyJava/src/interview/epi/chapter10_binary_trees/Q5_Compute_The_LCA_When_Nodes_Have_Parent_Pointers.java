package interview.epi.chapter10_binary_trees;

import interview.epi.chapter10_binary_trees.BinaryTreeWithParentPrototype.BinaryTree;

/**
 * 
 * Problem 10.5
 * Assume that each node has a parent pointer. The tree has n nodes and height
 * h. Your algorithm should run in O(1) space and O(h) time.
 * 
 * @author yazhoucao
 * 
 */
public class Q5_Compute_The_LCA_When_Nodes_Have_Parent_Pointers {

	public static void main(String[] args) {
		BinaryTree<Integer> r = new BinaryTree<Integer>(1);
		r.setLeft(new BinaryTree<Integer>(2));
		r.getLeft().setParent(r);
		r.getLeft().setLeft(new BinaryTree<Integer>(3));
		r.getLeft().getLeft().setParent(r.getLeft());
		System.out.println(getDepth(r.getLeft().getLeft()));
	}

	/**
	 * Calculate the depth of each, move the deeper one up till they are at the
	 * same depth, then move up together until meet the LCA.
	 * 
	 * Time: O(h) or O(lgn), h is the height, n is the number of nodes.
	 */
	public static <T> BinaryTree<T> LCA(BinaryTree<T> i, BinaryTree<T> j) {
		int depthI = getDepth(i), depthJ = getDepth(j);
		if (depthI > depthJ)
			return LCA_helper(i, j, depthI - depthJ);
		else
			return LCA_helper(j, i, depthJ - depthI);
	}

	public static <T> BinaryTree<T> LCA_helper(BinaryTree<T> deep,
			BinaryTree<T> shallow, int diff) {
		while (diff-- > 0)
			deep = deep.getParent();

		while (deep != shallow) {
			deep = deep.getParent();
			shallow = shallow.getParent();
		}
		return deep;
	}

	public static <T> int getDepth(BinaryTree<T> node) {
		int depth = 0;
		while (node != null) {
			node = node.getParent();
			depth++;
		}
		return depth;
	}
}
