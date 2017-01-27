package interview.company.epic;

import java.util.ArrayList;
import java.util.List;

/**
 * The stepping number:
 * 
 * A number is called as a stepping number if the adjacent digits are having a
 * difference of 1. For eg. 8,343,545 are stepping numbers. While 890, 098 are
 * not. The difference between a ‘9’ and ‘0’ should not be considered as 1.
 * 
 * Given start number(s) and an end number(e) your function should list out all
 * the stepping numbers in the range including both the numbers s & e.
 * 
 * @author yazhoucao
 * 
 */
public class SteppingNumber {

	public static void main(String[] args) {
		SteppingNumber o = new SteppingNumber();
		System.out.println(o.findStepNumber(1, 1000));
	}

	/**
	 * For every number between s and e, break down it into digits, check every
	 * digits is valid or not by calculating its difference with neighbors.
	 * 
	 * Time: O(n), n = e - s
	 */
	public List<Integer> findStepNumber(int s, int e) {
		List<Integer> res = new ArrayList<>();
		List<Integer> digits = new ArrayList<>();
		for (int i = s; i <= e; i++) {
			int num = i;
			while (num != 0) { // break down to digits
				digits.add(num % 10);
				num /= 10;
			}

			// check if every digit is valid
			boolean valid = true;
			int len = digits.size();
			if (digits.size() > 1
					&& ((Math.abs(digits.get(0) - digits.get(1)) > 1) || 
						(Math.abs(digits.get(len - 1) - digits.get(len - 2)) > 1)))
				valid = false;
			if (!valid) {
				digits.clear();
				continue;
			}
			for (int j = 1; j < digits.size() - 1; j++) {
				if (Math.abs(digits.get(j) - digits.get(j + 1)) > 1
						|| Math.abs(digits.get(j) - digits.get(j - 1)) > 1) {
					valid = false;
					break;
				}
			}

			if (valid)
				res.add(i);
			digits.clear();

		}
		return res;
	}
}
