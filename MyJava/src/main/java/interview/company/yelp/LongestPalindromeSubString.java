package interview.company.yelp;


public class LongestPalindromeSubString {

	public static void main(String[] args) {
		String s1 = "xsabwdcdwa";
		String s2 = "bb";
		System.out.println(useSymmetricProperty(s1));
		System.out.println(useSymmetricProperty(s2));
	}

	
	
	/**
	 * Practice 2, 7/9/2014
	 * @param s
	 */
	public static void palindrome(String s){
		int len = s.length();
		int[] palindr = new int[len];
		
		palindr[0] = 1;
		
		//index of current max length of palindrome
		int max = 1;	
		for(int i=1; i<len-1; i++){	//i is center index
			//symmetric point of i
			int j = 2*max-i;
			int rightMax = max+palindr[max];
			if(i<rightMax){ //max include it
				palindr[i] = palindr[j]; //symmetric property
			}else
				palindr[i] = 1;
			
			//max[i] = i<rightMax?max[j]:1;
			
			while(s.charAt(i+palindr[i])==s.charAt(i-palindr[i])) 
				palindr[i]++;
			
			//update max
			if(i+palindr[i]>rightMax){
				max = i;
				rightMax = i+palindr[i];
			}
		}
		
	}
	



	
	
	/**
	 * Use the symmetric property of palindrome to solve it
	 * Like Dynamic Programming
	 * Use centerAt[i] to save the max length of palindrome
	 * that s[i] is center of this palindrome
	 * 
	 * O(n) time
	 * O(n) space
	 * @param s
	 * @return
	 */
	public static String useSymmetricProperty(String str){

		char[] s = preProcess(str);
		int maxLen = 0;
		int bestId = 0;
		int len = s.length;
		int[] centerAt = new int[len];	//p
		int id = 0;	//the best i of maxLength
		int right = 0;	//right boundary of maxLength[id], mx
		
		for(int i=1; i<len-1; i++){
			//the symmetric point of i center in id
			int j = id*2-i;
			
			if(right>i) //make use of symmetric property
				centerAt[i] = Math.min(centerAt[j], right-i);
			else
				centerAt[i] = 1; 
			
			int l = i-centerAt[i];
			int r = i+centerAt[i];
			//if not cross boundary
			if(l>=0&&r<=len-1){
				while(s[l]==s[r]){
					centerAt[i]++;
					l = i-centerAt[i];
					r = i+centerAt[i];		
					if(l<0||r>len-1) //cross boundary
						break;
				}
			}
			
			
			int newRight = i + centerAt[i];
			if(newRight>right){
				id = i;
				right = newRight;
			}
			
			if(centerAt[i]>maxLen){
				maxLen = centerAt[i];
				bestId = i;
			}
		}
		
		//output the max palindrome in original string
		StringBuffer sb = new StringBuffer();
		int begin = bestId-maxLen+1;
		for(int i=begin; i<bestId+maxLen; i++)
			if(s[i]!='#')
				sb.append(s[i]);
		return sb.toString();
	}
	
	private static char[] preProcess(String s){
		//n*2+1 = n:string + n+1:#
		char[] chs = new char[s.length()*2+1];
		chs[0] = '#';
		for(int i=0; i<s.length(); i++){
			int idx = (i+1)*2;
			chs[idx-1] = s.charAt(i);
			chs[idx] = '#';
		}
		//chs[chs.length-1] = '\0';
		//System.out.println(Arrays.toString(s));
		return chs;
	}
	
	
	/*
	 * Related practice, not part of the above solution
	 * Two pointer move towards each other, 
	 * one from head and forward, the other form tail and backward
	 * Time: O(n)
	 */
	public static boolean isPalindrome(String s){
		int len = s.length();
		for(int i=0; i<len/2; i++){
			if(s.charAt(i)!=s.charAt(len-1-i))
				return false;
		}
		return true;
	}
}
