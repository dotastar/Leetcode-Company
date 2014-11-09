package interview.company.yelp;

import java.util.Stack;


public class LongestCommonSequence {
	public static void main(String[] args){
		String s1 = "abcdbceea";
		String s2 = "cabdefga";
		//result : abdea
		
		System.out.println("Max length: "+dp_solution(s1, s2));
	}
	
	
	/**
	 *   # c a b d e f g a s2	back track directions
	 * # 0 0 0 0 0 0 0 0 0			
	 * a 0 0 1 1 1 1 1 1 1				\	a
	 * b 0 0 1 2 2 2 2 2 2				\	b
	 * c 0 1 1 2 2 2 2 2 2				|
	 * d 0 1 1 2 3 3 3 3 3 				\	d
	 * b 0 1 1 2 3 3 3 3 3				|
	 * c 0 1 1 2 3 3 3 3 3				|
	 * e 0 1 2 2 3 4 4 4 4				\	e
	 * e 0 1 2 2 3 4 4 4 4				|	
	 * a 0 1 2 2 3 4 4 4 5				\	a
	 * s1
	 */
	
	
	/**
	 * Dynamic Programming Problem
	 * 
	 * States:
	 * dp[i] is the length of the longest common sequence
	 * s1[i] = s1[i-1] + e1 
	 * s2[i] = s2[i-1] + e2
	 * 
	 * Transition formula:
	 * dp[i] = 
	 * 		 1.s1[i-1] + s2[i-1] + 1, if e1 == e2
	 * 		 2.max(s1[i-1] + s2[i], s1[i] + s2[i-1]), if e1 != e2
	 * @param s1
	 * @param s2
	 */
	public static int dp_solution(String s1, String s2){
		int len1 = s1.length()+1;
		int len2 = s2.length()+1;
		int[][] maxLen = new int[len1][len2];
		int[][] directions = new int[len1][len2];
		for(int i=1; i<len1; i++){
			for(int j=1; j<len2; j++){
				if(s1.charAt(i-1)==s2.charAt(j-1)){
					maxLen[i][j] = maxLen[i-1][j-1] + 1;
					directions[i][j] = 3;
				}else{
					maxLen[i][j] = max(maxLen[i-1][j],maxLen[i][j-1]);
					//1:up direction;	2:left direction
					directions[i][j] = maxLen[i-1][j]>maxLen[i][j-1]?1:2;
				}
			}
		}

		print(maxLen);
		printRoute(directions,s1,s2);
		return maxLen[len1-1][len2-1];
	}
	
	
	public static int max(int a, int b){
		return a>b?a:b;
	}
	
	public static void printRoute(int[][] directions, String s1, String s2){
		int len1 = directions.length;
		if(len1==0) return;
		int len2 = directions[0].length;
		int i = len1-1;
		int j = len2-1;
		Stack<Character> route = new Stack<Character>();
		while(i!=0&&j!=0){
			int dir = directions[i][j];
			if(dir==1){
				i--;
			}else if(dir==2){
				j--;
			}else{
				i--;
				j--;
				route.push(s1.charAt(i));
			}
		}
		while(!route.isEmpty()){
			System.out.print(route.pop()+" ");
		}
		System.out.println();
	}

	private static void print(int[][] dp){
		if(dp.length==0) return;
		int len1 = dp.length;
		int len2 = dp[0].length;
		for(int i=0; i<len1; i++){
			for(int j=0; j<len2; j++)
				System.out.print(dp[i][j]+" ");
			System.out.println();
		}
	}
	
}