package interview.epi.chapter15_bst;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * Design a data structure that implements the following methods:
 * insert(s, c), which adds client s(string) with credit c(integer), overwriting
 * any existing entry for s.
 * remove(s), lookup(s),
 * addAll(c), the effect of which is to increment the number of credits for each
 * client currently present by C.
 * max(), returns any one client with the highest number of credits.
 * 
 * @author yazhoucao
 * 
 */
public class Q21_Add_Credits {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * It's HashMap + BST
	 * 
	 * addAll() - O(1) - offset
	 * lookup() - O(1) - HashMap
	 * max() - O(1) - TreeMap 
	 * insert() - O(logn) - TreeMap
	 * remove() - O(logn) - TreeMap
	 * 
	 * @author yazhoucao
	 *
	 */
	public static class ClientsCreditsInfo {
		private int offset;
		private Map<String, Integer> credits;
		private NavigableMap<Integer, Set<String>> inverseCredits;

		public ClientsCreditsInfo() {
			offset = 0;
			credits = new HashMap<>();
			inverseCredits = new TreeMap<>();
		}

		public void insert(String s, int c) {
			remove(s);
			credits.put(s, c - offset);
			Set<String> set = inverseCredits.get(c - offset);
			if (set == null) {
				set = new HashSet<>();
				inverseCredits.put(c - offset, set);
			}
			set.add(s);
		}

		public boolean remove(String s) {
			Integer creditsIt = credits.get(s);
			if (creditsIt != null) {
				inverseCredits.get(creditsIt).remove(s);
				if (inverseCredits.get(creditsIt).isEmpty()) {
					inverseCredits.remove(creditsIt);
				}
				credits.remove(s);
				return true;
			}
			return false;
		}

		public int lookup(String s) {
			Integer it = credits.get(s);
			return it == null ? -1 : it + offset;
		}

		public void addAll(int C) {
			offset += C;
		}

		public String max() {
			return inverseCredits.isEmpty() ? "" : inverseCredits.lastEntry().getValue().iterator().next();
		}
	}
}
