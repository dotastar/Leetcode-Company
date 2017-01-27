package machine_learning.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Tools {
	
	public static File[] LoadFiles(String address){
		File document = new File(address);
		return document.listFiles();
	}
	
	public static String[] ReadFile_String(File file) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		List<String> wordList = new ArrayList<String>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))){
			String text;
			while((text = reader.readLine())!=null){
				for(String word : text.split(" ")){
					wordList.add(word);
				}
			}
		}
		String[] words = new String[wordList.size()];
		wordList.toArray(words);
		return words;
	}
	
	public static List<float[]> ReadFile_Number(File file) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		List<float[]> list = new ArrayList<float[]>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))){
			String text;
			while((text = reader.readLine())!=null){
				String[] numbers = text.split(" ");
				float[] row = new float[numbers.length];
				for(int i=0; i<numbers.length; i++){
					row[i] = Float.parseFloat(numbers[i]);
				}
				list.add(row);			
			}
		}
		return list;
	}
	
	public static String RecognizeCategory(String nameInput){
		if(nameInput.contains("business")){
			return "business";
		}
		else if(nameInput.contains("sports")){
			return "sports";
		}
		else if(nameInput.contains("auto")){
			return "auto";
		}else if(nameInput.contains("yule")){
			return "yule";
		}
		else if(nameInput.contains("it")){
			return "it";
		}else
			return null;		
	}
	
	public static void main(String[] args){
		List<Float> list = new ArrayList<Float>();
		list.add(new Float(0.15));
		list.add(new Float(0.11));
		list.add(new Float(1.523));
		list.add(new Float(-5.5235));
		list.add(new Float(0.612));
		PrintList(list);
		QuickSort(list,0,list.size()-1);
		PrintList(list);
	}
	
	public static <T> void PrintList(List<T> list){
		for(T obj : list)
			System.out.print(obj+" ");
		System.out.println();
	}
	
	public static <T extends Comparable<T>> void QuickSort(List<T> list, int left, int right){
		if(left>=right)
			return;
		else{
			T pivot = list.get(left);
			int center = left;
			for(int i=left+1; i<=right; i++){
				if(list.get(i).compareTo(pivot)>0){
					center++;
					if(center!=i)
						Swap(list, center, i);
				}
			}
			
			Swap(list, left, center);
			QuickSort(list, left, center-1);
			QuickSort(list, center+1, right);
		}	
	}
	
	public static <T> void Swap(List<T> list, int a, int b){
		T temp = list.get(a);
		list.set(a, list.get(b));
		list.set(b, temp);
	}
}
