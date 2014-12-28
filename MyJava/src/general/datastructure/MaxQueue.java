package general.datastructure;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Use a dequeue to store the max information
 * 
 * @author yazhoucao
 * 
 */
public class MaxQueue {
	private Queue<Integer> q;
	private Deque<Integer> max;

	public MaxQueue() {
		q = new LinkedList<Integer>();
		max = new LinkedList<Integer>();
	}

	/**
	 * Iteratively eject the element at the tail till the element is greater
	 * than or equal to the new element 'ele'. Then append ele to the max queue.
	 * 
	 * Time: O(1) amortized, since every element can be added and removed at
	 * most once.
	 */
	public void enqueue(int ele) {
		q.add(ele);
		while (!max.isEmpty() && ele > max.peekLast())
			max.pollLast();
		max.add(ele);
	}

	/**
	 * If the dequeued element equals to the current max element, then dequeue
	 * the max as well.
	 * 
	 * Time: O(1)
	 */
	public int dequeue() {
		if (q.isEmpty())
			throw new RuntimeException("Empty queue!");
		int res = q.poll();
		if (res == max.peek())
			max.poll();
		return res;
	}

	public int max() {
		if (q.isEmpty())
			throw new RuntimeException("Empty queue!");
		return max.peek();
	}
}
