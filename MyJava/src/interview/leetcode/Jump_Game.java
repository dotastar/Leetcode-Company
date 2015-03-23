package interview.leetcode;

/**
 * Given an array of non-negative integers, you are initially positioned at the
 * first index of the array.
 * 
 * Each element in the array represents your maximum jump length at that
 * position.
 * 
 * Determine if you are able to reach the last index.
 * 
 * For example:
 * 
 * A = [2,3,1,1,4], return true.
 * 
 * A = [3,2,1,0,4], return false.
 * 
 * @author yazhoucao
 * 
 */
public class Jump_Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A0 = new int[] { 2, 3, 1, 1, 4 };
		int[] A1 = new int[] { 3, 2, 1, 0, 4 };
		System.out.println(canJump(A0));
		System.out.println(canJump(A1));
	}

	/**
	 * DP O(n^2), timeout
	 * 
	 * M[i] means if it can reach the end start from i
	 * Base case M[n-1] = true;
	 * Induction rule M[i] = if for any j that M[j] == true, i+1 <= j <= i+A[i]]
	 */
	public static boolean canJump(int[] A) {
		if (A.length <= 1)
			return true;
		boolean[] reachable = new boolean[A.length]; // M[i]
		reachable[A.length - 1] = true;
		for (int i = A.length - 2; i >= 0; i--) {
			for (int j = i + 1; j <= i + A[i]; j++) {
				if (reachable[j]) {
					reachable[i] = true;
					break;
				}
			}
		}
		return reachable[0];
	}

	/**
	 * Linear Time Solution, greedy
	 * 
	 * In Jump Game, we can save the farthest position we can reach when we go
	 * through the array. Every time we move, we will decrease the step. When we
	 * updating it, the step remaining for us is also updated.
	 * 
	 * For example, for array A = [2, 3, 1, 1, 4], when we visit A[0], we update
	 * the maxReach to 2, and step to 2. When we visit A[1], we update maxReach
	 * to 1 + 3 = 4. And step is updated to 3. We will continue doing this until
	 * we reach the end or step getting to zero. If step getting to zero before
	 * we reach the end, it means that we can’t move forward so that we should
	 * return false.
	 * 
	 */
	public static boolean canJump2(int[] A) {
		int steps = A[0];
		int reach = A[0];
		for (int i = 1; i < A.length; i++) {
			if (steps == 0)
				return false;
			if (reach >= A.length - 1)
				return true;
			steps--;
			if (A[i] > steps) {
				steps = A[i];
				reach = i + A[i];
			}
		}
		return true;
	}

	public boolean canJump1(int[] A) {
		int maxReach = 0;
		for (int i = 0; i < A.length - 1; i++) {
			if (maxReach < i + A[i])
				maxReach = i + A[i];
			if (maxReach == i)
				return false;
		}
		return true;
	}

	/**
	 * Linear Time, more concise version
	 */
	public boolean canJump_concise(int[] A) {
		int furthestReach = 0;
		for (int i = 0; i <= furthestReach && furthestReach < A.length - 1; ++i) {
			furthestReach = Math.max(furthestReach, i + A[i]);
		}
		return furthestReach >= A.length - 1;
	}

}
