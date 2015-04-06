package interview.laicode;

import java.util.HashMap;
import java.util.Map;

/**
 * Implement LRU Cache
 * Fair
 * Data Structure
 * 
 * Implement a least recently used cache. It should provide set(), get()
 * operations. If not exists, return null (Java), false (C++).
 * 
 * @author yazhoucao
 * 
 */
class Implement_LRU_Cache {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static class Solution<K, V> {

		int capacity;
		ListNode<K, V> head = new ListNode<K, V>(null, null);
		ListNode<K, V> tail = new ListNode<K, V>(null, null);
		Map<K, ListNode<K, V>> cache = new HashMap<>();

		public Solution(int limit) {
			capacity = limit;
			head.next = tail;
			tail.prev = head;
		}

		/**
		 * check if exists, if yes, update its value, then move ListNode<K, V>
		 * to head
		 * if no, check if the cache is full, if full, delete the least recently
		 * used Node in List and Map,
		 * then create a new node, insert it in the Map, and move/insert
		 * ListNode<K, V> to head
		 */
		public void set(K key, V value) {
			ListNode<K, V> valNode = null;
			if (cache.containsKey(key)) { // update
				valNode = cache.get(key);
				valNode.value = value;
				deleteFromList(valNode);
			} else { // insert
				if (cache.size() == capacity) {
					ListNode<K, V> leastUsed = tail.prev;
					deleteFromList(leastUsed);
					cache.remove(leastUsed.key);
				}
				valNode = new ListNode<K, V>(key, value);
				cache.put(key, valNode);
			}
			insertToHead(valNode);
		}

		/**
		 * check if exists, if no return null,
		 * if yes, move ListNode<K, V> to head, return value.
		 */
		public V get(K key) {
			V value = null;
			if (cache.containsKey(key)) {
				ListNode<K, V> valNode = cache.get(key);
				deleteFromList(valNode);
				insertToHead(valNode);
				value = valNode.value;
			}
			return value;
		}

		private void deleteFromList(ListNode<K, V> node) {
			ListNode<K, V> p = node.prev, n = node.next;
			p.next = n;
			n.prev = p;
			node.prev = null;
			node.next = null;
		}

		private void insertToHead(ListNode<K, V> node) {
			node.next = head.next;
			head.next.prev = node;
			node.prev = head;
			head.next = node;
		}

		public static class ListNode<K, V> {
			K key;
			V value;
			ListNode<K, V> next;
			ListNode<K, V> prev;

			public ListNode(K key, V value) {
				this.key = key;
				this.value = value;
			}
		}
	}

}
