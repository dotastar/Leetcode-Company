package interview.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, find two numbers such that they add up to a
 * specific target number.
 * 
 * The function twoSum should return indices of the two numbers such that they
 * add up to the target, where index1 must be less than index2. Please note that
 * your returned answers (both index1 and index2) are not zero-based.
 * 
 * You may assume that each input would have exactly one solution.
 * 
 * Input: numbers={2, 7, 11, 15}, target=9
 * 
 * Output: index1=1, index2=2
 * 
 * @author yazhoucao
 * 
 */
public class Sum2 {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(twoSum(new int[]{1,2,16,32,21,-1,1}, 33)));
		System.out.println(Arrays.toString(twoSum(new int[]{1,2,-1}, 0)));
		System.out.println(Arrays.toString(twoSum(new int[]{3,2,4}, 6)));
		System.out.println(Arrays.toString(twoSum(new int[]{0,3,2,0}, 0)));
	}


    public static int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
        for(int i=0; i<numbers.length; i++){
            int need = target - numbers[i];
            if(idxMap.containsKey(need)){
                return new int[]{ idxMap.get(need).intValue()+1, i+1};
            }else{
                idxMap.put(numbers[i], i);
            }
        }
        return new int[]{-1,-1};
    }
}
