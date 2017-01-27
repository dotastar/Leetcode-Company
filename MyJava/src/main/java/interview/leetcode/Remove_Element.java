package interview.leetcode;

/**
 * Given an array and a value, remove all instances of that value in place and
 * return the new length.
 * 
 * The order of elements can be changed. It doesn't matter what you leave beyond
 * the new length.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Remove_Element {

	public static void main(String[] args) {

	}

	public static int removeElement(int[] A, int elem) {
		int count = 0;
		for (int i = 0; i < A.length; i++) {
			if (A[i] != elem) {
				A[count] = A[i];
				count++;
			}
		}
		return count;
	}

	/**
	 * Second time
	 */
	public int removeElement2(int[] A, int elem) {
		int length = 0;
		for (int i = 0; i < A.length; i++) {
			if (A[i] != elem)
				A[length++] = A[i];
		}
		return length;
	}
}
