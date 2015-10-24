package interview.epi.chapter12_searching;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Suppose you were given a file containing roughly one billion IP addresses,
 * each of which is a 32-bit unsigned integer. How would you find an IP address
 * that is not in the file.
 * Assume you have unlimited hard drive space but only two megabytes of RAM at
 * your disposal.
 * 
 * @author yazhoucao
 * 
 */
public class Q12_Find_The_Missing_IP {

	public static void main(String[] args) {
		int n = 990000;
		Random r = new Random();
		File missingFile = new File("missing.txt");
		Set<Integer> hash = new HashSet<>();
		FileOutputStream ofs = null;
		try {
			try {
				ofs = new FileOutputStream(missingFile);
				OutputStreamWriter osw = new OutputStreamWriter(ofs);
				for (int i = 0; i < n; ++i) {
					int x;
					do {
						x = r.nextInt(1000000);
					} while (!hash.add(x));
					osw.write(x + "\n");
				}
				osw.close();
			} finally {
				if (ofs != null) 
					ofs.close();
			}
			FileInputStream ifs = null;
			try {
				ifs = new FileInputStream(missingFile);
				BufferedInputStream bis = new BufferedInputStream(ifs);
				int missing = findMissingElement(bis);
				assert (!hash.contains(missing));
				System.out.println(missing);
			} finally {
				if (ifs != null) {
					ifs.close();
				}
			}
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		} finally {
			// Remove file after the execution.
			missingFile.delete();
		}
	}

	public static int findMissingElement(InputStream ifs) throws IOException {
		int[] counter = new int[1 << 16];
		ifs.mark(Integer.MAX_VALUE);
		Scanner scan = new Scanner(ifs);
		while (scan.hasNextInt())
			counter[scan.nextInt()]++;

		for (int i = 0; i < counter.length; i++) {
			// Finds one bucket contains less than (1 << 16) elements.
			if (counter[i] < (1 << 16)) {
				BitSet bitVec = new BitSet(1 << 16);
				ifs.reset();
				scan = new Scanner(ifs);
				while (scan.hasNextInt()) {
					int x = scan.nextInt();
					if (i == (x >> 16)) {
						// Gets the lower 16 bits of x.
						bitVec.set(((1 << 16) - 1) & x);
					}
				}
				ifs.close();
				scan.close();
				for (int j = 0; j < (1 << 16); j++) {
					if (!bitVec.get(j))
						return (i << 16) | j;
				}
			}
		}

		throw new RuntimeException("no missing element");
	}
}
