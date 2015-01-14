package interview.epi.chapter13_hashtable;

import interview.epi.utils.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Write a function which takes as input the name of a log file and returns a
 * pair of pages which have the highest affinity.
 * A log file has such format:
 * Page id | User id: ("---" is space)
 * yahoo------u1
 * yahoo------u4
 * google-----u2
 * google-----u3
 * google-----u4
 * amazon-----u2
 * amazon-----u3
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Q14_Find_A_Highest_Affinity_Pair {

	public static void main(String[] args) {

	}

	/**
	 * Like collaborative filtering recommendation, first calculate the
	 * page-users vectors which is for each page as a vector, the values are the
	 * users(id), then calculate the intersection(similarity) between all the
	 * possible page pairs.
	 * Key is use the data structure: Map<String, Set<String>> pageUsersMap
	 * 
	 * Assume there are P pages, V views total, then there are total P^2 pairs.
	 * Time: build the map O(V) + for each pair do a intersection O(P^2*K), K is
	 * the average length (views) of each page vector = O(P^2*(V/P)) = O(VP).
	 * Space: O(V+P) = O(V)
	 */
	public static Pair<String, String> highestAffinityPair(InputStream ifs) {
		// Creates a mapping from pages to distinct users.
		Map<String, Set<String>> pageUsersMap = new HashMap<>();
		try {
			ObjectInputStream ois = new ObjectInputStream(ifs);
			while (true) {
				String page = ois.readUTF();
				String user = ois.readUTF();
				Set<String> users = pageUsersMap.get(page);
				if (users == null) {
					users = new HashSet<>();
				}
				users.add(user);
				pageUsersMap.put(page, users);
			}
		} catch (IOException e) {
		}
		Pair<String, String> result = null;
		int maxCount = 0;
		// Compares all pairs of pages to users maps.
		List<String> keys = new ArrayList<>(pageUsersMap.keySet());
		for (int i = 0; i < keys.size(); i++) {
			for (int j = i + 1; j < keys.size(); ++j) {
				Set<String> intersectUsers = new HashSet<>(
						pageUsersMap.get(keys.get(i)));
				intersectUsers.retainAll(pageUsersMap.get(keys.get(j)));
				// Updates result if we find larger intersection.
				if (intersectUsers.size() > maxCount) {
					maxCount = intersectUsers.size();
					result = new Pair<>(keys.get(i), keys.get(j));
				}
			}
		}
		return result;
	}
}
