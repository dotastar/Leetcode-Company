package interview.company.palantir;

import java.util.Arrays;
import java.util.Stack;

/**
 * Find the longest increasing sequence of an integer array
 * e.g.  input: 1 -2 3 -3 5 -4 7
 * output:1 3 5 7
 * @author Asia
 *
 */
public class LongestIncreasingSequence {

	public static void main(String[] args) {
		int[] input = {-3, 1, 2, -2, 4, -1, 6, -7,-6,5,0,1,2};
		findTheSequence(input);
	}

	/**
	 * Dynamic Programming
	 * record each state's max length,
	 * the maximum state must be from the second maximum+1,
	 * for each sub-state, traverse all the past(left) points to 
	 * find the current maximum state
	 * @param input
	 */
	public static void findTheSequence(int[] input){
		//record the current state's max length
		int[] maxLen = new int[input.length];
		maxLen[0] = 1;
		int currentMaxIndex=0;
		
		for(int i=1; i<input.length; i++){
			//pruning, go to maxLen's position directly
			if(input[i]>input[currentMaxIndex]){
				maxLen[i] = maxLen[currentMaxIndex]+1;
				currentMaxIndex = i;
			}else{
				maxLen[i]=1;
				//otherwise traverse all the past(left) points
				for(int j=i; j>=0; j--){  //j is the past
					if(input[i]>input[j]&&maxLen[j]>=maxLen[i]){
						maxLen[i] = maxLen[j]+1;
						if(maxLen[i]>maxLen[currentMaxIndex])
							currentMaxIndex = i;
					}
				}
			}
		}//end for
		
		System.out.println(Arrays.toString(maxLen));
		//System.out.println(maxLen[currentMaxIndex]);
		
		//output the sequence
		//must revere scan the maxLen,
		//begin from the currentMaxIndex
		Stack<Integer> stack = new  Stack<Integer>();
		stack.push(input[currentMaxIndex]);
		int lastMaxLen = maxLen[currentMaxIndex];
		for(int i=currentMaxIndex-1; i>=0; i--){
			if(maxLen[i]==lastMaxLen-1){
				stack.push(input[i]);
				lastMaxLen = maxLen[i];
			}
		}
		
		for(int i=0; i<maxLen[currentMaxIndex]; i++)
			System.out.print(stack.pop()+" ");
	}
	
}
