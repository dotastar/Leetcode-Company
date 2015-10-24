package machine_learning.naivebayes;

import java.util.HashMap;
import java.util.Map;


public class CategoryInfo {

	public String categoryName;
	
	Map<String, Integer> uniqueWords;
	
	public CategoryInfo(String cname){
		categoryName = cname;
		uniqueWords = new HashMap<String, Integer>();
	}
	
	public CategoryInfo(){uniqueWords = new HashMap<String, Integer>();}

	public float GetDefaultFrequency(){
		return (float)1/(uniqueWords.size()+1);
	}

	public static void main(String [] as){
		System.out.println("test naive bayes");
	}
}
