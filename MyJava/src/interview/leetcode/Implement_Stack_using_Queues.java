package interview.leetcode;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

/**
 * Implement the following operations of a stack using queues.
 * 
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * empty() -- Return whether the stack is empty.
 * 
 * Notes:
 * 
 * You must use only standard operations of a queue -- which means only push to
 * back, peek/pop from front, size, and is empty operations are valid.
 * Depending on your language, queue may not be supported natively. You may
 * simulate a queue by using a list or deque (double-ended queue), as long as
 * you use only standard operations of a queue.
 * You may assume that all operations are valid (for example, no pop or top
 * operations will be called on an empty stack).
 * 
 * @author yazhoucao
 *
 */
public class Implement_Stack_using_Queues {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Implement_Stack_using_Queues.class);
	}

	static class MyStack {
		Queue<Integer> data;
		Queue<Integer> buffer;

		public MyStack() {
			data = new LinkedList<>();
			buffer = new LinkedList<>();
		}

		// Push element x onto stack.
		public void push(int x) {
			while (!data.isEmpty())
				buffer.add(data.poll());
			data.add(x);
			while (!buffer.isEmpty())
				data.add(buffer.poll());
		}

		// Removes the element on top of the stack.
		public void pop() {
			data.poll();
		}

		// Get the top element.
		public int top() {
			return data.peek();
		}

		// Return whether the stack is empty.
		public boolean empty() {
			return data.isEmpty();
		}
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
