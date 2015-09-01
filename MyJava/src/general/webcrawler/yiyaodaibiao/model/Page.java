package general.webcrawler.yiyaodaibiao.model;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;
import lombok.Data;

import org.junit.Test;

@Data
public class Page {

	private String urlStr;
	
	
	
	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Page.class);
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
