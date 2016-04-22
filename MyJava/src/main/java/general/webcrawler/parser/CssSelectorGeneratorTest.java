package general.webcrawler.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import org.junit.Before;
import org.junit.Test;

public class CssSelectorGeneratorTest {

  CssSelectorGenerator generator;
  Element body;

  @Before
  public void setUp()
      throws Exception {
    // TODO(Asia): Create a new github profile for CssSelectorGenerator and use that instead of below
    String url = "http://www.github.com/jhalterman";
    Document document = Jsoup.connect(url).get();
    this.body = document.body();
    this.generator = new CssSelectorGenerator();
    this.generator.initialize(body);
  }

  private void selectUniqueText(String text) {
    FieldMatchCondition fieldMatchCondition = new FieldMatchCondition(
        FieldMatchCondition.FieldType.OWN_TEXT, text,
        FieldMatchCondition.MatchType.EQUALS);
    selectUniqueText(fieldMatchCondition);
  }

  private void selectUniqueText(String text, FieldMatchCondition.MatchType matchType) {
    FieldMatchCondition fieldMatchCondition = new FieldMatchCondition(
        FieldMatchCondition.FieldType.OWN_TEXT, text,
        matchType);
    selectUniqueText(fieldMatchCondition);
  }

  private void selectUniqueText(FieldMatchCondition fieldMatchCondition) {
    String generatedSelector = generator.autoGenerateSingleItem(body, fieldMatchCondition);
    assertNotNull(generatedSelector);
    System.out.println("Final generated CssSelector: " + generatedSelector);

    Elements selected = Selector.select(generatedSelector, body);
    assertEquals(1, selected.size());

    String text = CssSelectorGenerator.extractField(selected.first(), fieldMatchCondition);

    assertTrue(CssSelectorGenerator.isMatch(text, fieldMatchCondition));
  }

  @Test
  public void testSearchAndGenerate_SingleStep()
      throws Exception {
    String email = "jhalterman@gmail.com";
    selectUniqueText(email);

    String additionalName = "jhalterman";
    selectUniqueText(additionalName);

    String name = "Jonathan Halterman";
    selectUniqueText(name);

    String employer = "HPE Eucalyptus";
    selectUniqueText(employer);

    String area = "California";
    selectUniqueText(area);

    String url = "http://twitter.com/jhalt";
    selectUniqueText(url);
    String joinDate = "Mar 23, 2010";

    selectUniqueText(joinDate);

    String followers = "56";
    selectUniqueText(followers);

    String starred = "255";
    selectUniqueText(starred);

    String following = "13";
    selectUniqueText(following);

    String contributionDays = "total";
    selectUniqueText(contributionDays, FieldMatchCondition.MatchType.CONTAINS);

    String longestStreak = "16";
    selectUniqueText(longestStreak, FieldMatchCondition.MatchType.CONTAINS);

    String avartarLink = "githubusercontent.com";
    selectUniqueText(new FieldMatchCondition(
        avartarLink,
        FieldMatchCondition.FieldType.ATTRIBUTE,
        FieldMatchCondition.MatchType.CONTAINS,
        "src"));

  }

  @Test
  public void testSearchAndGenerate_MultiStep() {
    // Parse Popular repositories
    // First define all fields needed in a list item
    String project1Name = "modelmapper";
    String project1Description = "Simple, Intelligent, Object Mapping";
    // And also define one of the fields in one of sibling items
    String projec2tName = "typetools";
    FieldMatchCondition p1Name = new FieldMatchCondition(FieldMatchCondition.FieldType.OWN_TEXT,
        project1Name,
        FieldMatchCondition.MatchType.EQUALS);
    FieldMatchCondition p2Name = new FieldMatchCondition(FieldMatchCondition.FieldType.OWN_TEXT,
        projec2tName,
        FieldMatchCondition.MatchType.EQUALS);
    FieldMatchCondition p1Description = new FieldMatchCondition(
        FieldMatchCondition.FieldType.OWN_TEXT,
        project1Description, FieldMatchCondition.MatchType.EQUALS);

    ListItemMatchCondition listMatch = new ListItemMatchCondition(
        new FieldMatchCondition[] { p1Name, p1Description }, p2Name);
    List<String> result = generator.autoGenerateListItems(body, listMatch);

    System.out.println("Final generated list root CssSelector: " + result.get(0));
    Elements selected = Selector.select(result.get(0), body);
    assertEquals(1, selected.size());

    Element listRoot = selected.get(0);

    for (Element itemRoot : Selector.select(result.get(1), listRoot)) {
      for (int i = 2; i < result.size(); i++) {
        Elements select = Selector.select(result.get(i), itemRoot);
        assertEquals(1, selected.size());
        System.out.print(select.get(0).ownText() + "\t|\t");
      }
      System.out.println();
    }
  }
}
