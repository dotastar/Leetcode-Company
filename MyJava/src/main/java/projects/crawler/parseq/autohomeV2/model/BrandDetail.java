package projects.crawler.parseq.autohomeV2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * Created by yazhoucao on 8/13/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDetail {
  @JsonProperty
  protected String brandDetailedName;
  @JsonProperty
  protected List<String> sellingModels = new ArrayList<>();

  @Override
  public String toString() {
    return brandDetailedName + "," + Joiner.on(':').join(sellingModels);
  }
}
