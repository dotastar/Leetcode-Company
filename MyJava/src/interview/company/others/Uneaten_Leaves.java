package interview.company.others;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * K caterpillars are eating their way through N leaves. Each caterpillar falls
 * from leaf to leaf in a unique sequence. All caterpillars start at a twig in
 * position 0 and fall onto the leaves at positions between 1 and N. Each
 * caterpillar i has an associated 'jump-number' Ai. A caterpillar with jump
 * number j eats leaves at positions that are multiples of j. It will proceed in
 * the order j, 2j, 3j, ... till it reaches the end of leaves, then it stops and
 * builds it cocoon.
 * 
 * Given a set A of K elements, K <= 15, N <= 10^9, we need to determine the
 * number of uneaten leaves.
 * 
 * @author yazhoucao
 *
 */
public class Uneaten_Leaves {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Uneaten_Leaves.class);
	}

	public int countUneatenLeaves(int N, int[] A) {
		
		
		
		return 0;
	}

	@Test
	public void test1() {

		assertTrue(true);
	}
}
