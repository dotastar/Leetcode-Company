package interview.company.epic;

import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Given a NXN matrix, starting from the upper left corner of the matrix start
 * printing values in a counter-clockwise fashion.
 * 
 * Eg: Consider N = 4
 * 
 * Matrix= {
 * a, b, c, d,
 * e, f, g, h,
 * i, j, k, l,
 * m, n, o, p
 * }
 * 
 * Your function should output: dcbaeimnoplhgfjk
 * 
 * Another example would be
 * 
 * C I P E
 * R N K U
 * U O W O
 * L E S Y
 * 
 * The function should print: EPICRULESYOUKNOW
 * 
 * @author yazhoucao
 * 
 */
public class SpriralMatrix {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(SpriralMatrix.class);
	}

	public <T> void printSpiralMatrix(T[][] mat) {
		List<T> res = new ArrayList<>();
		int n = mat.length;
		int i = 0, j = n - 1;
		while (n > 0) {
			for (int k = 0; k < n - 1; k++)
				res.add(mat[i][j--]); // move left
			for (int k = 0; k < n - 1; k++)
				res.add(mat[i++][j]); // move down
			for (int k = 0; k < n - 1; k++)
				res.add(mat[i][j++]); // move right
			for (int k = 0; k < n - 1; k++)
				res.add(mat[i--][j]); // move up

			i++;
			j--;
			n -= 2;
		}
		if (mat.length % 2 == 1)
			res.add(mat[mat.length / 2][mat.length / 2]);

		System.out.println(res.toString());
	}

	@Test
	public void test1() {
		Character[][] mat = new Character[4][4];
		mat[0] = new Character[] { 'C', 'I', 'P', 'E' };
		mat[1] = new Character[] { 'R', 'N', 'K', 'U' };
		mat[2] = new Character[] { 'U', 'O', 'W', 'O' };
		mat[3] = new Character[] { 'L', 'E', 'S', 'Y' };
		printSpiralMatrix(mat);
	}

	@Test
	public void test2() {
		Integer[][] mat = new Integer[1][1];
		mat[0] = new Integer[] { 999 };
		printSpiralMatrix(mat);
	}

	@Test
	public void test3() {
		Integer[][] mat = new Integer[5][5];
		mat[0] = new Integer[] { 5, 4, 3, 2, 1 };
		mat[1] = new Integer[] { 6, 19, 18, 17, 16 };
		mat[2] = new Integer[] { 7, 20, 25, 24, 15 };
		mat[3] = new Integer[] { 8, 21, 22, 23, 14 };
		mat[4] = new Integer[] { 9, 10, 11, 12, 13 };
		printSpiralMatrix(mat);
	}
}
