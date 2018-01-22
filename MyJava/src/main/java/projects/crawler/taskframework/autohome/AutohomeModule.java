package projects.crawler.taskframework.autohome;

import com.google.inject.Provides;
import projects.crawler.taskframework.autohome.model.AutohomeConfig;
import projects.crawler.data.BaseModule;
import projects.crawler.data.db.DBConfig;

import javax.inject.Singleton;

/**
 * Autohome Guice dependency injection module
 *
 * Created by yazhoucao on 8/7/16.
 */
public class AutohomeModule extends BaseModule {
  @Override
  protected void configure() {
  }

  @Override
  @Provides
  @Singleton
  public DBConfig provideDBConfig() {
    return new AutohomeConfig();
  }

}
