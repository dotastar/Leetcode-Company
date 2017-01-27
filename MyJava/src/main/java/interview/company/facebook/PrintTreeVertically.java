package interview.company.facebook;

import interview.AutoTestUtils;
import interview.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/**
 * Given a binary tree, print it vertically. The following example illustrates
 * vertical order traversal.
 * 
 * Input:
 ********** 1
 ******** / _ \
 ****** 2 _____ 3
 ***** / \ ___ / \
 **** 4 _ 5 _ 6 _ 7
 ************* \ _ \
 ************** 8 _ 9
 * 
 * The output of print this tree vertically will be:
 * 4
 * 2
 * 1 5 6
 * 3 8
 * 7
 * 9
 * 
 * http://www.geeksforgeeks.org/print-binary-tree-vertical-order/
 * 
 * http://www.geeksforgeeks.org/vertical-sum-in-a-given-binary-tree/
 * 
 * @author yazhoucao
 *
 */
public class PrintTreeVertically {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(PrintTreeVertically.class);
	}

	/**
	 * The key is to get the horizontal information of each node
	 */

	/**
	 * Solution 1
	 * Store horizontal coords in TreeMap<Coord, Column>,
	 * sort it by the coord value and print it
	 * 
	 * Time: O(nlogn)
	 * Space: O(n)
	 * 
	 * @param root
	 */
	public void printTreeVertically(TreeNode root) {
		TreeMap<Integer, List<TreeNode>> columns = new TreeMap<>();
		traverse(root, 0, columns);
		while (!columns.isEmpty()) {
			System.out.println(columns.pollFirstEntry().getValue());
		}
	}

	private void traverse(TreeNode node, int horizVal,
			Map<Integer, List<TreeNode>> columns) {
		if (node == null)
			return;
		List<TreeNode> nodes = columns.get(horizVal);
		if (nodes == null) {
			nodes = new ArrayList<>();
			columns.put(horizVal, nodes);
		}
		nodes.add(node);
		traverse(node.left, horizVal - 1, columns);
		traverse(node.right, horizVal + 1, columns);
	}

	/**
	 * Solution 2 - space efficient
	 * 1.Traverse the tree once and get the minimum and maximum horizontal
	 * distance with respect to root. - the width
	 * 2.Iterate for each vertical line, for each vertical line traverse the
	 * tree and print the nodes which lie on that line.
	 * 
	 * Time: O(width * n), worst case : n^2
	 * Space: O(1)
	 */
	public void printTreeVertically2(TreeNode root) {
		int[] minmax = { 0, 0 };
		getWidth(root, 0, minmax);
		for (int i = minmax[0]; i <= minmax[1]; i++) {
			printColumn(root, 0, i);
			System.out.println();
		}
	}

	/**
	 * Print nodes which lie on that specific vertical line.
	 * Time: O(n)
	 */
	private void printColumn(TreeNode node, int currNum, int colNum) {
		if (node == null)
			return;
		if (currNum == colNum)
			System.out.print(node.key + " ");
		printColumn(node.left, currNum - 1, colNum);
		printColumn(node.right, currNum + 1, colNum);
	}

	private void getWidth(TreeNode node, int currVal, int[] width) {
		if (node == null)
			return;
		width[0] = currVal < width[0] ? currVal : width[0];
		width[1] = currVal > width[0] ? currVal : width[1];
		getWidth(node.left, currVal - 1, width);
		getWidth(node.right, currVal + 1, width);
	}

	@Test
	public void test1() {
		/**
		 ********** 1
		 ******** / _ \
		 ****** 2 _____ 3
		 ***** / \ ___ / \
		 **** 4 _ 5 _ 6 _ 7
		 ************* \ _ \
		 ************** 8 _ 9
		 */
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		root.right.left.right = new TreeNode(8);
		root.right.right.right = new TreeNode(9);
		printTreeVertically(root);
		printTreeVertically2(root);
		System.out.println();
	}

	@Test
	public void test2() {
		/**
		 ********** 1
		 ******** / _ \
		 ****** 2 _____ 3
		 ***** / \ ___ / \
		 **** 4 _ 5 _ 6 _ 7
		 ******* / *** \ _ \
		 ***** 10 ***** 8 _ 9
		 **** /
		 ** 11
		 * /
		 * 12
		 */
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		root.right.left.right = new TreeNode(8);
		root.right.right.right = new TreeNode(9);
		root.left.right.left = new TreeNode(10);
		root.left.right.left.left = new TreeNode(11);
		root.left.right.left.left.left = new TreeNode(12);
		printTreeVertically(root);
		printTreeVertically2(root);
		System.out.println();
	}

}
