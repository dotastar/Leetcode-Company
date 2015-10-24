package interview.company.epic;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement LookAndSay function. For example, first, let user input a number,
 * say 1. Then, the function will generate the next 10 numbers which satisfy
 * this condition:
 * 1, 11,21,1211,111221,312211...
 * explanation: first number 1, second number is one 1, so 11. Third number is
 * two 1(previous number), so 21. next number one 2 one 1, so 1211 and so on...
 * 
 * @author yazhoucao
 * 
 */
public class CountAndSay {

	public static void main(String[] args) {
		CountAndSay o = new CountAndSay();
		for (int i = 1; i < 9; i++)
			System.out.println(o.lookAndSay(i).toString());
	}

	/**
	 * Same as Count And Say of LeetCode
	 * Very intuitive: count and if different then say
	 */
	public List<Integer> lookAndSay(int n) {
		assert n > 0;
		List<Integer> prev = new ArrayList<>();
		prev.add(1); // initalize with 1
		for (int i = 1; i < n; i++) {
			List<Integer> curr = new ArrayList<>();
			int cnt = 1;
			for (int j = 0; j < prev.size(); j++) {
				// notice here deal with last element specially
				if (j == prev.size() - 1 || prev.get(j) != prev.get(j + 1)) {
					curr.add(cnt);
					curr.add(prev.get(j));
					cnt = 1;
				} else
					cnt++;
			}

			prev = curr;
		}

		return prev;
	}
}
