package interview.company.amazon;

public class MagicMirror {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Find whether word2 is the rotation of word1
	 */
	public boolean isRotation(String word1, String word2){
		if(word1.length()!=word2.length())
			return false;
		String concatenate = word2+word2;
		return concatenate.contains(word1);
	}
}
