package projects.crawler.utils;

import com.google.common.base.Verify;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import projects.crawler.annotation.Extraction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 *
 * Created by yazhoucao on 8/8/16.
 */
@Slf4j
public class ReflectionUtils {

  // fieldName case insensitive
  public static Method searchMethod(Class clazz, String fieldName, String prefix) {
    return Stream.of(clazz.getMethods())
        .filter(m -> m.getName().startsWith(prefix) && m.getName().toLowerCase().contains(fieldName.toLowerCase()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown fieldName " + fieldName));
  }

  // name case insensitive
  public static List<Method> searchMethod(Class clazz, String name) {
    return Stream.of(clazz.getMethods())
        .filter(m -> m.getName().toLowerCase().contains(name.toLowerCase()))
        .collect(toList());
  }

  public static Method getGetter(Class clazz, String fieldName) {
    return searchMethod(clazz, fieldName, "get");
  }

  public static List<Method> getAllGetters(Class clazz) {
    return searchMethod(clazz, "get");
  }

  public static <T, S> boolean extractAndApplyValues(T dataToApply, Element elem, Class<S> schemaClass) {
    try {
      Field[] fields = schemaClass.getDeclaredFields();
      List<Field> fieldsToExtract =
          Arrays.stream(fields).filter(f -> f.isAnnotationPresent(Extraction.class)).collect(Collectors.toList());
      Verify.verify(!fieldsToExtract.isEmpty(),
          format("Expecting schema %S has some fields to extract, declared fields: %s",
              schemaClass.getSimpleName(), Arrays.toString(fields)));
      S schemaInstance = schemaClass.newInstance();
      for (Field extractionField : fieldsToExtract) {
        extractionField.setAccessible(true);
        Object extraction = extractionField.get(schemaInstance);
        Verify.verify(BiConsumer.class.isInstance(extraction),
            format("Expecting field %s of Class %s has a type of %s", extractionField.getName(),
                dataToApply.getClass().getSimpleName(), BiConsumer.class));
        @SuppressWarnings("unchecked")
        BiConsumer<Element, T> consumer = (BiConsumer<Element, T>) extraction;
        consumer.accept(elem, dataToApply);
      }
    } catch (IllegalAccessException | InstantiationException e) {
      log.error("Parse {} \nout of Element({}) error -- {}", dataToApply, elem, e);
      return false;
    }
    return true;
  }
}
