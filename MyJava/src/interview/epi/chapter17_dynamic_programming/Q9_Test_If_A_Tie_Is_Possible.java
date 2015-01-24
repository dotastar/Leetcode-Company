package interview.epi.chapter17_dynamic_programming;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Q9_Test_If_A_Tie_Is_Possible {

	static Class<?> c = Q9_Test_If_A_Tie_Is_Possible.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * V contains the number of votes for each state. This function returns the
	 * total number of ways to tie.
	 */
	public long tiesElection(List<Integer> V) {
		int totalVotes = 0;
		for (int v : V) {
			totalVotes += v;
		}
		// No way to tie if the total number of votes is odd.
		if ((totalVotes & 1) != 0) 
			return 0;
		
		long[][] dp = new long[V.size() + 1][totalVotes + 1];
		dp[0][0] = 1; // Base condition: One way to reach 0.
		for (int i = 0; i < V.size(); ++i) {
			for (int j = 0; j <= totalVotes; ++j) {
				dp[i + 1][j] = dp[i][j] + (j >= V.get(i) ? dp[i][j - V.get(i)] : 0);
			}
		}
		return dp[V.size()][totalVotes / 2];
	}

	@Test
	public void simpleTest() {
		List<Integer> votes = Arrays.asList(4, 5, 2, 7);
		System.out.println(tiesElection(votes));
		assertTrue(tiesElection(votes) == 2);
	}
}
