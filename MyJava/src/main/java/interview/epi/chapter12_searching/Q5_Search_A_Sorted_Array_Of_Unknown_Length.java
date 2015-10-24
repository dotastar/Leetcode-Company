package interview.epi.chapter12_searching;

import interview.AutoTestUtils;

import java.util.List;

/**
 * Let A be a sorted array. The length of A is not known in advance, accessing
 * A[i] for i beyond the end of the array throws an exception.
 * 
 * @author yazhoucao
 * 
 */
public class Q5_Search_A_Sorted_Array_Of_Unknown_Length {

	static Class<?> c = Q5_Search_A_Sorted_Array_Of_Unknown_Length.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Find a possible boundary of A, and then binary search,
	 * if catch a exception, then search left.
	 */
	public int bsearch(List<Integer> A, int k) {
		// Find the possible range where k exists.
		int p = 0;
		while (true) {
			try {
				int val = A.get((1 << p) - 1);
				if (val == k) {
					return (1 << p) - 1;
				} else if (val > k) {
					break;
				}
			} catch (Exception e) {
				break;
			}
			++p;
		}
		// Binary search between indices 2^(p - 1) and 2^p - 2.
		// Need max below in case k is smaller than all entries
		// in A, since p becomes 0.
		int l = max(0, 1 << (p - 1)), r = (1 << p) - 2;
		while (l <= r) {
			int m = l + ((r - l) >> 1);
			try {
				int val = A.get(m);
				if (val == k) {
					return m;
				} else if (val > k) {
					r = m - 1;
				} else { // A[m] < k
					l = m + 1;
				}
			} catch (Exception e) {
				r = m - 1; // search the left part if out of boundary.
			}
		}
		return -1; // nothing matched k.
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}
}
