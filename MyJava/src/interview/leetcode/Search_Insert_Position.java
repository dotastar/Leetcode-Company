package interview.leetcode;

/**
 * Given a sorted array and a target value, return the index if the target is
 * found. If not, return the index where it would be if it were inserted in
 * order.
 * 
 * You may assume no duplicates in the array.
 * 
 * Here are few examples. [1,3,5,6], 5 → 2 [1,3,5,6], 2 → 1 [1,3,5,6], 7 → 4
 * [1,3,5,6], 0 → 0
 * 
 * @author yazhoucao
 * 
 */
public class Search_Insert_Position {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(searchInsert(new int[] { 1, 3, 5, 6 }, 5));
		System.out.print(searchInsert(new int[] { 1, 3, 5, 6 }, 2));
		System.out.print(searchInsert(new int[] { 1, 3, 5, 6 }, 7));
		System.out.print(searchInsert(new int[] { 1, 3, 5, 6 }, 0));
		System.out.print(searchInsert(new int[] { 1, 3 }, 0));

		System.out
				.print("\n=====================================================\n");

		System.out.print(bsearch(new int[] { 1, 3, 5, 6 }, 5, 0, 3));
		System.out.print(bsearch(new int[] { 1, 3, 5, 6 }, 2, 0, 3));
		System.out.print(bsearch(new int[] { 1, 3, 5, 6 }, 7, 0, 3));
		System.out.print(bsearch(new int[] { 1, 3, 5, 6 }, 0, 0, 3));
		System.out.print(bsearch(new int[] { 1, 3 }, 0, 0, 1));
	}

	/**
	 * Iterative
	 * 
	 * The return index is l happens in the case when there is no match in the
	 * array, it could be two cases: (they are both derived from the case
	 * l==r==mid.)
	 * 
	 * l==mid, r=mid-1 (target<A[mid]), so the target should be inserted right
	 * in the mid position which mid == l
	 * 
	 * or
	 * 
	 * r==mid, l=mid+1, (target>A[mid]), so the target should be inserted after
	 * mid position which is mid+1 == l
	 * 
	 */
	public static int searchInsert(int[] A, int target) {
		int l = 0;
		int r = A.length - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (A[mid] == target)
				return mid;
			else if (A[mid] > target)
				r = mid - 1;
			else
				l = mid + 1;
		}// end while, l>r, l=r+1

		return l;
	}

	/**
	 * Recursion
	 * 
	 */
	public static int bsearch(int[] A, int target, int l, int r) {
		if (l >= r)
			if (A[l] == target)
				return l;
			else if (target > A[l])
				return l + 1;
			else
				return l;

		int mid = (l + r) / 2;
		if (target == A[mid])
			return mid;
		else if (target > A[mid])
			return bsearch(A, target, mid + 1, r);
		else
			return bsearch(A, target, l, mid - 1);

	}
}
