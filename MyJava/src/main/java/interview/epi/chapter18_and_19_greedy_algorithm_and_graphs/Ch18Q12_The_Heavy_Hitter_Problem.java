package interview.epi.chapter18_and_19_greedy_algorithm_and_graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This problem is generalization of problem of find majority element.
 * In practice we may not be interested in a majority token but all tokens whose
 * count exceeds say 1% of the total token count. It is fairly straightforward
 * to show that it it impossible to compute such tokens in a single pass when
 * you have limited memory. However if you are allowed to pass through the
 * sequence twice, it is possible to identify the common tokens.
 * 
 * You are reading a sequence of strings separated by white space. You are
 * allowed to read the sequence twice. Devise an algorithm that uses O(k) space
 * to identify the words that occur at least n/k times, where n is the length of
 * the sequence.
 * 
 * @author yazhoucao
 * 
 */
public class Ch18Q12_The_Heavy_Hitter_Problem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Time: O(n)
	 * because each word in the sequence can be deleted only once, so the total
	 * time spent deleting is O(n), and the rest of the steps inside the outer
	 * loop run in O(1) time.
	 */
	public static List<String> searchFrequentItems(List<String> in, int k) {
		// Finds the candidates which may occur >= n / k times.
		Map<String, Integer> hash = new HashMap<>();
		int n = 0; // Counts the number of strings.
		for (String word : in) {
			hash.put(word, hash.containsKey(word) ? hash.get(word) + 1 : 1);
			++n;
			// Detecting k items in hash, at least one of them must have exactly
			// one in it. We will discard those k items by one for each.
			if (hash.size() == k) {
				List<String> delKeys = new ArrayList<>();
				for (Map.Entry<String, Integer> e : hash.entrySet()) {
					if (e.getValue() - 1 == 0)
						delKeys.add(e.getKey());
					hash.put(e.getKey(), e.getValue() - 1);
				}
				for (String s : delKeys)
					hash.remove(s);
			}
		}
		// Resets hash for the following counting.
		for (String it : hash.keySet()) {
			hash.put(it, 0);
		}
		// Counts the occurrence of each candidate word.
		for (String word : in) {
			Integer it = hash.get(word);
			if (it != null) {
				hash.put(word, it + 1);
			}
		}
		// Selects the word which occurs >= n / k times.
		List<String> ret = new ArrayList<>();
		for (Map.Entry<String, Integer> it : hash.entrySet()) {
			if (n * 1.0 / k <= (double) it.getValue()) {
				ret.add(it.getKey());
			}
		}
		return ret;
	}
}
