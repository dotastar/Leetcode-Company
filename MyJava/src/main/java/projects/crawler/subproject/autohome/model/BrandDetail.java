package projects.crawler.subproject.autohome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by yazhoucao on 8/13/16.
 */
@Data
@AllArgsConstructor
public class BrandDetail {
  @JsonProperty
  protected String brandName;
  @JsonProperty
  protected List<String> sellingModels = new ArrayList<>();
}
