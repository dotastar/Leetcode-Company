package interview.company.yelp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * Document Indexing
 * We are building a Document search tool that lets you search 
 * for a given string in an input document collection. The search 
 * function  returns all of the strings that occur in any document 
 * in which the search string also appears. For purposes of this 
 * problem, an array of strings is a document, and an array of 
 * arrays is a collection.
 * 
 * Input, passed to the index function:
 * String[][] input = [[“a”,”b","a"],[“b”,”c”,”d"],[“b”,”e"],
 * [“b”,”f"],[“g”,”e”]];
 * Example results given the above input:"b" -> [“e”,”f”,”c”,”d”,”a”]
 * "a" -> ["b"]
 * "e" -> ["g","b"]
 * 
 * Please write the following two functions:
 * void index(String[][] t){ }
 * 
 * Which may be as slow as necessary, and save any global state it needs to. 
 * The goal of the index function is to make search as fast as possible.
 * 
 * Collection<String> search(“b") -> [“e”,”f”,”c”,”d”,”a”]
 * search(“q") -> ()
 * 
 * The search function takes the single input string, and as quickly 
 * as possible, returns the strings that occur in any document with 
 * the input string.
*/
public class DocumentIndexing {
	
	public static void main(String[] args) {
		String[][] input = { { "a", "b", "a" }, { "b", "c", "d" },
				{ "b", "e" }, { "b", "f" }, { "g", "e" } };
		Map<String, Set<String>> index = indexing(input);
		System.out.println(search("a", index).toString());
		System.out.println(search("b", index).toString());
		System.out.println(search("c", index).toString());
		System.out.println(search("d", index).toString());
		System.out.println(search("f", index).toString());
	}

	/**
	 * Building index
	 * @param docs
	 * @return
	 */
	public static Map<String, Set<String>> indexing(String[][] docs) {
		Map<String, Set<String>> index = new HashMap<String, Set<String>>();

		int len = docs.length;
		for (int i = 0; i < len; i++) {
			String[] doc = docs[i];
			// for each doc, traverse each letter
			// build index in this doc
			for (int j = 0; j < doc.length; j++) {
				// for each letter,
				String key = doc[j];
				// check if added to index
				Set<String> content = index.get(key);
				if (content == null) {
					// add to index
					content = new HashSet<String>();
					index.put(key, content);
				}

				// building
				for (String letter : doc)
					if (!letter.equals(key))
						content.add(letter);
			}
		}
		return index;
	}

	public static Collection<String> search(String key, Map<String, Set<String>> index) {
		return index.get(key);
	}
}
