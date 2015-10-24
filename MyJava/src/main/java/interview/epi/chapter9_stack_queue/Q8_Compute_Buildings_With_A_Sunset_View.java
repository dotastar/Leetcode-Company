package interview.epi.chapter9_stack_queue;

import static org.junit.Assert.*;
import interview.AutoTestUtils;
import interview.epi.utils.Pair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import org.junit.Test;

/**
 * Problem 9.8
 * You are given with a series of buildings that have windows facing west. The
 * buildings are in a straight line, and if a building b is to the east of a
 * building whose height is greater than or equal to b, it is not possible to
 * view the sunset from b.
 * 
 * Design an algorithm that processes buildings presented in an online fashion
 * and records the buildings that have a view of the sunset. The number of
 * buildings is not known in advance. Buildings are given in east-to-west order
 * and are specified by their heights. Minimize the amount of memory your
 * algorithm uses.
 * 
 * @author yazhoucao
 * 
 */
public class Q8_Compute_Buildings_With_A_Sunset_View {
	static Class<?> c = Q8_Compute_Buildings_With_A_Sunset_View.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Similar to insertion sort
	 * When a new building comes, if it is taller than the lowest, pop the
	 * lowest out, keep popping out until it is the lowest in the result list,
	 * then push it in.
	 * Each building is pushed and popped both at most once,
	 * Therefore Time: O(n), Space: O(n).
	 */
	public static LinkedList<Pair<Integer, Integer>> examineBuildingsWithSunset(
			InputStream sin) {
		Stack<Pair<Integer, Integer>> buildings = new Stack<>();
		try (ObjectInputStream osin = new ObjectInputStream(sin)) {
			int idx = 0;
			while (true) {
				int height = (Integer) osin.readObject();
				while (!buildings.isEmpty()
						&& buildings.peek().getSecond() <= height)
					buildings.pop();
				buildings.push(new Pair<Integer, Integer>(idx++, height));
			}
		} catch (IOException e) {
			// Catching when there no more objects in InputStream
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return new LinkedList<Pair<Integer, Integer>>(buildings);
	}

	@Test
	public void test0() { // random test
		Random r = new Random();
		try {
			for (int times = 0; times < 1000; ++times) {
				int n = r.nextInt(10000) + 1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				for (int i = 0; i < n; ++i) {
					Integer height = r.nextInt(2 * n) + 1;
					oos.writeObject(height);
				}
				ByteArrayInputStream sin = new ByteArrayInputStream(
						baos.toByteArray());
				LinkedList<Pair<Integer, Integer>> res = examineBuildingsWithSunset(sin);
				Pair<Integer, Integer> prev = res.pop();
				System.out.println(prev);
				while (!res.isEmpty()) {
					Pair<Integer, Integer> current = res.pop();
					System.out.println(current);
					assertTrue(prev.getFirst() < current.getFirst());
					assertTrue(prev.getSecond() > current.getSecond());
					prev = current;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
