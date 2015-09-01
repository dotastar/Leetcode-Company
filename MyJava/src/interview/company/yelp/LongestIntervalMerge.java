package interview.company.yelp;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

/*
 * Your previous Plain Text content is preserved below:
 * 
 * """
 * 
 * Given a list of line segments, what is the maximum number of line segments
 * that we can choose (from the input list) such that they don't overlap each
 * other.
 * 
 * (1, 3) (2, 4) - Is an overlap
 * 
 * (1, 3) (3, 10) - Is not an overlap
 * 
 * Input:
 * 
 * line_segments = [(1, 3), (2, 4), (3, 10), (3, 5), (6, 10)]
 * 
 * Output:
 * 3 (maximum number of line segments we can pick)
 * 
 * """
 */
public class LongestIntervalMerge {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(LongestIntervalMerge.class);
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}

	// Test cases:
	// null
	// length = 0
	// length = 1
	// no overlap
	// all overlap
	// normal / random
	// unsorted intervals

	/**
	 * Dynamic Programming
	 * M[i] : the maximum number of line segments that ends at intervals[i]
	 * M[i] = max(M[j]) + 1, for all j < i that intervals[j] does not overlap
	 * with intervals[i]
	 **/
	public int mostLineSegs(Interval[] intervals) {
		if (intervals == null || intervals.length == 0)
			return 0;
		int length = intervals.length;
		int[] M = new int[length];
		Arrays.sort(intervals, new Comparator<Interval>() {
			@Override
			public int compare(Interval in1, Interval in2) {
				return in1.start - in2.start;
			}
		});

		int max = 1;
		for (int i = 0; i < length; i++) {
			M[i] = 1;
			for (int j = i - 1; j >= 0; j--) {
				if (!overlap(intervals[j], intervals[i])) {
					M[i] = Math.max(M[i], M[j] + 1);
					max = Math.max(M[i], max);
				}
			}
		}
		return max;
	}

	/**
	 * Assume in1.start <= in2.start
	 */
	private boolean overlap(Interval in1, Interval in2) {
		return in2.start <= in1.end;
	}

	static class Interval {
		int start;
		int end;
	}
}
