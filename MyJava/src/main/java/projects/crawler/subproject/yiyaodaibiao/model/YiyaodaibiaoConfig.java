package projects.crawler.subproject.yiyaodaibiao.model;

import lombok.Data;
import projects.crawler.data.db.DBConfig;

/**
 * Created by yazhoucao on 8/7/16.
 */
@Data
public class YiyaodaibiaoConfig implements DBConfig {
  private final String dBName = "yiyaodaibiao";
  // Default values
  private final String iP = "localhost";
  private final int port = 27017;
}
