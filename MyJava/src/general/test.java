package general;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isReflc("ab","aa"));
		System.out.println(isGreyCode((byte)6, (byte)7));
		System.out.println(isGreyCode((byte)0, (byte)1));
		System.out.println(isGreyCode((byte)1, (byte)0));
	}

	public static int isGreyCode(byte term1, byte term2){
		int res = term1^term2;
		if(res==0)	return 0;
		int count=0;
		for(int i=0; i<8; i++){
			if(count>1)
				return 0;
			if(((res>>i)&1)==1)
				count++;
		}
		return 1;
	}
	
	public static int isReflc(String word1, String word2){
		return ((word1.length() == word2.length()) && ((word1+word1).indexOf(word2) != -1))?1:-1;
	}
	
	
	public static int isReflc2(String word1, String word2){
		if(word1.length() != word2.length())
			return -1;
		if((word1+word1).indexOf(word2) != -1)
			return -1;
		
		return 1;
	}
}
