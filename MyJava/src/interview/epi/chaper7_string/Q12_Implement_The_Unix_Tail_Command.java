package interview.epi.chaper7_string;

import interview.epi.Utils;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Problem 7.12
 * Implement the UNIX tail command
 * 
 * @author yazhoucao
 * 
 */
public class Q12_Implement_The_Unix_Tail_Command {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Key: Random Access, read backward while counting the number of lines has
	 * been read.
	 */
	public static String tail(String fileName, int N) throws IOException {
		RandomAccessFile filePtr = new RandomAccessFile(fileName, "r");

		filePtr.seek(filePtr.length() - 1);
		long fileSize = filePtr.length(), newLineCount = 0;
		StringBuilder lastNLines = new StringBuilder();
		// Reads file in reverse looking for '\n'.
		for (long i = fileSize - 1; i != -1; i--) {
			filePtr.seek(i);
			int readByte = filePtr.readByte();
			char c = (char) readByte;
			if (c == '\n') {
				++newLineCount;
				if (newLineCount > N) {
					break;
				}
			}
			lastNLines.append(c);
		}

		Utils.close(filePtr);

		lastNLines.reverse();
		return lastNLines.toString();
	}
}
