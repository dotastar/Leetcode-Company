package interview.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given n points on a 2D plane, find the maximum number of points that lie on
 * the same straight line.
 * 
 * @author yazhoucao
 * 
 */
public class Max_Points_on_a_Line {
	/**
	 * Definition for a point.
	 * class Point {
	 * int x;
	 * int y;
	 * Point() { x = 0; y = 0; }
	 * Point(int a, int b) { x = a; y = b; }
	 * }
	 */
	public static void main(String[] args) {
		double pos0 = 0 / (double) 1, neg0 = 0 / (double) -1;
		String equals = pos0 == neg0 ? "==" : "!=";
		System.out.println(String.format("%f %s %f", pos0, equals, neg0));
		Map<Double, String> map = new HashMap<>();
		map.put(pos0, "Positive Zero");
		map.put(neg0, "Negative Zero");
		System.out.println(map.get(pos0) + ", " + map.get(neg0)
				+ " has different hash code!");

		Point[] pts0 = { new Point(1, 1), new Point(1, 1), new Point(1, 1),
				new Point(1, 1) };
		// common case
		Point[] pts1 = { new Point(2, 2), new Point(1, 2), new Point(2, 4),
				new Point(4, 5), new Point(2, 3), new Point(0, 4),
				new Point(4, 0), new Point(3, 6), new Point(4, 8),
				new Point(8, 10), new Point(1, -10), new Point(12, 15),
				new Point(5, 10), new Point(4, 4), new Point(6, 12) };

		// perpendicular to X axis, parallel to Y
		Point[] pts2 = { new Point(4, 0), new Point(4, -1), new Point(4, -5) };

		// repeated points
		Point[] pts3 = { new Point(1, 1), new Point(1, 1), new Point(2, 3) };

		// all are repeated points
		Point[] pts4 = { new Point(1, 1), new Point(1, 1), new Point(1, 1) };

		// two different repeated points
		Point[] pts5 = { new Point(3, 10), new Point(0, 2), new Point(0, 2),
				new Point(3, 10) };

		// check slope float decision
		Point[] pts6 = { new Point(84, 250), new Point(0, 0), new Point(1, 0),
				new Point(0, -70), new Point(0, -70), new Point(1, -1),
				new Point(21, 10), new Point(42, 90), new Point(-42, -230) };

		// perpendicular to Y axis, parallel to X
		// check if dealing with -0.0 and 0.0 of slope
		Point[] pts7 = { new Point(2, 3), new Point(3, 3), new Point(-5, 3) };

		Point[] pts8 = { new Point(560, 248), new Point(0, 16),
				new Point(30, 250), new Point(950, 187), new Point(630, 277),
				new Point(950, 187), new Point(-212, -268),
				new Point(-287, -222), new Point(53, 37),
				new Point(-280, -100), new Point(-1, -14), new Point(-5, 4),
				new Point(-35, -387), new Point(-95, 11), new Point(-70, -13),
				new Point(-700, -274), new Point(-95, 11), new Point(-2, -33),
				new Point(3, 62), new Point(-4, -47), new Point(106, 98),
				new Point(-7, -65), new Point(-8, -71), new Point(-8, -147),
				new Point(5, 5), new Point(-5, -90), new Point(-420, -158),
				new Point(-420, -158), new Point(-350, -129),
				new Point(-475, -53), new Point(-4, -47), new Point(-380, -37),
				new Point(0, -24), new Point(35, 299), new Point(-8, -71),
				new Point(-2, -6), new Point(8, 25), new Point(6, 13),
				new Point(-106, -146), new Point(53, 37), new Point(-7, -128),
				new Point(-5, -1), new Point(-318, -390), new Point(-15, -191),
				new Point(-665, -85), new Point(318, 342), new Point(7, 138),
				new Point(-570, -69), new Point(-9, -4), new Point(0, -9),
				new Point(1, -7), new Point(-51, 23), new Point(4, 1),
				new Point(-7, 5), new Point(-280, -100), new Point(700, 306),
				new Point(0, -23), new Point(-7, -4), new Point(-246, -184),
				new Point(350, 161), new Point(-424, -512), new Point(35, 299),
				new Point(0, -24), new Point(-140, -42), new Point(-760, -101),
				new Point(-9, -9), new Point(140, 74), new Point(-285, -21),
				new Point(-350, -129), new Point(-6, 9), new Point(-630, -245),
				new Point(700, 306), new Point(1, -17), new Point(0, 16),
				new Point(-70, -13), new Point(1, 24), new Point(-328, -260),
				new Point(-34, 26), new Point(7, -5), new Point(-371, -451),
				new Point(-570, -69), new Point(0, 27), new Point(-7, -65),
				new Point(-9, -166), new Point(-475, -53), new Point(-68, 20),
				new Point(210, 103), new Point(700, 306), new Point(7, -6),
				new Point(-3, -52), new Point(-106, -146), new Point(560, 248),
				new Point(10, 6), new Point(6, 119), new Point(0, 2),
				new Point(-41, 6), new Point(7, 19), new Point(30, 250) };

		System.out.println(maxPoints(pts0) + ":4");// 4
		System.out.println(maxPoints(pts1) + ":6");// 6
		System.out.println(maxPoints(pts2) + ":3");// 3
		System.out.println(maxPoints(pts3) + ":3");// 3
		System.out.println(maxPoints(pts4) + ":3");// 3
		System.out.println(maxPoints(pts5) + ":4");// 4
		System.out.println(maxPoints(pts6) + ":6");// 6
		System.out.println(maxPoints(pts7) + ":3");// 3
		System.out.println(maxPoints(pts8) + ":22");// 22
	}

	/**
	 * Time: O(n^2)
	 * For each point in the array, fix it, and try every point after it in the
	 * array and see if slopes are the same, if same then they are on the same
	 * line.
	 * Fix one point, and traverse the others, use this fashion to avoid
	 * calculate the intercept of a line.
	 * Because one point is fixed, so if two lines have the same slope and
	 * share the same fixed point, then they must be the same line.
	 * 
	 * Two keys:
	 * 1.fixed one point, traverse the rest, the global max is guaranteed in it.
	 * 2.be careful when store double in HashMap, double has -0.0, +0.0, they
	 * are equal in value, but have different hash code.
	 * When converting int to double: 0.0 + (double) (a.y - b.y) / (a.x - b.x);
	 * 3.how to represent the slope when P1.x==P2.x (Double.POSITIVE_INFINITY)
	 */
	public static int maxPoints(Point[] points) {
		int globalMax = 0;
		Map<Double, Integer> slopeCnt = new HashMap<>();
		for (int i = 0; i < points.length - 1; i++) {
			Point pi = points[i];
			int repeat = 1, maxCnt = 0;
			for (int j = i + 1; j < points.length; j++) {
				Point pj = points[j];
				if (pi.x == pj.x && pi.y == pj.y) {
					repeat++;
				} else {
					double slope = slope(pi, pj);
					Integer cnt = slopeCnt.get(slope);
					if (cnt == null)
						cnt = 0;
					slopeCnt.put(slope, ++cnt);
					maxCnt = Math.max(cnt, maxCnt);
				}
			}
			globalMax = Math.max(repeat + maxCnt, globalMax);
			slopeCnt.clear(); // clear Map for each comparison
		}
		return globalMax;
	}

	public static double slope(Point a, Point b) {
		if (a.x == b.x)
			return Double.POSITIVE_INFINITY;
		else
			// ***Important 0.0 + is for the case when
			// (double)0/-1 is -0.0, so we should use
			// 0.0+-0.0=0.0 to solve 0.0 !=-0.0
			return 0.0 + (double) (a.y - b.y) / (a.x - b.x);
	}

	public static class Point {
		int x;
		int y;

		public Point() {
			x = 0;
			y = 0;
		}

		public Point(int a, int b) {
			x = a;
			y = b;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
}
