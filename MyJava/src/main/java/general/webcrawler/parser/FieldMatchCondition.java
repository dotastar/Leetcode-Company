package general.webcrawler.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class FieldMatchCondition {
  @NonNull private String matchValue;
  @NonNull private FieldType fieldType;
  @NonNull private MatchType matchType;
  private String attributeName; // Only set when fieldType=ATTRIBUTE

  public FieldMatchCondition(FieldType fieldType, String matchValue, MatchType matchType) {
    this.fieldType = fieldType;
    this.matchValue = matchValue;
    this.matchType = matchType;
  }

  public enum FieldType {
    ID, CLASS, ATTRIBUTE, OWN_TEXT, TEXT
  }

  public enum MatchType {
    // All are case sensitive
    EQUALS, CONTAINS
  }
}
