package projects.crawler.yiyaodaibiao;

import projects.crawler.data.BaseModule;
import projects.crawler.data.DBConfig;
import projects.crawler.yiyaodaibiao.model.YiyaodaibiaoConfig;

/**
 * Autohome Guice dependency injection module
 *
 * Created by yazhoucao on 8/7/16.
 */
public class YiyaodaibiaoModule extends BaseModule {
  @Override
  protected void configure() {
    bind(DBConfig.class).to(YiyaodaibiaoConfig.class);
  }
}
