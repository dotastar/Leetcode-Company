package interview.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * A linked list is given such that each node contains an additional random
 * pointer which could point to any node in the list or null.
 * 
 * Return a deep copy of the list.
 * 
 * @author yazhoucao
 * 
 */
public class Copy_List_with_Random_Pointer {

	public static void main(String[] args) {
		RandomListNode h = new RandomListNode(-1);
		h.next = new RandomListNode(-1);
		h.random = h;

		RandomListNode res = copyRandomList_Improved(h);
		while (res != null) {
			System.out.print(res.toString() + "->");
			res = res.next;
		}
		System.out.println("null");
	}

	/**
	 * Thought: Using hashmap to store the correspondence between old node and
	 * new copy node, therefore the node pointed by random pointer can be copied
	 * easily by getting the the corresponding new node via the map.
	 * 
	 * Time: O(n), Space: O(n)
	 * 
	 * @param head
	 * @return
	 */
	public static RandomListNode copyRandomList(RandomListNode head) {
		if (head == null)
			return null;

		Map<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
		RandomListNode pre = new RandomListNode(0);
		RandomListNode p = head;

		while (p != null) { // copy a normal linked list
			pre.next = new RandomListNode(p.label);
			map.put(p, pre.next);
			p = p.next;
			pre = pre.next;
		}

		p = head;
		RandomListNode copy = map.get(head);
		while (p != null) { // copy random link to newlist
			copy.random = map.get(p.random);
			p = p.next;
			copy = copy.next;
		}

		return map.get(head);
	}

	/**
	 * We can solve this problem by doing the following steps:
	 * 
	 * 1. copy every node, i.e., duplicate every node, and insert it to the list
	 * 
	 * 2. copy random pointers for all newly created nodes
	 * 
	 * 3. break the list to two
	 * 
	 * Time: O(n), Space: O(1)
	 * 
	 * Note: step 2 and 3 can't be combined to one step, because the random
	 * pointer could point to a previous node, if do copying and breaking the
	 * list simultaneously in such a case, the random pointer will point to the
	 * node in original list instead of the node in copy list cause the previous
	 * nodes are all broke.
	 * 
	 */
	public static RandomListNode copyRandomList_Improved(RandomListNode head) {
		RandomListNode curr = head;
		while (curr != null) { // create copy, insert them between old nodes
			RandomListNode copy = new RandomListNode(curr.label);
			copy.next = curr.next;
			curr.next = copy;
			curr = copy.next;
		}

		curr = head;
		while (curr != null) { // copy random pointer
			RandomListNode copy = curr.next;
			if (curr.random != null)
				copy.random = curr.random.next;
			curr = copy.next;
		}

		curr = head;
		RandomListNode pre = new RandomListNode(0);
		RandomListNode copy = pre;
		while (curr != null) { // break the list to a copy and the original list
			copy.next = curr.next;
			copy = copy.next;
			curr.next = copy.next;
			curr = curr.next;
		}
		return pre.next;
	}

	public static class RandomListNode {
		int label;
		RandomListNode next, random;

		RandomListNode(int x) {
			this.label = x;
		}

		public String toString() {
			return Integer.toString(label);
		}
	}
}
