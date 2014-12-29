package interview.leetcode;

import java.util.Arrays;

/**
 * Given an array of integers, every element appears three times except for one.
 * Find that single one.
 * 
 * Note: Your algorithm should have a linear runtime complexity. Could you
 * implement it without using extra memory?
 * 
 * @author yazhoucao
 * 
 */
public class Single_Number_II {

	public static void main(String[] args) {
		
		System.out.println(singleNumber(new int[]{11,122,3177,11,122,3177,11,122,3177,4096,555,555,555}));
	}

	/**
	 * General solution for numbers appeared k times (k=3 for this case)
	 * 
	 * Create an array to count the number of times that the bit is 1
	 * All bits in that array should be the multiple of 3,
	 * except for all the bits of that single number is the multiple of 3 + 1. 
	 * 
	 */
	public static int singleNumber(int[] A) {
		int res = 0;
		int[] count = new int[32];
		for(int i=0; i<32; i++){
			for(int j=0; j<A.length; j++){
				if(((A[j]>>i)&1)==1)
					count[i]++;
			}
			res |= (count[i]%3)<<i;
		}
		System.out.println(Arrays.toString(count));
		return res;
	}
}
