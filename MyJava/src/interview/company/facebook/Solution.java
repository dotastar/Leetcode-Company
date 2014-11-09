package interview.company.facebook;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Solution {
	
	public static void main(String[] args) throws Exception{
		//BufferedReader br = new BufferedReader(new FileReader("C:/input000.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		String line = br.readLine();
		int round = Integer.parseInt(line);
		for(int i=0; i<round; i++){
			//test case
			line = br.readLine();
			
			String[] paras = line.split(" ");
			int peopleSize = Integer.parseInt(paras[0]);
			int K = Integer.parseInt(paras[1]);
			
			@SuppressWarnings("unchecked")
			List<Integer>[] numListArr = new ArrayList[peopleSize];
			
			for(int j=0; j<peopleSize; j++){
				//read each person's num list
				line = br.readLine();
				
				String[] numList_Str = line.split(" ");
				List<Integer> numList = new ArrayList<Integer>();
				for(int k=1; k<numList_Str.length; k++){
					//read each person's num list
					numList.add(Integer.parseInt(numList_Str[k]));
				}
				Collections.sort(numList);
				numListArr[j] = numList;
				
			}//initialization end
			
			
			//compare and find the number is bigger than the kSmallest
			//and smaller than the kBiggest number
			int shoutCount = 0;

			List<Integer> smallestList = new ArrayList<Integer>(peopleSize);
			List<Integer> biggestList = new ArrayList<Integer>(peopleSize);
			
			for(int j=0; j<peopleSize; j++){
				List<Integer> numList = numListArr[j];
				smallestList.add(numList.get(0));	//smallest
				biggestList.add(numList.get(numList.size()-1));	//biggest
			}
			
			Collections.sort(smallestList);
			Collections.sort(biggestList);
			
			int kBig = biggestList.get(K-1); 
			int kSmall = smallestList.get(K-1);
			
			for(int j=0; j<peopleSize;j++){
				List<Integer> numList = numListArr[j];
				//for(int k=numList.size()-1; k>=0; k--){
				for(int p=0; p<numList.size();p++){
					int num = numList.get(p);
					if(num>=kSmall&&num<=kBig){
						shoutCount++;
					}
				}
			}
			
			System.out.println(shoutCount);		
		}

	}
	
}
