package interview.laicode;

public class Largest_And_Second_Largest {

	public static void main(String[] args) {
		System.out.println(Integer.MIN_VALUE & 0x7fffffff);
		System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
	}

	public int[] largestAndSecond(int[] A) {
		int[] res;
		if (A[0] > A[1])
			res = new int[] { A[0], A[1] };
		else
			res = new int[] { A[1], A[0] };

		for (int i = 2; i < A.length; i++) {
			if (A[i] >= res[0]) {
				res[1] = res[0];
				res[0] = A[i];
			}
		}
		return res;
	}
}
