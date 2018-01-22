package projects.crawler.parseq.autohomeV2;

import com.google.inject.Provides;
import javax.inject.Singleton;
import projects.crawler.data.BaseModule;
import projects.crawler.data.db.DBConfig;
import projects.crawler.parseq.engine.EngineConfig;


/**
 * Autohome Guice dependency injection module
 *
 * Created by yazhoucao on 8/7/16.
 */
public class AutohomeV2Module extends BaseModule {
  @Override
  protected void configure() {
  }

  @Override
  @Provides
  @Singleton
  public DBConfig provideDBConfig() {
    return new AutohomeV2Config();
  }

  @Provides
  @Singleton
  public EngineConfig provideEngineConfig() {
    return new EngineConfig();
  }
}
