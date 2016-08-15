package projects.crawler.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongojack.Id;

import java.util.Set;

/**
 *
 * Created by yazhoucao on 10/25/15.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(of = { "name" })
public class City implements Model<String> {
  private static final Set<String> MUNICIPALITIES = ImmutableSet.of("北京", "上海", "天津", "重庆");

  public static boolean isMunicipality(String cityName) {
    return MUNICIPALITIES.contains(cityName);
  }
  @Id private final String name;
  @JsonProperty private String provinceName;
  @JsonProperty private final String url;

  public int order() {
    return order(name, provinceName);
  }

  // for sorting
  public static int order(String cityName, String provinceName) {
    Preconditions.checkNotNull(cityName);
    if (isMunicipality(cityName)) {
      return 100000 + cityName.hashCode();
    }
    int priority = 0;
    if (provinceName != null) {
      priority += provinceName.hashCode() + provinceName.length() * 10;
    }
    priority += cityName.hashCode() + cityName.length();
    return priority;
  }

  @Override
  public String getId() {
    return getName();
  }

  @Override
  public void setId(String key) {
    throw new UnsupportedOperationException();
  }
}
