package interview.leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Given two words (start and end), and a dictionary, find the length of
 * shortest transformation sequence from start to end, such that:
 * 
 * Only one letter can be changed at a time Each intermediate word must exist in
 * the dictionary For example,
 * 
 * Given: start = "hit" end = "cog"
 * 
 * dict = ["hot","dot","dog","lot","log"]
 * 
 * As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * 
 * return its length 5.
 * 
 * Note: Return 0 if there is no such transformation sequence.
 * 
 * All words have the same length.
 * 
 * All words contain only lowercase alphabetic characters.
 * 
 * @author yazhoucao
 * 
 */
public class Word_Ladder {

	public static void main(String[] args) {
		Word_Ladder obj = new Word_Ladder();
		String start = "hit";
		String end = "cog";
		Set<String> dict = new HashSet<String>();
		dict.add("hot");
		dict.add("dot");
		dict.add("dog");
		dict.add("lot");
		dict.add("log");
		System.out.println(obj.ladderLength(start, end, dict));

//		String start1 = "hit";
//		String end1 = "cog";
//		Set<String> dict1 = new HashSet<String>();
//		dict1.add("hot");
//		dict1.add("dot");
//		dict1.add("dog");
//		dict1.add("lot");
//		dict1.add("log");
//		System.out.println(obj.ladderLength_Improved(start1, end1, dict1));
	}

	/**
	 * BFS
	 * 
	 * Graph G(V,E),
	 * 
	 * Time O(V) which v must <= size of dict, because every time it meets a
	 * word(node) in dict, it deletes the it in dict.
	 * 
	 * Space O(E) which E = size of dict, it is an E because of 1 queue
	 */
	public int ladderLength(String start, String end, Set<String> dict) {
		int length = 1;
		if (start.equals(end))
			return 1;
		Queue<String> q = new LinkedList<String>();
		q.add(start);
		while (!q.isEmpty()) {	//BFS
			length++;
			int levelSize = q.size();
			for (int k = 0; k < levelSize; k++) {
				String w = q.poll();
				char[] wchs = w.toCharArray();
				for (int i = 0; i < wchs.length; i++) {
					char original = wchs[i];
					for (char j = 'a'; j <= 'z'; j++) {
						if (j == original)
							continue;
						wchs[i] = j;
						String dist1word = new String(wchs);
						if (dist1word.equals(end))
							return length;
						if (dict.contains(dist1word)) {
							q.add(dist1word);
							dict.remove(dist1word);
						}
					}
					wchs[i] = original;
				}
			}
		}
		return 0;
	}
}
