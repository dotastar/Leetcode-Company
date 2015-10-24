package interview.epi.chapter14_sorting;

import static org.junit.Assert.*;
import interview.AutoTestUtils;
import interview.epi.utils.Interval;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class Q8_The_Interval_Covering_Problem {

	static Class<?> c = Q8_The_Interval_Covering_Problem.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	public static List<Integer> findMinimumVisits(Interval[] intervals) {
		SortedSet<Interval> left = new TreeSet<>(new LeftComp());
		SortedSet<Interval> right = new TreeSet<>(new RightComp());
		for (Interval interval : intervals) {
			left.add(interval);
			right.add(interval);
		}
		List<Integer> s = new ArrayList<>();
		while (!left.isEmpty() && !right.isEmpty()) {
			int b = right.first().right;
			s.add(b);
			// Removes the intervals which intersect with R.cbegin().
			Iterator<Interval> it = left.iterator();
			while (it.hasNext()) {
				Interval interval = it.next();
				if (interval.left > b) {
					break;
				}
				right.remove(interval);
				it.remove();
			}
		}
		return s;
	}

	@Test
	public void simpleTest() {
		Interval[] intervals = new Interval[6];
		intervals[0] = new Interval(1, 4);
		intervals[1] = new Interval(2, 8);
		intervals[2] = new Interval(3, 6);
		intervals[3] = new Interval(3, 5);
		intervals[4] = new Interval(7, 10);
		intervals[5] = new Interval(9, 11);
		List<Integer> ans = findMinimumVisits(intervals);
		assertTrue(ans.size() == 2 && ans.get(0) == 4 && ans.get(1) == 10);
	}

	private static class LeftComp implements Comparator<Interval> {
		public int compare(Interval a, Interval b) {
			if (a.left < b.left) {
				return -1;
			}
			if (a.left > b.left) {
				return 1;
			}
			if (a.right < b.right) {
				return -1;
			}
			if (a.right > b.right) {
				return 1;
			}
			return 0;
		}
	}

	private static class RightComp implements Comparator<Interval> {
		public int compare(Interval a, Interval b) {
			if (a.right < b.right) {
				return -1;
			}
			if (a.right > b.right) {
				return 1;
			}
			if (a.left < b.left) {
				return -1;
			}
			if (a.left > b.left) {
				return 1;
			}
			return 0;
		}
	}
}
