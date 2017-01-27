package interview.epi.chapter10_binary_trees;

import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Problem 10.13
 * Design an O(n) time algorithm for reconstructing a binary tree from a
 * preorder traversal visit sequence that uses null to mark empty children. How
 * would you modify your reconstruction algorithm if the sequence corresponded
 * to a postorder or inorder traversal?
 * 
 * @author yazhoucao
 * 
 */
public class Q13_Reconstruct_A_Binary_Tree_From_A_Preorder_Traversal_With_Marker {

	public static void main(String[] args) {

	}

	/**
	 * Traverse the sequence from right-to-left. Push nodes and nulls on to a
	 * stack; every time we encountered a non-null node x, we pop the stack
	 * twice, the first is the right child, the second is the left child, the
	 * current is the parent. When the sequence is exhausted, there will be a
	 * single node on the stack, which will be the root.
	 * 
	 * Time: O(n)
	 */
	public static <T> BinaryTree<T> reconstructPreorder(ArrayList<T> preorder) {
		Stack<BinaryTree<T>> stk = new Stack<>();
		for (int i = preorder.size() - 1; i >= 0; i--) {
			T val = preorder.get(i);
			if (val != null) {
				BinaryTree<T> r = stk.pop();
				BinaryTree<T> l = stk.pop();
				stk.push(new BinaryTree<>(val, l, r));
			} else
				stk.push(null);
		}
		return stk.pop();
	}
}
