package interview.company.zenefits;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

/**
 * Zenefits
 * OA
 * 
 * https://www.hackerrank.com/challenges/ide-identifying-comments
 * 
 * Your task is to write a program, which accepts as input, a C or C++ or Java
 * program and outputs only the comments from those programs. Your program will
 * be tested on source codes of not more than 200 lines.
 * 
 * Single Line Comments:
 * // this is a single line comment
 * x = 1; // a single line comment after code
 * 
 * Multi Line Comments:
 * /* This is one way of writing comments *\/
 * /* This is a multiline
 * comment. These can often
 * be useful*\/
 * 
 * 
 * Precautions
 * Do not add any leading or trailing spaces. Remove any leading white space
 * before comments, including from all lines of a multi-line comment. Do not
 * alter the line structure of multi-line comments except to remove leading
 * whitespace. i.e. Do not collapse them into one line.
 * 
 * @author yazhoucao
 *
 */
public class Identifying_Comments {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Identifying_Comments.class);
	}

	/**
	 * Tricky part is how to handle block comment:
	 * /* ... could be multiple lines.... *\/
	 * 
	 * This can be decomposed to three parts:
	 * 1.starting block : /* .....
	 * 2.middle block : code in between, could have no block sign in the line
	 * 3.ending block : .... *\/
	 * 
	 * We print the line in all three cases.
	 * We use the start block to turn on middle block, and use the end block to
	 * shut middle block, middle is like a switch.
	 */
	public void printComments() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String line = null;
			boolean middleBlock = false;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.contains("//")) {
					int idx = line.indexOf("//");
					System.out.println(line.substring(idx));
				} else {
					boolean startBlock = line.contains("/*");
					boolean endBlock = line.contains("*/");
					if (startBlock)
						middleBlock = true;
					if (endBlock)
						middleBlock = false;
					if (startBlock || endBlock || middleBlock)
						System.out.println(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {
		String source = " // my  program in C++\n" + "#include <iostream>\n"
				+ "/* Test Block in one line */\n" + "int a = b + c;\n"
				+ "/** playing around in\n" + "a new programming language **/\n"
				+ "using namespace std;\n" + "int main ()\n" + "{\n"
				+ "cout << \"Hello World\";\n"
				+ "cout << \"I'm a C++ program\"; //use cout\n" + "return 0;\n" + "}";
		System.setIn(new ByteArrayInputStream(source.getBytes()));
		printComments();
		assertTrue(true);
	}
}
