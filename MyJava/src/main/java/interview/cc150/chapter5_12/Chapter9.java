package interview.cc150.chapter5_12;

import interview.cc150.Tools;
import interview.cc150.Tools.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * Recursion and Dynamic Programming
 * 
 * @author yazhoucao
 * 
 */
public class Chapter9 {

	public static void main(String[] args) {

		Tools.runAllQuestions(Chapter9.class, 11);
		/**
		 * Tools.printSeparator(1); q1();
		 * 
		 * Tools.printSeparator(2); q2();
		 * 
		 * Tools.printSeparator(3); q3();
		 * 
		 * Tools.printSeparator(4); q4();
		 **/
	}

	/********************************** 9.1 **********************************/
	/**
	 * 9.1 A child is running up a staircase with n steps, and can hop either
	 * 1step, 2 steps, or 3 steps at a time. Implement a method to count how
	 * many possible ways the child can run up the stairs.
	 */
	public static void q1() {
		int n = 26; // n stairs

		System.out.println("Recursion + Stack: ");
		Stack<Integer> stk = new Stack<Integer>();
		Tools.Timer.begin();
		System.out.println("totally " + q1_recursion(n, stk) + " ways. ");
		Timer.end();
		System.out.println("Recursion: ");
		Timer.begin();
		System.out.println("totally " + q1_recursion(n) + " ways.");
		Timer.end();

		System.out.println("Dynamic Programming: ");
		int[] map = new int[n + 1];
		Arrays.fill(map, -1);
		map[0] = 0;
		Timer.begin();
		System.out.println("totally " + q1_dp(n, map) + " ways.");
		Timer.end();
	}

	/**
	 * Recursion solution, very slow 
	 * Time: 3^n 
	 * Space: log(n)
	 * 
	 * @param n
	 */
	public static int q1_recursion(int n) {
		if (n < 0)
			return 0;
		if (n == 0)
			return 1;
		else
			return q1_recursion(n - 1) + q1_recursion(n - 2)
					+ q1_recursion(n - 3);
	}

	/**
	 * Stack<Integer> is unnecessary for just counting how many ways, it is for
	 * printing all the ways.
	 * 
	 * @param n
	 * @param stk
	 * @return
	 */
	public static int q1_recursion(int n, Stack<Integer> stk) {
		if(n<0) return 0;
		
		if (n == 0) {
			// System.out.println(stk.toString());
			return 1;
		}

		int count = 0;
		stk.push(1);
		count += q1_recursion(n - 1, stk);
		stk.pop();

		stk.push(2);
		count += q1_recursion(n - 2, stk);
		stk.pop();

		stk.push(3);
		count += q1_recursion(n - 3, stk);
		stk.pop();	

		return count;
	}

	/**
	 * Dynamic Programming version Use int[] map to store the previous state,
	 * which is the number of ways in that specific n steps E.g, map[5] is the
	 * number of ways of 5 steps, map[6] is the number of ways of 6 steps
	 * 
	 * Time: n 
	 * Space: n
	 * 
	 * @return
	 */
	public static int q1_dp(int n, int[] map) {
		if (n < 0)
			return 0;
		if (n == 0)
			return 1;
		if (map[n] != -1)
			return map[n];
		else
			map[n] = q1_dp(n - 1, map) + q1_dp(n - 2, map) + q1_dp(n - 3, map);

		return map[n];
	}

	/********************************** 9.2 **********************************/
	/**
	 * 9.2 Imagine a robot sitting on the upper left coner of an X by Y grid.
	 * The robot can only move in two directions: right and down. How many
	 * possible paths are there for the robot to go from (0, 0) to (X, Y)?
	 * FOLLOW UP: Imagine certain spots are "off limits," such that the robot
	 * cannot step on them. Design an algorithm to find a path for the robot
	 * from the top left to the bottom right.
	 */
	public static void q2() {
		int x = 3;
		int y = 7;
		System.out.println("Recursion: ");
		Timer.begin();
		System.out.println("totally " + q2_recursion(x, y) + " paths.");
		Timer.end();

		System.out.println("Dynamic Programming: ");
		Timer.begin();
		System.out.println("totally " + q2_dp(x, y) + " paths.");
		Timer.end();

		System.out.println("Math: ");
		Timer.begin();
		System.out.println("totally " + q2_math(x, y) + " paths.");
		Timer.end();

	}

	/**
	 * Recursion 
	 * Time: 2^(x or y), the greater one 
	 * Space: log(x or y)
	 * 
	 * @return total number of paths
	 */
	public static int q2_recursion(int x, int y) {
		if (x < 0 || y < 0)
			return 0;
		if (x == 1 && y == 1)
			return 1;
		else
			return q2_recursion(x - 1, y) + q2_recursion(x, y - 1);
	}

	/**
	 * Dynamic Programming dp[i][j] is the number of paths at point(i,j)
	 * dp[i][j] = dp[i-1][j] + dp[i][j-1]
	 * 
	 * Time: x*y 
	 * Space: x*y
	 * 
	 * @return
	 */
	public static int q2_dp(int x, int y) {
		int[][] dp = new int[x][y];
		for (int i = 0; i < x; i++)
			dp[i][0] = 1;
		for (int j = 0; j < y; j++)
			dp[0][j] = 1;

		for (int i = 1; i < x; i++) {
			for (int j = 1; j < y; j++) {
				dp[i][j] = (dp[i - 1][j]) + (dp[i][j - 1]);
			}
		}
		// Tools.printMat(dp);
		return dp[x-1][y-1];
	}

	/**
	 * It is simply a math problem, we can use combination to solve it. There
	 * are totally x+y steps from (0,0) to (x,y), to build a path, we are
	 * essentially selecting x times of "move to right" out of a total of x+y
	 * moves. So the total number of paths is C(n,x), selecting x from n paths
	 * where n=x+y. C(n,x) = n!/x!(n-x)! = (x+y)!/x!y!
	 * 
	 * Time: x+y 
	 * Space: 1
	 * 
	 * @return
	 */
	public static int q2_math(int x, int y) {
		long paths = 1;
		// (x+y)!/y!
		for (int i = x + y; i > y; i--)
			paths *= i;

		// ((x+y)!/y!) / x!
		for (int i = 2; i <= x; i++)
			paths /= i;

		return (int) paths;
	}

	/********************************** 9.3 **********************************/
	/**
	 * 9.3 A magic index in an array A[1...n-1] is defined to be an index such
	 * that A[i] = i. Given a sorted array of distinct integers, write a method
	 * to find a magic index, if one exists, in array A.
	 * 
	 * FOLLOW UP: What if the values are not distinct?
	 */
	public static void q3() {
		// 0 1 2 3 4 5 6 7 8 9
		int[] a = { -9, -7, -5, -3, -1, 1, 3, 5, 7, 9 };
		// 0 1 2 3 4 5 6 7 8 9 10
		int[] b = { -40, -20, -1, 1, 2, 3, 5, 7, 9, 12, 13 };

		System.out.println("Magic index is:"
				+ findMagicIndex(a, 0, a.length - 1));
		System.out.println("Magic index is:"
				+ findMagicIndex(b, 0, b.length - 1));

		// Follow up: 0 1 2 3 4 5 6 7 8 9 10
		int[] c = { -18, -5, 2, 2, 2, 3, 4, 5, 9, 12, 13 };
		System.out.println("Magic index is:"
				+ findMagicIndex_Repeat(c, 0, c.length - 1));
	}

	/**
	 * Binary search the index
	 * 
	 * @return
	 */
	public static int findMagicIndex(int[] a, int start, int end) {
		if (start > end)
			return -1;

		int mid = (end + start) / 2;
		if (a[mid] == mid)
			return mid;

		if (a[mid] < mid)
			return findMagicIndex(a, mid + 1, end);
		else
			return findMagicIndex(a, start, mid - 1);
	}

	/**
	 * Follow up solution (What if the values are not distinct?) Because it is
	 * not distinct, you have to search both of sides.
	 * 
	 * @return
	 */
	public static int findMagicIndex_Repeat(int[] a, int start, int end) {
		if (start > end)
			return -1;

		int mid = (end + start) / 2;
		if (a[mid] == mid)
			return mid;

		if (a[mid] < mid) {
			int index = findMagicIndex_Repeat(a, mid + 1, end);
			if (index > 0)
				return index;
		}

		return findMagicIndex_Repeat(a, start, mid - 1);
	}

	/********************************** 9.4 **********************************/
	/**
	 * 9.4 Write a method to return all subsets of a set.
	 * 
	 * Combination problem Tips: for a n elements set, there are total 2^n
	 * subsets.
	 */
	public static void q4() {
		int[] set = { 1, 2, 3 };
		boolean[] combs = new boolean[set.length];
		System.out.println("Recursion: ");
		q4_Recursion(set, combs, 0);
		
		System.out.println("Bit Manipulation: ");
		q4_BitManipulation(set);
	}

	/**
	 * Use something like { 1 0 0 0 0 } to represent a subset Recursively change
	 * one bit, a classical full binary tree recursion In each step, change the
	 * bit to 0 or 1 in bit array, 2^n steps total
	 * 
	 * Time: 2^n 
	 * Space: n
	 * 
	 * @param set
	 * @param combs
	 * @param step
	 */
	public static void q4_Recursion(int[] set, boolean[] combs, int step) {
		if (step == combs.length) { // reach leaf, index out of bound
			System.out.print("comb: ");
			for (int i = 0; i < combs.length; i++) {
				if (combs[i])
					System.out.print(set[i] + " ");
			}
			System.out.println();
			return;
		}

		combs[step] = true;
		q4_Recursion(set, combs, step + 1);

		combs[step] = false;
		q4_Recursion(set, combs, step + 1);
	}

	/**
	 * Traverse all integer from 0 to 2^n-1, each integer is a combination,
	 * for each integer, verify all bits whether it is 0 or 1.
	 * For 1, it is in the subset, for 0 it is not in the subset.
	 * 
	 * Idea:
	 * There are totally 2^n combinations, we can use number 0~2^n-1 to
	 * represetn all combinations. The problem left is to convert a integer
	 * number to a binary representation (bit array).
	 * 
	 * Actually we don't have to convert it to a "visible" bit array, 
	 * it is naturally a bit array in computer representation, we just need
	 * to verify the specific bit is 0 or 1? By using bit manipulation.  
	 * 
	 * Time: n*2^n
	 * Space: 1
	 * 
	 * @param set
	 */
	public static void q4_BitManipulation(int[] set) {
		int len = set.length;
		int n = (1<<len)-1;
		for(int i=0; i<=n; i++){
			System.out.println("Comb: "+bitConversion(set, i));
		}
	}
	
	/**
	 * Given a set and one of its subsets' binary representation, 
	 * convert it to a decimal subset. 
	 * @return
	 */
	public static List<Integer> bitConversion(int[] set, int binary){
		List<Integer> subset = new ArrayList<Integer>();
		for(int i=0; i<set.length; i++){
			if((binary&1)==1)	//if the rightmost bit is 1
				subset.add(set[i]);
			binary = binary>>1;
		}
		return subset;
	}
	
	
	/********************************** 9.5 **********************************/
	/**
	 * 9.5 
	 * Write a method to compute all permutations of a string
	 * Total permutations should be n! (suppose all n letters are different).
	 */
	public static void q5(){
		String s = "abcd";
		Set<String> perms = new HashSet<String>();
		swapWithRight(s.toCharArray(), perms, 0);
		System.out.println(perms.size()+"\n"+perms.toString());
	}
	
	
	/**
	 * To calculate all permutations of a string, we need to switch every letter
	 * in all possible positions.
	 * 
	 * Recursively swap current element with every element on its right side.
	 * 
	 * Time: n!
	 * Space: n
	 */
	public static void swapWithRight(char[] s, Set<String> perms, int current){
		int len = s.length;
		if(current==len-1){
			//System.out.println(new String(s));
			perms.add(new String(s));
			return;
		}
		//swap current with every element on its right side
		for(int right=current; right<len; right++){
			swap(s, current, right);
			swapWithRight(s, perms, current+1);
			swap(s, right, current);
		}
	}
	
	private static void swap(char[] chs, int i, int j){
		char tmp = chs[i];
		chs[i] = chs[j];
		chs[j] = tmp;
	}
	
	
	/********************************** 9.6 **********************************/
	/**
	 * 9.6 
	 * Implement an algorithm to print all valid combinations of n-pairs of 
	 * parentheses.  (i.e., properly opened and closed) 
	 */
	public static void q6(){
		int n=3;
		Set<String> combinations = new HashSet<String>();
		q6_recursion1(n, "()", combinations);
		System.out.println("Recursion1: "+combinations);
		
		
		combinations.clear();
		q6_recursion2(0,0,new char[n*2],combinations);
		System.out.println("Recursion2: "+combinations);
	}
	
	/**
	 * Bottom-up, recursively caculate from f(1) to f(n), 
	 * brute force enumerate all possible combinations.
	 * For each combination, insert a parenthesis in every position.
	 * Use set to remove duplicates
	 * 
	 * Time: n! factorial 
	 * Space: n!
	 * The space is much inefficient because of the input 'current' argument.
	 * When recursion happens, it will push down all references of intermediate 
	 * String into the recursion Stack which hold those Strings and cannot be 
	 * released until recursion end.
	 */
	public static void q6_recursion1(int n, String current, Set<String> pairs){
		if(current.length()==n*2){
			pairs.add(current);
			return;
		}
		
		for(int i=0; i<current.length(); i++){
			String newComb = insertParenthesis(i,current);
			q6_recursion1(n, newComb, pairs);
		}
	}
	
	public static String insertParenthesis(int index, String s){
		if(index<0 || index>s.length()) 
			throw new IllegalArgumentException();
		
		return s.substring(0,index)+"()"+s.substring(index,s.length());
	}
	
	/**
	 * Faster version, avoid duplicate cases
	 * Imagine it as a char[2n] array (n is the number of parenthesis).
	 * Each index could be either left or right, calculate all combinations.
	 * Recursively fill each index with left or riht, but it must follow the 
	 * two rules below. 
	 * Rule1: The number of left parenthesis is at most n.
	 * Rule2: The number of right parenthesis should be no greater than left. 
	 * 
	 * Time: 2^n
	 * Space: n
	 * 
	 * @param fillIdx: the index will be filled with '(' or ')'
	 * @param lCount: left parenthesis count 
	 * @param rCount: right parenthesis count
	 * @param str
	 * @param pairs
	 */
	public static void q6_recursion2(int lCount, int rCount, 
			char[] str, Collection<String> pairs){
		int idx = lCount+rCount;
		if(idx==str.length){
			pairs.add(new String(str));
			return;
		}
		
		//it can always fill in a left parenthesis
		if(lCount<str.length/2){
			str[idx] = '(';
			q6_recursion2(lCount+1,rCount,str,pairs);	
		}
		
		//if rigth<left, it can fill a right parenthesis
		if(rCount<lCount){
			str[idx] = ')';
			q6_recursion2(lCount,rCount+1,str,pairs);
		}
	}
	
	
	/********************************** 9.7 **********************************/
	/**
	 * 9.7
	 * Implement the "paint fill" function that one might see on many image 
	 * editing programs. That is, given a screen (represented by a two-dimensional 
	 * array of colors), a point, and a new color, fill in the surrounding area 
	 * until the color changes from the original color.
	 * 
	 * For simplisity, use int to represent color. 
	 */
	public static void q7(){
		int[][] canvas = initCanvas(10);
		Tools.printMat(canvas);
		paintFill(0, 5, 0, 0, canvas);
		Tools.printMat(canvas);
	}
	
	public static int[][] initCanvas(int size){
		int[][] canvas = new int[size][size];
		for(int i=0; i<size; i++){
			Random ran = new Random();
			int color = ran.nextInt(10);
			int begin = ran.nextInt(size/3);
			int end = begin+ran.nextInt(size/2);
			for(int j=begin; j<end; j++)
				canvas[i][j] = color;
		}
		return canvas;
	}
	
	public static void paintFill(int ocolor, int ncolor, int px, int py, int[][] canvas){
		if(outOfBoundary(px, py, canvas) || canvas[px][py]!=ocolor)
			return;
		
		canvas[px][py] = ncolor;
		paintFill(ocolor, ncolor, px+1, py, canvas);	//right
		paintFill(ocolor, ncolor, px-1, py, canvas);	//left
		paintFill(ocolor, ncolor, px, py+1, canvas);	//top
		paintFill(ocolor, ncolor, px, py-1, canvas);	//bottom
	}
	
	public static boolean outOfBoundary(int px, int py, int[][] area){
		if(area==null || area.length==0) return true;
		int lx = area.length;
		int ly = area[0].length;
		if(px<0 || py<0 || px>=lx || py >=ly) 
			return true;
		else	
			return false;
	}
	
	
	
	/********************************** 9.8 **********************************/
	/**
	 * Given an infinite number of quarters(25 cents), dimes(10 cents), 
	 * nickels(5 cents) and pennies(1 cent), write code to calculate the number 
	 * of ways of representing n cents.
	 */
	public static void q8(){
		int n = 26;
		System.out.println(recursion(n, 0, new int[]{25,10,5,1}));
	}
	
	/**
	 * Keep minus total by the current coin until it can not be used
	 * each use of coin is one combination.
	 * E.g. The recursion tree of total = 100 
	 * layer0 total = 100
	 * layer1 25 0 times, 1 times, 2 times, 3 times, 4 times
	 * layer2 10 [.....]  [.....]	[.....]	[.....]	 [.....]
	 * layer3 5	 [[]...[]]	....
	 * layer4 1	.....
	 * 	
	 * Fixed Depth of tree = 4; 
	 * @return
	 */
	public static int recursion(int total, int idx, int[] coins){
		if(total<0)
			return 0;
		if(total==0)
			return 1;
		//then total must > 0
		if(idx==coins.length)
			return 0;
		
		int count = 0;	
		//i: how many times used for current idx coin
		for(int i=0; i*coins[idx]<=total; i++){
			count+=recursion(total-i*coins[idx],idx+1,coins);
		}
		return count;
	}
	
	
	
	/********************************** 9.9 **********************************/
	/**
	 * 9.9
	 * Write an algorithm to prim all ways of arranging eight queens on an 8x8 
	 * chess board so that none of them share the same row, column or diagonal. 
	 * In this case, "diagonal" means all diagonals, not just the two that 
	 * bisect the board.
	 */
	public static void q9(){
		
	}
	
	/********************************** 9.10 **********************************/
	/**
	 * 9.10 
	 * You have a stack of n boxes, with widths wi, heights li and depths di. 
	 * The boxes cannot be rotated and can only be stacked on top of one another 
	 * if each box in the stack is strictly larger than the box above it in width, 
	 * height, and depth. 
	 * Implement a method to build the tallest stack possible, where the height of 
	 * a stack is the sum of the heights of each box.
	 */
	public static void q10(){
		
	}
	
	
	
	/********************************** 9.11 **********************************/
	/**
	 * 9.11 
	 * Given a boolean expression consistingof the symbols 0,1, &, |, and ^, 
	 * and a desired boolean result value 'result', implement a function to count 
	 * the number of ways of parenthesizing the expression such that it evaluates 
	 * to 'resuLt'.
	 */
	public static void q11(){
		
	}
}
