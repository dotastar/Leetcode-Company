package interview.company.epic;

/**
 * Given a M * N matrix, if the element in the matrix is larger than other 8
 * elements who stay around it, then named that element be mountain point.
 * Print all the mountain points.
 * 
 * @author yazhoucao
 * 
 */
public class MountainPoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void printMountainPoint(int[][] mat) {
		int m = mat.length;
		int n = m == 0 ? 0 : mat[0].length;
		for (int i = 1; i < m - 1; i++) {
			for (int j = 1; j < n - 1; j++) {
				if (isMtPt(mat, i, j)) {
					System.out.println(mat[i][j]);
					j++;
				}
			}
		}
	}

	private boolean isMtPt(int[][] mat, int i, int j) {
		int val = mat[i][j];
		if (mat[i][j - 1] >= val || mat[i][j + 1] >= val
				|| mat[i - 1][j] >= val || mat[i - 1][j - 1] >= val
				|| mat[i - 1][j + 1] >= val || mat[i + 1][j] >= val
				|| mat[i + 1][j - 1] >= val || mat[i + 1][j + 1] >= val)
			return false;
		return true;
	}
}
