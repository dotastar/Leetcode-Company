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
		intvls.add(new Interval(1,5));
//		intvls.add(new Interval(1, 2));
//		intvls.add(new Interval(3, 5));
//		intvls.add(new Interval(6, 7));
//		intvls.add(new Interval(8, 10));
//		intvls.add(new Interval(12, 16));
//		intvls.add(new Interval(19, 20));
		System.out.println(insert2(intvls, new Interval(0, 9)).toString());
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
				res.add(curr);	//only insert the cuurent one
			} else if (newInterval.end < curr.start) {
				// last one is the left, curr is the right, both not overlapped
				if (res.isEmpty() || res.get(res.size() - 1).end < newInterval.start) {
					res.add(newInterval);	//insert the new one
					inserted = true;
				}
				res.add(curr);	//insert the current one
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
	
	/**
	 * Same solution, Second time practice
	 */
    public static List<Interval> insert2(List<Interval> intervals, Interval newInterval) {
        List<Interval> res = new ArrayList<Interval>();
        int len = intervals.size();
        boolean inserted = false;
        for(int i=0; i<len; i++){
            Interval curr = intervals.get(i);
            if(!inserted && newInterval.start<curr.start && newInterval.end<curr.start){
                res.add(newInterval);
                res.add(curr);
                inserted = true;
            }else if(isIntersected(newInterval, curr)){
                merge(newInterval, curr);
                if(!inserted){
                    res.add(newInterval);
                    inserted = true;
                }
            }else
                res.add(curr);
        }
        if(!inserted)
            res.add(newInterval);
        return res;
    }
    
    private static void merge(Interval merger, Interval mergee){
        merger.start = merger.start<mergee.start?merger.start:mergee.start;
        merger.end = merger.end>mergee.end?merger.end:mergee.end;
    }
    
    /**
     * Three cases:
     * 1.start point(itv1) in the middle of itv2
     * 2.end point(itv1) in the middle of itv2
     * 3.itv1 included itv2
     */
    private static boolean isIntersected(Interval itv1, Interval itv2){
        return (itv1.start>=itv2.start && itv1.start<=itv2.end) || (itv1.end>=itv2.start && itv1.end<=itv2.end) || (itv1.start<=itv2.start && itv1.end>=itv2.end);
    }

    
    
    /**
     * Programcreek solution, very neat
     * Quickly summarize 3 cases. Whenever there is intersection, created a new interval.
     * 1.the newInterval is at the left of current interval
     * 2.the newInterval is at the right of current interval
     * 3.the newInterval is intersected with current interval
     * 
     * http://www.programcreek.com/2012/12/leetcode-insert-interval/
     */
    public class Solution {
        public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
     
            ArrayList<Interval> result = new ArrayList<Interval>();
     
            for(Interval interval: intervals){
                if(interval.end < newInterval.start){
                    result.add(interval);
                }else if(interval.start > newInterval.end){
                    result.add(newInterval);
                    newInterval = interval;        
                }else if(interval.end >= newInterval.start || interval.start <= newInterval.end){
                    newInterval = new Interval(Math.min(interval.start, newInterval.start), Math.max(newInterval.end, interval.end));
                }
            }
     
            result.add(newInterval); 
     
            return result;
        }
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
