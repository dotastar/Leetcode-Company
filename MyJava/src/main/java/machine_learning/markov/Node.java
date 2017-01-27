/**
 * 
 */
package machine_learning.markov;

/**
 * @author Asia
 *
 */
public class Node {
	private String name;
	private float score;
	private Node parentNode;
	
	public Node(String nameIn){
		name = nameIn;
	}
	
	public Node(String nameIn, float scoreIn){
		name = nameIn;
		score = scoreIn;
	}
	
	public Node(String nameIn, float scoreIn, Node parentIn){
		name = nameIn;
		score = scoreIn;
		parentNode = parentIn;
	}
	
	public void setName(String nameIn){
		name = nameIn;
	}
	
	public String getName(){
		return name;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
}
