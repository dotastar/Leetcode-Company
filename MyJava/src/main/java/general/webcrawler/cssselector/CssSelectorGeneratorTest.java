package general.webcrawler.cssselector;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


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
    CssSelectorGenerator.MatchCondition matchCondition =
        new CssSelectorGenerator.MatchCondition(CssSelectorGenerator.MatchCondition.TargetType.OWN_TEXT, text,
            CssSelectorGenerator.MatchCondition.EqualType.EQUALS);
    selectUniqueText(matchCondition);
  }

  private void selectUniqueText(String text, CssSelectorGenerator.MatchCondition.EqualType equalType) {
    CssSelectorGenerator.MatchCondition matchCondition =
        new CssSelectorGenerator.MatchCondition(CssSelectorGenerator.MatchCondition.TargetType.OWN_TEXT, text,
            equalType);
    selectUniqueText(matchCondition);
  }

  private void selectUniqueText(CssSelectorGenerator.MatchCondition matchCondition) {
    String generatedSelector = generator.searchAndGenerate(body, matchCondition);
    assertNotNull(generatedSelector);
    System.out.println("Final generated CssSelector: " + generatedSelector);

    Elements selected = Selector.select(generatedSelector, body);
    assertEquals(1, selected.size());

    String text = matchCondition.getMatchValue();
    switch (matchCondition.getEqualType()) {
      case EQUALS:
        assertEquals(text, selected.get(0).ownText());
        break;
      case CONTAINS:
        assertTrue(selected.get(0).ownText().contains(text));
        break;
      default:
        throw new IllegalArgumentException("Unknown EqualType " + matchCondition.getEqualType());
    }
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

    String starred = "254";
    selectUniqueText(starred);

    String following = "13";
    selectUniqueText(following);

    String contributionDays = "total";
    selectUniqueText(contributionDays, CssSelectorGenerator.MatchCondition.EqualType.CONTAINS);

    String longestStreak = "16";
    selectUniqueText(longestStreak, CssSelectorGenerator.MatchCondition.EqualType.CONTAINS);
  }

  @Test
  public void testSearchAndGenerate_MultiStep() {
    String project1Name = "modelmapper";
    String project1Description = "Simple, Intelligent, Object Mapping";
    String project1Stars = "339";
    String projec2tName = "typetools";
    CssSelectorGenerator.MatchCondition p1Name =
        new CssSelectorGenerator.MatchCondition(CssSelectorGenerator.MatchCondition.TargetType.OWN_TEXT, project1Name,
            CssSelectorGenerator.MatchCondition.EqualType.EQUALS);
    CssSelectorGenerator.MatchCondition p2Name =
        new CssSelectorGenerator.MatchCondition(CssSelectorGenerator.MatchCondition.TargetType.OWN_TEXT, projec2tName,
            CssSelectorGenerator.MatchCondition.EqualType.EQUALS);
    CssSelectorGenerator.MatchCondition p1Description =
        new CssSelectorGenerator.MatchCondition(CssSelectorGenerator.MatchCondition.TargetType.OWN_TEXT,
            project1Description, CssSelectorGenerator.MatchCondition.EqualType.EQUALS);
    CssSelectorGenerator.MatchCondition p1Starts =
        new CssSelectorGenerator.MatchCondition(CssSelectorGenerator.MatchCondition.TargetType.OWN_TEXT, project1Stars,
            CssSelectorGenerator.MatchCondition.EqualType.EQUALS);

    List<String> result =
        generator.searchListItemAndGenerate(body, new CssSelectorGenerator.MatchCondition[]{p1Name, p2Name}, p1Name,
            p1Description, p1Starts);

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
