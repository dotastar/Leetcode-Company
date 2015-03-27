package interview.laicode;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Deep Copy Skip List
 * Fair
 * Data Structure
 * 
 * A Skip List is a special type of linked list, where each of the nodes has a
 * forward pointer to another node in the front and forward pointers are
 * guaranteed to be in non-descending order.
 * 
 * Make a deep copy of the original skip list.
 * 
 * @author yazhoucao
 * 
 */
public class Deep_Copy_Skip_List {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Use HashMap to store the corresponding relationship
	 */
	public SkipListNode copy2(SkipListNode head) {
		Map<SkipListNode, SkipListNode> visited = new HashMap<>();
		SkipListNode curr = head;
		SkipListNode prehead = new SkipListNode(0);
		SkipListNode copy = prehead;
		while (curr != null) {
			if (visited.containsKey(curr)) {
				copy.next = visited.get(curr);
			} else {
				copy.next = new SkipListNode(curr.value);
				visited.put(curr, copy);
			}
			copy = copy.next;

			if (visited.containsKey(curr.forward))
				copy.forward = visited.get(curr.forward);
			else if (curr.forward != null) {
				copy.forward = new SkipListNode(curr.forward.value);
				visited.put(curr.forward, copy.forward);
			}

			curr = curr.next;
		}
		
		return prehead.next;
	}

	/**
	 * 1.copy original linked list without forward pointer
	 * 2.then copy the forward pointer
	 * 3.break the copied linked list
	 */
	public SkipListNode copy(SkipListNode head) {
		SkipListNode curr = head;
		// copy original linked list without forward pointer
		// by inserting new copied node after the current node
		while (curr != null) {
			SkipListNode copy = new SkipListNode(curr.value);
			copy.next = curr.next;
			curr.next = copy;
			curr = copy.next;
		}

		// copy the forward pointer
		curr = head;
		while (curr != null) {
			SkipListNode copy = curr.next;
			if (curr.forward != null)
				copy.forward = curr.forward.next;
			curr = copy.next;
		}

		// break the copied linked list
		SkipListNode prehead = new SkipListNode(0);
		SkipListNode currCopy = prehead;
		curr = head;
		while (curr != null) {
			currCopy.next = curr.next;
			currCopy = currCopy.next;
			curr.next = curr.next.next;
			curr = curr.next;
		}
		return prehead.next;
	}

	public static class SkipListNode {
		public int value;
		public SkipListNode next;
		public SkipListNode forward;

		public SkipListNode(int value) {
			this.value = value;
		}
	}
}
