package interview.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Follow up for problem "Populating Next Right Pointers in Each Node".
 * 
 * What if the given tree could be any binary tree? Would your previous solution
 * still work?
 * 
 * Note:
 * You may only use constant extra space.
 * 
 * For example, Given the following
 * binary tree,
 * 
 * 1
 * / \
 * 2 3
 * / \ \
 * 4 5 7
 * 
 * After calling your function, the tree should look like:
 * 
 * 1 -> NULL
 * / \
 * 2 -> 3 -> NULL
 * / \ \
 * 4-> 5 -> 7 -> NULL
 * 
 * @author yazhoucao
 * 
 */
public class Populating_Next_Right_Pointers_in_Each_Node_II {

	public static void main(String[] args) {
		TreeLinkNode root = new TreeLinkNode(1);
		root.left = new TreeLinkNode(9);
		root.right = new TreeLinkNode(20);
		root.right.left = new TreeLinkNode(15);
		root.right.right = new TreeLinkNode(7);
		connect(root);
		System.out.println(root.val);
	}

	/****************** Follow up version of Populating_Next_Right_Pointers_in_Each_Node I ******************/

	/**
	 * Version 1
	 * recursively connect the current level of all siblings
	 * form the first left node
	 * 
	 */
	public static void connect0(TreeLinkNode root) {
		if (root == null)
			return;
		TreeLinkNode nextFirst = null;
		TreeLinkNode p = root;
		while (p != null) {
			TreeLinkNode current = p;
			TreeLinkNode sibling = null;
			p = p.next;
			while (p != null) {
				if (p.left != null) {
					sibling = p.left;
					break;
				} else if (p.right != null) {
					sibling = p.right;
					break;
				}
				p = p.next;
			}
			if (current.right != null)
				current.right.next = sibling;
			if (current.left != null)
				current.left.next = (current.right == null ? sibling
						: current.right);
			if (nextFirst == null)
				if (current.left != null)
					nextFirst = current.left;
				else
					nextFirst = current.right;
		}
		connect0(nextFirst);
	}

	/**
	 * Version 2
	 * Just connect leftchild to rightchlild of current node,
	 * and rightchild to next node's leftchild recursively.
	 * 
	 * @param root
	 */
	public static void connect(TreeLinkNode root) {
		if (root == null)
			return;
		TreeLinkNode p = root.next;
		while (p != null) { // find the first non-null sibling node
			if (p.left != null) {
				p = p.left;
				break;
			} else if (p.right != null) {
				p = p.right;
				break;
			}
			p = p.next;
		}
		if (root.left != null)
			root.left.next = (root.right == null ? p : root.right);
		if (root.right != null)
			root.right.next = p;

		// must do right first, the order is important
		connect(root.right);
		connect(root.left);
	}

	/**
	 * Non-Recursive version of above Version 2, second practice
	 * Just connect leftchild to rightchlild of current node,
	 * and rightchild to next node's leftchild.
	 */
	public void connect2(TreeLinkNode root) {
		if (root == null)
			return;
		Queue<TreeLinkNode> q = new LinkedList<TreeLinkNode>();
		q.add(root);
		while (!q.isEmpty()) {
			TreeLinkNode curr = q.poll();
			if (curr.left == null && curr.right == null)
				continue;
			TreeLinkNode rightmost = null;
			if (curr.left != null) {
				rightmost = curr.left;
				q.add(curr.left);
			}
			if (curr.right != null) {
				rightmost = curr.right;
				q.add(curr.right);
			}
			if (curr.left != null && curr.right != null) {
				curr.left.next = curr.right;
			}
			TreeLinkNode nxt = curr.next;
			while (nxt != null) {// find the first non-null sibling node
				if (nxt.left != null) {
					rightmost.next = nxt.left;
					break;
				} else if (nxt.right != null) {
					rightmost.next = nxt.right;
					break;
				}
				nxt = nxt.next;
			}
		}
	}

	public static class TreeLinkNode {
		int val;
		TreeLinkNode left, right, next;

		TreeLinkNode(int x) {
			val = x;
		}
	}
}
