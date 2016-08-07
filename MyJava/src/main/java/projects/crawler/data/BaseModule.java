package projects.crawler.data;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Base Module, including the caller module itself when getInstance()
 *
 * Created by yazhoucao on 8/7/16.
 */
public abstract class BaseModule extends AbstractModule {
  public <T> T getInstance(Class<T> type, Module... modules) {
    Injector injector = Guice.createInjector(Lists.asList(this, modules));
    return injector.getInstance(type);
  }

  public <T> T getInstance(Class<T> type) {
    Injector injector = Guice.createInjector(this);
    return injector.getInstance(type);
  }
}
