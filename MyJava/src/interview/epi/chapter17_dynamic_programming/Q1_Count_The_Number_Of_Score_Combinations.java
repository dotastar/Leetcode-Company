package interview.epi.chapter17_dynamic_programming;

import java.util.Arrays;
import java.util.List;

/**
 * In an American football game, a play can lead to 2 points, 3 points, 7
 * points. Given the final score of a game, we want to compute how many
 * different combinations of 2,3,7 point plays could make up this score.
 * 
 * How would you find the number of combinations of plays that result in an
 * aggregate score of s?
 * 
 * How would you compute the number of distinct sequences of individual plays
 * that result in a score of s?
 * 
 * @author yazhoucao
 * 
 */
public class Q1_Count_The_Number_Of_Score_Combinations {

	public static void main(String[] args) {
		Q1_Count_The_Number_Of_Score_Combinations o = new Q1_Count_The_Number_Of_Score_Combinations();
		List<Integer> scoreWays = Arrays.asList(7, 2, 3);
		long comb = o.countCombinations(7, scoreWays);
		long perm = o.countPermutations(7, scoreWays);
		System.out.println(comb + "\t" + perm);
	}

	/**
	 * combinations[i] : the number of combinations of plays when the score is i
	 * Try every scoreWays:
	 * 1.for each scoreWay, find how many ways(combinations) to represent every
	 * score (the first loop)
	 * 2.for every combination of score j (combinations[j]), use the current
	 * scoreWay to add a new combination which combination[j-scoreWay] to
	 * itself. (the second loop)
	 */
	public long countCombinations(int k, List<Integer> scoreWays) {
		long[] combinations = new long[k + 1];
		combinations[0] = 1; // One way to reach 0.
		for (int scoreWay : scoreWays) {
			for (int j = scoreWay; j <= k; ++j) {
				combinations[j] += combinations[j - scoreWay];
			}
			// System.out.println(Arrays.toString(combinations));
		}
		System.out.println(Arrays.toString(combinations));
		return combinations[k];
	}

	/**
	 * permutations[i] : the number of distinct sequences of individual plays
	 * when score is i
	 */
	public long countPermutations(int k, List<Integer> scoreWays) {
		long[] permutations = new long[k + 1]; // use long to avoid overflow
		permutations[0] = 1; // One way to reach 0.
		for (int i = 0; i <= k; ++i) {
			for (int scoreWay : scoreWays) {
				if (i >= scoreWay) {
					permutations[i] += permutations[i - scoreWay];
				}
			}
		}
		System.out.println(Arrays.toString(permutations));
		return permutations[k];
	}
}
