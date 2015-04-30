package interview.laicode;

import interview.laicode.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Reconstruct Binary Search Tree With Level Order Traversal
 * Fair
 * Data Structure
 * 
 * Given the levelorder traversal sequence of a binary search tree, reconstruct
 * the original tree.
 * 
 * Assumptions
 * 
 * The given sequence is not null
 * There are no duplicate keys in the binary search tree
 * 
 * Examples
 * 
 * levelorder traversal = {5, 3, 8, 1, 4, 11}
 * 
 * the corresponding binary search tree is
 * 
 ** 5
 * 
 * / \
 * 
 * 3 8
 * 
 * / \ \
 * 
 * 1 4 11
 * 
 * How is the binary tree represented?
 * 
 * We use the pre order traversal sequence with a special symbol "#" denoting
 * the null node.
 * 
 * For Example:
 * 
 * The sequence [1, 2, #, 3, 4, #, #, #] represents the following binary tree:
 * 
 *** 1
 * 
 ** / \
 * 
 * 2 _ 3
 * 
 *** /
 * 
 * 4
 * 
 * @author yazhoucao
 * 
 */
public class Reconstruct_Binary_Search_Tree_With_Level_Order_Traversal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Construct tree in a BFS (level-order traversal) way, Time: O(n)
	 * Make use of the BST property that left < root, right > root.
	 * Need another Queue for the current max range value it has, the current
	 * node shouldn't greater than current max.
	 */
	public TreeNode reconstruct(int[] level) {
		if (level.length == 0)
			return null;
		TreeNode root = new TreeNode(level[0]);
		Queue<TreeNode> q = new LinkedList<>();
		Queue<Integer> maxQ = new LinkedList<>();
		q.offer(root);
		maxQ.offer(Integer.MAX_VALUE);
		int i = 1;
		while (i < level.length && !q.isEmpty()) {
			TreeNode node = q.poll();
			Integer max = maxQ.poll();
			if (level[i] < node.key && level[i] < max) {
				node.left = new TreeNode(level[i++]);
				q.offer(node.left);
				maxQ.offer(node.key);
			}
			if (i < level.length && level[i] > node.key && level[i] < max) {
				node.right = new TreeNode(level[i++]);
				q.offer(node.right);
				maxQ.offer(max);
			}
		}
		return root;
	}
}
