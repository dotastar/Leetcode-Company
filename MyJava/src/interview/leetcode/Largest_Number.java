package interview.leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given a list of non negative integers, arrange them such that they form the
 * largest number.
 * 
 * For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
 * 
 * Note: The result may be very large, so you need to return a string instead of
 * an integer.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Largest_Number {

	public static void main(String[] args) {

	}

	/**
	 * Sorting by switch position and see who is greater
	 * 贪心思路：对于两个备选数字a和b，如果str(a) + str(b) > str(b) + str(a)，则a在b之前，否则b在a之前
	 * 按照此原则对原数组从大到小排序即可
	 * Time: O(nlogn)
	 * 
	 * Notice: remove leading zeroes!
	 * E.g.
	 * Input: [0,0]
	 * Output: "00"
	 * Expected: "0"
	 */
	public String largestNumber(int[] num) {
		StringBuilder sb = new StringBuilder();
		String[] nums = new String[num.length];
		for (int i = 0; i < num.length; i++)
			nums[i] = String.valueOf(num[i]);

		Arrays.sort(nums, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				String n1 = o1 + o2;
				String n2 = o2 + o1;
				return n2.compareTo(n1); // no need to parse
				// (int) (Long.parseLong(n2) - Long.parseLong(n1));
			}
		});

		int i = 0;
		while (i < nums.length - 1 && nums[i].equals("0"))
			i++;
		for (; i < nums.length; i++)
			sb.append(nums[i]);
		return sb.toString();
	}
}
