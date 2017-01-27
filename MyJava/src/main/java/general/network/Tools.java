package general.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Tools {
	
	private static String data_10Kb = null;
	
	public static String getData_10Kb(){
		if(data_10Kb!=null){
			return data_10Kb;
		}
		
		String dataPath = "D:/Asia/Dropbox/MyWorkingSpace/MyJava/data_10Kb.txt";
		StringBuilder sb = new StringBuilder();
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(dataPath)))){
			String tmpStr = "";
			while((tmpStr = reader.readLine())!=null){
				sb.append(tmpStr);
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Read data end!");
		data_10Kb = sb.toString();
		return data_10Kb; 
	}
}
