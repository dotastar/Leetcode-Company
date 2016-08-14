package projects.crawler.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.EnumMultiset;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectionUtil {
  public static boolean isNullOrEmpty(Collection<?> c) {
    return c == null || c.isEmpty();
  }

  public static boolean isNullOrEmpty(Map<?, ?> m) {
    return m == null || m.isEmpty();
  }

  public static boolean isNullOrEmpty(Multimap<?, ?> m) {
    return m == null || m.isEmpty();
  }

  public static <T> Set<T> nullToEmpty(Set<T> c) {
    return c == null ? Collections.emptySet() : c;
  }

  public static <T> List<T> nullToEmpty(List<T> c) {
    return c == null ? Collections.emptyList() : c;
  }

  public static <T> Collection<T> nullToEmpty(Collection<T> c) {
    return c == null ? Collections.emptyList() : c;
  }

  /**
   * Useful for creating rankings by count.
   */
  public static <T extends Comparable<T>> SortedSetMultimap<Integer, T> invert(SortedMultiset<T> multiset) {
    return invert(multiset, false);
  }

  public static <T extends Comparable<T>> SortedSetMultimap<Integer, T> invert(SortedMultiset<T> multiset, boolean descending) {
    SortedSetMultimap<Integer, T> result = TreeMultimap.create(descending ? Collections.reverseOrder() : Ordering.natural(), multiset.comparator());
    for (Multiset.Entry<T> entry : multiset.entrySet()) {
      result.put(entry.getCount(), entry.getElement());
    }
    return result;
  }

  /**
   * Suitable for use with {@link com.google.common.util.concurrent.AtomicLongMap#asMap() AtomicLongMap.asMap()}.
   */
  public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> SortedSetMultimap<T2, T1> invert(Map<T1, T2> map, boolean descending) {
    SortedSetMultimap<T2, T1> result = TreeMultimap.create(descending ? Collections.reverseOrder() : Ordering.natural(), Ordering.natural());
    for (Map.Entry<T1, T2> entry : map.entrySet()) {
      result.put(entry.getValue(), entry.getKey());
    }
    return result;
  }

  /** Returns a view of the original map restricted to the specified set of keys. */
  public static <K, V> Map<K, V> subMap(final Set<K> keys, Map<K, V> map) {
    return Maps.filterKeys(map, new Predicate<K>() {
      @Override public boolean apply(K key) {
        return keys.contains(key);
      }
    });
  }

  /**
   * Assumes that input does not contain null elements.
   */
  public static <T> T selectFirst(Iterable<T> input, Comparator<T> comparator) {
    if (input == null) {
      return null;
    }
    T best = null;
    for (T next : input) {
      if (best == null || comparator.compare(next, best) < 0) {
        best = next;
      }
    }
    return best;
  }

  /**
   * Assumes that input does not contain null elements.
   */
  public static <T> T selectLast(Iterable<T> input, Comparator<T> comparator) {
    if (input == null) {
      return null;
    }
    T best = null;
    for (T next : input) {
      if (best == null || comparator.compare(next, best) > 0) {
        best = next;
      }
    }
    return best;
  }

  public static <K> Map<K, Integer> toMap(Multiset<K> multiset) {
    Map<K, Integer> map = Maps.newLinkedHashMap();
    for (Multiset.Entry<K> entry : multiset.entrySet()) {
      map.put(entry.getElement(), entry.getCount());
    }
    return map;
  }

  public static <K> Multiset<K> toMultiset(Map<K, Integer> map) {
    Multiset<K> multiset = LinkedHashMultiset.create();
    for (Map.Entry<K, Integer> entry : map.entrySet()) {
      multiset.setCount(entry.getKey(), entry.getValue());
    }
    return multiset;
  }

  @SneakyThrows(ReflectiveOperationException.class)
  public static <K extends Enum<K>> EnumMultiset<K> toMultiset(EnumMap<K, Integer> map) {
    Field f = EnumMap.class.getDeclaredField("keyType");
    f.setAccessible(true);
    @SuppressWarnings("unchecked") Class<K> keyType = (Class<K>) f.get(map);
    EnumMultiset<K> multiset = EnumMultiset.create(keyType);
    for (Map.Entry<K, Integer> entry : map.entrySet()) {
      multiset.setCount(entry.getKey(), entry.getValue());
    }
    return multiset;
  }

  public static <E extends Enum<E>> Collector<E, ?, EnumSet<E>> toEnumSet(Class<E> clazz) {
    return Collectors.toCollection(() -> EnumSet.noneOf(clazz));
  }

  public static <K extends Comparable<K>, V extends Comparable<V>> ImmutableSortedMap<K, V> inverseSortedMap(ImmutableMultimap<V, K> multimap) {
    ImmutableSortedMap.Builder<K, V> builder = ImmutableSortedMap.naturalOrder();
    multimap.entries().forEach(e -> builder.put(e.getValue(), e.getKey()));
    return builder.build();
  }

  public static <T, K, U> Collector<T, ?, SortedMap<K,U>> toSortedMap(
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends U> valueMapper) {
    return Collectors.toMap(keyMapper, valueMapper, throwingMerger(), TreeMap::new);
  }

  /** Default map merger not exposed by Collectors. */
  @SuppressWarnings("unchecked")
  @SneakyThrows(ReflectiveOperationException.class)
  private static <T> BinaryOperator<T> throwingMerger() {
    Method m = Collectors.class.getDeclaredMethod("throwingMerger");
    m.setAccessible(true);
    return (BinaryOperator<T>) m.invoke(null);
  }
}
