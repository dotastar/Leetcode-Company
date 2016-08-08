package projects.crawler.autohome;

import projects.crawler.autohome.model.AutohomeConfig;
import projects.crawler.data.BaseModule;
import projects.crawler.data.DBConfig;

/**
 * Autohome Guice dependency injection module
 *
 * Created by yazhoucao on 8/7/16.
 */
public class AutohomeModule extends BaseModule {
  @Override
  protected void configure() {
    bind(DBConfig.class).to(AutohomeConfig.class);
  }
}
