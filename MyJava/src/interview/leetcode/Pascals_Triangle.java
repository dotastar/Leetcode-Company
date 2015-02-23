package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given numRows, generate the first numRows of Pascal's triangle.
 * 
 * For example, given numRows = 5, Return
 * [
 * [1],
 * [1,1],
 * [1,2,1],
 * [1,3,3,1],
 * [1,4,6,4,1]
 * ]
 * 
 * @author yazhoucao
 * 
 */
public class Pascals_Triangle {

	public static void main(String[] args) {
		List<List<Integer>> tri1 = generate(6);
		for (int i = 0; i < tri1.size(); i++) {
			System.out.println(tri1.get(i).toString());
		}
	}

	/**
	 * Time: 1+2+3+4+...+n-1 = O(n^2), Space: O(n^2)
	 */
	public static List<List<Integer>> generate(int numRows) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (numRows == 0)
			return res;
		res.add(new ArrayList<Integer>());
		res.get(0).add(1);
		for (int i = 1; i < numRows; i++) {
			List<Integer> curr = new ArrayList<Integer>();
			curr.add(1);
			List<Integer> prev = res.get(i - 1);
			for (int j = 1; j < prev.size(); j++)
				curr.add(prev.get(j) + prev.get(j - 1));
			curr.add(1);
			res.add(curr);
		}
		return res;
	}

	/**
	 * Second time
	 */
	public List<List<Integer>> generate2(int numRows) {
		List<List<Integer>> res = new ArrayList<>();
		for (int i = 0; i < numRows; i++) {
			List<Integer> row = new ArrayList<>();
			row.add(1);
			if (!res.isEmpty()) {
				List<Integer> prevRow = res.get(res.size() - 1);
				for (int j = 1; j < prevRow.size(); j++)
					row.add(prevRow.get(j) + prevRow.get(j - 1));
				row.add(1);
			}
			res.add(row);
		}
		return res;
	}
}
