package interview.company.yelp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class FindPhoneNoAmongHugeFiles {
	public static void main(String[] args){
		
	}
	
	
	
	
	public Map<String, Long>[] buildIndex(File[] files){
		@SuppressWarnings("unchecked")
		Map<String, Long>[] indexes = new HashMap[files.length];
		int i=0;
		for(File f : files){
			Map<String, Long> map = new HashMap<String, Long>();
			try(RandomAccessFile raf = new RandomAccessFile(f, "r")){
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			indexes[i++] = map;
		}
		
		return null;
	}
	
	/**
	 * External Sort
	 * @param files
	 */
	public void sortFiles(File[] files){
		
	}
}
