package interview.company.amazon;

public class EliminateVowelString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * Eliminate all the vowel String in the given input s
	 */
	public static String charFilter(String s){
		char dict[] = {'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'};
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<s.length(); i++){
			boolean contains = false;
			char c = s.charAt(i);
			for(int j=0; j<dict.length; j++){
				if(c==dict[j]){
					contains=true;
					break;
				}
			}
			
			if(!contains)
				sb.append(c);
		}
		return sb.toString();
	}

}
