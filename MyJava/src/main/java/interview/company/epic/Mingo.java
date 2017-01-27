package interview.company.epic;

import java.util.Random;

/**
 * There is a game they termed as Mingo. A random generator (like a speaker
 * standing in a group housie game calls out a number) generates out any number
 * from 1 to 1000.
 * There is a 10X10 matrix. A random generator assigns values to each block of
 * this matrix(within 1 to 1000 obviously).
 * Whenever, a row or a column or a diagonal is fully filled in this 10x10 from
 * the numbers called out by the speaker, its called a 'Mingo'.
 * Write a program that will find first Mingo, then second Mingo, then thirds
 * Mingo...and so forth.
 * 
 * @author yazhoucao
 * 
 */
public class Mingo {

	public static void main(String[] args) {
		Mingo mg = new Mingo();
		mg.initialize();
		mg.doMingo(mg.matrix);
		mg.printMingle();
	}

	int[][] matrix = new int[10][10];
	boolean diagLeftVisited = false;
	boolean diagRightVisited = false;

	public void doMingo(int[][] mat) {
		int m = mat.length;
		int size = m * m;
		Random ran = new Random();
		for (int k = 0; k < size; k++) {
			int i = ran.nextInt(m);
			int j = ran.nextInt(m);
			while (mat[i][j] != 0) {
				i = ran.nextInt(m);
				j = ran.nextInt(m);
			}
			mat[i][j] = ran.nextInt(size);
			checkMingo(mat, i, j);
		}
	}

	private void checkMingo(int[][] mat, int i, int j) {
		int len = mat.length;
		int rowCnt = 0, colCnt = 0, diagLeftCnt = 0, diagRightCnt = 0;
		for (int k = 0; k < len; k++) {
			if (mat[i][k] > 0)
				rowCnt++;
			if (mat[k][j] > 0)
				colCnt++;

			if (!diagLeftVisited && mat[k][k] > 0)
				diagLeftCnt++;
			if (!diagRightVisited && mat[k][len - 1 - k] > 0)
				diagRightCnt++;
		}
		if (rowCnt == len)
			System.out.println("Mingo row :" + i);
		if (colCnt == len)
			System.out.println("Mingo col :" + j);
		if (!diagLeftVisited && diagLeftCnt == len) {
			System.out.println("Mingo diagonal left");
			diagLeftVisited = true;
		}
		if (!diagRightVisited && diagRightCnt == len) {
			System.out.println("Mingo diagonal right");
			diagRightVisited = true;
		}
	}

	private void initialize() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = 0;
			}
		}
	}

	private void printMingle() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}

}
