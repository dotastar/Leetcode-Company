package general.webcrawler.parser;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ListItemMatchCondition {
  @NonNull private FieldMatchCondition[] fieldsWithinListItem; // All fields that you need
  @NonNull private Pair<FieldMatchCondition, FieldMatchCondition> sameFieldBetweenSiblingItems; // Any field is OK, need two, each from a different node
}
