package machine_learning.naivebayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class NaiveBayes {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {	
		String address = "./datasets";	
		NaiveBayes model = new NaiveBayes(3);	
		model.TrainModel(address);
		System.out.println("Model Precision:" + model.GetPrecision());
		
		for(int i=0; i<model.GetCategoryNumber(); i++){	
			System.out.print(model.GetCategoryName(i) + ": | Accuracy:" + model.GetAccuracy(i) + " | Recall: " + model.GetRecall(i));
			System.out.println();
		}				
		System.out.println("End");
	}
	
	private List<File> trainDataSet;
	private List<File> testDataSet;

	private CategoryInfo[] categoryArr;
	private int[][] test_result;	//row is predict count, column is real count
	
	public NaiveBayes(int categoryAmount) {	

		trainDataSet = new ArrayList<File>();
		testDataSet =  new ArrayList<File>();

		categoryArr = new CategoryInfo[categoryAmount];
		for(int i=0; i<categoryAmount; i++){
			categoryArr[i] = new CategoryInfo(GetCategoryName(i));
		}
		
		test_result = new int[categoryAmount][categoryAmount];
	}
	
	public float GetPrecision(){
		int correct = 0;
		int total = 0;
	
		for(int i = 0; i< test_result.length; i++){
			correct += test_result[i][i];	//total correct
			for(int j = 0; j< test_result[i].length; j++)
				total += test_result[i][j];	//total count
		}
		
		return (float)correct/(float)total;
	}
	
	public float GetRecall(int categoryIndex){
		int correct = test_result[categoryIndex][categoryIndex];
		int total = 0;
		
		for(int i = 0; i< test_result.length; i++){
			total += test_result[categoryIndex][i];	//row is predict count
		}
		
		if(total==0) return 0;
		return (float)correct/(float)total;
	}
	
	public float GetAccuracy(int categoryIndex){
		int correct = test_result[categoryIndex][categoryIndex];
		int total = 0;
		
		for(int i = 0; i< test_result.length; i++){
			total += test_result[i][categoryIndex];	//column is real count
		}
		
		if(total==0) return 0;
		return (float)correct/(float)total;
	}
	
	public int GetCategoryNumber(){
		return categoryArr.length;
	}
	
	/**
	 * Predict article's probability in each category
	 * Probability(Category|Article) = Probability(Category) * [Probability(Word_1|Category)*Probability(Word_2|Category)*...]
	 * @param article
	 * @return
	 */
	public String PredictArticle(String[] article){
		String categoryName = "";
		float final_probability = 0f;
		
		int totalUniqueWords = 0;
		for(CategoryInfo category : categoryArr){
			totalUniqueWords += category.uniqueWords.size();
		}	
		
		for(CategoryInfo category : categoryArr){		
			float p_article = 0f;	//P(Y|X)
		
			float p_category = (float)category.uniqueWords.size()/totalUniqueWords;	//P(Y)

			float p_words = 0;	//P(X|Y) = P(x_1|Y)*P(x_2|Y)*...P(x_n|Y)
			
			for(String word : article){
				p_words += Math.log(GetWordProbability(word, category));
			}
			
			//P(Y|X) = //P(Y)*P(X|Y) 
			p_article =p_category * (1/(Math.abs(p_words)+1));	
			
			if(p_article>final_probability)	
			{
				final_probability = p_article;	//get the max probability
				categoryName = category.categoryName;	//get the category name
			}
		}
		
		return categoryName;
	}
	
	//train and test performance
	public void TrainModel(String address) throws IOException{
		LoadFilesToDataSets(address);
		Train();
		Test();
	}
	
	public void SaveModel(String address){
		
	}
	
	public void LoadModel(String address){
		
	}
	

	public String GetCategoryName(int index){
		if(index == 0)	return "business";
		else if(index == 1)	return "sports";
		else if(index == 2)	return "auto";
		else return null;
	}
	
	public int GetCategoryIndex(String name){
		if(name.equals("business")){
			return 0;
		}
		else if(name.equals("sports")){
			return 1;
		}
		else if(name.equals("auto")){
			return 2;
		}else
			return -1;
		
	}
	
	/**
	 * Calculate P(x_n|Y), which is the word's probability in the category
	 * @param word
	 * @return Probability(x_n|Y)
	 */
	private float GetWordProbability(String word, CategoryInfo category){
		float probability = 0f;
		Integer frequency = category.uniqueWords.get(word);
		if(frequency==null)
			probability = category.GetDefaultFrequency();
		else
			probability = (float)frequency.intValue()/category.uniqueWords.size();
		
		return probability;
	}
	
	private void LoadFilesToDataSets(String address){
		File document = new File(address);
		File[] allfiles = document.listFiles();
		for(File dataFile : allfiles){
			if(IfForTraining())
				trainDataSet.add(dataFile);
			else
				testDataSet.add(dataFile);
		}
		System.out.println("train set : " + trainDataSet.size() + " | test set : " + testDataSet.size());
	}
	
	private void Train() throws IOException{
		for(File file : trainDataSet){
			int index  = RecognizeCategory(file.getName());
			if(index<0)	continue;
			CategoryInfo category = categoryArr[index];
			Map<String, Integer> wordFrequencyTable = category.uniqueWords;
			
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))){
				String text;

				while((text = reader.readLine())!=null){
					for(String word : text.split(" "))
						if(wordFrequencyTable.containsKey(word)){						
							wordFrequencyTable.put(word, wordFrequencyTable.get(word)+1);
						}
						else{
							wordFrequencyTable.put(word, 1);
						}
				}
			}
		}
		
		System.out.println("Finish Training!");
	}
	
	private void Test() throws UnsupportedEncodingException, FileNotFoundException, IOException{
				
		for(File file : testDataSet){
			String[] article = ReadFile(file);
			String predictCategoryName = PredictArticle(article);
			int predictIndex = RecognizeCategory(predictCategoryName);		
			int realIndex = RecognizeCategory(file.getName());
			
			test_result[predictIndex][realIndex]++;
		}
		
		System.out.println("Finish Testing!");
	}
	
	
	private String[] ReadFile(File file) throws UnsupportedEncodingException, FileNotFoundException, IOException{
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
	
	private boolean IfForTraining(){
		Random ran = new Random();		
		float ranNumber = ran.nextFloat();	//ranNumber = 0.0 ~ 1.0
		if(ranNumber>=0.1f)
			return true;
		else
			return false;
	}

	
	private int RecognizeCategory(String nameInput){
		if(nameInput.contains("business")){
			return GetCategoryIndex("business");
		}
		else if(nameInput.contains("sports")){
			return GetCategoryIndex("sports");
		}
		else if(nameInput.contains("auto")){
			return GetCategoryIndex("auto");
		}else
			return -1;		
	}
	
	

}
