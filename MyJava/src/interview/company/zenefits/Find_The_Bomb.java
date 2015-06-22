package interview.company.zenefits;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Zenefits
 * OA
 * 
 * Given a sorted array of positive integers, write a funciton that returns true
 * if the array contains a bomb, and false otherwise.
 * A bomb is defined as 3 (or more) consecutive pairs of integers.
 * 
 * Some examples:
 * 1 2 2 3 3 4 5 5 --> no bombs
 * 1 2 2 3 3 4 4 5 --> yes, exists
 * 1 1 2 2 4 4 --> no bombs
 * 1 2 2 3 3 3 3 3 3 3 4 4 5 --> yes, eixsts
 * 
 * @author yazhoucao
 *
 */
public class Find_The_Bomb {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Find_The_Bomb.class);
	}

	/**
	 * Keep a cnt records the count of current number,
	 * a length stands for the length of consecutive numbers whose cnt >= 2,
	 * Go through the array, see if arr[i] == arr[i - 1]
	 * Two major cases:
	 * 1.arr[i] == arr[i-1] --> cnt++
	 * 2.arr[i] != arr[i-1] --> two sub cases:
	 * a.arr[i] == arr[i-1] + 1, consecutive -->
	 * length = cnt >= 2 ? length + 1 : 0, and reset cnt = 1;
	 * b.not consecutive --> length = 0, and reset cnt = 1;
	 */
	boolean contains_bomb(int[] arr) {
		int length = 0, cnt = 1;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] == arr[i - 1]) {
				cnt++;
			} else { // arr[i] != arr[i-1]
				if (arr[i] == arr[i - 1] + 1) { // consecutive
					length = cnt >= 2 ? length + 1 : 0;
				} else { // not consecutive, reset length
					length = 0;
				}
				cnt = 1; // reset cnt
			}
			if (length >= 3)
				return true;
		}
		if (cnt >= 2)
			length++;
		return length >= 3;
	}

	@Test
	public void test0() {
		int[] arr = { 1, 2 };
		assertTrue(!contains_bomb(arr));
	}

	@Test
	public void test1() {
		int[] arr = { 1, 2, 3, 3, 4, 4, 5, 5 };
		assertTrue(contains_bomb(arr));
	}

	@Test
	public void test2() {
		int[] arr = { 1, 2, 3, 3, 4, 5, 5 };
		assertTrue(!contains_bomb(arr));
	}

	@Test
	public void test3() {
		int[] arr = { 1, 2, 3, 3, 4, 5, 5, 6, 6, 7, 7 };
		assertTrue(contains_bomb(arr));
	}

	@Test
	public void test4() {
		int[] arr = { 1, 2, 3, 3, 4, 5, 5, 6, 6, 7 };
		assertTrue(!contains_bomb(arr));
	}

	@Test
	public void test5() {
		int[] arr = { 1, 2, 3, 3, 4, 5, 5, 6, 6, 8, 8, 8 };
		assertTrue(!contains_bomb(arr));
	}

	@Test
	public void test6() {
		int[] arr = { 1, 2, 3, 3, 5, 5, 5, 6, 6, 6, 7 };
		assertTrue(!contains_bomb(arr));
	}

	@Test
	public void test7() {
		int[] arr = { 1, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 5 };
		assertTrue(contains_bomb(arr));
	}

}
