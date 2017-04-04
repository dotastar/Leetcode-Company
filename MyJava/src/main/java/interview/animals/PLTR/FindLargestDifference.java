package interview.company.palantir;

/**
 * Find the largest possible difference in an array of integers,
 * such that the smaller integer occurs earlier in the array
 * @author Asia
 *
 */
public class FindLargestDifference {

	public static void main(String[] args) {
		int[] input = {7,5,1,4,9,6,0,11};
		solution(input);
	}

	/**
	 * Attention, the min value must appear before
	 * the max value
	 * @param a : input data
	 */
	public static void solution(int[] a){
		int currMin = Integer.MAX_VALUE;
		int currMaxDiff = Integer.MIN_VALUE;
		
		for(int i=0; i<a.length; i++){
			if(a[i]<currMin)
				currMin = a[i];
			if(currMaxDiff<a[i]-currMin)
				currMaxDiff = a[i] - currMin;
		}
		
		System.out.println(currMaxDiff);
	}
}
