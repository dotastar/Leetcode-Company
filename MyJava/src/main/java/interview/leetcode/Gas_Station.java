package interview.leetcode;

/**
 * There are N gas stations along a circular route, where the amount of gas at
 * station i is gas[i].
 * 
 * You have a car with an unlimited gas tank and it costs cost[i] of gas to
 * travel from station i to its next station (i+1). You begin the journey with
 * an empty tank at one of the gas stations.
 * 
 * Return the starting gas station's index if you can travel around the circuit
 * once, otherwise return -1.
 * 
 * Note: The solution is guaranteed to be unique.
 * 
 * @author yazhoucao
 * 
 */
public class Gas_Station {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Second time
	 * Greedy, Time: O(n), 2 passes
	 * One key trick: if a car travel from i to j and failed at j (out of gas),
	 * then it is guaranteed that the car starts from any point between i and j
	 * is also going to fail.
	 */
	public int canCompleteCircuit2(int[] gas, int[] cost) {
		final int N = gas.length;
		int start = 0, currGas = 0;
		for (int i = 0; i < N; i++) {
			currGas += gas[i] - cost[i];
			if (currGas < 0) {
				start = i + 1;
				currGas = 0;
			}
		}
		if (start == N)
			return -1;

		for (int i = 0; i < start; i++) {
			currGas += gas[i] - cost[i];
			if (currGas < 0)
				return -1;
		}
		return start;
	}

	/**
	 * Greedy, just one pass
	 */
	public int canCompleteCircuit(int[] gas, int[] cost) {
		int curGas = 0, startIdx = 0, totalGas = 0;
		for (int i = 0; i < gas.length; i++) {
			curGas += gas[i] - cost[i];
			totalGas += curGas;
			if (curGas < 0) {
				startIdx = i + 1;
				curGas = 0;
			}
		}
		return totalGas < 0 ? -1 : startIdx;
	}
}
