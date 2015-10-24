package interview.company.others;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * HP - vertica
 * 
 * Let's say you're working for a researcher who's analyzing social networks on
 * Web forums. They want to compare people who post lots of questions vs lots of
 * answers. As a starting point, they would like a list that prints out the top
 * 10 users by number of questions asked, and the top 10 by number of answers
 * posted.
 * 
 * The data is from StackOverflow (technically from their affiliate site
 * ServerFault). You can download and run this code against the full,
 * much-larger, data set here if you're interested:
 * http://blog.stackoverflow.com/category/cc-wiki-dump/
 * 
 * @author yazhoucao
 *
 */
public class TopUsersOfQA {

	private static final boolean DEBUG_MODE = false;
	private static final int TOP_N = 10;
	private static final String PATTERN_ID = "Id=\"";
	private static final String PATTERN_NAME = "DisplayName=\"";
	private static final String PATTERN_OWNER_ID = "OwnerUserId=\"";
	private static final String PATTERN_POST_TYPE = "PostTypeId=\"";
	private static final String[] USER_FIELDS = { PATTERN_ID, PATTERN_NAME };
	private static final String[] POST_FIELDS = { PATTERN_ID, PATTERN_POST_TYPE,
			PATTERN_OWNER_ID };

	public TopUsersOfQA() {
	}

	// Represents an entry in users.xml
	// Some fields omitted due to laziness
	public static class User {
		public final int Id;
		public final String DisplayName;

		public User(int id, String name) {
			Id = id;
			DisplayName = name;
		}
	};

	// Represents an entry in posts.xml
	// Some fields omitted due to laziness
	public static class Post {
		public int Id;
		public int PostTypeId;
		public int OwnerUserId;

		public Post(int id, int postTypeId, int ownerUserId) {
			Id = id;
			PostTypeId = postTypeId; // 1: Question, 2: Answer
			OwnerUserId = ownerUserId;
		}
	};

	// Information of a User's DisplayName, question count and answer count
	private static class UserCounts {
		public int UId;
		public String DisplayName;
		public int QuesCnt;
		public int AnsCnt;

		public UserCounts(int uid) {
			UId = uid;
			DisplayName = null;
			QuesCnt = 0;
			AnsCnt = 0;
		}
	};

	/**
	 * Parse the input line and get User's information by the USER_FIELDS
	 * Return the User object
	 * 
	 * (This method is not used currently, I write it here just for potential
	 * other usage)
	 * 
	 * @throws ParseException
	 */
	public User parseUser(String line) throws ParseException {
		String[] values = extractInfo(line, USER_FIELDS);
		int id = Integer.valueOf(values[0]);
		String name = values[1];
		return new User(id, name);
	}

	/**
	 * Parse the input line and get Post's information by the POST_FIELDS
	 * Return the Post object
	 * 
	 * (This method is not used currently, I write it here just for potential
	 * other usage)
	 * 
	 * @throws ParseException
	 */
	public Post parsePost(String line) throws ParseException {
		String[] values = extractInfo(line, POST_FIELDS);
		int id = Integer.valueOf(values[0]);
		int postTypeId = Integer.valueOf(values[1]);
		int ownerUserId = Integer.valueOf(values[2]);
		return new Post(id, postTypeId, ownerUserId);
	}

	/**
	 * Parse line and fill Post, use post as a container
	 * 
	 * @throws ParseException
	 */
	private void parseAndFillPost(String line, Post post) throws ParseException {
		String[] values = extractInfo(line, POST_FIELDS);
		post.Id = Integer.valueOf(values[0]);
		post.PostTypeId = Integer.valueOf(values[1]);
		post.OwnerUserId = Integer.valueOf(values[2]);
	}

	/**
	 * Do an one-pass scan to get the information of given fields
	 * Time: O(n), n = length(line)
	 * Auxiliary space: O(1)
	 * 
	 * @param line
	 *            : input line from reading buffer
	 * @param fields
	 *            : fields that want to extract from line,
	 *            the fields must be ordered by the occurring order in line
	 * @return a String array contains all the corresponding values
	 * @throws ParseException
	 */
	private String[] extractInfo(String line, String[] fields) throws ParseException {
		// begin index, end index of searching
		int begin = 0, end = 0;
		String[] values = new String[fields.length];
		int i = 0; // index of fields
		while (i < values.length && begin < line.length()) {
			begin = line.indexOf(fields[i]);
			if (begin < 0)
				throw new ParseException("No " + fields[i] + " in " + line, begin);

			// start searching after the 'field_name="'
			begin += fields[i].length();
			end = line.indexOf('\"', begin);
			if (end < 0 || end <= begin)
				throw new ParseException("Invalid " + fields[i] + " format!", end);

			values[i++] = line.substring(begin, end);
			// searching the next field from end + 1
			begin = end + 1;
		}
		if (i < fields.length)
			throw new ParseException("Non-complete parsing", i);
		return values;
	}

	/**
	 * Join users'name from a given file
	 * 
	 * Assume the input file has a valid format
	 * and the format is exactly like the provided file - users-short.xml
	 * 
	 * @param file
	 * @return
	 */
	private void joinUsersName(String file, Map<Integer, UserCounts> users) {
		String line;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)))) {

			while ((line = br.readLine()) != null) {
				if (!line.contains("<row"))
					continue; // only parse the line contains <row ....
				try {
					String[] values = extractInfo(line, USER_FIELDS);
					int id = Integer.valueOf(values[0]);
					if (users.containsKey(id))
						users.get(id).DisplayName = values[1]; // join name

				} catch (ParseException e) {
					// TODO Log it and ignore it ...
					if (DEBUG_MODE)
						System.err.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read posts one by one from a file stream,
	 * and aggregate the Q/A count to a HashMap<UserId, UserCounts>.
	 * 
	 * Assume the input file has a valid format
	 * and the format is exactly like the provided file - posts-short.xml
	 * 
	 * Assume the UserMap has provided all the users we want to count, we will
	 * ignore the post if the OwnerUserId of that post is not in the UserMap.
	 * 
	 * Exception handling:
	 * For ParseException, if the input line is missing one or more fields that
	 * we need, it will be ignored.
	 * 
	 * @param file
	 * @param userMap
	 */
	private Map<Integer, UserCounts> countPost(String file) {
		String line;
		Post post = new Post(0, 0, 0); // only use it as a container
		Map<Integer, UserCounts> userMap = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)))) {
			while ((line = br.readLine()) != null) {
				if (!line.contains("<row"))
					continue; // only parse the line contains <row ....

				try {
					parseAndFillPost(line, post);
				} catch (ParseException e) {
					// TODO Log it and ignore it ...
					if (DEBUG_MODE)
						System.err.println(e.getMessage());
				}
				UserCounts counts = userMap.get(post.OwnerUserId);
				if (counts == null) {
					counts = new UserCounts(post.OwnerUserId);
					userMap.put(post.OwnerUserId, counts);
				}

				if (post.PostTypeId == 1)
					counts.QuesCnt++;
				else
					counts.AnsCnt++;
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return userMap;
	}

	private void printTopUsers(PriorityQueue<UserCounts> askMostUsers,
			PriorityQueue<UserCounts> answerMostUsers) {
		// print Top N Users and Counts
		// use an array for reversing the order
		// so that it can print from highest to lowest
		UserCounts[] res = new UserCounts[TOP_N];
		int i = 0;
		// Calculate the users with the most questions
		System.out.println("Top 10 users with the most questions:");
		while (!askMostUsers.isEmpty())
			res[i++] = askMostUsers.poll();
		while (i > 0) {
			UserCounts uc = res[--i];
			System.out.println(uc.QuesCnt + "\t" + uc.DisplayName);
		}

		System.out.println();
		System.out.println();

		// Calculate the users with the most answers
		System.out.println("Top 10 users with the most answers:");
		while (!answerMostUsers.isEmpty())
			res[i++] = answerMostUsers.poll();
		while (i > 0) {
			UserCounts uc = res[--i];
			System.out.println(uc.AnsCnt + "\t" + uc.DisplayName);
		}
		System.out.println();
	}

	/**
	 * Find the Top N users who has answered most and who has asked most
	 * n = total # of Users, m = total # of Posts, since m >> n
	 * Time: O(m) + O(n) + O (n * log(TOP_N)) = O(m)
	 * Space: O(n)
	 * 
	 * @param userFile
	 * @param postFile
	 */
	public void run(String userFile, String postFile) {
		assert TOP_N > 0; // there is no point if TOP_N <= 0
		// Aggregate each count to a HashMap<UserId, UserCounts>
		Map<Integer, UserCounts> userMap = countPost(postFile);

		// min-Heap for the top users of answering
		PriorityQueue<UserCounts> answerMostUsers = new PriorityQueue<UserCounts>(TOP_N,
				new Comparator<UserCounts>() {
					@Override
					public int compare(UserCounts md1, UserCounts md2) {
						return md1.AnsCnt - md2.AnsCnt;
					}
				});
		// min-Heap for the top users of asking
		PriorityQueue<UserCounts> askMostUsers = new PriorityQueue<UserCounts>(TOP_N,
				new Comparator<UserCounts>() {
					@Override
					public int compare(UserCounts md1, UserCounts md2) {
						return md1.QuesCnt - md2.QuesCnt;
					}
				});

		// get the Top N UserCounts for ask and answer
		int i = 0;
		for (UserCounts curr : userMap.values()) {
			if (i < TOP_N) {
				answerMostUsers.add(curr);
				askMostUsers.add(curr);
			} else {
				UserCounts minAnsUser = answerMostUsers.peek();
				if (curr.AnsCnt > minAnsUser.AnsCnt) {
					answerMostUsers.poll();
					answerMostUsers.add(curr);
				}
				UserCounts minQuesUser = askMostUsers.peek();
				if (curr.QuesCnt > minQuesUser.QuesCnt) {
					askMostUsers.poll();
					askMostUsers.add(curr);
				}
			}
			i++;
		}

		// create a new HashMap stores the TOP_N users, for join their names
		userMap = new HashMap<Integer, UserCounts>(TOP_N * 2);
		for (UserCounts uc : answerMostUsers)
			userMap.put(uc.UId, uc);
		for (UserCounts uc : askMostUsers)
			userMap.put(uc.UId, uc);

		// join users' names
		joinUsersName(userFile, userMap);

		printTopUsers(askMostUsers, answerMostUsers);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// String userFile = "./dataset/Size15M/Users.xml";
		// String postFile = "./dataset/Size15M/Posts.xml";

		String userFile = "/Users/yazhoucao/Downloads/math.stackexchange.com/Users.xml";
		String postFile = "/Users/yazhoucao/Downloads/math.stackexchange.com/Posts.xml";

		TopUsersOfQA s = new TopUsersOfQA();
		long start = System.currentTimeMillis();
		s.run(userFile, postFile);
		long end = System.currentTimeMillis();
		System.out.println("Running time:" + (end - start) + "ms");
	}
}
