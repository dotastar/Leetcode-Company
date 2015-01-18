package interview.epi.chapter14_sorting;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Q15_Implement_A_Variable_Length_Sort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void indirectSort(String fileName) throws Exception {
		// Stores file records into A.
		Scanner ifs = null;
		List<Integer> A = new ArrayList<>();
		try {
			ifs = new Scanner(new File(fileName));
			while (ifs.hasNextInt()) {
				A.add(ifs.nextInt());
			}
		} finally {
			ifs.close();
		}
		// Indirectly sorts file.
		Collections.sort(A);
		// Outputs file.
		PrintWriter ofs = null;
		try {
			ofs = new PrintWriter(new FileWriter(fileName));
			for (Integer a : A) {
				ofs.println(a);
			}
		} finally {
			ofs.close();
		}
	}

}
