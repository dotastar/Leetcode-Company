package interview.company.palantir;

public class FindTheLargestInIncreasingDecreasingArray {

	public static void main(String[] args) {
		int[] input = {1,3,5,6,9,8,7,4,2};
		System.out.println(input[findPivot(input)]);
	}


	public static int findPivot(int[] arr){
	    return binarySearchPeak(arr, 0 ,  arr.length-2);
	}
	
	public static int binarySearchPeak(int[] arr, int left, int right){
	    if(left>=right)
	        return Integer.MIN_VALUE;
	    int mid = ((right-left)/2)+left;
	    if((arr[mid-1]<arr[mid])&&arr[mid]>arr[mid+1])
	        return mid;
	    else if((arr[mid-1]>arr[mid])&&(arr[mid]>arr[mid+1])){
	        //move to left
	        return binarySearchPeak(arr, left, mid);
	    }else{
	         //move to right
	         return binarySearchPeak(arr, mid+1 , right);
	    }
	}

}
