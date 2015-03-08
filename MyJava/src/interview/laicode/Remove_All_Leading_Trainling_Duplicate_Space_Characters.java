package interview.laicode;

public class Remove_All_Leading_Trainling_Duplicate_Space_Characters {

	public static void main(String[] args) {
		System.out.println(allUnique("aa"));
	}

	public static boolean allUnique(String word) {
		int[] dict = new int[8];
		for (int i = 0; i < word.length(); i++) {
			int chVal = word.charAt(i);
			int idx = chVal / 32;
			int offset = chVal % 32;
			if (((dict[idx] >> offset) & 1) == 1)
				return false;
			dict[idx] |= 1 << offset;
		}
		return true;
	}
}
