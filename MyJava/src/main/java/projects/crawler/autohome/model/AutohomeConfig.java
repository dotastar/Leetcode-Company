package projects.crawler.autohome.model;

import lombok.Data;
import projects.crawler.data.DBConfig;

/**
 * Created by yazhoucao on 8/7/16.
 */
@Data
public class AutohomeConfig implements DBConfig {
  private final String dBName = "autohome";
  // Default values
  private final String iP = "localhost";
  private final int port = 27017;
}
