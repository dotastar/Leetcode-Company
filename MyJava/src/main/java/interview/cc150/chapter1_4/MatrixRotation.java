package interview.cc150.chapter1_4;
import java.util.Random;

/*
1.6 Given an image represented by an NxN matrix, 
where each pixel in the image is 4 bytes, write 
a method to rotate the image by 90 degrees.
Can you do this in place?

Assume Rotate 90 degree clockwise
Input: N*N Matrix
*/

public class MatrixRotation{
	public static void main(String[] args){
		System.out.println("Begin");
		int[][] mat = createRandomMat(9);
		printMat(mat);

		System.out.println("After rotation:");
		rotate(mat);
		printMat(mat);
		
		System.out.println("End.");
	}

	/*
	*Rotate matrix by layer, from outer layer to inner layer,
	*In each layer, rotating each element one by one
	*Clockwise order,layer begin from 0 to the half of the length of a matrix
	*Running time: O(n^2)
	*Space: O(1)
	*/
	private static void rotate(int[][] mat){	
		int n = mat.length;
		for(int layer=0; layer<n/2; layer++){
			int tmp;
			int first = layer;	//first element index
			int last = n-layer-1;	//last element index
			
			//change elements from first element to last element, 
			//exclude the last element since it has changed in the beginning
			for(int i=first; i<last; i++){
				int offset = i-first; 
				//save right
				tmp = mat[i][last];
				//right = top
				mat[i][last] = mat[first][i];
				// top = left
				mat[first][i] = mat[last-offset][first];
				//left = bottom
				mat[last-offset][first] = mat[last][last-offset];
				//right to bottom
				mat[last][last-offset] = tmp;
			}
			
		}
		
	}

	
	/*******  Utilities  *******/
	public static void printMat(int[][] mat){
		if(mat==null) return;

		for(int i=0; i<mat.length; i++){
			int[] row = mat[i];
			for(int j=0; j<row.length; j++)
				System.out.print(row[j]+" ");
			System.out.println();
		}
		System.out.println();
	}

	public static int[][] createRandomMat(int n){
		int[][] mat = new int[n][n];
		Random ran = new Random();
		int max = 100;
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				mat[i][j] = ran.nextInt(max);

		return mat;
	}
}