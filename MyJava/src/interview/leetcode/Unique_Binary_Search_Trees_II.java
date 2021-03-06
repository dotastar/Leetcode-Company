package interview.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Given n, generate all structurally unique BST's (binary search trees) that
 * store values 1...n.
 * 
 * For example, Given n = 3, your program should return all 5 unique BST's shown
 * below.
 *  
   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
 * 
 * @author yazhoucao
 * 
 */
public class Unique_Binary_Search_Trees_II {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Brute force, enumerate all the trees
	 */
	public List<TreeNode> generateTrees(int n) {
		List<TreeNode> res = new LinkedList<TreeNode>();
		res.addAll(generate(1, n));
		return res;
	}

	public List<TreeNode> generate(int start, int end) {
		List<TreeNode> trees = new LinkedList<TreeNode>();
		if (start > end) {
			trees.add(null);
			return trees;
		}
		for (int i = start; i <= end; i++) {
			List<TreeNode> lefts = generate(start, i - 1);
			List<TreeNode> rights = generate(i + 1, end);
			for (TreeNode left : lefts) {
				for (TreeNode right : rights) {
					TreeNode node = new TreeNode(i);
					node.left = left;
					node.right = right;
					trees.add(node);
				}
			}
		}
		return trees;
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}

		public String toString() {
			return Integer.toString(val);
		}
	}
}
