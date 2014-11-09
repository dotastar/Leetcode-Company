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
		assert cache.get(4) == 4;  // 4,3,2
		assert cache.get(3) == 3; // 3,4,2
		assert cache.get(2) == 2; // 2,3,4
		assert cache.get(1) == -1;
		cache.set(5, 5); // 5, 2, 3
		assert cache.get(1) == -1;
		assert cache.get(2) == 2; //2 5 3
		assert cache.get(3) == 3; //3 2 5
		assert cache.get(4) == -1;
		assert cache.get(5) == 5; //5 3 2
		
		System.out.println("Test success!");
	}

	/**
	 * HashMap + Double linked list
	 * Use prehead, so we don't have to deal with the case when head is null.
	 * @author yazhoucao
	 */
	public static class LRUCache2 {
		private Map<Integer, LinkedNode> map;
		private int capacity;
		private LinkedNode prehead;
		private LinkedNode tail;

		public LRUCache2(int capacity) {
			map = new HashMap<Integer, LinkedNode>();
			this.capacity = capacity;
			prehead = new LinkedNode(-999, -999);
			tail = prehead;
		}

		public int get(int key) {
			if (map.containsKey(key)) {
				LinkedNode node = map.get(key);
				switchToHead(node);
				return node.val;
			} else
				return -1;
		}

		public void set(int key, int value) {
			LinkedNode node;
			if (map.containsKey(key)) { // modified
				node = map.get(key);
				node.val = value;
				switchToHead(node);
			} else { // insert
				node = new LinkedNode(key, value);
				map.put(key, node);
				insertToHead(node);

				if (map.size() > capacity) { // exceed the capacity
					map.remove(tail.key); // delete
					tail = tail.prev;
				}
			}
		}

		private void switchToHead(LinkedNode node) {
			if (node == null || node.prev.equals(prehead))
				return; // it is head already

			if (node.equals(tail))
				tail = tail.prev;

			node.prev.next = node.next; // link previous to next
			if (node.next != null) // link next to previous
				node.next.prev = node.prev;

			insertToHead(node); // move current to the head
		}

		private void insertToHead(LinkedNode node) {
			if (node == null)
				return;
			if (prehead.next != null) // link old head to current
				prehead.next.prev = node;
			else
				tail = node;	//first time insert

			node.next = prehead.next;
			node.prev = prehead;
			prehead.next = node;
		}

	}

	public static class LinkedNode {
		int key;
		int val;
		LinkedNode prev;
		LinkedNode next;

		public LinkedNode(int key, int value) {
			this.key = key;
			this.val = value;
		}

		public String toString() {
			return Integer.toString(val);
		}
	}
	
	
	/**
	 * Second time practice, same solution
	 * 
	 * @author yazhoucao
	 *
	 */
	public static class LRUCache {
	    private Map<Integer, Node> cache;
	    private Node head;
	    private Node tail;
	    int capacity;
	    
	    public LRUCache(int capacity) {
	        this.capacity = capacity;
	        cache = new HashMap<Integer, Node>(capacity);
	    }
	    
	    public int get(int key) {
	        Node node = cache.get(key);
	        if(node==null)
	            return -1;
	        switchToHead(node);
	        printState();
	        return node.val;
	    }
	    
	    public void set(int key, int value) {
	        if(cache.containsKey(key)){  //update
	            Node node = cache.get(key);
	            node.val = value;
	            switchToHead(node);
	        }else{  //insert
	            Node node = new Node(key, value);
	            cache.put(key, node);
	            if(cache.size()<=capacity){  //insert directly
	                if(head==null){
	                    head = node;
	                    tail = node;
	                }else
	                    insertToHead(node);
	            }else{  //delete first then insert
	            	cache.remove(tail.key);
	                tail = tail.prev;
	                if(tail!=null)
	                	tail.next = null;
	                insertToHead(node);
	            }
	            assert cache.get(key)!=null;
	        }
	        printState();
	    }
	    
	    private void insertToHead(Node node){
	        if(node==null)
	            return;
	        node.next = head; //node->head
	        head.prev = node; //node<-head
	        head = node;
	    }
	    
	    private void switchToHead(Node node){
	        if(node==null || node.equals(head))
	            return;
	        Node prev = node.prev;
	        prev.next = node.next;  //prev->next
	        if(node.next!=null)
	            node.next.prev = prev; //prev<-next
	        else    
	            tail = prev;    //node must be the tail, update tail
	        node.next = head;   //node->head
	        head.prev = node;   //node<-head
	        head = node;
	    }
	    
	    public void printState(){
	    	Node p = head;
	    	while(!p.equals(tail)){
	    		System.out.print(p.val+"->");
	    		p = p.next;
	    	}
	    	System.out.println(p.val);
	    }
	    
	    public static class Node{
	        int key;
	    	int val;
	        Node next, prev;
	        
	        public Node(int key, int val){
	        	this.key = key;
	            this.val = val;
	        }
	    }
	}
}
