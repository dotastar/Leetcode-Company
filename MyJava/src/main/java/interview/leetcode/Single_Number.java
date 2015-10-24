package interview.leetcode;

/**
 * Given an array of integers, every element appears twice except for one. Find
 * that single one.
 * 
 * Note:
 * Your algorithm should have a linear runtime complexity. Could you implement
 * it without using extra memory?
 * 
 * @author yazhoucao
 * 
 */
public class Single_Number {

	public static void main(String[] args) {

	}

	public int singleNumber(int[] A) {
		int res = 0;
		for (int a : A)
			res ^= a;
		return res;
	}

}
