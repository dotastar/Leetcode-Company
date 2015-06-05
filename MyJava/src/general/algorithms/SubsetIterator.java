package general.algorithms;

import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

/**
 * Given an String/array, implement an iterator that can yield all the subsets.
 * 1). order is not very important
 * 2). in lexicological order
 * E.g. in lexicological order
 * Input: "abc"
 * Output: “”,”a”,”ab”,”abc”,”ac”,”b”,”bc”,”c”
 * 
 * SubSetItertator iter = new SubSetIterator(“abc”);
 * while (iter.hasNext()) {
 *** System.out.println(iter.next());
 * }
 * 
 * @author yazhoucao
 *
 */
public abstract class SubsetIterator {

	public static void main(String[] args) {
		test_AnyOrder0();
		test_AnyOrder1();
		test_AnyOrder2();
		test_AnyOrder3();
		test_AnyOrder4();
		test_AnyOrder5();
	}

	protected String set;

	public SubsetIterator(String set) {
		this.set = set;
	}

	public abstract boolean hasNext();

	public abstract String next();

	public String originalSet() {
		return set;
	}

	/**
	 * Generate String based on the bit array of set
	 */
	protected String generateStr(byte[] bits) {
		StringBuilder sb = new StringBuilder();
		// System.out.println(Arrays.toString(bits));
		for (int i = 0; i < set.length(); i++) {
			int idx = set.length() - 1 - i;
			int pos = idx % 8; // position at a byte array
			if (((bits[idx / 8] >> pos) & 1) == 1)
				sb.append(set.charAt(i));
		}
		return sb.toString();
	}

	/**
	 * Requirement 1 - any order implementation
	 * 
	 * For the first requirement, an easy natural order
	 * Idea:
	 * Use a bits array to represent it:
	 * E.g. "abc"
	 * 000, 001, 010, 011, 100, 101, 110, 111
	 * 
	 * Next() will become number + 1
	 */
	public static class NaturalOrderIter extends SubsetIterator {

		private byte[] bitsArr;
		private boolean hasNext;
		private byte endingByte;

		public NaturalOrderIter(String set) {
			super(set);
			bitsArr = new byte[(set.length() - 1) / 8 + 1];
			hasNext = true;
			endingByte = 0;
			int lastOnes = (set.length() - 1) % 8 + 1;
			while (lastOnes > 0) {
				lastOnes--;
				endingByte |= 1 << lastOnes;
			}
		}

		/**
		 * as long as all bits are not '1', there exists next.
		 */
		@Override
		public boolean hasNext() {
			if (bitsArr.length == 0)
				return false;
			return hasNext;
		}

		/**
		 * Print current subset and move to next
		 * Time: 3n = O(n), n = length(bitsArr)
		 * 
		 * Notice:
		 * 1.0b11111111 need to conversion --> (byte) 0b11111111
		 * 2.byte array addition don't forget carry between bytes
		 * 3.Mark the last subset
		 */
		@Override
		public String next() {
			if (!hasNext())
				throw new NoSuchElementException();

			String currSubset = generateStr(bitsArr);
			if (!isTheLast()) { // if current is not the last, move to next
				// 0...1, 1...1, 1...1
				for (int i = 0; i < bitsArr.length; i++) {
					if (bitsArr[i] == (byte) 0b11111111) {
						bitsArr[i] = 0; // because of carry, we continue
					} else {
						bitsArr[i]++;
						break;
					}
				}
			} else
				hasNext = false; // current subset is the last

			return currSubset;
		}

		/**
		 * Time: O(n), n = length(bitsArr)
		 */
		private boolean isTheLast() {
			if (bitsArr.length == 0)
				return true;
			for (int i = 0; i < bitsArr.length - 1; i++) {
				if (bitsArr[i] != (byte) 0b11111111)
					return false;
			}
			// if the end byte == endingByte, it is the last bits array.
			return bitsArr[bitsArr.length - 1] == endingByte;
		}
	}

	/**
	 * Requirement 2 - lexicalogical order implementation
	 * 
	 * For the second requirement, it must be lexicological order
	 * Idea:
	 * E.g.
	 * a__ --> ab_ --> abc --> _b_ --> _bc --> __c --> ___
	 * 100 --> 110 --> 111 --> 010 --> 011 --> 001 --> 000
	 */
	public static class LexicOrderIter extends SubsetIterator {

		public LexicOrderIter(String set) {
			super(set);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String next() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/****************************** Test Begin ******************************/

	/**
	 * Easy order iterator test
	 */
	public static void test_AnyOrder0() {
		String set = "";
		SubsetIterator iter = new NaturalOrderIter(set);
		int cnt = 0;
		while (iter.hasNext()) {
			System.out.print(iter.next() + ", ");
			cnt++;
		}
		assertTrue(cnt == (int) Math.pow(2, set.length()));
		System.out.println();
	}

	public static void test_AnyOrder1() {
		String set = "abc";
		SubsetIterator iter = new NaturalOrderIter(set);
		int cnt = 0;
		while (iter.hasNext()) {
			System.out.print(iter.next() + ", ");
			cnt++;
		}
		assertTrue(cnt == (int) Math.pow(2, set.length()));
		System.out.println();
	}

	public static void test_AnyOrder2() {
		String set = "aaaa"; // does not support duplicates
		SubsetIterator iter = new NaturalOrderIter(set);
		int cnt = 0;
		while (iter.hasNext()) {
			System.out.print(iter.next() + ", ");
			cnt++;
		}
		assertTrue(cnt == (int) Math.pow(2, set.length()));
		System.out.println();
	}

	public static void test_AnyOrder3() {
		String set = "12345";
		SubsetIterator iter = new NaturalOrderIter(set);
		int cnt = 0;
		while (iter.hasNext()) {
			System.out.print(iter.next() + ", ");
			cnt++;
		}
		assertTrue(cnt == (int) Math.pow(2, set.length()));
		System.out.println();
	}

	public static void test_AnyOrder4() {
		String set = "123456789";
		SubsetIterator iter = new NaturalOrderIter(set);
		int cnt = 0;
		while (iter.hasNext()) {
			iter.next();
			cnt++;
		}
		System.out.println("Generated " + cnt + " for " + set);
		assertTrue(cnt == (int) Math.pow(2, set.length()));
	}

	public static void test_AnyOrder5() {
		String set = "1234567890abcdefghijklmno";
		SubsetIterator iter = new NaturalOrderIter(set);
		int cnt = 0;
		while (iter.hasNext()) {
			iter.next();
			cnt++;
		}
		// 16777216
		// 13954707
		// 19745532
		System.out.println("Generated " + cnt + " for " + set);
		assertTrue(cnt == (int) Math.pow(2, set.length()));
	}

	/****************************** Test End ******************************/

}
