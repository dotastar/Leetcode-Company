package interview.company.others;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.TreeSet;

import org.junit.Test;

public class ImplementMedianStack {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(ImplementMedianStack.class);
	}

	/**
	 * Assume input has no duplicates,
	 * when the size is a even number, median is n/2
	 * 
	 * @author yazhoucao
	 *
	 */
	public static class MedianStack {

		private Stack<Integer> stk;
		private TreeSet<Integer> bst;
		private Integer median;

		public MedianStack() {
			stk = new Stack<>();
			bst = new TreeSet<>();
			median = 0;
		}

		public void push(int ele) {
			bst.add(ele);
			stk.push(ele);
			int size = stk.size() - 1; // size before push
			if (size == 0) {
				median = ele;
			} else {
				if (size % 2 == 1 && ele > median) {
					median = bst.ceiling(median + 1);
				} else if (size % 2 == 0 && ele < median) {
					median = bst.floor(median - 1);
				}
			}

		}

		public int pop() {
			if (stk.isEmpty())
				throw new IndexOutOfBoundsException();
			int size = stk.size(); // size before pop
			int ele = stk.pop();
			if (size % 2 == 1 && ele <= median) {
				median = bst.ceiling(median + 1);
			} else if (size % 2 == 0 && ele >= median) {
				median = bst.floor(median - 1);
			}
			bst.remove(ele);
			return ele;
		}

		public int median() {
			if (stk.isEmpty())
				throw new NoSuchElementException();
			return median;
		}
	}

	@Test
	public void test1() { // ordered
		int[] data = { 1, 2, 3, 4, 5, 6, 7 };
		MedianStack mstk = new MedianStack();
		for (int i = 0; i < data.length; i++) {
			mstk.push(data[i]);
			int res = mstk.median();
			int ans = getMedian(mstk);
			assertTrue(res + " != " + ans, res == ans);
		}

		assertTrue(mstk.stk.size() == mstk.bst.size());
		while (!mstk.stk.isEmpty()) {
			int res = mstk.median();
			int ans = getMedian(mstk);
			assertTrue(res + " != " + ans, res == ans);
			mstk.pop();
		}
		assertTrue(mstk.stk.size() == mstk.bst.size());
	}

	@Test
	public void test2() { // unordered data set
		int[] data = { 3, 5, 2, 4, 1, 8, 6, 7, 0 };
		MedianStack mstk = new MedianStack();
		for (int i = 0; i < data.length; i++) {
			mstk.push(data[i]);
			int res = mstk.median();
			int ans = getMedian(mstk);
			assertTrue(res + " != " + ans, res == ans);
		}

		assertTrue(mstk.stk.size() == mstk.bst.size());
		while (!mstk.stk.isEmpty()) {
			int res = mstk.median();
			int ans = getMedian(mstk);
			assertTrue(res + " != " + ans, res == ans);
			mstk.pop();
		}
		assertTrue(mstk.stk.size() == mstk.bst.size());
	}

	public int getMedian(MedianStack stk) {
		List<Integer> data = new ArrayList<>(stk.stk);
		Collections.sort(data);
		return data.get(data.size() / 2);
	}
}
