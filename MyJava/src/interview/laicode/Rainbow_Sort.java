package interview.laicode;

/**
 * Rainbow Sort
 * Fair
 * Data Structure
 * 
 * Given an array of balls, where the color of the balls can only be Red, Green
 * or Blue, sort the balls such that all the Red balls are grouped on the left
 * side, all the Green balls are grouped in the middle and all the Blue balls
 * are grouped on the right side. (Red is denoted by -1, Green is denoted by 0,
 * and Blue is denoted by 1).
 * 
 * Examples
 * 
 * {0} is sorted to {0}
 * {1, 0} is sorted to {0, 1}
 * {1, 0, 1, -1, 0} is sorted to {-1, 0, 0, 1, 1}
 * 
 * Corner Cases
 * 
 * What if the input array is null? In this case, we should not do anything.
 * What if the input array is of length zero? In this case, we should not do
 * anything as well.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Rainbow_Sort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Time: O(n)
	 * 3挡板，4区域, i,j同向而行，k反向而行
	 * 1.0~i: -1
	 * 2.i~j: 0
	 * 3.j~k: unvisited
	 * 4.k~end:1
	 */
	public int[] rainbowSort(int[] array) {
		for (int i = 0, j = 0, k = array.length - 1; j <= k;) {
			if (array[j] == 0) {
				j++;
			} else if (array[j] == -1) {
				swap(array, i++, j++);
			} else { // array[j] == 1
				swap(array, j, k--);
			}
		}
		return array;
	}

	private void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
}
