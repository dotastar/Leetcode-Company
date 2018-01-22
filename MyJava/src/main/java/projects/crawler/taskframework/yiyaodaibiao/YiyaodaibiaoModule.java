package projects.crawler.taskframework.yiyaodaibiao;

import com.google.inject.Provides;
import projects.crawler.data.BaseModule;
import projects.crawler.data.db.DBConfig;
import projects.crawler.taskframework.yiyaodaibiao.model.YiyaodaibiaoConfig;

import javax.inject.Singleton;

/**
 * Autohome Guice dependency injection module
 *
 * Created by yazhoucao on 8/7/16.
 */
public class YiyaodaibiaoModule extends BaseModule {
  @Override
  protected void configure() {
  }

  @Override
  @Provides
  @Singleton
  public DBConfig provideDBConfig() {
    return new YiyaodaibiaoConfig();
  }

}
