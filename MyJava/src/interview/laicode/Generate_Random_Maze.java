package interview.laicode;

import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * Generate Random Maze
 * Hard
 * Recursion
 * 
 * Randomly generate a maze of size N * N (where N = 2K + 1) whose corridor and
 * wall’s width are both 1 cell. For each pair of cells on the corridor, there
 * must exist one and only one path between them. (Randomly means that the
 * solution is generated randomly, and whenever the program is executed, the
 * solution can be different.). The wall is denoted by 1 in the matrix and
 * corridor is denoted by 0.
 * 
 * Assumptions
 * 
 * N = 2K + 1 and K >= 0
 * the top left corner must be corridor
 * there should be as many corridor cells as possible
 * for each pair of cells on the corridor, there must exist one and only one
 * path between them
 * 
 * Examples
 * 
 * N = 5, one possible maze generated is
 * 
 * 0 0 0 1 0
 * 
 * 1 1 0 1 0
 * 
 * 0 1 0 0 0
 * 
 * 0 1 1 1 0
 * 
 * 0 0 0 0 0
 * 
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Generate_Random_Maze {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Generate_Random_Maze.class);
	}

	Random ran = new Random();

	public int[][] maze(int n) {
		int[][] mat = new int[n][n];
		for (int i = 0; i < n; i++)
			Arrays.fill(mat[i], 1);
		generate(mat, 0, 0);
		return mat;
	}

	/**
	 * DFS try all four neighbors of (x, y)
	 * Assume (i, j) is a valid corridor
	 * 1.check its 4 neighbors, generate all possible positions as the next
	 * corridor, put them in a list
	 * 2.shuffle the list to make sure it has randomness
	 * 3.for each position, check again its validity, if valid, generate on it
	 * 4.repeat step 1 until there is no possible solution for current (i, j).
	 * 
	 */
	private void generate(int[][] maze, int x, int y) {
		int n = maze.length;
		maze[x][y] = 0;
		List<Integer> solutions = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((i == 0 || j == 0) && isValid(maze, x + i, y + j, x, y))
					solutions.add((x + i) * n + y + j);
			}
		}

		Collections.shuffle(solutions);
		for (Integer solu : solutions) {
			// check the current solution's validity every time,
			// cause the maze could be changed due to previous generate()
			if (isValid(maze, solu / n, solu % n, x, y))
				generate(maze, solu / n, solu % n);
		}
	}

	/**
	 * Check if (x,y) is a valid position, (x,y) is the neighbor of (oldx, oldy)
	 * A position is valid if itself and all its neighbors has value 1 except
	 * for its origin (oldx, oldy)
	 */
	private boolean isValid(int[][] maze, int x, int y, int oldx, int oldy) {
		final int N = maze.length;
		if (x < 0 || y < 0 || x >= N || y >= N || maze[x][y] != 1)
			return false;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 || j == 0) {
					if (x + i < 0 || x + i >= N || y + j < 0 || y + j >= N
							|| (x + i == oldx && y + j == oldy))
						continue;
					if (maze[x + i][y + j] != 1)
						return false;
				}
			}
		}
		return true;
	}

	// [0, 0, 0, 1, 0],
	// [1, 1, 0, 1, 0],
	// [0, 1, 0, 0, 0],
	// [0, 0, 1, 1, 0],
	// [1, 0, 0, 0, 0]

	// [0, 0, 1, 1, 0, 0, 0],
	// [1, 0, 0, 0, 0, 1, 0],
	// [0, 1, 1, 1, 0, 1, 0],
	// [0, 0, 0, 1, 1, 0, 0],
	// [0, 1, 1, 0, 0, 0, 1],
	// [0, 0, 0, 0, 1, 0, 0],
	// [0, 1, 0, 1, 1, 1, 0]

	@Test
	public void test1() {
		int n = 7;
		int times = 5;
		for (int i = 0; i < times; i++) {
			int[][] res = maze(n);
			printMat(res);
		}
	}

	private void printMat(int[][] mat) {
		for (int i = 0; i < mat.length; i++)
			System.out.println(Arrays.toString(mat[i]));
		System.out.println();
	}
}
