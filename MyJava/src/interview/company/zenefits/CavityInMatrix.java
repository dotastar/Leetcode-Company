package interview.company.zenefits;

import interview.AutoTestUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

/**
 * Zenefits
 * Skype onsite
 * 
 * You are given a square map whose sides are n in length. Each of the n x n
 * cells in the has a value denoting the average depth in its area. A cell is
 * called a cavity if and only if each cell adjacent to it has strictly smaller
 * depth, and the cell is not on the border of the map. Two cells are adjacent
 * if they have a common side.
 * You need to find all the cavities on the map and depict them with character
 * X.
 * 
 * Input Format
 * The first line contains an integer n (1 < n < 100) denoting the size of the
 * map. Each of the following lines contains n digits without spaces. A digit
 * (1-9) denotes the depth of the corresponding area.
 * 
 * Output Format
 * Output n lines, echoing the input with no spaces between the digits, but
 * substituting the character X for the depth of each cell that you determine to
 * be a cavity.
 * 
 * Sample Input
 * 4
 * 1112
 * 1912
 * 1892
 * 1234
 * 
 * Sample Output
 * 1112
 * 1X12
 * 18X2
 * 1234
 * 
 * https://www.hackerrank.com/challenges/cavity-map
 * 
 * @author yazhoucao
 *
 */
public class CavityInMatrix {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(CavityInMatrix.class);
	}

	public static void run() {
		int N;
		char[][] mat;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
			N = Integer.valueOf(reader.readLine());
			mat = new char[N][N];
			for (int i = 0; i < N; i++) {
				String line = reader.readLine();
				mat[i] = line.toCharArray();
			}

			cavityMap(N, mat);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void cavityMap(int n, char[][] mat) {
		for (int i = 1; i < n - 1; i++) {
			for (int j = 1; j < n - 1; j++) {
				if (isCavity(mat, i, j))
					mat[i][j] = 'X';
			}
		}

		StringBuffer res = new StringBuffer();
		for (int i = 0; i < n; i++) {
			res.append(mat[i]);
			res.append(System.lineSeparator());
		}
		System.out.println(res.toString());
	}

	private static boolean isCavity(char[][] mat, int i, int j) {
		if (mat[i + 1][j] == 'X' || mat[i + 1][j] >= mat[i][j])
			return false;
		if (mat[i - 1][j] == 'X' || mat[i - 1][j] >= mat[i][j])
			return false;
		if (mat[i][j + 1] == 'X' || mat[i][j + 1] >= mat[i][j])
			return false;
		if (mat[i][j - 1] == 'X' || mat[i][j - 1] >= mat[i][j])
			return false;
		return true;
	}

	@Test
	public void test1() {
		String input = "4\n1112\n1912\n1892\n1234\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		run();
		// 1112
		// 1X12
		// 18X2
		// 1234
	}

	@Test
	public void test2() {
		String input = "6\n462664\n669722\n297288\n796928\n584497\n357431\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		run();
		// 462664
		// 66X722
		// 297288
		// 796X28
		// 5844X7
		// 357431
	}

}
