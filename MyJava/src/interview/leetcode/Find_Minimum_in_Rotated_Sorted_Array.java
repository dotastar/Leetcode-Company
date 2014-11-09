package interview.leetcode;

/**
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * 
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * 
 * Find the minimum element.
 * 
 * You may assume no duplicate exists in the array.
 * 
 * @author yazhoucao
 * 
 */
public class Find_Minimum_in_Rotated_Sorted_Array {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Find_Minimum_in_Rotated_Sorted_Array obj = new Find_Minimum_in_Rotated_Sorted_Array();
		int[] A0 = {1};
		int[] A1 = {2, 1};	
		assert obj.findMin(A0)==1 ;
		assert obj.findMin(A1)==1:obj.findMin(A1);
	}

	public int findMin(int[] A) {
		int l = 0;
		int r = A.length - 1;
		while (l <= r) {
			int m = (l + r) / 2;
			if (A[l] <= A[r]) {
				return A[l];
			} else if(A[m] > A[m+1]){
				return A[m+1];
			}else{	//A[l] > A[r]
				if (A[m] > A[l])
					l = m;
				else
					r = m;
			}
		}
		return -1;
	}
}
