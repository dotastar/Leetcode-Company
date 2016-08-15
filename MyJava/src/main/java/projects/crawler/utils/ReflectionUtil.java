package projects.crawler.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import projects.crawler.annotation.Extraction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 *
 * Created by yazhoucao on 8/8/16.
 */
@Slf4j
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

  public static <T, S> boolean extractAndApplyValues(T data, Element elem, S schema) {
    try {
      Field[] fields = schema.getClass().getDeclaredFields();
      for (Field extractionField : fields) {
        if (!extractionField.isAnnotationPresent(Extraction.class)) {
          continue;
        }
        extractionField.setAccessible(true);
        Object extraction = extractionField.get(schema);
        if (!BiConsumer.class.isInstance(extraction)) {
          log.error("Expecting field {} of Class {} has a type of {}", extractionField.getName(), data.getClass().getSimpleName(), BiConsumer.class);
          throw new AssertionError();
        }
        @SuppressWarnings("unchecked")
        BiConsumer<Element, T> consumer = (BiConsumer<Element, T>) extraction;
        consumer.accept(elem, data);
      }
    } catch (Exception e) {
      log.error("Parse {} \nout of Element({}) error -- {}", data, elem, e);
      return false;
    }
    return true;
  }
}
