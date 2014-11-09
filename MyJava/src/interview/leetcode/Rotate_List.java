package interview.leetcode;


/**
 * Given a list, rotate the list to the right by k places, where k is
 * non-negative.
 * 
 * For example: Given 1->2->3->4->5->NULL and k = 2, return 4->5->1->2->3->NULL.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Rotate_List {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0; i<=9; i++){
			ListNode head = new ListNode(1);
			head.next = new ListNode(2);
			head.next.next = new ListNode(3);
			head.next.next.next = new ListNode(4);
			head.next.next.next.next = new ListNode(5);
			head.next.next.next.next.next = new ListNode(6);
			
			ListNode res = rotateRight(head, i);
			System.out.print("i = "+i+":\t");
			while (res != null) {
				System.out.print(res.toString() + " -> ");
				res = res.next;
				if (res == null)
					System.out.println("null");
			}	
		}
	}

	/**
	 * Time: O(n), Space: O(1)
	 * 
	 * Note: n could be greater than the length, 2 times, 3 times greater.
	 */
	public static ListNode rotateRight(ListNode head, int n) {
        if(n==0 || head==null)  
            return head;
            
        int length = 1;
        ListNode tail = head;
        while(tail.next!=null){ //get length
            tail = tail.next;
            length++;
        }
        int k = n%length;
        if(k==0)
            return head;
            
        //convert our goal to rotate left j nodes
        int j = length - k;
        ListNode prev = null;
        ListNode curr = head;
        while(curr!=null && j>0){
            prev = curr;
            curr = curr.next;
            j--;
        }
        tail.next = head;
        prev.next = null;
        return curr;
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
