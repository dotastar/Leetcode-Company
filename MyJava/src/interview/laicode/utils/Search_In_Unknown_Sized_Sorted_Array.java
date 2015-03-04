package interview.laicode.utils;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;

import org.junit.Test;

public class Search_In_Unknown_Sized_Sorted_Array {

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Search_In_Unknown_Sized_Sorted_Array.class);
	}

	public int search(Dictionary dict, int target) {
		int l = 0, r = 1;
		while (true) { // Find the possible range where target exists.
			if (dict.get(r) == null || dict.get(r) > target)
				break;
			else if (dict.get(r) == target)
				return r;
			l = r;
			r *= 2;
		}
		// Binary search between indices 2^(p - 1) and 2^p.;
		while (l <= r) {
			int mid = l + (r - l) / 2;
			// Key: search the left part if out-of-bound.
			if (dict.get(mid) == null || dict.get(mid) > target)
				r = mid - 1;
			else if (dict.get(mid) < target)
				l = mid + 1;
			else
				return mid;
		}
		return -1;
	}

	@Test
	public void test1() {
		Dictionary dict = new Dictionary();
		for (int i = 1; i <= 1005; i++)
			dict.add(i);
		int res = search(dict, 1000);
		assertTrue("Wrong answer: " + res, res == 999);
	}

	private static class Dictionary {
		private ArrayList<Integer> list;

		public Dictionary() {
			list = new ArrayList<Integer>();
		}

		public Integer get(int index) {
			if (index >= list.size())
				return null;
			else
				return list.get(index);
		}

		public void add(int value) {
			list.add(value);
		}
	}
}
