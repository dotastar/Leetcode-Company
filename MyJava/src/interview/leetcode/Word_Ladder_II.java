package interview.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Given two words (start and end), and a dictionary, find all shortest
 * transformation sequence(s) from start to end, such that:
 * 
 * Only one letter can be changed at a time Each intermediate word must exist in
 * the dictionary For example,
 * 
 * Given: start = "hit" end = "cog" dict = ["hot","dot","dog","lot","log"]
 * Return
 * 
 * [
 * 
 * ["hit","hot","dot","dog","cog"],
 * 
 * ["hit","hot","lot","log","cog"]
 * 
 * ]
 * 
 * 
 * Note:
 * 
 * All words have the same length.
 * 
 * All words contain only lowercase alphabetic characters.
 * 
 * @author yazhoucao
 * 
 */
public class Word_Ladder_II {

	public static void main(String[] args) {
		Word_Ladder_II obj = new Word_Ladder_II();
		String start = "hit";
		String end = "cog";
		Set<String> dict = new HashSet<String>();
		dict.add("hot");
		dict.add("dot");
		dict.add("dog");
		dict.add("lot");
		dict.add("log");

		List<List<String>> res = obj.findLadders_Improved2(start, end, dict);
		System.out.println(res.toString());

		String start2 = "a";
		String end2 = "c";
		Set<String> dict2 = new HashSet<String>();
		dict2.add("a");
		dict2.add("b");
		dict2.add("c");
		List<List<String>> res2 = obj
				.findLadders_Improved2(start2, end2, dict2);
		System.out.println(res2.toString());

		
		String start01 = "hot";
		String end01 = "dog";
		Set<String> dict01 = new HashSet<String>();
		dict01.add("hot");
		dict01.add("dog");
		List<List<String>> res01 = obj
				.findLadders_Improved2(start01, end01, dict01);
		System.out.println(res01.toString());
		
		
		// big data set
		String start1 = "nape";
		String end1 = "mild";
		String[] raw1 = { "dose", "ends", "dine", "jars", "prow", "soap",
				"guns", "hops", "cray", "hove", "ella", "hour", "lens", "jive",
				"wiry", "earl", "mara", "part", "flue", "putt", "rory", "bull",
				"york", "ruts", "lily", "vamp", "bask", "peer", "boat", "dens",
				"lyre", "jets", "wide", "rile", "boos", "down", "path", "onyx",
				"mows", "toke", "soto", "dork", "nape", "mans", "loin", "jots",
				"male", "sits", "minn", "sale", "pets", "hugo", "woke", "suds",
				"rugs", "vole", "warp", "mite", "pews", "lips", "pals", "nigh",
				"sulk", "vice", "clod", "iowa", "gibe", "shad", "carl", "huns",
				"coot", "sera", "mils", "rose", "orly", "ford", "void", "time",
				"eloy", "risk", "veep", "reps", "dolt", "hens", "tray", "melt",
				"rung", "rich", "saga", "lust", "yews", "rode", "many", "cods",
				"rape", "last", "tile", "nosy", "take", "nope", "toni", "bank",
				"jock", "jody", "diss", "nips", "bake", "lima", "wore", "kins",
				"cult", "hart", "wuss", "tale", "sing", "lake", "bogy", "wigs",
				"kari", "magi", "bass", "pent", "tost", "fops", "bags", "duns",
				"will", "tart", "drug", "gale", "mold", "disk", "spay", "hows",
				"naps", "puss", "gina", "kara", "zorn", "boll", "cams", "boas",
				"rave", "sets", "lego", "hays", "judy", "chap", "live", "bahs",
				"ohio", "nibs", "cuts", "pups", "data", "kate", "rump", "hews",
				"mary", "stow", "fang", "bolt", "rues", "mesh", "mice", "rise",
				"rant", "dune", "jell", "laws", "jove", "bode", "sung", "nils",
				"vila", "mode", "hued", "cell", "fies", "swat", "wags", "nate",
				"wist", "honk", "goth", "told", "oise", "wail", "tels", "sore",
				"hunk", "mate", "luke", "tore", "bond", "bast", "vows", "ripe",
				"fond", "benz", "firs", "zeds", "wary", "baas", "wins", "pair",
				"tags", "cost", "woes", "buns", "lend", "bops", "code", "eddy",
				"siva", "oops", "toed", "bale", "hutu", "jolt", "rife", "darn",
				"tape", "bold", "cope", "cake", "wisp", "vats", "wave", "hems",
				"bill", "cord", "pert", "type", "kroc", "ucla", "albs", "yoko",
				"silt", "pock", "drub", "puny", "fads", "mull", "pray", "mole",
				"talc", "east", "slay", "jamb", "mill", "dung", "jack", "lynx",
				"nome", "leos", "lade", "sana", "tike", "cali", "toge", "pled",
				"mile", "mass", "leon", "sloe", "lube", "kans", "cory", "burs",
				"race", "toss", "mild", "tops", "maze", "city", "sadr", "bays",
				"poet", "volt", "laze", "gold", "zuni", "shea", "gags", "fist",
				"ping", "pope", "cora", "yaks", "cosy", "foci", "plan", "colo",
				"hume", "yowl", "craw", "pied", "toga", "lobs", "love", "lode",
				"duds", "bled", "juts", "gabs", "fink", "rock", "pant", "wipe",
				"pele", "suez", "nina", "ring", "okra", "warm", "lyle", "gape",
				"bead", "lead", "jane", "oink", "ware", "zibo", "inns", "mope",
				"hang", "made", "fobs", "gamy", "fort", "peak", "gill", "dino",
				"dina", "tier" };
		Set<String> dictraw = new HashSet<String>();
		for (String str : raw1)
			dictraw.add(str);

		Set<String> dict1 = new HashSet<String>(dictraw);

		long begin = System.currentTimeMillis();
		List<List<String>> res1 = obj.findLadders(start1, end1, dict1);
		long finish = System.currentTimeMillis();
		System.out.println("res1 solution size:" + res1.size() + "\t time: "
				+ (finish - begin));
		System.out.println(res1.toString());

		/**************** Improved ****************/
		dict1 = new HashSet<String>(dictraw);

		begin = System.currentTimeMillis();
		List<List<String>> res1I = obj
				.findLadders_Improved(start1, end1, dict1);
		finish = System.currentTimeMillis();
		System.out.println("\nres1I solution size:" + res1I.size()
				+ "\t time: " + (finish - begin));
		System.out.println(res1I.toString());

		/**************** Improved2 ****************/
		dict1 = new HashSet<String>(dictraw);

		begin = System.currentTimeMillis();
		List<List<String>> res1I2 = obj.findLadders_Improved2(start1, end1,
				dict1);
		finish = System.currentTimeMillis();
		System.out.println("\nres1I2 solution size:" + res1I2.size()
				+ "\t time: " + (finish - begin));
		System.out.println(res1I2.toString());

	}

	/**
	 * BFS, to store all the paths during BFS
	 * 
	 * Every time it finds a new word in dict, it saves it in visited, and copy
	 * the current path to a new path and add the word to the new path Time out
	 * solution. And when go down to the next depth, delete all visited words in
	 * previous level.
	 * 
	 * The branches of traverse every level is limited by the size of dict,
	 * everytime dict removes all visited words, the number of branches is
	 * narrowed down.
	 * 
	 * One time consuming step could be the one that copying the old path and
	 * create a new path.
	 * 
	 * @return
	 */
	public List<List<String>> findLadders(String start, String end,
			Set<String> dict) {
		List<List<String>> res = new ArrayList<List<String>>();
		Queue<List<String>> q = new LinkedList<List<String>>();
		List<String> path = new ArrayList<String>();
		path.add(start);
		if (start == end) {
			path.add(end);
			res.add(path);
			return res;
		}

		q.add(path);
		int curr = 1;
		int next = curr;
		dict.add(end);
		Set<String> visited = new HashSet<String>();
		while (!q.isEmpty()) {
			List<String> currPath = q.poll();
			curr--;
			if (curr == 0) { // go down to next level
				if (res.size() > 0)
					break;

				curr = next;
				next = 0;
				dict.removeAll(visited);
				visited.clear();
			}

			String last = currPath.get(currPath.size() - 1);
			if (last.equals(end)) {
				res.add(currPath);
				continue;
			} else {
				char[] lastarr = last.toCharArray();

				for (int i = 0; i < last.length(); i++) {
					char ci = lastarr[i];
					for (char c = 'a'; c <= 'z'; c++) {
						if (ci == c)
							continue;
						lastarr[i] = c;
						String dist1word = new String(lastarr);
						if (dict.contains(dist1word)) {
							// this step may be time consuming
							List<String> newpath = new ArrayList<String>(
									currPath);
							newpath.add(dist1word);
							q.add(newpath);
							next++;

							visited.add(dist1word);
						}
					}
					lastarr[i] = ci;
				}
			}
		}

		return res;
	}

	/**
	 * Poorly Improved solution, it is even worse than the first solution
	 * 
	 * Create a LinkedNode class to store the parent of each node, to avoid
	 * copying the whole old path everytime (above solution)
	 * 
	 * @return
	 */
	public List<List<String>> findLadders_Improved(String start, String end,
			Set<String> dict) {
		List<List<String>> res = new ArrayList<List<String>>();
		Queue<LinkedNode> q = new LinkedList<LinkedNode>();

		if (start == end) {
			List<String> path = new LinkedList<String>();
			path.add(start);
			path.add(end);
			res.add(path);
			return res;
		}

		List<LinkedNode> endnodes = new ArrayList<LinkedNode>();
		q.add(new LinkedNode(start));
		int depth = 1;
		int curr = 1;
		int next = curr;
		dict.add(end);
		Set<String> visitedCurrDepth = new HashSet<String>();

		while (!q.isEmpty()) {
			LinkedNode currNode = q.poll();
			curr--;
			if (curr == 0) { // go down to next level
				if (res.size() > 0)
					break;
				depth++;
				curr = next;
				next = 0;
				dict.removeAll(visitedCurrDepth);
				visitedCurrDepth.clear();
			}

			String last = currNode.val;
			if (last.equals(end)) {
				endnodes.add(currNode);
			} else {
				char[] lastarr = last.toCharArray();
				for (int i = 0; i < last.length(); i++) {
					char original = lastarr[i];
					for (char ci = 'a'; ci <= 'z'; ci++) {
						lastarr[i] = ci;
						String dist1word = new String(lastarr);
						if (ci != original && dict.contains(dist1word)) {
							LinkedNode nextnode = new LinkedNode(dist1word);
							nextnode.parent = currNode;
							q.add(nextnode);
							next++;
							visitedCurrDepth.add(dist1word);
						}
					}
					lastarr[i] = original;
				}
			}
		}

		// convert all end node to a complete path
		for (LinkedNode node : endnodes) {
			List<String> path = new ArrayList<String>(depth);
			int i = 0;
			int j = -1;

			while (node != null) { // traverse from end to start
				path.add(node.val); // convert to a path
				node = node.parent;
				j++;
			}

			// reverse the order of the path
			while (i < j) {
				String tmp = path.get(i);
				path.set(i, path.get(j));
				path.set(j, tmp);
				i++;
				j--;
			}

			res.add(path);
		}

		return res;
	}

	public static class LinkedNode {
		LinkedNode parent;
		String val;

		public LinkedNode(String str) {
			val = str;
		}

		public String toString() {
			return val;
		}
	}

	/**
	 * Do a BFS, search start from end, during traversing, store all the
	 * precursors of the current node
	 * 
	 * After gathered all the precursor information, do a DFS, search the end
	 * from start.
	 * 
	 * This is much faster than above two solutions because the BFS is much more
	 * pruned than previous two, the BFS is pruned by the complete visitedAll,
	 * which records all nodes that have met. In previous solution, they only
	 * add nodes of visited of current level to queue after traversed the
	 * current level.
	 * 
	 * So the time of BFS will be just O(n), which n = size(dict)
	 * 
	 * Then the running time of DFS is very limited because the number of
	 * precursors of each node is very limited.
	 * 
	 */
	public List<List<String>> findLadders_Improved2(String start, String end,
			Set<String> dict) {
		List<List<String>> res = new ArrayList<List<String>>();
		Queue<GraphNode> q = new LinkedList<GraphNode>();

		if (start == end) {
			List<String> path = new LinkedList<String>();
			path.add(start);
			path.add(end);
			res.add(path);
			return res;
		}

		q.add(new GraphNode(end));
		int depth = 1;
		int curr = 1;
		int next = curr;
		boolean findStart = false;
		dict.add(start);
		Set<String> visitedAll = new HashSet<String>();
		Map<String, GraphNode> precursorMap = new HashMap<String, GraphNode>();
		precursorMap.put(start, new GraphNode(start));

		while (!q.isEmpty()) {
			GraphNode currNode = q.poll();
			curr--;
			if (curr == 0) { // go down to next level
				if (findStart)
					break;
				depth++;
				curr = next;
				next = 0;
				dict.removeAll(visitedAll);
			}

			String last = currNode.val;

			char[] lastarr = last.toCharArray();
			for (int i = 0; i < last.length(); i++) {
				char original = lastarr[i];
				for (char ci = 'a'; ci <= 'z'; ci++) {
					lastarr[i] = ci;
					String dist1word = new String(lastarr);
					if (ci != original && dict.contains(dist1word)) {
						GraphNode nextnode;
						if (!precursorMap.containsKey(dist1word)) {
							nextnode = new GraphNode(dist1word);
							precursorMap.put(dist1word, nextnode);
						} else
							nextnode = precursorMap.get(dist1word);

						nextnode.neighbors.add(currNode);

						if (dist1word.equals(start)) {
							findStart = true;
						}

						if (visitedAll.add(dist1word)) {
							q.add(nextnode);
							next++;
						}
					}
				}// end for
				lastarr[i] = original;
			}// end for
		}// end while

		GraphNode startnode = precursorMap.get(start);
		Stack<String> paths = new Stack<String>();
		paths.add(startnode.val); // reason of below: depth - 1
		buildPath(startnode, end, paths, depth - 1, res);
		return res;
	}

	private void buildPath(GraphNode start, String end, Stack<String> paths,
			int depth, List<List<String>> res) {
		if (depth == 0) {
			if (start.val.equals(end)) {
				List<String> validPath = new ArrayList<String>();
				validPath.addAll(paths);
				res.add(validPath);
			}
			return;
		}

		for (GraphNode neighbor : start.neighbors) {
			paths.push(neighbor.val);
			buildPath(neighbor, end, paths, depth - 1, res);
			paths.pop();
		}
	}

	private static class GraphNode {
		String val;
		List<GraphNode> neighbors;

		public GraphNode(String str) {
			val = str;
			neighbors = new ArrayList<GraphNode>();
		}

		public String toString() {
			return val + " : " + neighbors.toString();
		}
	}

	
	

}
