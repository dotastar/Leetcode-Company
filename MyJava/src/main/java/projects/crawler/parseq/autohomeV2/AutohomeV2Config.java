package projects.crawler.parseq.autohomeV2;

import lombok.Data;
import projects.crawler.data.db.DBConfig;

@Data
public class AutohomeV2Config implements DBConfig {
  private final String dBName = "autohomeV2";
  // Default values
  private final String iP = "localhost";
  private final int port = 27017;
}
