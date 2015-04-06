package interview.laicode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;
import interview.laicode.utils.Point;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

public class Largest_Set_Of_Points_With_Positive_Slope {
	static Class<?> c = Largest_Set_Of_Points_With_Positive_Slope.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * DP
	 * slope = (y1 - y2) / (x1 - x2)
	 * 
	 * (0,0)
	 * (0,4)
	 * (1,1)
	 * (2,1)
	 * (2,2)
	 * (3,2)
	 * (3,3)
	 * (4,0)
	 * 
	 * M[i] = the number of the largest set of points from 0 to i, that any two
	 * points can form a line with positive slope
	 * M[0] = 0.
	 * Induction rule:
	 * M[i] = max(M[j]) + 1, j = [0, j-1]
	 * if points[i].x > points[j].x && points[i].y > points[j].y)
	 * 
	 */
	public int largest(Point[] points) {
		Arrays.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				if (p1.x != p2.x)
					return p1.x - p2.x;
				else
					return p1.y - p2.y;
			}
		});
		int[] M = new int[points.length];
		int max = 0;
		for (int i = 1; i < points.length; i++) {
			for (int j = i - 1; j >= 0; j--) {
				if (points[i].x > points[j].x && points[i].y > points[j].y) {
					int currSize = M[j] == 0 ? 2 : M[j] + 1;
					M[i] = Math.max(M[i], currSize);
				}
			}
			max = Math.max(M[i], max);
		}
		return max;
	}

	@Test
	public void test1() {
		Point[] points = { new Point(0, 1), new Point(2, 2), new Point(2, 3),
				new Point(3, 1), new Point(3, 3), new Point(3, 3),
				new Point(4, 2), new Point(5, 5), new Point(6, 9) };
		int res = largest(points);
		int ans = 5;
		assertTrue(res == ans);
	}

}
