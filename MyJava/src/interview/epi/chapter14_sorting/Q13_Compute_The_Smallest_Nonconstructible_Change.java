package interview.epi.chapter14_sorting;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Write a function which takes an array A of positive integers and returns the
 * smallest k such that k is not equal to the sum of subset of elements of A.
 * E.g. A = {1,1,5,10}, the smallest value of change which cannot be made is 3.
 * 
 * @author yazhoucao
 * 
 */
public class Q13_Compute_The_Smallest_Nonconstructible_Change {

	static Class<?> c = Q13_Compute_The_Smallest_Nonconstructible_Change.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(nlogn)
	 * 
	 * Idea:
	 * Suppose A can produce every amount up to and including V but not V+1.
	 * Then adding a new element u to A allows A to produce every amount up to
	 * V+u iff u <= V+1.
	 * 
	 * Reasoning:
	 * If u > V+1, then adding u to A precludes A from producing V+1 using u.
	 * Conversely, if u<= V+1, then we can produce V+u by using u and the
	 * existing elements of A to product V.
	 * 
	 */
	public int noncontructibleChange(int[] A) {
		Arrays.sort(A);
		int V = 0; // boundary
		for (int u : A) {
			if (u > V + 1)
				break;
			V += u;
		}
		return V + 1;
	}

	@Test
	public void test1() {
		int[] A = { 1, 1, 5, 10 };
		int res = noncontructibleChange(A);
		assertTrue(res == 3);
	}

	@Test
	public void tes21() {
		int[] A = { 21, 14, 5, 10 };
		int res = noncontructibleChange(A);
		assertTrue(res == 1);
	}

	@Test
	public void test3() {
		int[] A = { 11, 1, 1, 1 };
		int res = noncontructibleChange(A);
		assertTrue(res == 4);
	}

	@Test
	public void test4() {
		int[] A = { 4, 3, 1, 5, 2 };
		int res = noncontructibleChange(A);
		assertTrue(res == 16);
	}
}
