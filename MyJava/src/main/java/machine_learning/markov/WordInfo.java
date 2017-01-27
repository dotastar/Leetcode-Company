package machine_learning.markov;

/**
 * @author Asia
 *
 */
public class WordInfo {
	private String content;
	private int freq;
	
	public WordInfo(String s){
		content = s;
		freq = 1;
	}
	
	public void AddFreq(){
		freq += 1;
	}
	
	public void AddFreq(int value){
		freq += value;
	}
	
	public void SetFreq(int value){
		freq = value;
	}
	
	public int GetFreq(){
		return freq;
	}
	
	public String GetContent(){
		return content;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof WordInfo){
			WordInfo word = (WordInfo)obj;
			return this.content.equals(word.content);
		}
		else
			return false;
	}
	
	@Override
	public int hashCode(){
		return this.content.hashCode();
	}
}
