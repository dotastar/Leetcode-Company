package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a triangle, find the minimum path sum from top to bottom. Each step you
 * may move to adjacent numbers on the row below.
 * 
 * For example, given the following triangle
 * 
 [
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
 * 
 * The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 * 
 * Note: Bonus point if you are able to do this using only O(n) extra space,
 * where n is the total number of rows in the triangle.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Triangle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<List<Integer>> triangle = new ArrayList<List<Integer>>();
		List<Integer> row0 = new ArrayList<Integer>();
		triangle.add(row0);
		row0.add(-1);
		
		List<Integer> row1 = new ArrayList<Integer>();
		triangle.add(row1);
		row1.add(2);
		row1.add(3);
		
		List<Integer> row2 = new ArrayList<Integer>();
		//triangle.add(row2);
		row2.add(1);
		row2.add(-1);
		row2.add(-3);
		
		List<Integer> row3 = new ArrayList<Integer>();
		//triangle.add(row3);
		row3.add(4);
		row3.add(1);
		row3.add(8);
		row3.add(3);
		
		System.out.println(minimumTotal_Impr(triangle));
	}

	/**
	 * DP, Time: O(n), Space: O(n),  n is the number of total elements
	 */
    public static int minimumTotal(List<List<Integer>> triangle) {
    	List<List<Integer>> dp = new ArrayList<List<Integer>>();
    	int height = triangle.size();
    	if(height==0)	return 0;
    	
    	dp.add(triangle.get(0));
    	for(int i=1; i<height; i++){
    		List<Integer> dpprev = dp.get(i-1);
    		List<Integer> dpcurr = new ArrayList<Integer>();
    		List<Integer> tricurr = triangle.get(i);
    		int len = tricurr.size();
    		for(int j=0; j<len; j++){
    			if(j==0)
    				dpcurr.add(dpprev.get(0)+tricurr.get(j));
    			else if(j==len-1)
    				dpcurr.add(dpprev.get(j-1)+tricurr.get(j));
    			else{
    				int min = dpprev.get(j)<dpprev.get(j-1)?dpprev.get(j):dpprev.get(j-1);
    				min += tricurr.get(j);
    				dpcurr.add(min);
    			}
    		}
    		dp.add(dpcurr);
    	}
    	
    	List<Integer> leaves = dp.get(height-1);
    	int min = leaves.get(0);
    	for(int sum : leaves)
    		if(sum<min)
    			min = sum;
    	return min;
    }
    
    
    /**
     * DP, Space Improved, Time: O(n), Space: O(rows), n is the total number of elements
     * 
     * The key is to bottom up DP solve the problem, why? because the current row's value 
     * is depend on the lower row's value, so it's much easier.
     */
    public static int minimumTotal_Impr(List<List<Integer>> triangle) {
        int rows = triangle.size();
        if(rows==0) 
        	return 0;
        int[] dp = new int[rows]; //rows is also the size of bottom level
        for(int i=0; i<rows; i++)
        	dp[i] = triangle.get(rows-1).get(i);

        for(int i=rows-2; i>=0; i--){  //bottom up, calculate the path
            for(int j=0; j<=i; j++){
                dp[j] = dp[j] < dp[j+1] ? dp[j] : dp[j+1];
                dp[j] += triangle.get(i).get(j);
            }
        }
        return dp[0];
    }
}
