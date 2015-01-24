package interview.epi.chapter17_dynamic_programming;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Random;

import org.junit.Test;

/**
 * Write a function which you can move 1 to k steps at a time. You need to make
 * exactly n steps to get to your destination.
 * 
 * @author yazhoucao
 * 
 */
public class Q18_Count_The_Number_Of_Steps_In_A_Board_Game {

	static Class<?> c = Q18_Count_The_Number_Of_Steps_In_A_Board_Game.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * The key to solve this problem is to reason backwards.
	 * There are k possibilities for the final move, each of these is the last
	 * move in a different sequences of moves.
	 * Therefore: dp(n,k) = sum(dp(n-i, k)), i = 1...k.
	 * Time: O(nk), Space: O(n)
	 */
	public int numberSteps(int n, int k) {
		if (n <= 1)
			return 1;

		int[] dp = new int[n + 1];
		dp[0] = dp[1] = 1;
		for (int i = 2; i < n + 1; i++) {
			for (int j = 1; j < k + 1 && i >= j; j++)
				dp[i] += dp[i - j];
		}
		return dp[n];
	}

	/**
	 * Same solution, from EPI
	 * 
	 * Time: O(nk), Space: O(k)
	 */
	public int numberSteps_Improved(int n, int k) {
		int steps[] = new int[k + 1];
		steps[0] = steps[1] = 1;
		for (int i = 2; i <= n; ++i) {
			steps[i % (k + 1)] = 0;
			for (int j = 1; j <= k && i - j >= 0; ++j) {
				steps[i % (k + 1)] += steps[(i - j) % (k + 1)];
			}
		}
		return steps[n % (k + 1)];
	}

	@Test
	public void randomTest() {
		Random ran = new Random();
		for (int times = 0; times < 50; times++) {
			int n = ran.nextInt(20) + 5, k = ran.nextInt(10) + 1;
			int res1 = numberSteps(n, k);
			int res2 = numberSteps_Improved(n, k);
			System.out.println("n:" + n + " k:" + k + " " + res1);
			assertTrue(res1 + "\t" + res2, res1 == res2);
		}
	}
}
