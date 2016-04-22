package general.webcrawler.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ListItemMatchCondition {
  @NonNull private FieldMatchCondition[] fieldsWithinListItem; // All fields that you need
  @NonNull private FieldMatchCondition fieldWithinSiblingNode; // Any field is OK, choose a stable one
}
