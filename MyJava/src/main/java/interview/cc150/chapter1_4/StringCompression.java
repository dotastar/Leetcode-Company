package interview.cc150.chapter1_4;

/*
Implement a method to perform basic string compression 
using the counts of repeated characters. For example, 
the string aabcccccaaa would become a2blc5a3. If the 
"compressed" string would not become smaller than the 
original string, your method should return the original string.
*/
public class StringCompression{
	public static void main(String[] args){
		//String input1 = "aabcccccaaa";	//a2blc5a3
		String input2 = "abcdeffffffddeeeeaaccccg";	//a1b1c1d1e1f6d2e4a2c4g1
		System.out.println("Begin");
		String res = strCompress(input2);
		System.out.println(res);
		System.out.println("End");
	}


	public static String strCompress(String s){
		int len = s.length();
		int newSize = 0;
		char last = s.charAt(0);
		int count = 0;
		for(int i=0; i<len; i++){
			if(s.charAt(i)==last)
				count++;
			else{
				newSize += String.valueOf(count).length()+1; //1 is the letter count
				count = 1;
				last = s.charAt(i);
			}
		}
		//add last letter to size
		newSize += String.valueOf(count).length()+1;
		
		//check size
		System.out.println("New size: "+newSize+ ", Old size: "+len);
		if(newSize>=len) return s;

		//compress
		char[] res = new char[newSize];
		int j=0;
		res[0] = s.charAt(0);
		count=0;
		last = res[0];
		for(int i=0; i<len; i++){
			if(s.charAt(i)==last)
				count++;
			else{
				//set char
				res[j++] = last;
				char[] countChars = String.valueOf(count).toCharArray();
				for(char ch : countChars)
					res[j++] = ch;
				
				//re-initialize state
				count = 1;
				last = s.charAt(i);
			}
		}
		//set last char
		res[j++] = last;
		char[] countChars = String.valueOf(count).toCharArray();
		for(char ch : countChars)
			res[j++] = ch;
		
		
		return String.valueOf(res);
	}
}