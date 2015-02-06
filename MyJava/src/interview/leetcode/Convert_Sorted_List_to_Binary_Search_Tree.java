package interview.leetcode;

/**
 * Given a singly linked list where elements are sorted in ascending order,
 * convert it to a height balanced BST.
 * 
 * @author yazhoucao
 * 
 */
public class Convert_Sorted_List_to_Binary_Search_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Convert_Sorted_List_to_Binary_Search_Tree obj = new Convert_Sorted_List_to_Binary_Search_Tree();
		ListNode head = new ListNode(3);
		head.next = new ListNode(5);
		head.next.next = new ListNode(8);
		head.next.next.next = new ListNode(10);
//		head.next.next.next.next = new ListNode(12);
//		head.next.next.next.next.next = new ListNode(14);

	}


	/**
	 * Create nodes bottom-up, and assign them to its parents. The bottom-up
	 * approach enables us to access the list in its order at the same time as
	 * creating nodes.
	 * 
	 * @param head
	 * @return
	 */
	static ListNode h;

	public TreeNode sortedListToBST(ListNode head) {
		if (head == null)
			return null;
		h = head;
		return toBST(0, getLength(head) - 1);
	}

	/**
	 * It is an inorder traversal, bottom up construct an BST
	 */
	public TreeNode toBST(int start, int end) {
		if (start > end)
			return null;
		int mid = (start + end) / 2;
		TreeNode left = toBST(start, mid - 1);
		// visiting
		TreeNode parent = new TreeNode(h.val);
		parent.left = left;
		h = h.next;
		// traverse right
		parent.right = toBST(mid + 1, end);

		return parent;
	}

	public int getLength(ListNode head) {
		int len = 0;
		while (head != null && head.next != null) {
			head = head.next.next;
			len += 2;
		}
		if (head != null)
			len++;
		return len;
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

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}

		public String toString() {
			return Integer.toString(val);
		}
	}
}
