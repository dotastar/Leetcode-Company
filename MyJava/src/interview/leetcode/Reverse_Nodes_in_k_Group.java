package interview.leetcode;

/**
 * Given a linked list, reverse the nodes of a linked list k at a time and
 * return its modified list.
 * 
 * If the number of nodes is not a multiple of k then left-out nodes in the end
 * should remain as it is.
 * 
 * You may not alter the values in the nodes, only nodes itself may be changed.
 * 
 * Only constant memory is allowed.
 * 
 * For example, Given this linked list: 1->2->3->4->5
 * 
 * For k = 2, you should return: 2->1->4->3->5
 * 
 * For k = 3, you should return: 3->2->1->4->5
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Reverse_Nodes_in_k_Group {

	public static void main(String[] args) {
		Reverse_Nodes_in_k_Group obj = new Reverse_Nodes_in_k_Group();
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		head.next.next.next.next.next = new ListNode(6);
		
		ListNode res = obj.reverseKGroup2(head, 3);
		while(res!=null){
			System.out.print(res.toString()+" -> ");
			res = res.next;
			if(res==null)
				System.out.println("null");
		}
	}
	
    public ListNode reverseKGroup2(ListNode head, int k) {
        if(k<2 || head==null) 
            return head;
        ListNode prehead = new ListNode(0);
        prehead.next = head;
        ListNode preK = prehead;
        ListNode curr = head;
        int i=1;
        while(curr!=null){
        	if(i%k==0){
        		preK = reverseKNodes(preK, curr.next);
        		curr = preK.next;
        	}else
        		curr = curr.next;
        	i++;
        	
        }
        return prehead.next;
    }
    
    private ListNode reverseKNodes(ListNode pre, ListNode end){
    	ListNode prev = pre;
    	ListNode curr = pre.next;
    	while(curr!=end){
    		ListNode next = curr.next;
    		curr.next = prev;
    		
    		prev = curr;
    		curr = next;
    	}
    	ListNode newpre = pre.next;
    	newpre.next = end;	//link tail
    	pre.next = prev;	//link head
    	return newpre;
    	
    }

	/**
	 * Time : 2n = O(n)
	 * 
	 */
	public ListNode reverseKGroup(ListNode head, int k) {
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		ListNode pre = prehead;
		ListNode current = head;
		int i=0;
		while(current!=null){
			i++;
			if(i%k==0){//reverse k-length
				pre = reverse(pre, current.next);
				current = pre.next;
			}else
				current = current.next;
		}		
		return prehead.next;
	}

	/**
	 * Reverse a link list between pre and next exclusively
	 * @param pre
	 * @param next
	 * @return the tail of reversed list
	 */
	public ListNode reverse(ListNode pre, ListNode next) {
		ListNode tail = pre.next;	//first will be the tail
		ListNode p = tail.next;
		while (p!=next) {
			//when this end, tail points to next, 
			//it also caches the next node of p
			tail.next = p.next; 	
			p.next = pre.next;
			//when this end, pre points to head (was the tail)
			pre.next = p;
			p = tail.next;			
		}
		return tail;
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
