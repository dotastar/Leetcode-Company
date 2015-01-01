package interview.epi.chaper11_heap;

import static org.junit.Assert.*;
import interview.AutoTestUtils;
import interview.epi.utils.Pair;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Test;

/**
 * Problem 11.10
 * Implement stack and queue using heaps.
 * 
 * @author yazhoucao
 * 
 */
public class Q10_Implement_Stack_And_Queue_Using_Heaps {
	static Class<?> c = Q10_Implement_Stack_And_Queue_Using_Heaps.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * The key is using another int variable 'order' to maintain the order.
	 * 
	 * @author yazhoucao
	 */
	public static class Stack<T> {
		private PriorityQueue<Pair<Integer, T>> maxHeap;
		private int order;

		public Stack() {
			order = 0;
			// java default is min-heap
			maxHeap = new PriorityQueue<>(new Comparator<Pair<Integer, T>>() {
				@Override
				public int compare(Pair<Integer, T> o1, Pair<Integer, T> o2) {
					return o2.getFirst() - o1.getFirst();
				}
			});
		}

		public void push(T t) {
			maxHeap.add(new Pair<Integer, T>(order++, t));
		}

		public T pop() {
			order--;
			return maxHeap.poll().getSecond();
		}

		public T peek() {
			return maxHeap.peek().getSecond();
		}
	}
	
	
	public static class Queue<T> {
		private PriorityQueue<Pair<Integer, T>> maxHeap;
		private int order;

		public Queue() {
			order = 0;
			maxHeap = new PriorityQueue<>(new Comparator<Pair<Integer, T>>() {
				@Override
				public int compare(Pair<Integer, T> o1, Pair<Integer, T> o2) {
					return o2.getFirst() - o1.getFirst();
				}
			});
		}

		public void enqueue(T t) {
			maxHeap.add(new Pair<Integer, T>(order--, t));
		}

		public T dequeue() {
			return maxHeap.poll().getSecond();
		}

		public T head() {
			return maxHeap.peek().getSecond();
		}

	}

	/****************** Unit Test ******************/

	@Test
	public void test1() {
		Stack<Integer> s = new Stack<Integer>();
		s.push(1);
		s.push(2);
		s.push(3);
		assertTrue(s.peek().equals(3));
		s.pop();
		assertTrue(s.peek().equals(2));
		s.pop();
		s.push(4);
		assertTrue(s.peek().equals(4));
		s.pop();
		s.pop();
		try {
			s.pop();
		} catch (Exception e) {
			System.out.println("empty stack");
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void test2() {
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(1);
		q.enqueue(2);
		assertTrue(q.head().equals(1));
		q.dequeue();
		assertTrue(q.head().equals(2));
		q.dequeue();
		try {
			q.dequeue();
		} catch (Exception e) {
			System.out.println("empty queue");
			System.out.println(e.getMessage());
		}
	}
}
