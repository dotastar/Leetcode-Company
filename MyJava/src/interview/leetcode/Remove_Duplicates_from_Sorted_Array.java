package interview.leetcode;

import java.util.Arrays;

/**
 * Given a sorted array, remove the duplicates in place such that each element
 * appear only once and return the new length.
 * 
 * Do not allocate extra space for another array, you must do this in place with
 * constant memory.
 * 
 * For example, Given input array A = [1,1,2],
 * 
 * Your function should return length = 2, and A is now [1,2].
 * 
 * @author yazhoucao
 * 
 */
public class Remove_Duplicates_from_Sorted_Array {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a0 = new int[]{1};
		int[] a1 = new int[]{1,1,2};
		int[] a2 = new int[]{1,1,2,3,44,55,56,56,60};
		int[] a3 = new int[]{1,2,3,4,5,6,7,8};
		int[] a4 = new int[]{1,1,1};
		int[] a5 = new int[]{1,1,1,2,2,3,3,3,4,4};
		
		System.out.println("Length\tArray");
		System.out.println(removeDuplicates(a0)+"\t"+Arrays.toString(a0));
		System.out.println(removeDuplicates(a1)+"\t"+Arrays.toString(a1));
		System.out.println(removeDuplicates(a2)+"\t"+Arrays.toString(a2));
		System.out.println(removeDuplicates(a3)+"\t"+Arrays.toString(a3));
		System.out.println(removeDuplicates(a4)+"\t"+Arrays.toString(a4));
		System.out.println(removeDuplicates(a5)+"\t"+Arrays.toString(a5));
	}

	public static int removeDuplicates(int[] A) {
		if(A.length==0) return 0;
		int diff = 1;
		for(int i=1; i<A.length; i++){
			if(A[i]!=A[i-1]){
				A[diff] = A[i];
				diff++;
			}
		}
		return diff;
	}
}
