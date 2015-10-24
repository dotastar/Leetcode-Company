package interview.epi.chapter16_recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q3_N_Queens {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<List<String>> nQueens(int n) {
		int[] placement = new int[n];
		List<List<String>> result = new ArrayList<>();
		nQueensHelper(n, 0, placement, result);
		return result;
	}

	private static void nQueensHelper(int n, int row, int[] colPlacement,
			List<List<String>> result) {
		if (row == n) {
			result.add(createOutput(colPlacement));
		} else {
			for (int col = 0; col < n; ++col) {
				colPlacement[row] = col;
				if (isFeasible(colPlacement, row)) {
					nQueensHelper(n, row + 1, colPlacement, result);
				}
			}
		}
	}

	private static List<String> createOutput(int[] colPlacement) {
		List<String> sol = new ArrayList<>();
		for (int aColPlacement : colPlacement) {
			char[] line = new char[colPlacement.length];
			Arrays.fill(line, '.');
			line[aColPlacement] = 'Q';
			sol.add(new String(line));
		}
		return sol;
	}

	private static boolean isFeasible(int[] colPlacement, int row) {
		for (int i = 0; i < row; ++i) {
			int diff = Math.abs(colPlacement[i] - colPlacement[row]);
			if (diff == 0 || diff == row - i) {
				return false;
			}
		}
		return true;
	}
}
