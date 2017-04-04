package interview.company.zenefits;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Zenefits
 * Skype onsite
 * 
 * Our app is about showing interesting people around a location. Interesting
 * people are those who share a lot of friends and interests with you.
 * 
 * Input:. visit 1point3acres.com for more.
 * N -- number of commands. Followed by N lines, each of the format:
 * Op args
 * 
 * Op can either be ‘Q’ or ‘W’.
 * Q is a query operation which takes two args:
 * user_id miles
 * 
 * miles is always one of the 3 values: 1, 5 or 30.
 * 
 * W is the insertion operation, which takes args of the form:
 * user_id lat lng interest_id1 weight1 interest_id2 weight2 ...
 * 
 * interest_ids are the set of all interests that user cares about. This could
 * be his facebook friends or interests, each of them having a specific weight.
 * 
 * Output:
 * 
 * For each query operation, output a single line containing the top 10 (or
 * less) user_id’s who are within the ‘miles’ radius of the user and have the
 * highest scalar product of common interest weights. These must be sorted in
 * the order of the weights highest to lowest, tie broken by user_id lowest to
 * highest.
 * 
 * Constraints:
 * 
 * This is more of a real-life problem, than a programming contest puzzle. So
 * the test-case will reflect practically what happens in our software, and not
 * hand-generated corner cases.
 * 
 * Test case will have one densely populated university (Stanford) - 500 people,
 * one city (Bay Area) - 50K people, and one sparsely populated country (US) -
 * 500K people.
 * 
 * Each person has on average 5K interest_ids, which is a random number between
 * 1 and 10K (interest_ids are not necessarily numbered 1 to 10K). Number of
 * people who have a specific interest_id tends to follow power law
 * distribution, with 20% of interests having 80% of user_ids, recursively.
 * 
 * Scoring: It’s a function of:
 * - Time taken for the program to run.
 * - Number of words of code. Therefore obfuscation doesn’t help much, you can
 * gladly give large variable names. The idea is to keep the logic extremely
 * simple.
 * 
 * @author yazhoucao
 *
 */
public class DesignAppFriendsRecommend {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(DesignAppFriendsRecommend.class);
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertTrue(true);
	}
}
