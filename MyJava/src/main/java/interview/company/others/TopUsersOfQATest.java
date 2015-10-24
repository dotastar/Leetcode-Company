package interview.company.others;
import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

public class TopUsersOfQATest {

	private TopUsersOfQA instance;

	@Before
	public void init() {
		instance = new TopUsersOfQA();
	}

	/********************************** test run() begin **********************************/

	/********************************** test parsePost() begin **********************************/
	@Test
	public void testparsePost1() throws ParseException {
		// Normal case
		String line = "<row Id=\"123\" PostTypeId=\"2\" OwnerUserId=\"0\" />";
		TopUsersOfQA.Post p = instance.parsePost(line);
		assertTrue(p.Id == 123);
		assertTrue(p.PostTypeId == 2);
		assertTrue(p.OwnerUserId == 0);
	}

	@Test
	public void testparsePost2() throws ParseException {
		// Negative Int
		String line = "<row Id=\"-2147483647\" PostTypeId=\"1\" OwnerUserId=\"-2147483647\" />";
		TopUsersOfQA.Post p = instance.parsePost(line);
		assertTrue(p.Id == -2147483647);
		assertTrue(p.PostTypeId == 1);
		assertTrue(p.OwnerUserId == -2147483647);
	}

	@Test
	public void testparsePost3() throws ParseException {
		// Max Int
		String line = "<row Id=\"2147483647\" PostTypeId=\"2\" OwnerUserId=\"2147483647\" />";
		TopUsersOfQA.Post p = instance.parsePost(line);
		assertTrue(p.Id == 2147483647);
		assertTrue(p.PostTypeId == 2);
		assertTrue(p.OwnerUserId == 2147483647);
	}

	@Test
	public void testparsePost4() throws ParseException {
		// Random row from real dataset
		String line = "<row Id=\"2\" PostTypeId=\"1\" AcceptedAnswerId=\"1238\" CreationDate=\"2009-04-30T07:04:18.883\" Score=\"18\" ViewCount=\"1951\" Body=\"&lt;p&gt;We've struggled with the RAID controller in our database server, a &lt;a href=&quot;http://www.pc.ibm.com/europe/server/index.html?nl&amp;amp;cc=nl&quot; rel=&quot;nofollow&quot;&gt;Lenovo ThinkServer&lt;/a&gt; RD120. It is a rebranded Adaptec that Lenovo / IBM dubs the &lt;a href=&quot;http://www.redbooks.ibm.com/abstracts/tips0054.html#ServeRAID-8k&quot; rel=&quot;nofollow&quot;&gt;ServeRAID 8k&lt;/a&gt;.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;We have patched this &lt;a href=&quot;http://www.redbooks.ibm.com/abstracts/tips0054.html#ServeRAID-8k&quot; rel=&quot;nofollow&quot;&gt;ServeRAID 8k&lt;/a&gt; up to the very latest and greatest:&lt;/p&gt;&#xA;&#xA;&lt;ul&gt;&#xA;&lt;li&gt;RAID bios version&lt;/li&gt;&#xA;&lt;li&gt;RAID backplane bios version&lt;/li&gt;&#xA;&lt;li&gt;Windows Server 2008 driver&lt;/li&gt;&#xA;&lt;/ul&gt;&#xA;&#xA;&lt;p&gt;This RAID controller has had multiple critical BIOS updates even in the short 4 month time we've owned it, and the &lt;a href=&quot;ftp://ftp.software.ibm.com/systems/support/system%5Fx/ibm%5Ffw%5Faacraid%5F5.2.0-15427%5Fanyos%5F32-64.chg&quot; rel=&quot;nofollow&quot;&gt;change history&lt;/a&gt; is just.. well, scary. &lt;/p&gt;&#xA;&#xA;&lt;p&gt;We've tried both write-back and write-through strategies on the logical RAID drives. &lt;strong&gt;We still get intermittent I/O errors under heavy disk activity.&lt;/strong&gt; They are not common, but serious when they happen, as they cause SQL Server 2008 I/O timeouts and sometimes failure of SQL connection pools.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;We were at the end of our rope troubleshooting this problem. Short of hardcore stuff like replacing the entire server, or replacing the RAID hardware, we were getting desperate.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;When I first got the server, I had a problem where drive bay #6 wasn't recognized. Switching out hard drives to a different brand, strangely, fixed this -- and updating the RAID BIOS (for the first of many times) fixed it permanently, so I was able to use the original &quot;incompatible&quot; drive in bay 6. On a hunch, I began to assume that &lt;a href=&quot;http://www.newegg.com/Product/Product.aspx?Item=N82E16822136143&quot; rel=&quot;nofollow&quot;&gt;the Western Digital SATA hard drives&lt;/a&gt; I chose  were somehow incompatible with the ServeRAID 8k controller.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;Buying 6 new hard drives was one of the cheaper options on the table, so I went for &lt;a href=&quot;http://www.newegg.com/Product/Product.aspx?Item=N82E16822145215&quot; rel=&quot;nofollow&quot;&gt;6 Hitachi (aka IBM, aka Lenovo) hard drives&lt;/a&gt; under the theory that an IBM/Lenovo RAID controller is more likely to work with the drives it's typically sold with.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;Looks like that hunch paid off -- we've been through three of our heaviest load days (mon,tue,wed) without a single I/O error of any kind. Prior to this we regularly had at least one I/O &quot;event&quot; in this time frame. &lt;strong&gt;It sure looks like switching brands of hard drive has fixed our intermittent RAID I/O problems!&lt;/strong&gt;&lt;/p&gt;&#xA;&#xA;&lt;p&gt;While I understand that IBM/Lenovo probably tests their RAID controller exclusively with their own brand of hard drives, I'm disturbed that a RAID controller would have such subtle I/O problems with particular brands of hard drives.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;So my question is, &lt;strong&gt;is this sort of SATA drive incompatibility common with RAID controllers?&lt;/strong&gt; Are there some brands of drives that work better than others, or are &quot;validated&quot; against particular RAID controller? I had sort of assumed that all commodity SATA hard drives were alike and would work reasonably well in any given RAID controller (of sufficient quality).&lt;/p&gt;&#xA;\" OwnerUserId=\"1\" LastActivityDate=\"2011-03-08T08:18:15.380\" Title=\"Do RAID controllers commonly have SATA drive brand compatibility issues?\" Tags=\"&lt;raid&gt;&lt;ibm&gt;&lt;lenovo&gt;&lt;serveraid8k&gt;\" AnswerCount=\"8\" FavoriteCount=\"2\" />";
		TopUsersOfQA.Post p = instance.parsePost(line);
		assertTrue(p.Id == 2);
		assertTrue(p.OwnerUserId == 1);
		assertTrue(p.PostTypeId == 1);
	}

	/********************************** test parsePost() end **********************************/

	/********************************** test parseUser() begin **********************************/
	@Test
	public void testparseUser1() throws ParseException {
		// Normal case
		String line = "<row Id=\"423514\" Reputation=\"228\" CreationDate=\"2009-04-30T07:54:26.550\" DisplayName=\"Mauli\" />";
		TopUsersOfQA.User u = instance.parseUser(line);
		assertTrue(u.Id == 423514);
		assertTrue(u.DisplayName.equals("Mauli"));
	}

	@Test
	public void testparseUser2() throws ParseException {
		// Negative Int, Name contains other characters
		String line = "<row Id=\"-123523\" Reputation=\"228\" CreationDate=\"2009-04-30T07:54:26.550\" DisplayName=\"Yazhou Cao Test 1235 \" />";
		TopUsersOfQA.User u = instance.parseUser(line);
		assertTrue(u.Id == -123523);
		assertTrue(u.DisplayName.equals("Yazhou Cao Test 1235 "));
	}

	@Test
	public void testparseUser3() throws ParseException {
		// Max Int
		String line = "<row Id=\"2147483647\" Reputation=\"228\" CreationDate=\"2009-04-30T07:54:26.550\" DisplayName=\"Mauli\" />";
		TopUsersOfQA.User u = instance.parseUser(line);
		assertTrue(u.Id == 2147483647);
		assertTrue(u.DisplayName.equals("Mauli"));
	}

	@Test
	public void testparseUser4() throws ParseException {
		// Random row from real dataset
		String line = "<row Id=\"44\" Reputation=\"228\" CreationDate=\"2009-04-30T07:54:26.550\" DisplayName=\"Mauli\" EmailHash=\"305b35de31b6f9e1ad1fce2e8f015705\" LastAccessDate=\"2011-08-26T07:19:25.900\" WebsiteUrl=\"http://www.juergenrose.com\" Location=\"Munich, Germany\" Age=\"32\" AboutMe=\"My favourite language is Python, although for work I have to program in Java (Spring, Hibernate, OpenSCADA).\" Views=\"60\" UpVotes=\"13\" DownVotes=\"1\" />";
		TopUsersOfQA.User u = instance.parseUser(line);
		assertTrue(u.Id == 44);
		assertTrue(u.DisplayName.equals("Mauli"));
	}
	/********************************** test parseUser() end **********************************/
}
