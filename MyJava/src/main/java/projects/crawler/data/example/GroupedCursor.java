package projects.crawler.data.example;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.base.CharMatcher;
import org.mongojack.DBCursor;
import org.mongojack.DBSort;
import org.mongojack.JacksonDBCollection;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.ReadPreference;

import lombok.Getter;
import lombok.SneakyThrows;

public class GroupedCursor<T, K> implements Iterator<Map.Entry<K, List<T>>> {
  @Getter private final DBCursor<T> cursor;
  @Getter private final String keyPath;
  @Getter private final Class<K> keyType;
  private final Function<Object, K> keyGetter;
  private boolean started = false;
  private T next = null;

  public GroupedCursor(DBCursor<T> cursor, String sortPath, String keyPath, Function<Object, K> keyGetter) {
    Preconditions.checkArgument(keyPath.startsWith(sortPath + '.'),
        "Key path \"%s\" does not start with sort path \"%s\" in %s.", keyPath, sortPath, cursor.getCollection().getFullName());
    Preconditions.checkArgument(isIndexed(sortPath, cursor.getCollection()),
        "Path \"%s\" is not indexed in %s.", sortPath, cursor.getCollection().getFullName());
    this.cursor = cursor.sort(DBSort.asc(sortPath));
    this.keyPath = keyPath;
    this.keyGetter = keyGetter;
    this.keyType = returnType(keyGetter);
  }

  // NB(kevin): will not work with large collections if keyPath involves any arrays
  // (see: https://docs.mongodb.org/v3.0/core/query-optimization/#limitations)
  public GroupedCursor(DBCursor<T> cursor, String keyPath, Function<Object, K> keyGetter) {
    Preconditions.checkArgument(isIndexed(stripIndicesFromKeyPath(keyPath), cursor.getCollection()),
        "Path \"%s\" is not indexed in %s.", keyPath, cursor.getCollection().getFullName());
    this.cursor = cursor.sort(DBSort.asc(keyPath));
    this.keyPath = keyPath;
    this.keyGetter = keyGetter;
    this.keyType = returnType(keyGetter);
  }

  protected String stripIndicesFromKeyPath(String keyPath) {
    String[] paths = keyPath.split("\\.");

    String strippedPath = "";
    for (String path : paths) {
      if (!CharMatcher.DIGIT.matchesAllOf(path)) {
        if (!strippedPath.isEmpty()) {
          strippedPath += ".";
        }

        strippedPath += path;
      }
    }
    return strippedPath;
  }

  protected boolean isIndexed(String path, JacksonDBCollection<?, ?> collection) {
    for (DBObject info : collection.getIndexInfo()) {
      DBObject key = (DBObject) info.get("key");
      if (key.keySet().iterator().next().equals(path)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean hasNext() {
    ensureStarted();
    return next != null;
  }

  @Override
  public Map.Entry<K, List<T>> next() {
    ensureStarted();
    List<T> group = Lists.newArrayList();
    group.add(next);
    K key = keyGetter.apply(next);
    while(cursor.hasNext()) {
      T tmp = cursor.next();
      if (key.equals(keyGetter.apply(tmp))) {
        group.add(tmp);
      } else {
        next = tmp;
        return new AbstractMap.SimpleEntry<>(key, group);
      }
    }
    next = null;
    return new AbstractMap.SimpleEntry<>(key, group);
  }

  @SuppressWarnings("unchecked") @SneakyThrows(ReflectiveOperationException.class)
  private static <K> Class<K> returnType(Function<Object, K> keyGetter) {
    return (Class<K>) keyGetter.getClass().getMethod("apply", Object.class).getReturnType();
  }

  public void skipThrough(K keyValue) {
    cursor.greaterThan(keyPath, keyValue);
  }

  private void ensureStarted() {
    if (!started) {
      next = cursor.hasNext() ? cursor.next() : null;
      started = true;
    }
  }

  public GroupedCursor<T, K> setReadPreference(ReadPreference readPreference) {
    cursor.setReadPreference(readPreference);
    return this;
  }

}
