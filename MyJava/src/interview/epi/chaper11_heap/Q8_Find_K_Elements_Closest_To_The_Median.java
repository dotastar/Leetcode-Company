package interview.epi.chaper11_heap;

import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem 11.8
 * Design an O(n) time algorithm to compute the k elements closest to the median
 * of an array A.
 * 
 * @author yazhoucao
 * 
 */
public class Q8_Find_K_Elements_Closest_To_The_Median {
	static Class<?> c = Q8_Find_K_Elements_Closest_To_The_Median.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	public static List<Integer> findKClosestToMedian(List<Integer> A, int k) {
		// TODO to be continued
		
		return new ArrayList<>(A.subList(0, k));
	}
}
