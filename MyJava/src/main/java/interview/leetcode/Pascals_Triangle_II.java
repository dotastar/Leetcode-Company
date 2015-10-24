package interview.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an index k, return the kth row of the Pascal's triangle.
 * 
 * For example, given k = 3, Return [1,3,3,1].
 * 
 * Note: Could you optimize your algorithm to use only O(k) extra space?
 * 
 * @author yazhoucao
 * 
 */
public class Pascals_Triangle_II {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++)
			System.out.println(getRow(i).toString());
	}

	/**
	 * Time: 1+2+3+4+...+n-1 = O(n^2), Space: O(n)
	 */
	public static List<Integer> getRow(int rowIndex) {
		Integer[] row = new Integer[rowIndex + 1];
		row[0] = 1;
		for (int i = 1; i <= rowIndex; i++) {
			// Notice! Reversely traverse, to maintain the old value unchanged
			for (int j = i - 1; j >= 1; j--)
				row[j] = row[j] + row[j - 1]; // row[i-1][j] + row[i-1][j-1]
			row[i] = 1;
		}
		return Arrays.asList(row);
	}

	/**
	 * Second time
	 */
	public List<Integer> getRow2(int rowIndex) {
		List<Integer> row = new ArrayList<>();
		row.add(1);
		for (int i = 0; i < rowIndex; i++) {
			List<Integer> newrow = new ArrayList<>();
			newrow.add(1);
			for (int j = 1; j < row.size(); j++)
				newrow.add(row.get(j) + row.get(j - 1));
			newrow.add(1);
			row = newrow;
		}
		return row;
	}
}
