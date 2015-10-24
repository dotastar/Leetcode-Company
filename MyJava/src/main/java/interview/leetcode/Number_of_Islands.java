package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of
 * islands. An island is surrounded by water and is formed by connecting
 * adjacent lands horizontally or vertically. You may assume all four edges of
 * the grid are all surrounded by water.
 * 
 * Example 1:
 * 
 * 11110
 * 11010
 * 11000
 * 00000
 * 
 * Answer: 1
 * 
 * Example 2:
 * 
 * 11000
 * 11000
 * 00100
 * 00011
 * 
 * Answer: 3
 * 
 * @author yazhoucao
 *
 */
public class Number_of_Islands {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Number_of_Islands.class);
	}

	/**
	 * An variation of the standard problem: “Counting number of connected
	 * components in a undirected graph”.
	 */
	public int numIslands(char[][] grid) {
		int cnt = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == '1') {
					coloring(grid, i, j);
					cnt++;
				}
			}
		}
		return cnt;
	}

	private void coloring(char[][] grid, int i, int j) {
		if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length
				|| grid[i][j] != '1')
			return;
		grid[i][j] = '#';
		coloring(grid, i + 1, j);
		coloring(grid, i - 1, j);
		coloring(grid, i, j + 1);
		coloring(grid, i, j - 1);
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertTrue(true);
	}
}
