package interview.company.zenefits;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.TreeSet;

import org.junit.Test;

/**
 * Zenefits
 * Phone
 * 
 * Moving window min:
 * 
 * Input: A[N], K
 * Output: B[N], B = min(A, A[i+1], A[i+2],..., A[i+K-1])
 * 
 * Sample Input:
 * A[7] = {7, 5, 4, 3, 6, 7, 8}, K=3
 * 
 * Output:
 * B[7] = {4, 3, 3, 3, 6, 7, 8}
 * 
 * @author yazhoucao
 *
 */
public class MinimumInSlidingWindow {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(MinimumInSlidingWindow.class);
	}

	/**
	 * Time O(nlogk)
	 * Maintain a k-sized BST
	 */
	public int[] minWindow2(int[] A, int k) {
		TreeSet<Integer> bst = new TreeSet<>();
		int[] res = new int[A.length];
		int l = 0;
		for (int r = 0; r < A.length; r++) {
			if (r >= k) {
				res[l] = bst.first();
				bst.remove(A[l++]);
			}
			bst.add(A[r]);

		}
		while (l < A.length) {
			res[l] = bst.first();
			bst.remove(A[l++]);
		}
		return res;
	}

	/**
	 * Time O(n) solution:
	 * http://articles.leetcode.com/2011/01/sliding-window-maximum.html
	 * 
	 * Doubly linked-list - put the index of current min element in the head
	 * 
	 * The trick is to find a way such that the largest element in the window
	 * would always appear in the front of the queue.
	 * 
	 * Besides, you might notice that there are some redundant elements in the
	 * queue that we shouldn’t even consider about.
	 * 
	 * A natural way most people would think is to try to maintain the queue
	 * size the same as the window’s size. Try to break away from this thought
	 * and try to think outside of the box. Removing redundant elements and
	 * storing only elements that need to be considered in the queue is the key
	 * to achieve the efficient O(n) solution below.
	 * 
	 */
	public int[] minWindow(int[] A, int k) {
		Deque<Integer> Q = new LinkedList<>();
		for (int i = 0; i < k; i++) {
			while (!Q.isEmpty() && A[i] <= A[Q.getLast()])
				Q.pollLast();
			Q.add(i);
		}
		int[] res = new int[A.length];
		for (int i = 0; i < A.length; i++) {
			while (Q.peek() < i)
				Q.pollFirst(); // remove deprecated indices

			res[i] = A[Q.peek()]; // set current min

			if (i + k < A.length) {
				while (!Q.isEmpty() && A[i + k] <= A[Q.getLast()])
					Q.pollLast(); // remove all impossible result indices
				Q.add(i + k); // add new min
			}
		}
		return res;
	}

	@Test
	public void test1() {
		int[] A = { 7, 5, 4, 3, 6, 7, 8 };
		int k = 3;
		int[] res = minWindow(A, k);
		int[] ans = { 4, 3, 3, 3, 6, 7, 8 };
		assertTrue("result:" + Arrays.toString(res), Arrays.equals(ans, res));
	}

	@Test
	public void test2() {
		int[] A = { 1, 3, -1, -3, 5, 3, 6, 7 };
		int k = 3;
		int[] res = minWindow(A, k);
		int[] ans = { -1, -3, -3, -3, 3, 3, 6, 7 };
		assertTrue("result:" + Arrays.toString(res), Arrays.equals(ans, res));
	}
}
