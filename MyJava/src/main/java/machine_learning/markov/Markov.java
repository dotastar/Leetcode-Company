/**
 * Markov Model
 */
package machine_learning.markov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Asia
 *
 */
public class Markov {
	private final String DefaultMissing = "**default**";
	private final String DefaultBegin = "**begin**";
	private final String DefaultEnd = "**end**";
	
	private Map<String, Integer> wordMap;
	private Map<String, Map<String, Float>> bigramFreqTable;
	private long totalfreq;
	
	public Markov(){
		wordMap = new HashMap<String, Integer>();
		bigramFreqTable  = new HashMap<String, Map<String, Float>>();
		InitDefaultValue();
		totalfreq = 0;
	}
	
	private void InitDefaultValue(){
		wordMap.put(DefaultBegin, 0);
		bigramFreqTable.put(DefaultBegin, new HashMap<String, Float>());
		//wordMap.put(DefaultEnd, 0);
		//bigramFreqTable.put(DefaultEnd, new HashMap<String, Float>());
	}
		
	
	public String[] Participle(String rawtext){
		String text = rawtext.replace(" ","");
		char[] charArr = text.toCharArray();
		Node[] graph = GenerateGraph(charArr);
		
		List<String> phraseList = new ArrayList<String>();
		Node endNode = graph[graph.length-1];
		phraseList.add(endNode.getName());
		//find the best path
		Node nextNode = endNode.getParentNode();
		phraseList.add(nextNode.getName());
		while(nextNode.getParentNode()!=null){	//loop until meet the begin node	 
			nextNode = nextNode.getParentNode();
			phraseList.add(nextNode.getName());
		}
		
		//reverse phrases order
		int listsize = phraseList.size();
		String[] result = new String[listsize];
		for(int i=0; i<listsize-1; i++){
			result[i] = phraseList.get(listsize-1-i);
		}
		
		return result;
	}
	
	private Node[] GenerateGraph(char[] text){
		//initialize
		int length = text.length;
		Node[] graph = new Node[length+1];
		graph[0] = new Node(DefaultBegin, 0, null);

		//begin node
		//String firstChar = String.valueOf(text[0]);	//first character
		//graph[1] = new Node(firstChar, GetBigramProb(firstChar, graph[0].getName()), graph[0]);
		//middle nodes layer
		for(int i=1; i<length+1; i++){	//begin from second character
			//find best node in each layer
			String phrase = String.valueOf(text[i-1]);	//single char
			Node bestNode = new Node(phrase, GetBigramProb(phrase, graph[i-1].getName()), graph[i-1]);
			for(int j=i-1; j>=1; j--){
				phrase += text[j];	//combine char
				float prob = GetBigramProb(phrase, graph[j-1].getName());
				if(prob>bestNode.getScore()){	//compare probability
					bestNode.setName(phrase);
					bestNode.setScore(prob);
					bestNode.setParentNode(graph[j-1]);
				}
			}
			graph[i] = bestNode;
		}
		
		return graph;
	}
	
	public void TrainModel(String fileaddress){
		File file = new File(fileaddress);	
		
		//read text from file
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"))){
			String text;
			int lineNumber = 0;	//each line contains a "begin" and "end"
			while((text=reader.readLine())!=null){
				//System.out.println(text);		//for test
				String[] words = text.split(" ");
				int wordslength = words.length;
				totalfreq += wordslength;	//calculate total frequency
				lineNumber++;
				for(int i=0; i<wordslength; i++){
					String word = words[i];
					//add to word map
					if(wordMap.containsKey(word))
						wordMap.put(word, wordMap.get(word).intValue()+1);
					else
						wordMap.put(word, new Integer(1));
					
					//if not exists, create new bigram word combination
					if(!bigramFreqTable.containsKey(word)){
						bigramFreqTable.put(word, new HashMap<String, Float>());
						bigramFreqTable.get(word).put(DefaultMissing, 0f);
						bigramFreqTable.get(word).put(DefaultEnd, 0f);
						//bigramFreqTable.get(word).put(DefaultBegin, 0f);
					}
						
					//add new word or frequency+1				
					Map<String, Float> wordAfters = bigramFreqTable.get(word);
					if(i>0&&i<wordslength-1){
						String nextWord = words[i+1];
						if(wordAfters.containsKey(nextWord))
							wordAfters.put(nextWord, wordAfters.get(nextWord).floatValue()+1);
						else
							wordAfters.put(nextWord, new Float(1));
					}
					else if(i==0){	//begin word
						Map<String, Float> beginAfters = bigramFreqTable.get(DefaultBegin);
						if(beginAfters.containsKey(word))
							beginAfters.put(word, beginAfters.get(word).floatValue()+1);
						else
							beginAfters.put(word, 1f);
					}
					else{	//i==wordslength-1, end word
						wordAfters.put(DefaultEnd, wordAfters.get(DefaultEnd).floatValue()+1);
					}
				}
			}
			wordMap.put(DefaultBegin, wordMap.get(DefaultBegin)+lineNumber);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UpdateDefaultProb();
		
		System.out.println("Finish Training!");
		//PrintProbTable();
	}
	
	
	/**
	 * Prob(current | last) = Count(last+current)+1/(Count(last)+totalword.size())
	 * @param current word
	 * @param last word
	 * @return probability
	 */
	private float GetBigramProb(String current, String last){		
		Integer last_word = wordMap.get(current);
		if(last_word==null)
			return 1/totalfreq;
		Float lastcurrent_word = bigramFreqTable.get(last).get(current);
		if(lastcurrent_word==null)
			return bigramFreqTable.get(last).get(DefaultMissing);
		
		float last_count = last_word.intValue();
		float lastcurrent_count = lastcurrent_word.floatValue();
		float probability = (lastcurrent_count+1)/(last_count+wordMap.size());
		return probability;
	}
	
	
	/**
	 * Update each BigramWord's probability
	 */
	private void UpdateDefaultProb(){
		float totalWordSize = wordMap.size();
		for(Entry<String, Map<String, Float>> afters : bigramFreqTable.entrySet()){
			int singleFreq = wordMap.get(afters.getKey()).intValue();
			//set default probability
			float defaultProb =1/(float)(singleFreq+totalWordSize);
			afters.getValue().put(DefaultMissing, defaultProb);	
		}
	}
	
	/**
	 * Convert biGramFreqTable's each value from frequency to probability
	 */
	public void ConvertToProb(){	
		float totalWordSize = wordMap.size();
		
		//calculate each bigram word's probability
		for(Entry<String, Map<String, Float>> afters : bigramFreqTable.entrySet()){
			String currentWord = afters.getKey();
			int singleFreq = wordMap.get(currentWord).intValue();
			for(Entry<String, Float> afterWord : afters.getValue().entrySet()){
				//calculate probability value
				float probability = (afterWord.getValue()+1)/(singleFreq+totalWordSize);
				afterWord.setValue(probability);	
			}
			
			//set default probability
			float defaultProb =1/(float)(singleFreq+totalWordSize);
			afters.getValue().put(DefaultMissing, defaultProb);	
		}
	}
	
	
	
	public void PrintProbTable(){
		for(Entry<String, Map<String, Float>> probs : bigramFreqTable.entrySet()){
			System.out.print(probs.getKey() + " : ");
			for(Entry<String, Float> word : probs.getValue().entrySet()){
				System.out.print(word.getKey() + "_" + word.getValue()+", ");
			}
			System.out.println();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String address = "./markov_dataset/RenMindata.txt";
		Markov model = new Markov();
		model.TrainModel(address);
		//model.PrintMap();
		//model.PrintWordsAfter();
		
		String testtext = "�ܲ��ִܷ��أ� ��Ҳ��֪������һ�¾�֪���ˣ��й��������꣡";
		String[] result = model.Participle(testtext);
		for(int i=0; i<result.length-1; i++)
			System.out.print(result[i] + " | ");
		
		System.out.println("End");
	}

}
