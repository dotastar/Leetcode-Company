package projects.crawler.data;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Data;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * MongoDB config
 *
 * Created by yazhoucao on 8/7/16.
 */
@Data
@Singleton
public class MongoConn {
  private MongoClient client;
  private MongoDatabase conn;

  @Inject
  public MongoConn(DBConfig config) {
    client = new MongoClient(config.getIP(), config.getPort());
    conn = client.getDatabase(config.getDBName());
  }
}
