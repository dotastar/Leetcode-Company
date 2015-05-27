package interview.company.others;

import interview.AutoTestUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Stack;

import org.junit.Test;

/**
 * Zenefits
 * 
 * https://www.hackerrank.com/challenges/stockmax
 * 
 * Your algorithms have become so good at predicting the market that you now
 * know what the share price of Wooden Orange Toothpicks Inc. (WOT) will be for
 * the next N days.
 * 
 * Each day, you can either buy one share of WOT, sell any number of shares of
 * WOT that you own, or not make any transaction at all. What is the maximum
 * profit you can obtain with an optimum trading strategy?
 * 
 * Input
 * The first line contains the number of test cases T. T test cases follow:
 * The first line of each test case contains a number N. The next line contains
 * N integers, denoting the predicted price of WOT shares for the next N days.
 * 
 * Output
 * Output T lines, containing the maximum profit which can be obtained for the
 * corresponding test case.
 * 
 * Constraints
 * 1 <= T <= 10
 * 1 <= N <= 50000
 * 
 * All share prices are between 1 and 100000
 * 
 * Sample Input
 * 3
 * 3
 * 5 3 2
 * 3
 * 1 2 100
 * 4
 * 1 3 1 2
 * 
 * Sample Output
 * 0
 * 197
 * 3
 * 
 * @author yazhoucao
 *
 */
public class Stock_Maximize {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Stock_Maximize.class);
	}

	public void startProgramIntInput() {
		try (DataInputStream br = new DataInputStream(System.in)) {
			int cases = br.readInt();
			for (int i = 0; i < cases; i++) {
				int size = br.readInt(); // read size
				int[] A = new int[size];
				for (int j = 0; j < size; j++) { // read data
					A[j] = br.readInt();
				}

				System.out.println(maxProfit_optimized(A));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startProgramStringInput() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			int cases = Integer.parseInt(br.readLine()); // read # cases
			for (int i = 0; i < cases; i++) {
				br.readLine(); // for size
				String line = br.readLine();
				String[] data = line.split(" ");
				int[] A = new int[data.length];
				for (int j = 0; j < data.length; j++) {
					A[j] = Integer.parseInt(data[j]);
				}
				System.out.println(maxProfit(A));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * For each day check whether you can find a better deal on days to come. If
	 * not sell it today.
	 * The trick is if you do proper pre-processing, the look-ahead for better
	 * deal takes O(1).
	 */
	public long maxProfit(int[] A) {
		if (A.length <= 1)
			return 0;
		long[] highestAfter = new long[A.length];
		long highest = A[A.length - 1];
		for (int i = A.length - 1; i >= 0; i--) {
			highest = A[i] > highest ? A[i] : highest;
			highestAfter[i] = highest;
		}
		long profit = 0;
		for (int i = 0; i < A.length; i++) {
			profit += highestAfter[i] - A[i];
		}
		return profit;
	}

	/**
	 * Optimization:
	 * we don't have to use an array to store the highestAfter,
	 * we can use a Stack to store the index of the hightestAfter
	 */
	public long maxProfit_optimized(int[] A) {
		Stack<Integer> highest = new Stack<>();
		for (int i = A.length - 1; i >= 0; i--) {
			if (highest.isEmpty() || A[highest.peek()] < A[i])
				highest.push(i);
		}
		long profit = 0;
		for (int i = 0; i < A.length; i++) {
			if (i == highest.peek())
				highest.pop();
			else
				profit += A[highest.peek()] - A[i];
		}
		return profit;
	}

	@Test
	public void test1() {
		int[] input = { 2, 3, 5, 3, 2, 3, 1, 2, 100 };
		// 0, 197
		ByteBuffer bbuf = ByteBuffer.allocate(input.length * 4);
		for (int num : input)
			bbuf.putInt(num);
		System.setIn(new ByteArrayInputStream(bbuf.array()));
		startProgramIntInput();
	}

	@Test
	public void test2() {
		int[] input = { 1, 4, 1, 3, 1, 2 }; // 3
		ByteBuffer bbuf = ByteBuffer.allocate(input.length * 4);
		for (int num : input)
			bbuf.putInt(num);
		System.setIn(new ByteArrayInputStream(bbuf.array()));
		startProgramIntInput();
	}

	@Test
	public void test3() {
		int[] input = { 1, 8, 1, 4, 2, 0, 1, 3, 2, 4 }; // 15
		ByteBuffer bbuf = ByteBuffer.allocate(input.length * 4);
		for (int num : input)
			bbuf.putInt(num);
		System.setIn(new ByteArrayInputStream(bbuf.array()));
		startProgramIntInput();
	}
}
