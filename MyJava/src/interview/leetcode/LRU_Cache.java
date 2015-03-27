package interview.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Design and implement a data structure for Least Recently Used (LRU) cache. It
 * should support the following operations: get and set.
 * 
 * get(key) - Get the value (will always be positive) of the key if the key
 * exists in the cache, otherwise return -1.
 * 
 * set(key, value) - Set or insert the value if the key is not already present.
 * When the cache reached its capacity, it should invalidate the least recently
 * used item before inserting a new item.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class LRU_Cache {

	public static void main(String[] args) {
		// 3,[set(1,1),set(2,2),set(3,3),set(4,4),get(4),
		// get(3),get(2),get(1),set(5,5),get(1),get(2),get(3),get(4),get(5)]
		// Expected: [4,3,2,-1,-1,2,3,-1,5]

		LRUCache cache = new LRUCache(3);
		cache.set(1, 1); // 1
		cache.set(2, 2); // 2,1
		cache.set(3, 3); // 3,2,1
		cache.set(4, 4); // 4,3,2
		assert cache.get(4) == 4; // 4,3,2
		assert cache.get(3) == 3; // 3,4,2
		assert cache.get(2) == 2; // 2,3,4
		assert cache.get(1) == -1;
		cache.set(5, 5); // 5, 2, 3
		assert cache.get(1) == -1;
		assert cache.get(2) == 2; // 2 5 3
		assert cache.get(3) == 3; // 3 2 5
		assert cache.get(4) == -1;
		assert cache.get(5) == 5; // 5 3 2

		System.out.println("Test success!");
	}

	/**
	 * HashMap + Double linked list
	 * Create two dummy node head and tail, so we don't have to deal with the
	 * case when head/tail is null.
	 */
	public static class LRUCache {

		private Map<Integer, ListNode> cache;
		private ListNode head;
		private ListNode tail;
		private final int capacity;

		public LRUCache(int capacity) {
			this.capacity = capacity;
			cache = new HashMap<>();
			head = new ListNode(-1, -1);
			tail = new ListNode(-1, -1);
			head.next = tail;
			tail.prev = head;
		}

		/**
		 * 1.check if key exists
		 * 2.if exists, get the value
		 * 3.move the node to the head
		 * 4.return value
		 */
		public int get(int key) {
			if (!cache.containsKey(key))
				return -1;
			ListNode value = cache.get(key);
			deleteNodeFromList(value);
			insertToHead(value);
			return value.val;
		}

		/**
		 * 1.check if key exists
		 * 2.if exists, update the value in HashMap, move the node to the head
		 * 3.else, check if exceeds the capacity, if yes, delete the tail node,
		 * then, put the key, value into HashMap, insert the node after head
		 */
		public void set(int key, int value) {
			ListNode valueNode = cache.get(key);
			if (valueNode == null) { // insert
				if (cache.size() == capacity) {
					ListNode leastUsed = tail.prev;
					deleteNodeFromList(leastUsed);
					cache.remove(leastUsed.key);
				}
				valueNode = new ListNode(key, value);
				cache.put(key, valueNode);
			} else { // update
				valueNode.val = value;
				deleteNodeFromList(valueNode);
			}
			insertToHead(valueNode);
		}

		private void deleteNodeFromList(ListNode node) {
			// delete it from original list
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}

		private void insertToHead(ListNode node) {
			// connect with head.next
			node.next = head.next;
			head.next.prev = node;
			// connect with head
			node.prev = head;
			head.next = node;
		}

		public static class ListNode {
			int key;
			int val;
			ListNode prev;
			ListNode next;

			public ListNode(int key, int value) {
				this.key = key;
				this.val = value;
			}

			public String toString() {
				return "{ " + String.valueOf(key) + ", " + String.valueOf(val)
						+ " }";
			}
		}
	}
}
