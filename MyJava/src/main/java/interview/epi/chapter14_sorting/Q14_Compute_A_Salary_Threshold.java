package interview.epi.chapter14_sorting;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

/**
 * Let A be an array of n nonnegative real numbers and S' be a nonnegative real
 * number less than Sum(A[i]), i=0..n-1.
 * Design an algorithm for computing eta such that sum(min(A[i], eta)) = S', if
 * such a eta exists.
 * 
 * @author yazhoucao
 * 
 */
public class Q14_Compute_A_Salary_Threshold {

	@Test
	public static void main(String[] args) {
		Q14_Compute_A_Salary_Threshold o = new Q14_Compute_A_Salary_Threshold();
		Random r = new Random();
		for (int times = 0; times < 10000; ++times) {
			int n;
			double tar;
			if (args.length == 1) {
				n = Integer.parseInt(args[0]);
				tar = r.nextInt(100000);
			} else if (args.length == 2) {
				n = Integer.parseInt(args[0]);
				tar = Integer.parseInt(args[1]);
			} else {
				n = r.nextInt(1000) + 1;
				tar = r.nextInt(100000);
			}
			double[] A = new double[n];
			for (int i = 0; i < n; ++i) {
				A[i] = (double) r.nextInt(10000);
			}
			System.out.println("A = " + Arrays.toString(A));
			System.out.println("tar = " + tar);
			double ret = o.completionSearch(A, tar);
			if (ret != -1.0) {
				System.out.println("ret = " + ret);
				double sum = 0.0;
				for (int i = 0; i < n; ++i) {
					if (A[i] > ret) {
						sum += ret;
					} else {
						sum += A[i];
					}
				}
				tar -= sum;
				System.out.println("sum = " + sum);
				assertTrue(tar < 1.0e-8);
			}
		}
	}

	public double completionSearch(double[] A, double budget) {
		Arrays.sort(A);
		double sum = 0;
		for (double a : A)
			sum += a;
		double threshold = 0;
		for (int i = A.length - 1; i >= 1; i--) {
			if (sum > budget) {
				threshold = A[i - 1];
				sum = sum - A[i] + A[i - 1];
			} else
				break;
		}
		return threshold;
	}
}
