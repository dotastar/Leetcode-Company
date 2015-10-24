package interview.epi.chapter9_stack_queue;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

/**
 * Problem 9.9
 * Design an algorithm to sort a stack S in descending order. The only
 * operations allowed are push, pop, top(peek), and isEmpty. You cannot
 * explicitly allocate memory outside of a few words.
 * 
 * @author yazhoucao
 * 
 */
public class Q9_Sort_Stack {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Q9_Sort_Stack.class);
	}

	/**
	 * Time: O(n^2)
	 */
	public static void sort(LinkedList<Integer> S) {
		LinkedList<Integer> sorted = new LinkedList<>();
		while (!S.isEmpty())
			insert(sorted, S.pop()); // it's ascending order finally
		System.out.println(sorted.toString());
	}

	private static void insert(LinkedList<Integer> S, Integer ele) {
		LinkedList<Integer> tmp = new LinkedList<>();
		while (!S.isEmpty() && S.peek() < ele)
			tmp.push(S.pop());
		S.push(ele);
		while (!tmp.isEmpty())
			S.push(tmp.pop());
	}

	@Test
	public void test0() {
		Random r = new Random();
		for (int times = 0; times < 10; ++times) {
			int n = r.nextInt(1000) + 1;
			LinkedList<Integer> S = new LinkedList<>();
			for (int i = 0; i < n; i++) {
				S.push(r.nextInt(1000000));
			}
			sort(S);
			int pre = Integer.MAX_VALUE;
			while (!S.isEmpty()) {
				assertTrue(pre >= S.peek());
				System.out.println(S.peek());
				pre = S.pop();
			}
		}
	}

}
