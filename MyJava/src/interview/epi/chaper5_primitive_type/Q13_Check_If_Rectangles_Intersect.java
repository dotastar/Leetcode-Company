package interview.epi.chaper5_primitive_type;

import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A rectangle R whose sides are parallel to the X-axis and Y-axis is said to be
 * XY-aligned. Such a rectangle can be represented by its left-most lower point
 * (Rx,Ry), width Rw, and height Rh.
 * 
 * Problem 5.13
 * Let R and S be XY-aligned rectangles. Write a function which tests if R and S
 * have a nonempty intersection. If the intersection is nonempty, return the
 * rectangle formed by their intersection.
 * 
 * @author yazhoucao
 * 
 */
public class Q13_Check_If_Rectangles_Intersect {

	static String methodName = "";
	static Class<?> c = Q13_Check_If_Rectangles_Intersect.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(1).
	 */
	public static Rectangle intersectRectangle(Rectangle R, Rectangle S) {
		if (isIntersect(R, S)) {
			return new Rectangle(
					Math.max(R.x, S.x),
					Math.max(R.y, S.y),
					Math.min(R.x + R.width, S.x + S.width) - Math.max(R.x, S.x),
					Math.min(R.y + R.height, S.y + S.height)
							- Math.max(R.y, S.y));
		} else {
			return new Rectangle(0, 0, -1, -1); // No intersection.
		}
	}
	
	/**
	 * Assume R is at the left, S is at the right
	 */
	public static boolean isIntersect(Rectangle R, Rectangle S) {
		return R.x <= S.x + S.width && R.x + R.width >= S.x
				&& R.y <= S.y + S.height && R.y + R.height >= S.y;
	}

	public static class Rectangle {
		int x, y, width, height;

		public Rectangle(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public void print(String s) {
			System.out.println(s + x + " " + y + " " + width + " " + height);
		}
	}

	/****************** Unit Test ******************/

	public boolean invokeMethod(Method m, Rectangle a, Rectangle b) {
		try {
			Object[] args = new Object[] { a, b };
			Object res = m.invoke(null, args);
			return (boolean) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
	}
}
