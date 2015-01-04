package interview.company.epic;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Edge Detection:
 * Two-dimensional array representation of an image can also be represented by a
 * one-dimensional array of W*H size, where W represent row and H represent
 * column size and each cell represent pixel value of that image. you are also
 * given a threshold X. For edge detection, you have to compute difference of a
 * pixel value with each of it's adjacent pixel and find maximum of all
 * differences. And finally compare if that maximum difference is greater than
 * threshold X. if so, then that pixel is an edge pixel and have to display it.
 * 
 * @author yazhoucao
 * 
 */
public class EdgeDetection {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(EdgeDetection.class);
	}

	/**
	 * Time: O(n)
	 * 
	 * Remember the position = i*width + j, for a two dimensional point(i, j)
	 * Be careful of the boundary, up and down boundary is easy,
	 * left and right is error prone.
	 */
	public List<Integer> processEdges(int[] image, int width, int threshold) {
		int n = image.length;
		List<Integer> output = new ArrayList<>();
		for (int pos = 0; pos < n; pos++) {
			// pos = i*width + j
			// calculate index of up down left right
			int up = pos - width, down = pos + width;
			int left = pos % width == 0 ? -1 : pos - 1;
			int right = pos % width == width - 1 ? n : pos + 1;
			// calculate the difference of up down left right
			int upDiff = up >= 0 ? Math.abs(image[up] - image[pos]) : 0;
			int downDiff = down < n ? Math.abs(image[down] - image[pos]) : 0;
			int leftDiff = left >= 0 ? Math.abs(image[left] - image[pos]) : 0;
			int rightDiff = right < n ? Math.abs(image[right] - image[pos]) : 0;

			int maxDiff = max(max(upDiff, downDiff), max(leftDiff, rightDiff));
			if (maxDiff > threshold)
				output.add(pos);
		}
		return output;
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}

	@Test
	public void test1() {
		/**
		 * 1,3,4,5,
		 * 2,4,6,3,
		 * 5,6,7,3,
		 * 8,2,12,52
		 */
		int image[] = { 1, 3, 4, 5, 2, 4, 6, 3, 5, 6, 7, 3, 8, 2, 12, 52 };
		int threshold = 3;
		int width = 4;
		List<Integer> res = processEdges(image, width, threshold);
		int[] ans = { 9, 10, 11, 12, 13, 14, 15 };
		for (int i = 0; i < res.size(); i++) {
			int pos = res.get(i);
			System.out.println("Idx:" + pos + "\tval:" + image[pos]);
			assertTrue(pos == ans[i]);
		}

	}

}
