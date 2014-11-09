package general.algorithms;

import java.util.Arrays;

public class KnapsackProblems {
	static boolean debug;

	public static void main(String[] args) {
		debug = true;

		int[] Cost = { 3, 9, 3, 4, 5 }; // cost
		int[] Vals = { 3, 4, 5, 6, 7 }; // values
		int V = 9;

		Classic01Knapsack knapsack01 = new Classic01Knapsack();
		System.out.println(knapsack01.naive(Cost, Vals, V));
		System.out.println(knapsack01.space_improved(Cost, Vals, V));
		System.out.println(knapsack01.fullKnapsack(Cost, Vals, V));

		CompleteKnapsack complete = new CompleteKnapsack();
		System.out.println(complete.classic(Cost, Vals, V));
		System.out.println(complete.optimized_filter(Cost, Vals, V));
	}

	/**
	 * Given a knapsack has a volume of V, and N items, each item has a cost and
	 * value, calculate the maximum value of the knapsack can have.
	 * 
	 * The items can be put in only once.
	 * 
	 */
	public static class Classic01Knapsack {
		/**
		 * Time: O(NV), Space: O(NV),
		 * 
		 * State transition :
		 * 
		 * dp[i][j] = max(dp[i-1][j], dp[i-1][j-C[i]] + value[i])
		 */
		public int naive(int[] C, int[] val, int V) {
			assert C.length == val.length;
			int N = C.length;
			int[][] dp = new int[N + 1][V + 1];
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= V; j++) {
					if (j >= C[i - 1]) { // C[i-1] is Ci, because dp is N+1
						dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - C[i - 1]]
								+ val[i - 1]);
					} else
						dp[i][j] = dp[i - 1][j];
				}
			}
			printDP(dp);
			return dp[N][V];
		}

		/**
		 * Same time, O(V) Space
		 * 
		 * The key is to backward scan the inner loop, from V...1
		 * 
		 * Previous solution need dp(i-1, v-Ci) to calculate later dp(i, v). If
		 * we use normal order(increasing order) to traverse the loop, previous
		 * value will be updated and lost. If we do backward, dp(i-1, v-Ci) will
		 * be saved, at the same time we can still get dp(i-1, v) to calculate
		 * dp(i, v).
		 * 
		 * dp(i,v) = max(dp(i-1,v), dp(i-1,vCi)+value[i]) can be calculated by:
		 * dp(v) = max(dp(v), dp(v-Ci) + value[i])
		 */
		public int space_improved(int[] C, int[] val, int V) {
			assert C.length == val.length;
			int N = C.length;
			int[] dp = new int[V + 1];
			printDP(dp);
			for (int i = 0; i < N; i++) { // here, we can start from 0 to N-1
				for (int j = V; j >= C[i]; j--) {
					// notice the condition j >= C[i], because of it, we don't
					// need: if (j >= C[i]) here
					dp[j] = max(dp[j], dp[j - C[i]] + val[i]);
				}
				printDP(dp);
			}
			return dp[V];
		}

		/**
		 * Follow-up: get the maximum of the full Knapsack.
		 * 
		 * Thought: the differences is in the initialization of dp[].
		 * 
		 * In previous solution, the initial value of dp[0...V] means the legal
		 * state of bag volume can start from 0 to V, and the maximum could be
		 * derived from any state of those values.
		 * 
		 * And if we set dp[0] to 0, dp[1...V] to Integer.Min, then any value
		 * derived from dp[1...V] will still be a negative value which is
		 * illegal, and only the value derived from dp[0] will be greater than 0
		 * which is a legal value. (The value is derived by the state transition
		 * formula)
		 * 
		 */
		public int fullKnapsack(int[] C, int[] val, int V) {
			assert C.length == val.length;
			int N = C.length;
			int[] dp = new int[V + 1];
			for (int i = 1; i <= V; i++)
				dp[i] = Integer.MIN_VALUE;
			printDP(dp);
			for (int i = 0; i < N; i++) {
				for (int j = V; j >= C[i]; j--) {
					dp[j] = max(dp[j], dp[j - C[i]] + val[i]);
				}
				printDP(dp);
			}

			return dp[V] > 0 ? dp[V] : 0;
		}
	}

	/**
	 * Given a knapsack whose volume is V, and N items.
	 * 
	 * However, each item can be put in infinite times (or you can imagine each
	 * different item has infinite number)
	 */
	public static class CompleteKnapsack {

		/**
		 * Classic solution, Time: O(VN), Space: O(V)
		 * 
		 * The differences between above 01 knapsack problem (space-improved
		 * solution) is that the order of inner loop is an increasing order,
		 * which means we want to use the both updated value dp(i, j-Ci) and the
		 * not updated value dp(i-1, j-Ci).
		 * 
		 * When we use the updated value, it means item_i could have been added
		 * more than one times, and want to add it again. And it can be added as
		 * many times as V/Ci.
		 * 
		 * And the repeated adding the same item is just one option, we can also
		 * add others which still depend on the state transition formula.
		 */
		public int classic(int[] C, int[] val, int V) {
			assert C.length == val.length;
			int N = C.length;
			int[] dp = new int[V + 1];
			printDP(dp);
			for (int i = 0; i < N; i++) {
				for (int j = C[i]; j <= V; j++)
					dp[j] = max(dp[j], dp[j - C[i]] + val[i]);
				printDP(dp);
			}
			return dp[V];
		}

		/**
		 * Same idea, just add two optimizations when N > V, this can improve
		 * the time to max(O(V^2), O(N+V)), it only fit for the case: N > V.
		 * 
		 * Space still the same O(V)
		 * 
		 * Two optimization achieved in O(V+N) time:
		 * 
		 * 1.If C(i)>V, item(i) should be discarded.
		 * 
		 * 2.If C(i)<=C(j) && val(i)>=val(j), item(j) should be discarded.
		 * 
		 * Use counting sort and at the same time discard those items which
		 * should be discarded by above two rules. Then we will have
		 * 
		 * After sorting, we have an array which value is item's value, index is
		 * item's cost.
		 * 
		 */
		public int optimized_filter(int[] C, int[] val, int V) {
			assert C.length == val.length;
			int N = C.length;
			int[] sorted = new int[V + 1]; // index is cost, value is val
			// Optimization1 O(N), Counting sort
			for (int i = 0; i < N; i++) {
				if (C[i] <= V) {
					int idx = C[i]; // use cost as Index
					sorted[idx] = val[i] > sorted[idx] ? val[i] : sorted[idx];
				}
			}

			// Optimization2 O(V), filtering, discard some low ratio items
			int max = sorted[0]; // use max to store the previous non-zero value
			for (int i = 1; i < V; i++) {
				if (sorted[i] <= max) // discard the value
					sorted[i] = 0;
				else
					max = sorted[i]; // do not discard, update max
			}

			int[] dp = new int[V + 1];
			for (int i = 1; i <= V; i++) {
				printDP(dp);
				if (sorted[i] == 0) // discarded
					continue;
				for (int j = i; j <= V; j++) { // i as index is the cost
					dp[j] = max(dp[j], dp[j - i] + sorted[i]);
				}
			}
			printDP(dp);

			return dp[V];
		}

	}

	/**
	 * Given a bag V, N items, each item_i has a cost of C[i], a value of Val[i]
	 * and can be used M[i] times.
	 * 
	 */
	public static class MultiKnapsack {
		// to be continued......
	}

	/********************* Tools *********************/
	private static void printDP(int[][] dp) {
		if (!debug || dp.length == 0)
			return;
		System.out.print("   ");
		for (int i = 0; i < dp[0].length; i++)
			System.out.print(i + ", ");
		System.out.println();
		for (int i = 0; i < dp.length; i++)
			System.out.println(i + " " + Arrays.toString(dp[i]));
	}

	private static void printDP(int[] dp) {
		if (!debug || dp.length == 0)
			return;

		for (int i = 0; i < dp.length; i++)
			System.out.print(dp[i] + " ");
		System.out.println();
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}
}
