package interview.epi.chapter9_stack_queue;

import static org.junit.Assert.assertTrue;
import general.datastructure.Queue_Array;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Problem 9.11
 * Implement a queue API using an array for storing elements. your API should
 * include a constructor function, which takes as argument the capacity of the
 * queue, enqueue and dequeue functions, a size function, which returns the
 * number of elements stored, and implement dynamic resizing.
 * 
 * @author yazhoucao
 * 
 */
public class Q11_Implement_A_Circular_Queue {
	static Class<?> c = Q11_Implement_A_Circular_Queue.class;

	/**
	 * Implementation is at general/datastructure/Queue_Array.java
	 * @param args
	 */
	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	@Test
	public void test1() {
		Queue_Array<Integer> q = new Queue_Array<Integer>(8);
		q.offer(1);
		q.offer(2);
		q.offer(3);
		q.offer(4);
		q.offer(5);
		q.offer(6);
		q.offer(7);
		q.offer(8);
		// Now head = 0 and tail = 0

		assertDequeue(q, 1);
		assertDequeue(q, 2);
		assertDequeue(q, 3);
		// Now head = 3 and tail = 0

		q.offer(11);
		q.offer(12);
		q.offer(13);
		// Ok till here. Now head = 3 and tail = 3

		q.offer(14); // now the vector (data) is resized; but the head and
						// tail. (or elements) does not change accordingly.
		q.offer(15);
		q.offer(16);
		q.offer(17);
		q.offer(18);
		// The elements starting from head=3 are overwriten!

		assertDequeue(q, 4);
		assertDequeue(q, 5);
		assertDequeue(q, 6);
		assertDequeue(q, 7);
		assertDequeue(q, 8);
		assertDequeue(q, 11);
		assertDequeue(q, 12);
	}

	private static <T> void assertDequeue(Queue_Array<T> q, T t) {
		T dequeue = q.poll();
		assertTrue(t.equals(dequeue));
	}

	@Test
	public void test2() { // test size
		Queue_Array<Integer> q = new Queue_Array<>(10);
		for (int i = 1; i < 8; i++) {
			q.offer(i);
			assertTrue(q.size() == i);
		}
	}

	@Test
	public void test3() { // test enqueue, dequeue
		Queue_Array<Integer> q = new Queue_Array<>(10);
		for (int i = 0; i < 8; i++)
			q.offer(i);
		assertTrue(q.size() == 8);
		
		for (int i = 0; i < 3; i++)
			assertTrue(q.poll() == i);
		assertTrue(q.size() == 5);
	}

	@Test
	public void test4() { // test resize
		Queue_Array<Integer> q = new Queue_Array<>(4);
		for (int i = 0; i < 16; i++)
			q.offer(i);
		assertTrue(q.size() == 16);
		
		for(int i=0; i<16; i++)
			q.poll();
		assertTrue(q.size()==0);
	}

}
