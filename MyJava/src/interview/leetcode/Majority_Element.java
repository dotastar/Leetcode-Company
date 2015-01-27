package interview.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of size n, find the majority element. The majority element is
 * the element that appears more than ⌊ n/2 ⌋ times.
 * 
 * You may assume that the array is non-empty and the majority element always
 * exist in the array.
 * 
 * 
 * Follow up : find items occur certain times (e.g. appears 33%)
 * Solution see Q12_The_Heavy_Hitter_Problem of Chapter18 of EPI
 * 
 * @author yazhoucao
 * 
 */
public class Majority_Element {

	public static void main(String[] args) {

	}

	/**
	 * Hash, count the appear times of every number
	 * Time: O(n), Space: O(n)
	 */
	public int majorityElement(int[] num) {
		int majority = 0;
		Map<Integer, Integer> cnts = new HashMap<>();
		for (int n : num) {
			Integer cnt = cnts.get(n);
			if (cnt == null)
				cnt = new Integer(0);
			cnts.put(n, ++cnt);
			majority = (cnt > num.length / 2) ? n : majority;
		}
		return majority;
	}

	/**
	 * Sort, sort the array, the majority element must be the median.
	 * Time: O(nlgn), Space: O(1).
	 * 
	 * If array.length is odd, it's simple, length/2 is the median.
	 * Else length is even, then there are two cases:
	 * 1.median left == median right: 1233 3345, return either left or right.
	 * 2.median left != median right, then there are also two cases:
	 * a.majority is at left part: 1111 2245, if this is the case,
	 * then the first element (num[0]) must equal to the median left.
	 * b.majority is at right part: 1233 4444, if this is the case,
	 * then the last element (num[lenght-1]) must equal to the median right.
	 */
	public int majorityElement_Sort(int[] num) {
		Arrays.sort(num);
		if (num.length % 2 == 1)
			return num[num.length / 2];

		int left = (num.length / 2) - 1;
		int right = (num.length / 2);
		if (num[left] == num[right])
			return num[left];
		else if (num[left] == num[0])
			return num[left];
		else
			return num[right];
	}

	/**
	 * Moore voting algorithm: Time: O(n), Space: O(1)
	 * We maintain a current candidate and a counter initialized to 0. As we
	 * iterate the array, we look at the current element x:
	 * 1.If the counter is 0, we set the current candidate to x and the counter
	 * to 1
	 * 2.If the counter is not 0, we increment or decrement the counter based on
	 * whether x is the current candidate.
	 * 
	 * After one pass, the current candidate is the majority element.
	 */
	public int majorityElement_voting(int[] num) {
		int majIdx = 0, cnt = 1;
		for (int i = 1; i < num.length; i++) {
			cnt = num[i] == num[majIdx] ? cnt + 1 : cnt - 1;
			if (cnt == 0) {
				majIdx = i;
				cnt = 1;
			}
		}
		return num[majIdx];
	}
}
