package interview.leetcode;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


/**
 * Given two words word1 and word2, find the minimum number of steps required to
 * convert word1 to word2. (each operation is counted as 1 step.)
 *
 * You have the following ONLY 2 operations permitted on a word:
 *
 * a) Insert a character b) Delete a character
 *
 * @author yazhoucao
 *
 */
public class Edit_Distance_No_Replace_Operation {

	public static void main(String[] args) {
		System.out.println(minDistanceImprImpr("eat", "sea"));
		System.out.println(minDistanceImprImpr("a", "a"));
	}

	@Test
	public void testCase0() {
		int distance = minDistance("ab", "ab");
		assertThat(distance).isEqualTo(0);
		assertThat(minDistanceImpr("ab", "ab")).isEqualTo(0);
		assertThat(minDistanceImprImpr("ab", "ab")).isEqualTo(0);
	}

	@Test
	public void testCase1() {
		int distance = minDistance("ab", "ef");
		assertThat(distance).isEqualTo(4);
		assertThat(minDistanceImpr("ab", "ef")).isEqualTo(4);
		assertThat(minDistanceImprImpr("ab", "ef")).isEqualTo(4);
	}

	@Test
	public void testCase2() {
		int distance = minDistance("abc", "ef");
		assertThat(distance).isEqualTo(5);
		assertThat(minDistanceImpr("abc", "ef")).isEqualTo(5);
		assertThat(minDistanceImprImpr("abc", "ef")).isEqualTo(5);
	}

	@Test
	public void testCase3() {
		int distance = minDistance("fec", "ef");
		assertThat(distance).isEqualTo(3);
		assertThat(minDistanceImpr("fec", "ef")).isEqualTo(3);
		assertThat(minDistanceImprImpr("fec", "ef")).isEqualTo(3);
	}

	@Test
	public void testCase4() {
		int distance = minDistance("ab", "bc");
		assertThat(distance).isEqualTo(2);
		assertThat(minDistanceImpr("ab", "bc")).isEqualTo(2);
		assertThat(minDistanceImprImpr("ab", "bc")).isEqualTo(2);
	}

	@Test
	public void testCase5() {
		int distance = minDistance("sss", "ssh");
		assertThat(distance).isEqualTo(2);
		assertThat(minDistanceImpr("sss", "ssh")).isEqualTo(2);
		assertThat(minDistanceImprImpr("sss", "ssh")).isEqualTo(2);
	}


	/**
	 * Dynamic programming
	 * dp[i][j] is the min distance of word1.sub(0...i-1) and word2.sub(0...j-1)
	 * x = word1[i], y = word2[j],
	 *
	 * When x==y:
	 * A.1. if x == y, then dp[i][j] == dp[i-1][j-1]
	 *
	 * When x!=y, dp[i][j] is the min of the three situations:
	 * B.1. if we insert y for word1, then dp[i][j] = dp[i][j-1] + 1
	 * B.2. if we delete x for word1, then dp[i][j] = dp[i-1][j] + 1
	 * B.3. if we replace x with y for word1, then dp[i][j] = dp[i-1][j-1] + 1
	 *
	 */
	public static int minDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
		int[][] dp = new int[len1 + 1][len2 + 1];
		for (int i = 1; i <= len1; i++)
			dp[i][0] = i;
		for (int i = 1; i <= len2; i++)
			dp[0][i] = i;
		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
				}
			}
		}
		return dp[len1][len2];
	}

	/**
	 * DP, Space improved, like knapsack problem
	 */
	public static int minDistanceImpr(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
		int[] curr = new int[len2 + 1];
		int[] prev = new int[len2 + 1];
		for (int i = 1; i <= len2; i++)
			curr[i] = i;

		for (int i = 1; i <= len1; i++) {
			for (int j = 0; j <= len2; j++)
				prev[j] = curr[j];

			curr[0] = i;
			for (int j = 1; j <= len2; j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
					// dp[i][j] = dp[i-1][j-1];
					curr[j] = prev[j - 1];
				} else {
					// dp[i][j] = min(dp[i-1][j],dp[i][j-1],dp[i-1][j-1]) + 1;
					curr[j] = Math.min(curr[j - 1], prev[j]) + 1;
				}
			}
		}
		return curr[len2];
	}

	/**
	 * Further improved, need only one array and one variable is enough
	 */
	public static int minDistanceImprImpr(String word1, String word2) {
		int m = word1.length(), n = word2.length();
		int[] dp = new int[n + 1];
		int prev = 0; // to store dp[i-1][j-1]
		for (int j = 0; j <= n; j++)
			dp[j] = j;  // initialize dp[0][j]

		for (int i = 1; i <= m; i++) {
			prev = i - 1; // or prev = dp[0];
			dp[0] = i;	// initialize dp[i][0]
			for (int j = 1; j <= n; j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
					// dp[i][j] = dp[i-1][j-1];
					int temp = dp[j];
					dp[j] = prev;
					prev = temp;
				} else {
					prev = dp[j];
					// dp[j-1] = dp[i][j-1], dp[j] = dp[i-1][j]
					dp[j] = Math.min(dp[j - 1], dp[j]) + 1;
				}
			}
		}
		return dp[n];
	}
}
