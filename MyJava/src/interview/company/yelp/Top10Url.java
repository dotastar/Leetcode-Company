package interview.company.yelp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;

public class Top10Url {

	public static void main(String[] args) {
		List<Url> list = generateUrls(100);
		System.out.println(Arrays.toString(solution1(list)));
	}
	
	public static List<Url> generateUrls(int size){
		Random ran = new Random();
		List<Url> list = new ArrayList<Url>();
		char name = 'a';
		for(int i=0; i<20; i++)
			list.add(new Url("www."+ name++ +".com"));
		
		for(int i=0; i<size; i++)
			list.add(list.get(ran.nextInt(20)));
		return list;
	}

	/**
	 * Priority Queue
	 * @param list
	 * @return
	 */
	public static Url[] solution1(List<Url> list){
		//Count statistics 
		Map<Url, Integer> countMap = new HashMap<Url, Integer>();
		for(Url url : list){
			Integer count = countMap.get(url);
			if(count==null)
				countMap.put(url, 1);
			else
				countMap.put(url, count+1);
		}
		
		//Top K
		PriorityQueue<Entry<Url, Integer>> q = new PriorityQueue<Entry<Url, Integer>>(
					10,	//K
					new Comparator<Entry<Url, Integer>>(){
						@Override
						public int compare(Entry<Url, Integer> o1,
								Entry<Url, Integer> o2) {
							//Decreasing order
							return o2.getValue()-o1.getValue();
						}
					}
				);
		
		for(Entry<Url, Integer> entry : countMap.entrySet())
			q.add(entry);
		
		Url[] urls = new Url[10];
		for(int i=0; i<10; i++)
			urls[i] = q.poll().getKey();
		return urls;
	}
	
	
	/**
	 * Bucket Sort
	 * Suppose there are only N number of Urls totally.
	 * @param list
	 * @return
	 */
	public static Url[] solution2(List<Url> list){
		final int N = 100;
		int[] counts = new int[N];
		
		for(Url url : list){
			int id = findID(url);
			counts[id]++;
		}
		int[] sorted = counts.clone();
		Arrays.sort(sorted);
		int lowest = sorted[90];
		Url[] result = new Url[10];
		int k=0;
		for(int i=0; i<counts.length; i++)
			if(counts[i]>=lowest)
				result[k++] = list.get(i);
		return result;
	}
	
	private static int findID(Url url){
		return url.hashCode();
	}
	
	public List<File> externalGroupBy(File f){
		final int MAXMEM = 100; //MB
		//TODO suppose f.length() can get the number of tuples
		int buckets = (int) (f.length()/MAXMEM);
		List<File> files = new ArrayList<File>();
		for(int i=0; i<buckets; i++){
			files.add(new File("Buffer"+i));
		}
		
		try(BufferedReader br = new BufferedReader(new FileReader(f))){
			String line = "";
			while((line=br.readLine())!=null){
				Url url = new Url(line);
				int index = url.hashCode()%buckets;
				File buffer = files.get(index);
				try(BufferedWriter wr = new BufferedWriter(new FileWriter(buffer))){
					wr.append(url+"\n");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return files;
	}
	
	public static class Url{
		String url;
		public Url(String s){ url = s; }
		public String toString(){ return url; }
	}
}
