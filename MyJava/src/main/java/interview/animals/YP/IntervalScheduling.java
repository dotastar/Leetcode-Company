package interview.company.yelp;

import java.util.Arrays;
import java.util.Comparator;

public class IntervalScheduling {

	public static void main(String[] args) {
		
		int[][] testData = {{0,5},{2,3},{5,7},{4,6},{8,10},{1,4}};
		solution(testData);
	}
	
	
	
	
	
	
	
	/**
	 * Greedy Algorithm:
	 * 1.Select the interval, x, with the earliest finishing time.
	 * 2.Remove x, and all intervals intersecting x, from the set of candidate intervals.
	 * 3.Continue until the set of candidate intervals is empty.
	 * 
	 * O(nlog(n)) Time(because of the sorting)
	 * O(1)	Space, if just return the max length of intervals
	 * @param intervals
	 * @return
	 */
	public static void solution(int[][] list){
		for(int[] pair : list)
			System.out.print(Arrays.toString(pair)+" ");
		System.out.println();
		
		Arrays.sort(list, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1.length!=2||o2.length!=2)
					throw new IllegalArgumentException();
				//increasing order
				return o1[1]-o2[1];
			}
			
		});
		
		//remove pairs that overlapped
		int[] prev = list[0];
		System.out.print(Arrays.toString(prev)+" ");
		for(int i=1; i<list.length; i++){
			int[] current = list[i];
			//current and previous do not overlapped
			if(current[0]>=prev[1]){
				System.out.print(Arrays.toString(current)+" ");
				prev = current;
			}
		}
	}
	
}
