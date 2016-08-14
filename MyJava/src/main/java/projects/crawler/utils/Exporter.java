package projects.crawler.utils;

import org.mongojack.DBCursor;
import projects.crawler.data.BaseDao;
import projects.crawler.data.model.Model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * Export Utils
 *
 * Created by yazhoucao on 8/8/16.
 */
public class Exporter<T extends Model<K>, K> {
  private static final String DIR = "/Users/yazhoucao/Downloads/";

  public void exportToCsv(String fileName, BaseDao<T, K> dao, String[] fields) {
    try {
      Files.createDirectories(Paths.get(DIR));
      StringBuilder content = new StringBuilder();

      DBCursor<T> iterable = dao.find();
      for (T t : iterable) {
        for (String fieldName : fields) {
          Method getter = ReflectionUtil.getGetter(t.getClass(), fieldName);
          try {
            String value = String.valueOf(getter.invoke(t));
            content.append(value).append(',');
          } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
          }
        }
        content.append('\n');
      }

      Files.write(Paths.get(DIR + fileName), content.toString().getBytes("UTF-8"), CREATE, WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
