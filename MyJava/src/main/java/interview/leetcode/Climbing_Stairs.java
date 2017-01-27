package interview.leetcode;

/**
 * You are climbing a stair case. It takes n steps to reach to the top.
 * 
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can
 * you climb to the top?
 * 
 * @author yazhoucao
 * 
 */
public class Climbing_Stairs {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(climbStairs(3) + "\t");
		System.out.print(climbStairs(6) + "\t");
		System.out.print(climbStairs(10) + "\t");
		System.out.print(climbStairs(15) + "\t");
		System.out.print(climbStairs(25) + "\t");
		System.out.println();
		System.out.print(climbStairs_Recur(3) + "\t");
		System.out.print(climbStairs_Recur(6) + "\t");
		System.out.print(climbStairs_Recur(10) + "\t");
		System.out.print(climbStairs_Recur(15) + "\t");
		System.out.print(climbStairs_Recur(25) + "\t");
	}

	/**
	 * DP Improved
	 * Time: O(n), Space: O(1)
	 * 
	 * Notice: there is a general formula that can get the Nth item directly in
	 * log(n) time.
	 */
	public int climbStairs_DPImproved(int n) {
		if (n < 2)
			return 1;
		int first = 1;
		int second = 1;
		int third = 0;
		for (int i = 2; i <= n; i++) {
			third = first + second;
			first = second;
			second = third;
		}
		return third;
	}

	/**
	 * DP
	 * Time: O(n), Space: O(n)
	 */
	public static int climbStairs(int n) {
		int[] a = new int[n + 1];
		a[0] = 1;
		a[1] = 1;
		for (int i = 2; i < a.length; i++) {
			a[i] = a[i - 1] + a[i - 2];
		}
		return a[n];
	}

	/**
	 * Recursion
	 * O(2^n)
	 * 
	 * @param n
	 * @return
	 */
	public static int climbStairs_Recur(int n) {
		if (n == 0)
			return 1;
		else if (n < 0)
			return 0;
		return climbStairs_Recur(n - 1) + climbStairs_Recur(n - 2);
	}
}
