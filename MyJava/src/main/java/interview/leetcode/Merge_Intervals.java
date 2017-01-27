package interview.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Given a collection of intervals, merge all overlapping intervals.
 * 
 * For example, Given [1,3],[2,6],[8,10],[15,18], return [1,6],[8,10],[15,18].
 * 
 * @author yazhoucao
 * 
 */
public class Merge_Intervals {

	public static void main(String[] args) {
		List<Interval> intvls = new ArrayList<Interval>();
		intvls.add(new Interval(1, 4));
		intvls.add(new Interval(2, 5));
		System.out.println(merge(intvls).toString());
	}

	/**
	 * Sort and merge
	 * 
	 * O(nlgn) Time
	 */
	public static List<Interval> merge(List<Interval> intervals) {
		List<Interval> res = new ArrayList<Interval>();
		if (intervals.size() == 0)
			return res;
		Collections.sort(intervals, new Comparator<Interval>() {
			@Override
			public int compare(Interval o1, Interval o2) {
				return o1.start - o2.start;
			}
		});

		Interval current = intervals.get(0);
		for (Interval next : intervals) {
			if (current.end >= next.start) { // end >= start, merge
				current.end = next.end > current.end ? next.end : current.end;
			} else {
				res.add(current);
				current = next;
			}
		}
		res.add(current); // add last
		return res;
	}

	/**
	 * Second time
	 */
	public List<Interval> merge2(List<Interval> intervals) {
		Collections.sort(intervals, new Comparator<Interval>() {
			@Override
			public int compare(Interval o1, Interval o2) {
				return o1.start - o2.start;
			}
		});

		List<Interval> res = new ArrayList<>();
		for (Interval curr : intervals) {
			if (res.size() == 0)
				res.add(curr);
			else {
				Interval last = res.get(res.size() - 1);
				if (last.end < curr.start)
					res.add(curr);
				else if (curr.end > last.end)
					last.end = curr.end;
			}
		}
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
