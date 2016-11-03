package general.webcrawler.parser;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class ListIterationCssSelector {
  private final List<String> fieldsSelector = new ArrayList<>();
  @NonNull private String listSelector;
  @NonNull private String list2ItemSelector;

  public ListIterationCssSelector(String listSelector) {
    this.listSelector = listSelector;
  }
}
