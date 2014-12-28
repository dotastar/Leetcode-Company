package interview.epi.chapter9_stack_queue;

import static org.junit.Assert.assertTrue;
import general.datastructure.MaxQueue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Problem 9.13
 * Implement a queue with a max() API.
 * 
 * @author yazhoucao
 * 
 */
public class Q13_Implement_A_Queue_With_Max_API {
	static Class<?> c = Q13_Implement_A_Queue_With_Max_API.class;

	/**
	 * The implementation is at general/datastructure/MaxQueue.java
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	@Test
	public void test1() {
		MaxQueue Q = new MaxQueue();
		Q.enqueue(1);
		Q.enqueue(2);
		assertTrue(2 == Q.max());
		assertDequeue(Q, 1);
		assertTrue(2 == Q.max());
		assertDequeue(Q, 2);
		Q.enqueue(3);
		assertTrue(3 == Q.max());
		assertDequeue(Q, 3);

		Q.enqueue(4);
		assertTrue(4 == Q.max());
		Q.enqueue(1);
		Q.enqueue(2);

		assertTrue(4 == Q.max());
		Q.dequeue();
		assertTrue(2 == Q.max());
		Q.dequeue();
		assertTrue(2 == Q.max());

		try {
			Q.max();
		} catch (RuntimeException e) {
			System.out.println(e.getMessage() + " -> as expected.");
		}
		try {
			Q.dequeue();
		} catch (RuntimeException e) {
			System.out.println(e.getMessage() + " -> as expected.");
		}
	}

	private static void assertDequeue(MaxQueue q, int t) {
		int dequeue = q.dequeue();
		assertTrue(t == dequeue);
	}
}
