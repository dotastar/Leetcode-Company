package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.Test;

/**
 * Top K Frequent Words
 * Fair
 * Data Structure
 * 
 * Given a composition with different kinds of words, return a list of the top K
 * most frequent words in the composition.
 * 
 * Assumptions
 * 
 * the composition is not null and is not guaranteed to be sorted
 * K >= 1 and K could be larger than the number of distinct words in the
 * composition, in this case, just return all the distinct words
 * 
 * Return
 * 
 * a list of words ordered from most frequent one to least frequent one (the
 * list could be of size K or smaller than K)
 * 
 * Examples
 * 
 * Composition = ["a", "a", "b", "b", "b", "b", "c", "c", "c", "d"], top 2
 * frequent words are [“b”, “c”]
 * Composition = ["a", "a", "b", "b", "b", "b", "c", "c", "c", "d"], top 4
 * frequent words are [“b”, “c”, "a", "d"]
 * Composition = ["a", "a", "b", "b", "b", "b", "c", "c", "c", "d"], top 5
 * frequent words are [“b”, “c”, "a", "d"]
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Top_K_Frequent_Words {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Top_K_Frequent_Words.class);
	}

	public String[] topKFrequent(String[] combo, int k) {
		if (k == 0 || combo.length == 0) // sanity check
			return new String[0];
		// calculate frequency
		final Map<String, Integer> freqMap = new HashMap<>();
		for (String word : combo) {
			Integer cnt = freqMap.get(word);
			if (cnt == null)
				cnt = 0;
			freqMap.put(word, cnt + 1);
		}
		// roughly sort words by frequency
		PriorityQueue<String> maxHeap = new PriorityQueue<>(k,
				new Comparator<String>() {
					@Override
					public int compare(String str1, String str2) {
						return freqMap.get(str2) - freqMap.get(str1);
					}
				});
		for (String word : freqMap.keySet())
			maxHeap.add(word);
		// find top k frequency words
		k = freqMap.size() < k ? freqMap.size() : k;
		String[] res = new String[k];
		for (int i = 0; i < k && !maxHeap.isEmpty(); i++) {
			res[i] = maxHeap.poll();
		}
		return res;
	}

	@Test
	public void test1() {
		String[] combo = { "a", "a", "b" };
		int k = 1;
		String[] res = topKFrequent(combo, k);
		String[] ans = { "a" };
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(res, ans));
	}
}
