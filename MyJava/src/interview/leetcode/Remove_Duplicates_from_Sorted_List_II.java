package interview.leetcode;

/**
 * Given a sorted linked list, delete all nodes that have duplicate numbers,
 * leaving only distinct numbers from the original list.
 * 
 * For example,
 * 
 * Given 1->2->3->3->4->4->5, return 1->2->5.
 * 
 * Given 1->1->1->2->3, return 2->3.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Remove_Duplicates_from_Sorted_List_II {

	public static void main(String[] args) {
		ListNode head = new ListNode(1);
		head.next = new ListNode(1);
		head.next.next = new ListNode(2);
		head.next.next.next = new ListNode(2);
		ListNode res = deleteDuplicates(head);
		while (res != null) {
			System.out.print(res.toString() + "->");
			res = res.next;
		}
		System.out.println("null");
	}
	
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode prehead = new ListNode(0);
        prehead.next = head;
        ListNode curr = head;
        ListNode pre = prehead;
        while(curr!=null && curr.next!=null){
            if(curr.val==curr.next.val){
                while(curr.next!=null && curr.val==curr.next.val){
                    curr.next = curr.next.next;
                }
                pre.next = curr.next;
            }else
                pre = pre.next;
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
