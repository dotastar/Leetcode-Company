package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Find the total area covered by two rectilinear rectangles in a 2D plane.
 * 
 * Each rectangle is defined by its bottom left corner and top right corner as
 * shown in the figure.
 * Rectangle Area
 * 
 * Assume that the total area is never beyond the maximum possible value of int.
 * 
 * @author yazhoucao
 *
 */
public class Rectangle_Area {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Rectangle_Area.class);
	}

	/**
	 * S1 + S2 - intersection(S1, S2)
	 * S1 = (x2 - x1) * (y2 - y1)
	 * S2 = ((x4 - x3) * (y4 - y3)
	 * intersection(S1, S2) = length * width
	 * 
	 * The difficulty is calculate the overlapped area
	 * 
	 * The key is to figure out left, right, top, bottom coordinates
	 * 
	 */
	public int computeArea(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		int area = (x2 - x1) * (y2 - y1) + (x4 - x3) * (y4 - y3); // S1 + S2
		int left = Math.max(x1, x3);
		int right = Math.min(x2, x4);
		int bottom = Math.max(y1, y3);
		int top = Math.min(y2, y4);
		if (left < right && bottom < top) // when there is an overlap
			area -= (right - left) * (top - bottom);
		return area;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
