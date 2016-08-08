package projects.crawler.utils;

import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * Created by yazhoucao on 8/8/16.
 */
public class ReflectionUtil {

  public static Method searchMethod(Class clazz, String fieldName, String prefix) {
    return Stream.of(clazz.getMethods())
        .filter(m -> m.getName().startsWith(prefix) && m.getName().toLowerCase().contains(fieldName.toLowerCase()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown fieldName " + fieldName));
  }

  public static Method getGetter(Class clazz, String fieldName) {
    return searchMethod(clazz, fieldName, "get");
  }

  public static Method getSetter(Class clazz, String fieldName) {
    return searchMethod(clazz, fieldName, "set");
  }
}
