package interview.leetcode;

/**
 * Given a linked list, swap every two adjacent nodes and return its head.
 * 
 * For example, Given 1->2->3->4, you should return the list as 2->1->4->3.
 * 
 * Your algorithm should use only constant space. You may not modify the values
 * in the list, only nodes itself can be changed.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Swap_Nodes_in_Pairs {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		head.next.next.next.next.next = new ListNode(6);

		ListNode res = swapPairs(head);
		while (res != null) {
			System.out.print(res.val + "->");
			res = res.next;
		}
		System.out.print("null\n");
	}

	public static ListNode swapPairs(ListNode head) {
        ListNode prehead = new ListNode(0);
        prehead.next = head;
        ListNode pre = prehead;
        ListNode curr = head;
        while(curr!=null && curr.next!=null){
            ListNode next = curr.next.next;
            pre.next = curr.next;
            curr.next.next = curr;  //swapping
            curr.next = next;
            
            pre = curr;
            curr = curr.next;
        }
        return prehead.next;
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
