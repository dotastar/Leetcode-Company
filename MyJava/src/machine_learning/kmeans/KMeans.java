package machine_learning.kmeans;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import machine_learning.common.Tools;

public class KMeans {
	
	int K = 5;	//number of clusters
	double Threshold = 0.001;
	Map<String, Float>[] vectorArr;
	Map<String, Float>[] centerArr;
	int[] clusterNumArr;
	int [] clusterSizeArr;
	double []clusterWCSSArr;	//WCSS: within-cluster sum of squares
	
	//supervised, for validating
	String[] realCategoryNameArr;
	int [] realCategorySizeArr;
	int [][] clusterDistribution;
	float [][] performance; 
	enum category{auto, business, it, sports, yule};
	
	public KMeans(){}
	
	public static void main(String[] args) throws Exception{
		String address = "./kmeans_dataset";
		
		KMeans model = new KMeans();		
		model.InitialModel(address);	//Initialize centerArr, vectorArr
		model.Cluster();
		model.TrainStatics();
		System.out.println("End!");
	}
	
	public void TrainStatics(){
		System.out.println("Finish Clustering! Training Data:");	
		DecimalFormat nf = new DecimalFormat("#0.00%");
		clusterDistribution = new int[K][K];
		performance = new float[K+1][2];
		PrintWCSS();
		//collect each cluster's real category distribution
		for(int i=0; i<vectorArr.length; i++){
			//find all clusters
			int clusterIndex = clusterNumArr[i];	//vector_i's cluster index
			int realIndex = GetCategoryIndex(realCategoryNameArr[i]);
						
			clusterDistribution[clusterIndex][realIndex]++;
		}
		
		//Print category distribution
		for(int i=0; i<K; i++){
			System.out.print("cluster"+i+" ");

			float correct=0, totalClustered=0;
			int categoryIndex=0;
			//find max category amount and index which is probably the real one
			for(int j=0; j<K; j++){
				float size = clusterDistribution[i][j];
				totalClustered+=size;
				System.out.print(GetCategoryName(j)+":"+size+"|");
				if(size>correct){
					correct=size;	
					categoryIndex = j;	
				}
			}

			float recall = correct/realCategorySizeArr[categoryIndex];
			float purity = correct/totalClustered;
			//float AvgDistance = (float)clusterWCSSArr[i]/(float)clusterSizeArr[i];		
			performance[i][0] = recall;
			performance[i][1] = purity;
			System.out.println(" '"+GetCategoryName(categoryIndex)+"' || recall:"+nf.format(recall)+"|purity:"+nf.format(purity));
		}
		
		//System's total performance
		float avgPrecision=0, avgPurity=0; 
		for(int i=0; i<K; i++){
			avgPrecision+=performance[i][0];
			avgPurity+=performance[i][1];
		}
		avgPrecision = avgPrecision/K;
		avgPurity = avgPurity/K;
		performance[K][0]=avgPrecision;
		performance[K][1]=avgPurity;
		System.out.println("Total precision:"+nf.format(avgPrecision)+"|total purity:"+nf.format(avgPurity));
	}
	
	//Initialize
	@SuppressWarnings("unchecked")
	public void InitialModel(String address) throws Exception{
		File[] allFiles = Tools.LoadFiles(address);
		int length = allFiles.length;
		vectorArr = new Map[length];
		clusterNumArr = new int[length];
		realCategoryNameArr = new String[length];	//for validating
		
		centerArr = new Map[K];
		clusterWCSSArr = new double[K];
		clusterSizeArr = new int[K];
		realCategorySizeArr = new int[K];	//for validating
		
		//set vectorArr's value
		for(int i=0; i<length; i++){
			File file = allFiles[i];
			vectorArr[i] = ConvertStringVector(Tools.ReadFile_String(file));
			Normalize(vectorArr[i]);
			
			//for validating
			String categoryName = Tools.RecognizeCategory(file.getName());
			if(categoryName==null)	throw new NullPointerException();
			realCategoryNameArr[i] = categoryName;
			realCategorySizeArr[GetCategoryIndex(categoryName)]++;
		}
		
		Random ran = new Random();
		//set centerArr's value
		for(int i=0; i<K; i++){
			centerArr[i] = vectorArr[ran.nextInt(length-1)];
		}
		System.out.println("Initialize over!");
	}
	
	public void Cluster(){			
		DecimalFormat df = new DecimalFormat("#0.00%");    //#0.00%   �ٷֱȸ�ʽ�����治��2λ����0����
		
		int i = 0;
		double nowWCSS=0,oldWCSS=0,convergeRate=0;	
		nowWCSS = DivideClusters();	//first divide
		System.out.println();
		do{
			i++;
			System.out.println("Iteration" + i + ": " + nowWCSS);		
			oldWCSS = nowWCSS;	
			ReCalcCenters();	
			nowWCSS = DivideClusters();								
			convergeRate=(oldWCSS-nowWCSS)/oldWCSS;
			System.out.println("Iteration" + i + " End, WCSS:" + nowWCSS+ " | Convergency rate:"+df.format(convergeRate));
			System.out.println();
		}
		while(convergeRate>Threshold);
		
		ReCalcCenters();
	}


	//calculating the cluster's center
	private void ReCalcCenters(){
		//reinitialize centers
		for(int i=0; i<K; i++){
			centerArr[i] = new HashMap<String, Float>();
		}
		
		for(int i=0; i<vectorArr.length; i++){
			Map<String, Float> vector = vectorArr[i];
			
			//the cluster's center
			Map<String, Float> center = centerArr[clusterNumArr[i]];
			
			//add all vectors which are in the same cluster to the center
			for(Map.Entry<String, Float> wordinfo : vector.entrySet()){
				String word = wordinfo.getKey();
				if(center.containsKey(word))
					center.put(word, new Float(center.get(word).floatValue()+wordinfo.getValue().floatValue()));
				else
					center.put(word, wordinfo.getValue());
			}
		}
		
		//calculate its mean value
		for(int i=0; i<K; i++){
			Map<String, Float> center = centerArr[i];
			int size = clusterSizeArr[i];
			for(Map.Entry<String, Float> wordinfo : center.entrySet()){
				wordinfo.setValue(new Float(wordinfo.getValue().floatValue()/(float)size));
			}
			//System.out.println("center"+i+":"+center.size());
		}
	}
	
	
	/*
	 * Divide clusters by new centers
	 * Calculate all vecter's distance to every center, update clusterNum(divide)
	 */
	private double DivideClusters(){
		//reinitialize WCSS, clusterSize
		for(int i=0; i<K; i++){
			clusterWCSSArr[i] = 0;
			clusterSizeArr[i] = 0;
		}

		//find minimal distance and divide
		int vectorIndex = 0;		
		for(Map<String, Float> vector : vectorArr){	
			int centerIndex=0, i=0;
			double minDistance = Double.MAX_VALUE;
			for(Map<String, Float> center : centerArr){
				double newDistance = CalcDistance(center, vector);
				if(newDistance < minDistance){	
					minDistance = newDistance;	//minimal distance
					centerIndex = i;
				}
				i++;
			}
			
			//divide the vector to the cluster, calculate WCSS
			clusterNumArr[vectorIndex] = centerIndex;	//divide cluster number
			clusterWCSSArr[centerIndex] += minDistance;	//calculate WCSS
			clusterSizeArr[centerIndex]++;	//store cluster's size
			vectorIndex++;
		}

		//PrintWCSS();
		PrintClusterSize();
		return GetTotalWCSS();
	}
	
	
	/**
	 * Calculate two vectors'distance
	 * @param vector1
	 * @param vector2
	 * @return WCSS
	 */
	private double CalcDistance(Map<String, Float> vector1, Map<String, Float> vector2){
		double distance = 0;
		for(Map.Entry<String, Float> wordinfo : vector2.entrySet()){
			String word = wordinfo.getKey();
			if(vector1.containsKey(word))
				distance += Math.pow((wordinfo.getValue().floatValue() - vector1.get(word).floatValue()), 2);
			else
				distance += Math.pow(wordinfo.getValue().floatValue(), 2);
		}
		
		for(Map.Entry<String, Float> wordinfo : vector1.entrySet()){
			String word = wordinfo.getKey();
			if(!vector2.containsKey(word))
				distance += Math.pow(wordinfo.getValue().floatValue(), 2);
		}
		
		//return Math.sqrt(distance);	//distance
		return distance;	//WCSS
	}
	
	private void Normalize(Map<String, Float> vector){
		float sum = 0;
		for(Map.Entry<String, Float> wordinfo : vector.entrySet())
			sum+=Math.pow(wordinfo.getValue().floatValue(), 2);
		sum = (float) Math.sqrt(sum);
		for(Map.Entry<String, Float> wordinfo : vector.entrySet())
			wordinfo.setValue(new Float(wordinfo.getValue().floatValue()/sum));
	}
	
	@SuppressWarnings("unused")
	private String[] FindHotWord(Map<String, Float> vector, int n){
		String[] hotWords = new String[n];
		List<Entry<String, Float>> wordfreqList = new ArrayList<Entry<String, Float>>(vector.entrySet());
		//Tools.QuickSort(wordfreqList, 0, vector.size()-1);
		
		
		return hotWords;
	}
	
	private int GetCategoryIndex(String name){
		if(name.equals("auto"))
			return 0;
		else if(name.equals("business"))
			return 1;
		else if(name.equals("it"))
			return 2;
		else if(name.equals("sports"))
			return 3;
		else if(name.equals("yule"))
			return 4;
		else
			return -1;
	}
	
	private String GetCategoryName(int index){
		if(index==0)
			return "auto";
		else if(index==1)
			return "business";
		else if(index==2)
			return "it";
		else if(index==3)
			return "sports";
		else if(index==4)
			return "yule";
		else
			return null;
	}
	

	private void PrintClusterSize(){
		int total = 0;
		System.out.print("Clusters'sizes: ");
		for(int i=0; i<K; i++){
			total+=clusterSizeArr[i];
			System.out.print(clusterSizeArr[i]+"|");
		}
		System.out.print("  "+total);
		System.out.println();
	}
	
	private void PrintWCSS(){
		for(int i=0; i<K; i++)
			System.out.println("cluster" + i + " WCSS:" + clusterWCSSArr[i]+"|Size:"+clusterSizeArr[i]);
	
		System.out.println("Total WCSS:" + GetTotalWCSS());
	}
	
	
	private Map<String, Float> ConvertStringVector(String[] vectorS){
		Map<String, Float> vector = new HashMap<String, Float>(vectorS.length);
		for(String word : vectorS){
			if(vector.containsKey(word))
				vector.put(word, new Float(1.0));
				//vector.put(word, new Float(vector.get(word).floatValue()+1.0));
			else
				vector.put(word, new Float(1.0));
		}
		return vector;
	}
	
	private double GetTotalWCSS(){
		double total = 0;
		for(double wcss: clusterWCSSArr)
			total += wcss;
		return total;
	}

}
