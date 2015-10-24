package interview.company.palantir;

/**
 * ���������������ĺ�
 * Max Sum Of Continuous Sub-Array
 * @author Asia
 *
 */
public class MaxSumOfContinuousSubArray {

	public static void main(String[] args) {
		int[] input1 = {1,-2,3,5,-3,2};
		int[] input2 = {0,-2,3,5,-1,2};
		int[] input3 = {-9,-2,-3,-5,-3};
		
		solution(input1);
		solution(input2);
		solution(input3);
	}
	
	/**
	 * Dynamic programming
	 * d[i] = max(d[i-1]+a[i], a[i])
	 * @param input
	 */
	public static void solution(int[] a){
		int[] state = new int[a.length];
		state[0] = a[0];
		//end initialize
		int max = state[0];
		for(int i=1; i<a.length; i++){
			state[i] = max(state[i-1]+a[i], a[i]);
			if(state[i]>max)
				max = state[i];
		}
		System.out.println(max);
	}

	private static int max(int a, int b){
		return a>b?a:b;
	}
}
