package interview.company.palantir;

/**
 * ����������� LCS
 * Longest Common Sequence
 * @author Asia
 *
 */
public class LongestCommonSequence {

	public static void main(String[] args) {
		char[] sa = "abcfgeh".toCharArray();
		char[] sb = "bbccfhe".toCharArray();
		solution(sa,sb);
	}
	
	/**
	 * Dynamic Programming
	 * d[i,j] stands for the longest common 
	 * sequence of top i elements of Sa and 
	 * top j elements of Sb
	 * 
	 * State transition equation d[i,j]:
	 * if(Sa[i] = Sb[j]) 
	 * 		d[i,j] = d[i-1,j-1]+1
	 * else 
	 * 		d[i,j] = max(d[i-1,j],d[i,j-1])
	 */
	public static void solution(char[] sa, char[] sb){
		//init
		int[][] lcs = new int[sa.length][sb.length];
		for(int i=0; i<sa.length; i++){
			if(sa[i]==sb[0])
				lcs[i][0] = 1;
			else
				lcs[i][0] = 0;
		}
			
		for(int i=0; i<sb.length; i++){
			if(sb[i]==sa[0])
				lcs[0][i] = 1;
			else
				lcs[0][i] = 0;
		}
		//end init
		
		
		for(int i=1; i<sa.length; i++){
			for(int j=1; j<sb.length; j++){
				if(sa[i]==sb[j])
					lcs[i][j] = lcs[i-1][j-1]+1;
				else
					lcs[i][j] = max(lcs[i-1][j],lcs[i][j-1]);
			}
		}
		
		for(int i=0; i<sa.length; i++){
			for(int j=0; j<sb.length; j++)
				System.out.print(lcs[i][j]+" ");
			System.out.println();
		}
			
		System.out.println(lcs[sa.length-1][sb.length-1]);
	}
	
	public static int max(int a, int b){
		return a>b?a:b;
	}
}
