package projects.crawler.subproject.autohome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
}
