package interview.leetcode;

/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number
 * of rows like this: (you may want to display this pattern in a fixed font for
 * better legibility)
 * P A H N
 * A P L S I I G
 * Y I R
 * 
 * And then read line by line: "PAHNAPLSIIGYIR" Write the code that will take a
 * string and make this conversion given a number of rows:
 * 
 * string convert(string text, int nRows); convert("PAYPALISHIRING", 3) should
 * return "PAHNAPLSIIGYIR".
 * 
 * @author yazhoucao
 * 
 */
public class ZigZag_Conversion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(convert("PAYPALISHIRING", 3));
	}

	/**
	 * Second time
	 * Calculate the distance of the next char of the same row. 
	 */
	public String convert2(String s, int nRows) {
		if (nRows == 1)
			return s;
		StringBuilder sb = new StringBuilder();
		final int step = 2 * nRows - 2; // distance between normal chars
		int zigzagDist = step; // distance between normal char and zigzag char 
		for (int i = 0; i < nRows; i++) {
			for (int j = i; j < s.length(); j += step) {
				sb.append(s.charAt(j)); // append normal column
				// first and last row don't have zigzag column
				if (i > 0 && i < nRows - 1 && j + zigzagDist < s.length())
					sb.append(s.charAt(j + zigzagDist)); // append zigzag column
			}
			zigzagDist -= 2;
		}
		return sb.toString();
	}

	/**
	 * Time: O(n), Space:O(n), n = s.length()
	 * There are two kinds of node, one is the common node consists of each 
	 * vertical column, the other consists of the zigzag path, in the middle of
	 * every two vertical column.
	 * 1      7
	 * 2    6 8
	 * 3  5   9
	 * 4      10
	 * 
	 * Two key points:
	 * 1.the distance between each column node is: step = 2 * nRows - 2;
	 * 2.the distance between each column node and it's zigzag node is: 
	 * zigzag = j + step - 2 * i; (and the node in the first and last row don't have
	 * zigzag node)
	 */
	public static String convert(String s, int nRows) {
		if (nRows <= 1)
			return s;
		int len = s.length();
		int step = 2 * nRows - 2;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nRows; i++) {
			for (int j = i; j < len; j += step) {
				sb.append(s.charAt(j));
				int zigzag = j + step - 2 * i;
				if (i != 0 && i != nRows - 1 && zigzag < len)
					sb.append(s.charAt(zigzag));
			}
		}
		return sb.toString();
	}
}
