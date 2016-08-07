package projects.crawler.data;

import lombok.Data;
import lombok.NonNull;

import javax.inject.Inject;

/**
 * MongoConn Config
 *
 * Created by yazhoucao on 8/7/16.
 */

public interface DBConfig {
  String getDBName();
  String getIP();
  int getPort();
}
