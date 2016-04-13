package interview.leetcode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T,
 * for example: "ACGAATTCCG". When studying DNA, it is sometimes useful to
 * identify repeated sequences within the DNA.
 * 
 * Write a function to find all the 10-letter-long sequences (substrings) that
 * occur more than once in a DNA molecule.
 * 
 * For example,
 * 
 * Given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",
 * 
 * Return:
 * ["AAAAACCCCC", "CCCCCAAAAA"].
 * 
 * @author yazhoucao
 * 
 */
public class Repeated_DNA_Sequences {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Repeated_DNA_Sequences.class);
	}

	/**
	 * We convert a 10-digits DNA sequence to a 20-bit number, then rolling hash
	 * Encoding A, C, G, T to :
	 * A -> 00
	 * C -> 01
	 * G -> 10
	 * T -> 11
	 */
	public List<String> findRepeatedDnaSequences_Improved(String s) {
		List<String> res = new ArrayList<>();
		Map<Integer, Integer> dna = new HashMap<>();
		int hash = 0;
		for (int i = 0; i < 10 && i < s.length(); i++) {
			hash = (hash << 2) | toInt(s.charAt(i));
		}
		final int mask = (1 << 20) - 1;
		dna.put(hash, 1);
		// rolling hash, remove the highest, add a new one to lowest
		for (int i = 10; i < s.length(); i++) {
			hash = (hash << 2) | toInt(s.charAt(i));
			hash &= mask;
			if (!dna.containsKey(hash)) {
				dna.put(hash, 1);
			} else if (dna.get(hash) == 1) {
				res.add(s.substring(i - 9, i + 1));
				dna.put(hash, 2);
			}
		}
		return res;
	}

	/**
	 * Like String Match, Rolling hash + Quaternary system
	 * 
	 * AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT
	 * 01234567890123456789012345678901
	 * ^
	 */
	public List<String> findRepeatedDnaSequences(String s) {
		List<String> res = new ArrayList<>();
		final int highBase = (int) Math.pow(4, 9);
		int hash = 0;
		Map<Integer, Integer> dna = new HashMap<>();
		// initialize the first 10 digit hash
		for (int i = 0; i < 10 && i < s.length(); i++)
			hash = hash * 4 + toInt(s.charAt(i));
		dna.put(hash, 1);
		// rolling hash, remove the highest, add a new one to lowest
		for (int i = 10; i < s.length(); i++) {
			hash %= highBase; // remove the most significant digit
			hash = hash * 4 + toInt(s.charAt(i)); // add one lowest significant

			if (!dna.containsKey(hash)) {
				dna.put(hash, 1);
			} else if (dna.get(hash) == 1) {
				res.add(s.substring(i - 10, i));
				dna.put(hash, 2);
			}

		}
		return res;
	}

	/**
	 * Convert from a char to a integer
	 */
	private int toInt(char c) {
		switch (c) {
		case 'A':
			return 0; // 00
		case 'C':
			return 1; // 01
		case 'G':
			return 2; // 10
		case 'T':
			return 3; // 11
		default:
			throw new RuntimeException("Unexpected Char: " + c);
		}
	}

	@Test
	public void test1() {
		String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
		List<String> res = findRepeatedDnaSequences_Improved(s);
		String[] ans = { "AAAAACCCCC", "CCCCCAAAAA" };
		assertTrue("Wrong size:" + res.size(), ans.length == res.size());
		for (int i = 0; i < res.size(); i++) {
			assertTrue("Wrong ans:" + res.get(i), ans[i].equals(res.get(i)));
		}
	}

	@Test
	public void test2() {
		String s = "AAAAAAAAAAA";
		List<String> res = findRepeatedDnaSequences_Improved(s);
		String[] ans = { "AAAAAAAAAA" };
		assertTrue("Wrong size:" + res.size(), ans.length == res.size());
		for (int i = 0; i < res.size(); i++) {
			assertTrue("Wrong ans:" + res.get(i), ans[i].equals(res.get(i)));
		}
	}

	@Test
	public void test3() {
		String s = "GATGGATACTGCATTCGAACCAGAGCCGGCTTTTGCGGGAC"
				+ "TAGCATGAGGGACTTGGCTGTTGAGGCTGTACGAGGTCAGTCC"
				+ "TCCGGCAGTGCTATCGCAGGAATTTTTGCAACTCCACTGCTTAT"
				+ "AATCCACCAAGTCCAGACTCAAAGCTCAACTCGGGGATCGCACGG"
				+ "TATGGTCACTGTCGCGCATGCAGTAATAGTCCAGACGAACGCACATT"
				+ "GGTCGTCCCCTGAGCCCGTGCCAGCCTAATACTTCTTATGCCTGCGTA"
				+ "AGTAGACTTTGCCAACGTAATCTCATCCTTATGCCAGATTATTAGTTC"
				+ "ATTGAATGTCGGTCGCCGGCGCTCCCGCATTTCTTATCCGCGTATCTTG"
				+ "GGGTCAAGACGTCCCCAGCTTGTTAATACAAGCTACTTTCCCTCGCAAT"
				+ "TACTAAGTTCGTGACAAGCGAATCACGCTAAGATGTTATTGGACTCTAC"
				+ "AGAAATATTGAATTGACAACATTCGTCTGTTCAGATCGTCGTTCACGCC"
				+ "ACTGATAGCGCAGCTCGAGCACTCTGGAGCCACAATGCGGAATGTCCAG"
				+ "AACCTTTGCGCAAGAGTCCGTGGAAAGCATAATCGTGAACAGAATGGCT"
				+ "AGCCGAGGTGCGCAAGGATAGGACCGTCTACACAAAGTATGGGCACCAC"
				+ "GCACATCGACACCCCGTAGTGTGTCAGTCGGCTCAGCGGCTAATGGGTTC"
				+ "GGCGTGAGGAATAGAAATAATAGGCAGTGGTGCCAATTGTGGGGTCTTCT"
				+ "TTTGACTTTCTCATCTCTCTATGAATCAGATCGGCCTCTCGCCCCCGCCG"
				+ "GCCCTCTGGCTTTTTTAAATCCTAGATTGTGCACGTGCCCCGGTTTCCTT"
				+ "CAAGGCAAGTGAAGCGCGTCTTTGCTCTAAACCCACGGCCGTTGCACGGC"
				+ "GCCGAACAGGTGTCTCGGTGCGACCGGAGTGAGCAAGTTCTGTCCGCATC"
				+ "GTATGATTATACCCCCTCCTGTCACGGCTCGGGACTTATCGCACCACAGA"
				+ "TCAGCTCGCAGCCCCGCGCGAGTACTAGGGACGGGAGGGAAACCAAAGAT"
				+ "AATCGTCTTTGCATGGGCCGGCATGTGAATCATTCGTATCATCTTCTGGA"
				+ "GTCTTGTCACGACGATTTTCGATACAGACTGTTGACCCATCTAATCGTGT"
				+ "TGTCAGTCTGGGAACCGTACTTTTTAACCCGTCGTTCGAGCGGCCCGATC"
				+ "AGGGATGCCCGCAGTGTACGGGCACATCGTCGTCTTGGGAACAAAAGCTT"
				+ "GACGGACACCTCTATGCAGACATGAGACGTGAGGCCCCTGCAATAGTGCG"
				+ "GTCACAGGGACCGGCTGTCGATCAGTAGGTATAATCTTGATGTTTGCTGGG"
				+ "AGATTAACAGAGGGCGGAGTTCCGCATCGCCTAACCACTGACAGTCATTGA"
				+ "TAGACGCCTAAGTTTGTCCCTGTAGCTACAGTGGTGGCAAAGTGGCCTTGG"
				+ "ACGGTTCTGCGCTTGTCAATAAGTCTGTCCCAATCACGAGTGAAAAACTAG"
				+ "CTAGGGTCGGTGATGTGTTTTCAATCATATTTCTCCATCCATCCGGGGCTCC"
				+ "CTAGTACGGAGGAAATCTCCGGGTAACTCTGGATCTCCAGCATTGCGTAAGC"
				+ "AAACCGCCAATCGGGCCGCAGTGAGTTCTTAAACTACGGTTTGGCCCTAATCG"
				+ "CACTATTGGGTGTTGTAGATACGATAGCAAGGTGATTATGAAATCAAGGCACG"
				+ "CACGACCTGTACGTTGATCGTCGAGTGCTCTCGAGTTACTTCATGGGTCAGCC"
				+ "ATGCGATTGTCCAAATGGACCGGAAAGTACACTACAAATTGTACCACTGTGCG"
				+ "TTGTACCTCACAAGAACTGTTTGGGTCTACTTACTTTTTACTTGGATCTTTCC"
				+ "TGGTCTCCCTCAGCGTAATTATTCGACACAATGCTGCAGCTGCGTTGTAGTTT"
				+ "TGGCGGTACAGGAAAAATTCTTGTGAGCAACCAGGCCATTCCCTGGAAGGCAG"
				+ "TCCTTGCGAGTATGTTGAGATATATGCTGGGGATGAATTAGAACATTATGCCA"
				+ "TCTAAAGTTTGGATTACCGGGGATTCGGCATACCAAATGGATTCGTTGATTAT"
				+ "AGCCCCCCCACCTCCTTTTAGGTAAAATGCCCAATCCTGGCGCTGAGCAGGAG"
				+ "GCATGTTGGCCTCTTGTCCGGTAGTACGCTTGACTAGTTCCTAGCGGCGCAAA"
				+ "TCACTTGGTCTCTGTCCGTCCTGAATGTTACAAAGCCATATACATGTGTGGAG"
				+ "GTCAAGACATTCCTTATCCGCCCCCCTCGCGATGCAGTTAGATTCGCATTCAAG"
				+ "GTTGAGACCGGAGACCTTCTAACCGGATTTTGGAGTATAGCCCCTTGATAAGAG"
				+ "AAGGAACCATGCTGGGTCTCACGACTATTGAGTTCGGGAAAAGGTGAATGCTCA"
				+ "ACGCTAACGCAGTTTGTTACGCCTGGCGGAATAACGTCAGGGACAAACTATATT"
				+ "CTGGCGCCCCAGTGTGGGCTCTTTGACGACATAGGACGGATTAGGCCGGTCTCA"
				+ "ACCGCCTCGTAACCCAGGAAGCGGTTCTACTCCCGGCTACTTTTTTGGAGTGTG"
				+ "CAAGGACGTTGCACACAGTGGGTGTCAGATCTAGCCCGTCACATGCAAGTGGTC"
				+ "ATATGGATCCCATAATACTCACTGAGTGTCTCGCCAACGGGACTATTAATAGAC"
				+ "ACGGTGATAGACGGTAGGAATTGTGAGATTCATAATTAGTAACAGTAGGAGCGC"
				+ "CGTAGGCCACGGACCGATACATCGGAACCCTTCGCCGAATACGTTAAGGGTTTG"
				+ "CAACCGGGGGTGCATCTAATCCTTGGGGCTGATCTGTCAAAGGCGTCTCATGCG";
		System.out.println("Big dataset test: ");
		long begin = System.currentTimeMillis();
		List<String> res = findRepeatedDnaSequences(s);
		long end = System.currentTimeMillis();
		System.out.println("Quaternery solution, \tTime cost: " + (end - begin)
				+ ", Result size: " + res.size());

		begin = System.currentTimeMillis();
		res = findRepeatedDnaSequences_Improved(s);
		end = System.currentTimeMillis();
		System.out.println("Bit solution, \t\tTime cost: " + (end - begin)
				+ ", Result size: " + res.size());
	}

}