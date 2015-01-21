package interview.epi.chapter6_array;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Problem 6.1
 * Write a function that takes an array A of length n and an index i into A, and
 * rearranges the elements such that all elements less than A[i] appear first,
 * followed by elements equal to A[i], followed by elements greater than A[i].
 * Your algorithm should have O(1) space complexity and O(n) time complexity.
 * 
 * @author yazhoucao
 * 
 */
public class Q1_The_Dutch_National_Flag_Problem {

	static String methodName = "threeWaySort";
	static Class<?> c = Q1_The_Dutch_National_Flag_Problem.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	public static void dutchFlagPartition(int[] A, int idx) {
		int pivot = A[idx];
		int lt = 0;
		int gt = A.length - 1;
		int i = 0;
		while (i <= gt) {
			if (A[i] < pivot)
				swap(A, i++, lt++);
			else if (A[i] > pivot)
				swap(A, i, gt--);
			else
				i++;
		}
	}

	/**
	 * Three way quick-sort
	 * 
	 * Time: Worst O(n^2), Average O(lgn)
	 * Space: O(lgn)
	 * 
	 * Partition the array into three parts, one each for items with keys
	 * smaller than, equal to, and larger than the partitioning item's key.
	 */
	public static void threeWaySort(int[] A) {
		threeWaySort(A, 0, A.length - 1);
	}

	public static void threeWaySort(int[] A, int l, int r) {
		if (l >= r)
			return;
		int lt = l; // less than pivot
		int gt = r; // greater than pivot
		int pivot = A[l];
		int i = l + 1;
		while (i <= gt) {
			if (A[i] < pivot)
				swap(A, i++, lt++);
			else if (A[i] > pivot)
				swap(A, i, gt--);
			else
				i++;
		}
		threeWaySort(A, l, lt - 1); // note right boundary -1
		threeWaySort(A, gt + 1, r); // note left boundary -1
		assert isSorted(A, l, r);
	}

	private static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}

	private static boolean isSorted(int[] A) {
		return isSorted(A, 0, A.length - 1);
	}

	private static boolean isSorted(int[] A, int l, int r) {
		for (int i = l; i < r - 1; i++) {
			if (A[i] > A[i + 1])
				return false;
		}
		return true;
	}

	/****************** Unit Test For Sorting ******************/

	public void invokeMethod(Method m, int[] A) {
		try {
			m.invoke(null, A);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = {};
			invokeMethod(m, A);
			assertTrue(m.getName(), isSorted(A));
		}
	}

	@Test
	public void test2() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 10, -1, 3, -6, 5 };
			invokeMethod(m, A);
			assertTrue(m.getName(), isSorted(A));
		}
	}

	@Test
	public void test3() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 2, 5, 1, 2, 3, 4, 2, 8, 2, 3, 1, 3, 4, 6, 1, 3 };
			invokeMethod(m, A);
			assertTrue(m.getName(), isSorted(A));
		}
	}

	@Test
	public void test4() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3 };
			invokeMethod(m, A);
			assertTrue(m.getName(), isSorted(A));
		}
	}

	@Test
	public void test5() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = {};
			invokeMethod(m, A);
			assertTrue(m.getName(), isSorted(A));
		}
	}

}
