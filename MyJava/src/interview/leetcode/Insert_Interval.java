package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a set of non-overlapping intervals, insert a new interval into the
 * intervals (merge if necessary).
 * 
 * You may assume that the intervals were initially sorted according to their
 * start times.
 * 
 * Example 1: Given intervals [1,3],[6,9], insert and merge [2,5] in as
 * [1,5],[6,9].
 * 
 * Example 2: Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in
 * as [1,2],[3,10],[12,16].
 * 
 * This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
 * 
 * @author yazhoucao
 * 
 */
public class Insert_Interval {

	public static void main(String[] args) {
		List<Interval> intvls = new ArrayList<Interval>();
		intvls.add(new Interval(1, 5));
		// intvls.add(new Interval(1, 2));
		// intvls.add(new Interval(3, 5));
		// intvls.add(new Interval(6, 7));
		// intvls.add(new Interval(8, 10));
		// intvls.add(new Interval(12, 16));
		// intvls.add(new Interval(19, 20));
		System.out.println(insert(intvls, new Interval(0, 9)).toString());
	}

	/**
	 * Best solution, very neat
	 * Always leave one interval that is not inserted, insert it at last.
	 * Quickly summarize 3 cases:
	 * 1.the newInterval is at the left of current interval
	 * 2.the newInterval is at the right of current interval
	 * 3.the newInterval is intersected with current interval
	 */
	public List<Interval> insert2(List<Interval> intervals, Interval newInterval) {
		List<Interval> res = new ArrayList<>();
		for (int i = 0; i < intervals.size(); i++) {
			Interval curr = intervals.get(i);
			if (newInterval.end < curr.start) { // at left
				res.add(newInterval);
				newInterval = curr; // key!
			} else if (curr.end < newInterval.start) // at right
				res.add(curr);
			else if (curr.start < newInterval.end
					|| curr.end > newInterval.start) { // intersected
				newInterval.start = Math.min(newInterval.start, curr.start);
				newInterval.end = Math.max(newInterval.end, curr.end);
			}
		}
		res.add(newInterval);
		return res;
	}

	/**
	 * Another view of look at it, basically the same solution.
	 * Two while instead of one for.
	 */
	public static List<Interval> insertInterval(List<Interval> intervals,
			Interval newInterval) {
		int i = 0;
		List<Interval> result = new ArrayList<>();
		// Inserts intervals appeared before newInterval.
		while (i < intervals.size() && newInterval.start > intervals.get(i).end) {
			result.add(intervals.get(i++));
		}
		// Merges intervals that overlap with newInterval.
		while (i < intervals.size()
				&& newInterval.end >= intervals.get(i).start) {
			newInterval = new Interval(Math.min(newInterval.start,
					intervals.get(i).start), Math.max(newInterval.end,
					intervals.get(i).end));
			++i;
		}
		result.add(newInterval);
		// Inserts intervals appearing after newInterval.
		result.addAll(intervals.subList(i, intervals.size()));
		return result;
	}

	/**
	 * Like merge interval, you just traverse every interval and see whether
	 * should merge, insert or merge and insert
	 * 
	 * O(n) Time
	 * 
	 * Because you have to return the new list of all elements, so you have to
	 * traverse all intervals no matter what, the time can't be less than O(n).
	 */
	public static List<Interval> insert(List<Interval> intervals,
			Interval newInterval) {
		List<Interval> res = new ArrayList<Interval>();
		boolean inserted = false;
		for (Interval curr : intervals) { // not overlapped, on the left
			if (inserted || curr.end < newInterval.start) {
				res.add(curr); // only insert the cuurent one
			} else if (newInterval.end < curr.start) {
				// last one is the left, curr is the right, both not overlapped
				if (res.isEmpty()
						|| res.get(res.size() - 1).end < newInterval.start) {
					res.add(newInterval); // insert the new one
					inserted = true;
				}
				res.add(curr); // insert the current one
			} else { // overlapped, merge only
				if (curr.start < newInterval.start)
					newInterval.start = curr.start;
				if (curr.end > newInterval.end)
					newInterval.end = curr.end;
			}
		}
		if (!inserted)
			res.add(newInterval);
		return res;
	}

	public static class Interval {
		int start;
		int end;

		Interval() {
			start = 0;
			end = 0;
		}

		Interval(int s, int e) {
			start = s;
			end = e;
		}

		public String toString() {
			return "{" + Integer.toString(start) + "," + Integer.toString(end)
					+ "}";
		}
	}
}
