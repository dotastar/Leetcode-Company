package projects.crawler.utils;

import java.util.Comparator;
import org.mongojack.DBCursor;
import projects.crawler.data.BaseDao;
import projects.crawler.data.model.City;
import projects.crawler.data.model.Model;
import projects.crawler.taskframework.autohome.model.DealerPost;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * Export Utils
 *
 * Created by yazhoucao on 8/8/16.
 */
public class ExportUtils {
  private static final String DIR = "/Users/yazhoucao/Downloads/";

  public static <T extends Model<K>, K> void exportToCsv(String fileName, BaseDao<T, K> dao, String[] fields) {
    try {
      Files.createDirectories(Paths.get(DIR));
      StringBuilder content = new StringBuilder();

      DBCursor<T> iterable = dao.find();
      for (T t : iterable) {
        for (String fieldName : fields) {
          Method getter = ReflectionUtils.getGetter(t.getClass(), fieldName);
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

  public static <T> void exportToCsv(String fileName, Iterator<T> data) {
    try {
      Files.createDirectories(Paths.get(DIR));
      StringBuilder content = new StringBuilder();

      List<Method> getters = null;
      while (data.hasNext()) {
        T t = data.next();
        if (getters == null) {
          getters = ReflectionUtils.getAllGetters(t.getClass());
          getters.sort(Comparator.comparingInt(a -> a.getName().length()));
        }
        for (Method getter : getters) {
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

  public static void exportDealersToCsv(String fileName, DealerPost.Dao dao) {
    List<DealerPost> dealerPosts = dao.find().toArray();
//    Map<String, List<DealerPost>> dealerByCity = StreamSupport.stream(cursor.spliterator(), false)
//        .collect((groupingBy(DealerPost::getCity)));
    // Sort by province first, then by city
    dealerPosts.sort((a, b) -> {
      if (a.getCity() != null && b.getCity() != null) {
        return City.order(a.getCity(), a.getProvince()) - City.order(b.getCity(), b.getProvince());
      }
      return a.getCity() != null ? 1 : -1;
    });
    exportToCsv(fileName, dealerPosts.iterator());
  }

}
