package interview.company.epic;

import java.util.ArrayList;
import java.util.List;

/**
 * You can swap only two consecutive elements. You have to show all steps to
 * convert a string into another string (both strings will be anagrams of each
 * other). E.g. GUM to MUG
 * 
 * GUM
 * GMU
 * MGU
 * MUG
 * 
 * @author yazhoucao
 * 
 */
public class SwapCharacter {

	public static void main(String[] args) {
		SwapCharacter o = new SwapCharacter();
		System.out.println(o.findSwapPath("GUM", "MUG"));
	}

	/**
	 * Similar to permutation, but it can only swap neighbors
	 * There are two steps:
	 * For each character of target: (loop)
	 * 1.Find it in s, say it is s[i]
	 * 2.Swap s[i] to the same position in target, record every swap
	 */
	public List<String> findSwapPath(String s, String target) {
		List<String> res = new ArrayList<>();
		char a[] = s.toCharArray();
		int i, j = 0; // i for a, j for b
		// match one character of b at a time
		while (j < target.length()) {
			i = j;
			res.add(new String(a));
			// find target[j] in a's array which is finally a[i]
			while (a[i] != target.charAt(j))
				i++;

			// keep swapping till a[i] is at a[j] so that a[j] = target[j],
			while (i > j) {
				swap(a, i, i - 1);
				res.add(new String(a));
				i--;
			} // end, they are matched in the same character at position j
			j++;
		}
		return res;
	}

	private void swap(char[] arr, int i, int j) {
		char temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
