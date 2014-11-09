package interview.leetcode;

/**
 * Given two binary trees, write a function to check if they are equal or not.
 * 
 * Two binary trees are considered equal if they are structurally identical and
 * the nodes have the same value.
 * 
 * @author yazhoucao
 * 
 */
public class Same_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null)
			return true;
		else if (p != null && q != null && p.val == q.val)
			return (isSameTree(p.left, q.left) && isSameTree(p.right, q.right));
		else
			return false;
	}

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

}
