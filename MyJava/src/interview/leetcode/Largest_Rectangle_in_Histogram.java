package interview.leetcode;

import java.util.Stack;

/**
 * Given n non-negative integers representing the histogram's bar height where
 * the width of each bar is 1, find the area of largest rectangle in the
 * histogram
 * 
 * For example, Given height = [2,1,5,6,2,3], return 10.
 * 
 * @author yazhoucao
 * 
 */
public class Largest_Rectangle_in_Histogram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] heights0 = new int[] { 1, 2, 3, 4, 5, 3, 4, 2, 6, 7, 5 };// { 1, 1
																		// };
		int[] heights1 = new int[] { 2, 0, 2 };
		int[] heights2 = new int[] { 2, 3, 1, 5, 1, 1, 1, 1, 1, 1, 1 };
		int[] heights3 = new int[] { 0, 9 };
		int[] heights4 = new int[] { 1, 2, 2 };
		int[] heights5 = new int[] { 0, 2, 0 };
		int[] heights6 = new int[] { 4, 3, 2, 1, 1, 1 };
		System.out.print(largestRectangleArea(heights0));
		System.out.print(largestRectangleArea(heights1));
		System.out.print(largestRectangleArea(heights2));
		System.out.print(largestRectangleArea(heights3));
		System.out.print(largestRectangleArea(heights4));
		System.out.print(largestRectangleArea(heights5));
		System.out.print(largestRectangleArea(heights6));
		System.out.println();
		System.out.print(largestRectangleArea_Imroved(heights0));
		System.out.print(largestRectangleArea_Imroved(heights1));
		System.out.print(largestRectangleArea_Imroved(heights2));
		System.out.print(largestRectangleArea_Imroved(heights3));
		System.out.print(largestRectangleArea_Imroved(heights4));
		System.out.print(largestRectangleArea_Imroved(heights5));
		System.out.print(largestRectangleArea_Imroved(heights6));
		System.out.println();
		System.out.print(largestRectangleArea_Imroved2(heights0));
		System.out.print(largestRectangleArea_Imroved2(heights1));
		System.out.print(largestRectangleArea_Imroved2(heights2));
		System.out.print(largestRectangleArea_Imroved2(heights3));
		System.out.print(largestRectangleArea_Imroved2(heights4));
		System.out.print(largestRectangleArea_Imroved2(heights5));
		System.out.print(largestRectangleArea_Imroved2(heights6));
		System.out.println();
		System.out.print(largestRectangleArea_Imroved3(heights0));
		System.out.print(largestRectangleArea_Imroved3(heights1));
		System.out.print(largestRectangleArea_Imroved3(heights2));
		System.out.print(largestRectangleArea_Imroved3(heights3));
		System.out.print(largestRectangleArea_Imroved3(heights4));
		System.out.print(largestRectangleArea_Imroved3(heights5));
		System.out.print(largestRectangleArea_Imroved3(heights6));
		System.out.println();
	}

	/**
	 * A very important observation:
	 * Scanning histogram from left to right, if left to right is in increasing
	 * order, then the maximum area between left to right only need one
	 * traversal by backward traverse from right to left to calculate area.
	 */

	/**
	 * Naive, brute force solution area = (r-l) * min(height)
	 * 
	 * O(n^2) Time, Time out
	 */
	public static int largestRectangleArea(int[] height) {
		int maxArea = 0;
		for (int end = 0; end < height.length; end++) {
			int h = height[end];
			for (int i = end; i >= 0; i--) {
				h = Math.min(height[i], h);
				int currArea = (end - i + 1) * h;
				maxArea = Math.max(currArea, maxArea);
			}
		}
		return maxArea;
	}

	/**
	 * Naive solution improved by pruning, O(n^2) Time
	 * 
	 * when height[r+1] >= height[r], you don't have to calculate current r,
	 * because you still have to calculate r+1 because area r+1 must > r.
	 * 
	 * Note several points:
	 * 
	 * 1.don't pruning the last elements
	 * 
	 * 2.there is height[r] = 0 case, should be noticed.
	 * 
	 * 3.the comparison order is r+1 compare to r, not r compare to r-1, there
	 * are several advantages of this.
	 * 
	 */
	public static int largestRectangleArea_Imroved(int[] height) {
		int maxArea = 0;
		for (int end = 0; end < height.length; end++) {
			if (end != height.length - 1 && height[end + 1] >= height[end])
				continue;
			int h = height[end];
			for (int i = end; i >= 0; i--) {
				h = Math.min(height[i], h);
				int currArea = (end - i + 1) * h;
				maxArea = Math.max(currArea, maxArea);
			}
		}
		return maxArea;
	}

	/**
	 * Traverse the histograms, use Stack to store the index of the increasing
	 * order of histograms.
	 * 
	 * Every time it meets a histogram which the height[i] lower than the left
	 * highest one (the top of stack), it stops, and calculate the area of every
	 * possible area from the left highest to the histogram that is the last one
	 * that higher than the current height[i] (keep popping out and calculating
	 * the area until the top of the stack is lower than the current height[i]).
	 * 
	 * Then continue the for loop(push the highest in).
	 * 
	 * At last, calculate the area from highest to lowest that the stack left.
	 * 
	 * O(n) Time
	 * 
	 */
	public static int largestRectangleArea_Imroved2(int[] height) {
		int max = 0;
		Stack<Integer> lefts = new Stack<Integer>(); // left histograms
		for (int i = 0; i < height.length; i++) {
			if (lefts.isEmpty() || height[i] > height[lefts.peek()]) {
				lefts.push(i);
			} else { // current (height[i]) <= height[stack.peek()]
				int hidx = lefts.pop(); // height[left] is the min
				// if lefts.isEmpty(), the width is from 0 to i which is i
				int width = lefts.isEmpty() ? i : i - (lefts.peek() + 1);
				max = height[hidx] * width > max ? height[hidx] * width : max;
				i--; // important, equals to a while loop
			}
		}

		while (!lefts.isEmpty()) { // reach the end
			int hidx = lefts.pop(); // height[left] is the min
			int width = lefts.isEmpty() ? height.length : height.length
					- lefts.peek() - 1;
			max = height[hidx] * width > max ? height[hidx] * width : max;
		}
		return max;
	}

	/**
	 * Another way of writing it as the above solution
	 * 
	 * Every time it meets a histogram lower than the left highest one (the top
	 * of stack), then pop out it and calculate its area, keep popping out and
	 * calculating until current is the highest one, then continue the for
	 * loop(push the highest in).
	 * 
	 */
	public static int largestRectangleArea_Imroved3(int[] height) {
		int max = 0;
		int len = height.length;
		Stack<Integer> lefts = new Stack<Integer>();
		for (int i = 0; i < len; i++) {
			while (!lefts.isEmpty() && height[i] < height[lefts.peek()]) {
				int idx = lefts.pop();
				// if lefts.isEmpty(), the width is from 0 to i which is i
				int width = lefts.isEmpty() ? i : i - (lefts.peek() + 1);
				int area = width * height[idx];
				max = area > max ? area : max;
			}
			lefts.push(i);
		}

		while (!lefts.isEmpty()) { // reach the end
			int idx = lefts.pop();
			int width = lefts.isEmpty() ? len : len - (lefts.peek() + 1);
			int area = width * height[idx];
			if (area > max)
				max = area;
		}
		return max;
	}

	/**
	 * Second time of above solution
	 */
	public int largestRectangleArea3(int[] height) {
		int maxArea = 0;
		Stack<Integer> stk = new Stack<Integer>();
		for (int r = 0; r < height.length; r++) {
			int newH = height[r], h = Integer.MAX_VALUE;
			while (!stk.isEmpty() && newH < height[stk.peek()]) {
				int l = stk.pop();
				int width = stk.isEmpty() ? r : r - (stk.peek() + 1);
				h = Math.min(h, height[l]);
				maxArea = Math.max(maxArea, width * h);
			}
			stk.push(r);
		}
		while (!stk.isEmpty()) {
			int l = stk.pop();
			int width = stk.isEmpty() ? height.length : height.length - (stk.peek() + 1);
			maxArea = Math.max(maxArea, width * height[l]);
		}
		return maxArea;
	}

	/**
	 * Best solution
	 * Use Stack, Time: O(n)
	 * Combine the above two whiles together
	 */
	public int largestRectangleArea_Best(int[] height) {
		int max = 0;
		Stack<Integer> stk = new Stack<Integer>();
		for (int i = 0; i <= height.length; i++) {
			while (!stk.isEmpty() && (i == height.length || height[i] < height[stk.peek()])) {
				int curr = stk.pop();
				int width = stk.isEmpty() ? i : i - stk.peek() - 1;
				int area = width * height[curr];
				max = area > max ? area : max;
			}
			stk.push(i);
		}
		return max;
	}

}
