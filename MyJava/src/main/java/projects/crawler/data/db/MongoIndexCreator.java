package projects.crawler.data.db;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoCommandException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toSet;
import static org.mongojack.internal.util.SerializationUtils.serializeFields;

@Slf4j
public class MongoIndexCreator implements Consumer<DBCollection> {
  @VisibleForTesting static final Set<Object> FALSEY_VALUES = unmodifiableSet(
      new LinkedHashSet<>(asList(false, null, 0, 0L, 0f, 0d, -0f, -0d)));

  @Data
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class IndexSpec {
    /** key is field name, value is sort order (true for ascending, false for descending) */
    private final ImmutableList<Map.Entry<String, Boolean>> fields;
    private final boolean sparse;
    private final boolean unique;
    private final Long secondsTTL;
    /** BSON expression (serialized to a String to make it immutable) */
    private final String partialFilterExpression;

    public DBObject getFields() {
      BasicDBObject object = new BasicDBObject();
      for (Map.Entry<String, Boolean> field : fields) {
        object.append(field.getKey(), field.getValue() ? 1 : -1);
      }
      return object;
    }

    public DBObject indexOptions(boolean background) {
      BasicDBObject options = new BasicDBObject();
      if (background) {
        options.append("background", true);
      }
      if (sparse) {
        options.append("sparse", true);
      }
      if (unique) {
        options.append("unique", true);
      }
      if (secondsTTL != null) {
        options.append("expireAfterSeconds", secondsTTL);
      }
      if (partialFilterExpression != null) {
        options.append("partialFilterExpression", BasicDBObject.parse(partialFilterExpression));
      }
      return options;
    }

    private IndexSpec(Map<String, Boolean> fields, boolean sparse, boolean unique, Long secondsTTL,
        DBObject partialFilterExpression) {
      this(ImmutableList.copyOf(fields.entrySet()),
          sparse, unique, secondsTTL, Objects.toString(partialFilterExpression, null));
    }

    public IndexSpec(String[] fields, boolean sparse, boolean unique, Long secondsTTL,
        DBObject partialFilterExpression) {
      this(indexFields(fields),
          sparse, unique, secondsTTL, partialFilterExpression);
    }

    @SuppressWarnings("unchecked")
    public IndexSpec(DBObject existingIndex) {
      this(
          Maps.transformValues((Map<String, Object>) existingIndex.get("key"),
              IndexSpec::normalizeSortOrderValue),
          !FALSEY_VALUES.contains(existingIndex.get("sparse")),
          !FALSEY_VALUES.contains(existingIndex.get("unique")),
          longValue(existingIndex.get("expireAfterSeconds")),
          (DBObject) existingIndex.get("partialFilterExpression"));
    }

    private static Long longValue(Object o) {
      return o == null ? null : ((Number) o).longValue();
    }

    private static boolean normalizeSortOrderValue(Object value) {
      return ((Number) value).doubleValue() > 0;
    }

    private static ImmutableMap<String, Boolean> indexFields(String... fieldNames) {
      ImmutableMap.Builder<String, Boolean> fields = ImmutableMap.builder();
      for (String field : fieldNames) {
        if (field.startsWith("-")) {
          fields.put(field.substring(1), false);
        } else {
          fields.put(field, true);
        }
      }
      return fields.build();
    }
  }

  private final ImmutableSet<IndexSpec> indexSpecs;

  private MongoIndexCreator(Iterable<IndexSpec> specs) {
    this.indexSpecs = ImmutableSet.copyOf(specs);
  }

  @Override
  public void accept(DBCollection collection) {
    if (indexSpecs.isEmpty()) {
      return;
    }
    Set<IndexSpec> existingIndexes = collection.getIndexInfo().stream().map(IndexSpec::new).collect(toSet());
    for (IndexSpec indexSpec : Sets.difference(indexSpecs, existingIndexes)) {
      log.warn("Building missing index on {}: {}\n\t({} existing indexes: {})",
          collection, indexSpec, existingIndexes.size(), existingIndexes);
      createIndex(collection, indexSpec);
    }
  }

  private void createIndex(DBCollection collection, IndexSpec spec) {
    long nanos = System.nanoTime();
    DBObject fields = spec.getFields();
    try {
      collection.createIndex(fields, spec.indexOptions(spec.secondsTTL == null));
    } catch (MongoCommandException e) {
      log.error("Failed to create {}{}{}index {} on collection {}: ",
          spec.sparse ? "sparse " : "", spec.unique ? "unique " : "", spec.secondsTTL != null ? "TTL " : "",
          fields, collection.getName(), e);
    } finally {
      nanos = System.nanoTime() - nanos;
      log.debug("Created {}{}{}index on {} in {}ms",
          spec.sparse ? "sparse " : "",
          spec.unique ? "unique " : "",
          spec.secondsTTL != null ? "TTL " : "",
          collection.getName(), (nanos + 500_000) / 1_000_000);
    }
  }

  public static Builder builder(MongoIndexCreator... parents) {
    return new Builder(parents);
  }

  public static class Builder {
    private final ArrayList<IndexSpec> specs = new ArrayList<>();

    private Builder(MongoIndexCreator... parents) {
      for (MongoIndexCreator parent : parents) {
        specs.addAll(parent.indexSpecs);
      }
    }

    public MongoIndexCreator build() {
      return new MongoIndexCreator(specs);
    }

    public Builder createIndex(String... fieldNames) {
      specs.add(new IndexSpec(fieldNames, false, false, null, null));
      return this;
    }

    public Builder createSparseIndex(String... fieldNames) {
      specs.add(new IndexSpec(fieldNames, true, false, null, null));
      return this;
    }

    public Builder createUniqueIndex(String... fieldNames) {
      specs.add(new IndexSpec(fieldNames, false, true, null, null));
      return this;
    }

    public Builder createSparseUniqueIndex(String... fieldNames) {
      specs.add(new IndexSpec(fieldNames, true, true, null, null));
      return this;
    }

    public Builder createTTLIndex(long secondsTTL, String fieldName) {
      specs.add(new IndexSpec(new String[] { fieldName }, false, false, secondsTTL, null));
      return this;
    }

    public Builder createPartialIndex(boolean unique, DBObject filter, String... fieldNames) {
      specs.add(new IndexSpec(fieldNames, false, unique, null,
          serializeFields(ModelMapper.MONGO_MAPPER, filter)));
      return this;
    }
  }
}
