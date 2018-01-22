package projects.crawler.data.example;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.EnumMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Bytes;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongojack.DBCursor;
import org.mongojack.DBProjection;
import org.mongojack.DBProjection.ProjectionBuilder;
import org.mongojack.DBQuery;
import org.mongojack.DBQuery.Query;
import org.mongojack.DBUpdate;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import org.mongojack.internal.update.SingleUpdateOperationValue;
import projects.crawler.utils.CollectionUtils;
import projects.crawler.data.model.Model;
import projects.crawler.data.db.ModelSerializer;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by yazhoucao on 8/13/16.
 */
@Slf4j
public abstract class OldBaseDao<T extends Model<K>, K> {

  /** Enables wrapping of generic types, rather than only raw classes (with no unbound type variables) */
  private static class GenericJacksonDBCollection<T, K> extends JacksonDBCollection<T, K> {

    protected GenericJacksonDBCollection(DBCollection dbCollection, JavaType type, JavaType keyType,
        ObjectMapper objectMapper, Class<?> view, Map<Feature, Boolean> features) {
      super(dbCollection, type, keyType, objectMapper, view, features);
    }

    public static <T, K> JacksonDBCollection<T, K> wrap(DBCollection collection, ParameterizedType type,
        Class<K> keyType, ObjectMapper objectMapper) {
      return new GenericJacksonDBCollection<>(collection, objectMapper.constructType(type),
          objectMapper.constructType(keyType), objectMapper, null, null);
    }
  }

  public static enum Upsert {
    DISABLED,
    ENABLED,
  }

  public static enum Multi {
    DISABLED,
    ENABLED,
  }

  protected final JacksonDBCollection<T, K> collection;

  public OldBaseDao(DB db, String collectionName, Class<T> clazz, Class<K> idClazz) {
    this.collection = JacksonDBCollection.wrap(db.getCollection(collectionName), clazz, idClazz, ModelSerializer.MONGO_MAPPER);
  }

  public OldBaseDao(DB db, String collectionName, Class<? extends OldBaseDao<T, K>> clazz) {
    this(db, collectionName, actualTypeArguments(clazz));
  }

  @SuppressWarnings("unchecked")
  private OldBaseDao(DB db, String collectionName, Type[] actualTypeArguments) {
    this.collection = GenericJacksonDBCollection.wrap(db.getCollection(collectionName),
        (ParameterizedType) actualTypeArguments[0], (Class<K>) actualTypeArguments[1], ModelSerializer.MONGO_MAPPER);
  }

  @SuppressWarnings("unchecked")
  private static <T extends Model<K>, K> Type[] actualTypeArguments(Class<? extends OldBaseDao<T,K>> clazz) {
    Type t = clazz.getGenericSuperclass();
    if (t instanceof ParameterizedType) {
      return actualTypeArguments(((ParameterizedType) t));
    }
    return actualTypeArguments((Class<? extends OldBaseDao<T,K>>) t);
  }

  @SuppressWarnings("unchecked")
  private static <T extends Model<K>, K> Type[] actualTypeArguments(ParameterizedType t) {
    Type rawType = t.getRawType();
    if (rawType.equals(OldBaseDao.class)) {
      return t.getActualTypeArguments();
    }
    return actualTypeArguments((Class<? extends OldBaseDao<T, K>>) rawType);
  }

  public String getDbName() {
    return collection.getDB().getName();
  }

  public String getCollectionName() {
    return collection.getName();
  }

  public String getDbAndCollectionName() {
    return collection.getFullName();
  }

  public Map<String, DBObject> getIndexes() {
    Map<String, DBObject> indexMap = new TreeMap<>();
    for (DBObject indexInfo : collection.getIndexInfo()) {
      indexMap.put((String) indexInfo.get("name"), indexInfo);
    }
    return indexMap;
  }

  public ReadPreference getReadPreference() {
    return collection.getReadPreference();
  }

  public void setReadPreference(ReadPreference readPreference) {
    collection.setReadPreference(readPreference);
  }

  public List<?> distinct(String key, DBObject query) {
    return collection.distinct(key, query);
  }

  public List<?> distinct(String key, Query query) {
    return distinct(key, queryToDBObject(query));
  }

  public DBCursor<T> find() {
    return collection.find();
  }

  public DBCursor<T> find(DBObject query) {
    return collection.find(query).addOption(Bytes.QUERYOPTION_NOTIMEOUT);
  }

  public DBCursor<T> find(Query query) {
    return collection.find(query).addOption(Bytes.QUERYOPTION_NOTIMEOUT);
  }

  public DBCursor<T> find(DBObject query, Map<String, Boolean> projection) {
    return collection.find(query, new BasicDBObject(projection)).addOption(Bytes.QUERYOPTION_NOTIMEOUT);
  }

  public DBCursor<T> find(Query query, Map<String, Boolean> projection) {
    return collection.find(query, new BasicDBObject(projection)).addOption(Bytes.QUERYOPTION_NOTIMEOUT);
  }

  /** Returns the next element or null.  Uses limit(1) first which built-in findOne() doesn't. */
  public T findOne() {
    DBCursor<T> cursor = collection.find().limit(1);
    return cursor.hasNext() ? cursor.next() : null;
  }

  public T findOne(ReadPreference readPreference) {
    org.mongojack.DBCursor<T> cursor = collection.find(new BasicDBObject(), null).limit(1)
        .setReadPreference(readPreference);
    if (cursor.hasNext()) {
      return cursor.next();
    } else {
      return null;
    }
  }

  protected T findOne(Query query, Map<String, Boolean> projection) {
    return collection.findOne(query, new BasicDBObject(projection));
  }

  public T getById(K id) {
    if (id == null) {
      return null;
    }
    return collection.findOneById(id);
  }

  public T getById(K id, ReadPreference readPreference) {
    return collection.findOne(DBQuery.is("_id", checkNotNull(id)), null, readPreference);
  }

  public T getById(K id, Collection<String> projectedFields) {
    return collection.findOneById(checkNotNull(id), new BasicDBObject(toProjection(projectedFields)));
  }

  public DBCursor<T> getByIds(Collection<K> ids) {
    return collection.find().in("_id", checkNotNull(ids));
  }

  public Map<K, T> getMapByIds(Collection<K> ids) {
    Map<K, T> map = new HashMap<>();
    for (T obj : collection.find().in("_id", checkNotNull(ids))) {
      map.put(obj.getId(), obj);
    }
    return map;
  }

  public DBCursor<T> getByIds(Collection<K> ids, Collection<String> projectedFields) {
    return collection.find(matches("_id", checkNotNull(ids)), new BasicDBObject(toProjection(projectedFields)));
  }

  @SuppressWarnings("unchecked")
  public List<K> getIds(Query query) {
    return (List<K>) distinct("_id", query);
  }

  public T getOrCreateById(K id) {
    if (id == null) {
      return null;
    }
    DBUpdate.Builder update = new DBUpdate.Builder();
    update.addOperation("$setOnInsert", "_id", new SingleUpdateOperationValue(false, true, id));
    return collection.findAndModify(DBQuery.is("_id", id), null, null, false, update, true, true);
  }

  public Map<K, T> mapByIds(Iterable<T> objects) {
    Map<K, T> map = new HashMap<>();
    for (T obj : objects) {
      map.put(obj.getId(), obj);
    }
    return map;
  }

  public Map<String, T> mapByIdStrings(Iterable<T> objects) {
    Map<String, T> map = new HashMap<>(Iterables.size(objects));
    for (T obj : objects) {
      map.put(obj.getId().toString(), obj);
    }
    return map;
  }

  /** Converts from a collection of models to a collection of their keys (e.g. User -> ObjectId) */
  // TODO(kevin): move to Model once we migrate to Java 8
  public static <K> Collection<K> toIds(Collection<? extends Model<K>> objects) {
    return Collections2.transform(objects, Model<K>::getId);
  }

  /** Converts from an iterable of models to an iterable of their keys (e.g. User -> ObjectId) */
  // TODO(kevin): move to Model once we migrate to Java 8
  public static <K> Iterable<K> toIds(Iterable<? extends Model<K>> objects) {
    return Iterables.transform(objects, Model<K>::getId);
  }

  // TODO(kevin): remove catch DuplicateKeyException once MongoDB has been upgraded with fix for MongoDB bug SERVER-14322
  public void save(T entity) {
    try {
      attemptSave(entity);
    } catch (DuplicateKeyException e) {
      T refetchedEntity = getById(entity.getId());
      log.warn("Re-trying save of {}:\n{}\nRefetched contents:\n{}\nCaused by: ", entity.getClass().getSimpleName(),
          convertToDbObject(entity), convertToDbObject(refetchedEntity), e);
      attemptSave(entity);
    } catch (Exception e) { // wrap the thrown exception with a more informative message
      String entityString;
      try { // first try using default ObjectMapper to convert entity to loggable json
        entityString = new ObjectMapper().writeValueAsString(entity);
      } catch (Exception e2) {
        // if that didn't work, just use entity.toString()
        entityString = entity.toString();
      }
      throw new RuntimeException("Failed to save " + entity.getClass().getSimpleName() + " " + entityString + ":\n", e);
    }
  }

  private void attemptSave(T entity) {
    WriteResult<T, K> result = collection.save(entity);
    if (entity.getId() == null) {
      entity.setId(result.getSavedId());
    }
  }

  /**
   * Bulk save (i.e. upsert by id) operation.
   *
   * Unordered, for best possible performance.
   * Not made public so that it can be exposed publicly only in select Daos where it is not too dangerous.
   * NB(kevin): always uses update rather than insert because bulk update can't mix inserts and updates.
   */
  // TODO(kevin): contribute this method to MongoJack (as a new method in JacksonDBCollection)
  protected BulkWriteResult saveAll(Iterable<T> entities) {
    BulkWriteOperation bulk = collection.getDbCollection().initializeUnorderedBulkOperation();
    // TODO(bhan): handle empty entities iterable
    for (T entity : entities) {
      DBObject document = collection.convertToDbObject(entity);
      Object id = document.get("_id");
      if (id == null) {
        document.put("_id", id = new ObjectId());
        entity.setId(collection.convertFromDbId(id));
      }
      bulk.find(new BasicDBObject("_id", id)).upsert().replaceOne(document);
    }
    return bulk.execute();
  }

  public boolean insert(T entity) {
    try {
      WriteResult<T, K> result = collection.insert(entity);
      if (entity.getId() == null) {
        entity.setId(result.getSavedId());
      }
      return true;
    } catch (MongoException e) {
      log.debug("MongoDB insert exception: ", e);
      return false;
    }
  }

  /** Atomically insert a new entity or return existing entity with the same _id. */
  // TODO(kevin): contribute this method to MongoJack (as a new method in JacksonDBCollection)
  public T insertOrGetById(T entity) throws MongoException {
    K id = entity.getId();
    DBObject document = collection.convertToDbObject(entity);
    document.removeField("_id");
    return collection.findAndModify(collection.serializeQuery(DBQuery.is("_id", id)),
        null, null, false, new BasicDBObject("$setOnInsert", document), false, true);
  }

  public WriteResult<T, K> insert(List<T> entities, WriteConcern wc) {
    return collection.insert(entities, wc);
  }

  public WriteResult<T, K> delete(T entity) {
    return collection.removeById(entity.getId());
  }

  public WriteResult<T, K> deleteById(K id) {
    return collection.removeById(id);
  }

  public WriteResult<T, K> updateById(K id, DBUpdate.Builder update) {
    return collection.updateById(id, update);
  }

  public void dropCollection() {
    collection.drop();
  }

  public DBCursor<T> getAll() {
    return collection.find().addOption(Bytes.QUERYOPTION_NOTIMEOUT);
  }

  public DBCursor<T> getFirst(int n) {
    return collection.find().limit(n);
  }

  public DBCursor<T> getLast(int n) {
    return collection.find().sort(new BasicDBObject("$natural", -1)).limit(n);
  }

  public long getCount() {
    return collection.getCount();
  }

  public long getCountEstimate() {
    CommandResult stats = collection.getStats();
    return !stats.containsField("count") ? 0l : stats.getLong("count");
  }

  public long getCount(Query query) {
    return collection.getCount(query);
  }

  public DBCursor<T> getAllWithFields(Map<String, Boolean> fields) {
    return find(DBQuery.empty(), fields);
  }

  /**
   * Client-side grouping.
   * Requires that keyPath is indexed. Also Checks that getter for keyPath returns keyType.
   */
  public <V> GroupedCursor<T, V> group(DBCursor<T> cursor, final String keyPath, Class<V> keyType) {
    return new GroupedCursor<>(cursor, keyPath, getter(keyPath, keyType));
  }

  /**
   * Client-side grouping.
   * Requires that sortPath is indexed. Also Checks that getter for keyPath returns keyType.
   */
  public <V> GroupedCursor<T, V> group(DBCursor<T> cursor, final String sortPath, final String keyPath, Class<V> keyType) {
    return new GroupedCursor<>(cursor, sortPath, keyPath, getter(keyPath, keyType));
  }

  protected <V> Function<Object, V> getter(String path, Class<V> keyType) {
    return getter(Splitter.on('.').split(path).iterator(), collection.getCollectionKey().getType(), keyType);
  }

  private static <V> Function<Object, V> getter(Iterator<String> pathIterator, JavaType type, Class<V> keyType) {
    String path = pathIterator.next();

    if (CharMatcher.DIGIT.matchesAllOf(path)) {
      if (!type.isCollectionLikeType()) {
        throw new IllegalArgumentException("Can only use indices on collection-like types");
      }

      return chain(x -> Iterables.get((Iterable<?>) x, Integer.valueOf(path)), getter(pathIterator, type.containedType(0), keyType));
    }

    BeanDescription beanDescription = ModelSerializer.MONGO_MAPPER.getSerializationConfig().introspect(type);

    BeanPropertyDefinition property = findProperty(beanDescription, path);
    if (!pathIterator.hasNext()) {
      return getter(property, keyType);
    }

    return chain(getter(property, Object.class),
        getter(pathIterator, property.getAccessor().getType(beanDescription.bindingsForBeanType()), keyType));
  }

  /** NPE-proof function composition. */
  private static <T1, T2, T3> Function<T1, T3> chain(Function<T1, T2> f1, Function<? super T2, T3> f2) {
    return o -> {
      T2 v = f1.apply(o);
      return v == null ? null : f2.apply(v);
    };
  }

  public void setField(K id, String fieldName, Object value) {
    checkNotNull(id);
    checkArgument(!StringUtils.isBlank(fieldName));

    DBUpdate.Builder builder;
    if (value == null) {
      builder = DBUpdate.unset(fieldName);
    } else {
      builder = DBUpdate.set(fieldName, value);
    }
    collection.updateById(checkNotNull(id), builder);
  }

  /**
   * Update only the provided (key, value) pair fields.
   *
   * Note: Ignores any non-existent fields.  MongoDB doesn't error on those.
   *
   * @param id key of the object to update
   * @param fields the set of (field, fieldValue) to update
   */
  public void updateFields(K id, Map<String, ? extends Object> fields) {
    checkArgument(!fields.isEmpty());
    for (String field : fields.keySet()) {
      checkArgument(!StringUtils.isBlank(field));
    }

    DBUpdate.Builder update = new DBUpdate.Builder();
    for (Map.Entry<String, ? extends Object> field : fields.entrySet()) {
      update.set(field.getKey(), field.getValue());
    }
    collection.updateById(id, update);
  }

  /**
   * Update only the specified fields.
   * Particularly useful for saving changes made to partial objects, such as returned by
   * {@link #getAllWithFields}.
   * Note: field names are the bound JSON names, not the bean property names, which may be different.
   */
  public WriteResult<T, K> updateFields(T bean, String... fields) {
    return updateFields(bean, Arrays.asList(fields));
  }

  /**
   * Update only the specified fields.
   * Particularly useful for saving changes made to partial objects, such as returned by
   * {@link #getAllWithFields}.
   * Note: field names are the bound JSON names, not the bean property names, which may be different.
   */
  public WriteResult<T, K> updateFields(T bean, Collection<String> fields) {
    checkArgument(!fields.isEmpty());
    for (String field : fields) {
      checkArgument(!StringUtils.isBlank(field));
    }

    DBUpdate.Builder builder = new DBUpdate.Builder();
    //JsonNode rootNode = ModelSerializer.MONGO_MAPPER.valueToTree(pojo);
    BeanDescription beanDescription = ModelSerializer.MONGO_MAPPER.getSerializationConfig()
        .introspect(ModelSerializer.MONGO_MAPPER.constructType(bean.getClass()));
    //List<BeanPropertyDefinition> properties = beanDescription.findProperties();
    for (String field : fields) {
      BeanPropertyDefinition property = findProperty(beanDescription, field);
      Object newValue = getValue(bean, property);
      if (newValue == null) {
        builder.unset(field);
      } else if (ModelSerializer.MONGO_MAPPER.getSerializationConfig().getSerializationInclusion() == Include.NON_EMPTY
          && isEmpty(newValue)) {
        builder.unset(field);
      } else {
        builder.set(field, newValue);
      }
    }
    return collection.updateById(bean.getId(), builder);
  }

  public WriteResult<T, K> update(Query query, DBUpdate.Builder update, Upsert upsert, Multi multi) {
    return collection.update(
        query,
        update,
        upsert == Upsert.ENABLED,
        multi == Multi.ENABLED);
  }

  /**
   * Returns a cursor over all documents in the collection which have the required fields.
   * The projection returns only the _id field and the required and optional fields provided.
   */
  public DBCursor<T> getAllWithFields(Collection<String> requiredFields, Collection<String> optionalFields) {
    Query condition;
    if (!CollectionUtils.isNullOrEmpty(requiredFields)) {
      List<Query> parts = Lists.newArrayList();
      for (String field : requiredFields) {
        parts.add(DBQuery.exists(field));
      }
      Query[] partsArray = parts.toArray(new Query[parts.size()]);
      condition = DBQuery.and(partsArray);
    } else {
      condition = DBQuery.empty();
    }
    Map<String, Boolean> projection = toProjection(requiredFields);
    projection.putAll(toProjection(optionalFields));
    return find(condition, projection).addOption(Bytes.QUERYOPTION_NOTIMEOUT);
  }

  /**
   * Returns a cursor over all documents in the collection with id greater than indicated,
   * which have the required fields, which have one or more of the needed fields and with
   * a projection of the wanted fields.
   *
   * @author Shawn Murphy (shawn@connectifier.com)
   */
  public DBCursor<T> getAllWithRequiredNeededAndWantedFields(Collection<String> requiredFields,
      Collection<String> neededFields, Collection<String> wantedFields) {
    Query condition;
    if (!CollectionUtils.isNullOrEmpty(requiredFields)) {
      List<Query> parts = Lists.newArrayList();
      for (String field : requiredFields) {
        parts.add(DBQuery.exists(field));
      }
      List<Query> needs = Lists.newArrayList();
      for (String field : neededFields) {
        needs.add(DBQuery.exists(field));
      }
      Query[] needsArray = needs.toArray(new Query[needs.size()]);
      parts.add(DBQuery.or(needsArray));
      Query[] partsArray = parts.toArray(new Query[parts.size()]);
      condition = DBQuery.and(partsArray);
    } else {
      condition = DBQuery.empty();
    }
    Map<String, Boolean> projection = toProjection(requiredFields);
    projection.putAll(toProjection(neededFields));
    projection.putAll(toProjection(wantedFields));
    return find(condition, projection);
  }

  /**
   * Returns a cursor over all documents in the collection which have the required fields.
   * Does not use a projection.
   */
  public DBCursor<T> getOnlyWithFields(Collection<String> requiredFields) {
    Query condition;
    if (!CollectionUtils.isNullOrEmpty(requiredFields)) {
      List<Query> parts = Lists.newArrayList();
      for (String field : requiredFields) {
        parts.add(DBQuery.exists(field));
      }
      Query[] partsArray = parts.toArray(new Query[parts.size()]);
      condition = DBQuery.and(partsArray);
    } else {
      condition = DBQuery.empty();
    }
    return find(condition);
  }

  protected static Map<String, Boolean> toProjection(String... fields) {
    return toProjection(Arrays.asList(fields));
  }

  protected static Map<String, Boolean> toProjection(Iterable<String> fields) {
    Map<String, Boolean> projection = Maps.newHashMap();
    if (fields != null) {
      for (String field : fields) {
        projection.put(field, true);
      }
    }
    return projection;
  }

  public SortedMultiset<Integer> aggregateHistogram(Collection<String> uniqueTuples, String keyColumn, Query query) {
    Iterable<BasicDBObject> map = aggregateCountQuery(uniqueTuples, keyColumn, query);
    SortedMultiset<Integer> histogram = TreeMultiset.create();
    for (BasicDBObject count : map) {
      histogram.add(count.getInt("count"));
    }
    return histogram;
  }

  /**
   * About 3x faster than the equivalent group() query.
   *
   * @return a count of each value (of type V) in keyColumn for documents that match query
   */
  public <V> Map<V, Integer> aggregateCount(String keyColumn, Query query) {
    Iterable<BasicDBObject> results = aggregateCountQuery(null, keyColumn, query);
    return toMap(keyColumn, results);
  }

  private Iterable<BasicDBObject> aggregateCountQuery(Collection<String> uniqueTuples, String keyColumn, Query query) {
    Preconditions.checkNotNull(query); // use DBQuery.empty() to match all
    List<DBObject> stages = Lists.newArrayList();
    stages.add(match(query));
    if (!CollectionUtils.isNullOrEmpty(uniqueTuples)) {
      // If requested, first select unique tuples of the specified columns.
      // e.g. first get unique (user, person) pairs then count persons contacted by user or users contacting persons
      stages.add(groupCount(ImmutableMap.of("userId", "$userId", "personId", "$personId")));
      keyColumn = "_id." + keyColumn;
    }
    stages.add(groupCount(keyColumn));
    return aggregate(stages, null);
  }

  public <V> Map<V, SortedMap<String, Integer>> aggregateCountExists(String keyColumn, Query query, Iterable<String> columnsToCount) {
    Iterable<BasicDBObject> results = aggregateCountExistsQuery(keyColumn, query, columnsToCount);
    return toMapMap(keyColumn, results, columnsToCount);
  }

  private Iterable<BasicDBObject> aggregateCountExistsQuery(String keyColumn, Query query, Iterable<String> columnsToCount) {
    Preconditions.checkNotNull(query); // use DBQuery.empty() to match all
    return aggregate(ImmutableList.of(
        match(query),
        groupCountExists(keyColumn, columnsToCount)), null);
  }

  protected Iterable<BasicDBObject> aggregateField(String keyColumn, Query query, String aggregatedField) {
    Preconditions.checkNotNull(query);
    return aggregate(ImmutableList.of(
        match(query),
        project(ImmutableMap.of(keyColumn, Boolean.TRUE, aggregatedField, Boolean.TRUE)),
        groupField(keyColumn, aggregatedField)), null);
  }

  @SuppressWarnings("unchecked")
  protected Iterable<BasicDBObject> aggregate(List<DBObject> pipeline, ReadPreference readPreference) {
    DBCollection dbCollection = collection.getDbCollection();
    AggregationOutput output;
    if (readPreference != null) {
      output = dbCollection.aggregate(pipeline, readPreference);
    } else {
      output = dbCollection.aggregate(pipeline);
    }
    Iterable<?> results = output.results();
    return (Iterable<BasicDBObject>) results;
  }

  protected BasicDBObject project(Map<String, ? extends Object> fields) {
    ProjectionBuilder projection = DBProjection.include("FOOOOOOO"); // TODO(erik): Silly
    for (Entry<String, ? extends Object> field : fields.entrySet()) {
      projection.put(field.getKey(), field.getValue());
    }
    return new BasicDBObject("$project", projection);
  }

  protected BasicDBObject match(Query query) {
    return new BasicDBObject("$match", queryToDBObject(query));
  }

  protected DBObject queryToDBObject(Query query) {
    return collection.serializeQuery(query);
  }

  public DBObject updateToDBObject(DBUpdate.Builder update) {
    return update.serialiseAndGet(ModelSerializer.MONGO_MAPPER, collection.getCollectionKey().getType());
  }

  protected static BasicDBObject groupField(String keyColumn, String field) {
    return new BasicDBObject("$group", new BasicDBObject("_id", "$" + keyColumn).append(field + "s", new BasicDBObject("$push", "$" + field)));
  }

  // Just one field for _id
  protected static BasicDBObject groupCount(String keyColumn) {
    return new BasicDBObject("$group", new BasicDBObject("_id", "$" + keyColumn).append("count", new BasicDBObject("$sum", 1)));
  }

  protected static BasicDBObject groupCountExists(String keyColumn, Iterable<String> columnsToCount) {
    BasicDBObject group = new BasicDBObject("_id", "$" + keyColumn);
    for (String columnToCount : columnsToCount) {
      group.append(columnToCount + "s", sumExists(columnToCount));
    }
    return new BasicDBObject("$group", group);
  }

  /** @return {$sum: {$cond: [ {$ifNull:["$<<columnToCount>>", 0]}, 1, 0] }} */
  private static BasicDBObject sumExists(String columnToCount) {
    BasicDBObject ifNull = new BasicDBObject("$ifNull", Arrays.asList("$" + columnToCount, 0));
    BasicDBObject cond = new BasicDBObject("$cond", Arrays.asList(ifNull, 1, 0));
    return new BasicDBObject("$sum", cond);
  }

  // Group by a set of named fields and count
  protected static BasicDBObject groupCount(Map<String, ? extends Object> keyMapping) {
    return new BasicDBObject("$group", groupKey(keyMapping).append("count", new BasicDBObject("$sum", 1)));
  }

  // Group by a set of named fields
  protected static BasicDBObject group(Map<String, ? extends Object> keyMapping) {
    return new BasicDBObject("$group", groupKey(keyMapping));
  }

  // Group by a set of named fields: Extensible argument for $group aggregate command
  protected static BasicDBObject groupKey(Map<String, ? extends Object> keyMapping) {
    BasicDBObject keyGroup = null;
    for (Map.Entry<String, ? extends Object> keyPair : keyMapping.entrySet()) {
      if (keyGroup == null) {
        keyGroup = new BasicDBObject(keyPair.getKey(), keyPair.getValue());
      } else {
        keyGroup.append(keyPair.getKey(), keyPair.getValue());
      }
    }
    return new BasicDBObject("_id", keyGroup);
  }

  protected <V> Map<V, Integer> toMap(String keyColumn, Iterable<BasicDBObject> results) {
    Class<?> keyType = getType(keyColumn);
    Map<V, Integer> counts = Maps.newHashMap();
    for (BasicDBObject row : results) {
      // A little introspection magic is required here to decide whether to use get, getInt, getLong, or getObjectId
      V key = getKey(row, "_id", keyType); // In an aggregate group query, the key is always _id
      counts.put(key, row.getInt("count"));
    }
    return counts;
  }

  private <V> Map<V, SortedMap<String, Integer>> toMapMap(String keyColumn, Iterable<BasicDBObject> results, Iterable<String> columnsToCount) {
    Class<?> keyType = getType(keyColumn);
    Map<V, SortedMap<String, Integer>> counts = Maps.newHashMap();
    for (BasicDBObject row : results) {
      // A little introspection magic is required here to decide whether to use get, getInt, getLong, or getObjectId
      V key = getKey(row, "_id", keyType); // In an aggregate group query, the key is always _id
      SortedMap<String, Integer> submap = Maps.newTreeMap();
      for (String columnToCount : columnsToCount) {
        submap.put(columnToCount, row.getInt(columnToCount + "s"));
      }
      counts.put(key, submap);
    }
    return counts;
  }

  /**
   * @deprecated use aggregates instead
   */
  @Deprecated
  @SuppressWarnings("unchecked")
  private ArrayList<BasicDBObject> group(String keyColumn, Query condition, Map<String, ?> initial, String reduceJs) {
    log.debug("init: {}, reduce: {}", initial, reduceJs);
    DBObject result = collection.group(new BasicDBObject(keyColumn, 1),
        condition == null ? null : collection.serializeQuery(condition),
        new BasicDBObject(initial),
        reduceJs);
    return (ArrayList<BasicDBObject>) result;
  }

  /** Assumes there is a POJO Field for the JSON field */
  private Class<?> getType(String field) {
    BeanDescription beanDescription = ModelSerializer.MONGO_MAPPER.getSerializationConfig()
        .introspect(collection.getCollectionKey().getType());
    BeanPropertyDefinition property = findProperty(beanDescription, field);
    Class<?> type = property.getField().getAnnotated().getType();
    log.debug("POJO type of \"{}\" is \"{}\"", field, type);
    return type;
  }

  @SuppressWarnings("unchecked")
  private static <V> V getKey(BasicDBObject row, String keyColumn, Class<?> keyType) {
    switch(keyType.getName()) {
    case "int":
    case "java.lang.Integer":
      return (V) Integer.valueOf(row.getInt(keyColumn));
    case "long":
    case "java.lang.Long":
      return (V) Long.valueOf(row.getLong(keyColumn));
    case "org.bson.types.ObjectId":
      return (V) checkNotNull(row.getObjectId(keyColumn));
    default:
      log.info("Default column type: " + keyType.getName());
      return (V) row.get(keyColumn);
    }
  }

  protected <V extends Comparable<? super V>, C extends Enum<C> & GroupCountable>
  SortedMap<V, Multiset<C>> group(String keyColumn, Query condition, Collection<C> countables) {
    ArrayList<BasicDBObject> counts = group(keyColumn, condition, initial(countables), reduceJs(countables));
    SortedMap<V, Multiset<C>> results = new TreeMap<>();
    for (BasicDBObject o : counts) {
      @SuppressWarnings("unchecked")
      V value = (V) o.get("domainSearched");
      Multiset<C> stats = EnumMultiset.create(countables.iterator().next().getDeclaringClass());
      results.put(value, stats);
      for (C countable : countables) {
        stats.add(countable, o.getInt(countable.getName()));
      }
    }
    return results;
  }

  public static interface GroupCountable {
    public String getName();
    public String getPredicate();
  }

  private static <C extends GroupCountable> ImmutableMap<String, Integer> initial(Collection<C> countables) {
    ImmutableMap.Builder<String, Integer> builder = ImmutableMap.builder();
    for (C countable : countables) {
      builder.put(countable.getName(), 0);
    }
    return builder.build();
  }

  private static <C extends GroupCountable> String reduceJs(Collection<C> countables) {
    StringBuilder sb = new StringBuilder("function(curr,result){\n");
    for (C countable : countables) {
      sb.append("  if(" + countable.getPredicate() + ") result." + countable.getName() + " += 1;\n");
    }
    return sb.append('}').toString();
  }

  private static Object getValue(Object bean, BeanPropertyDefinition property) {
    AnnotatedMethod getter = property.getGetter();
    if (getter != null) {
      return getter.getValue(bean);
    }
    AnnotatedField field = property.getField();//.getAccessor();
    if (field != null) {
      //return field.getValue(bean);
      return getValue(field, bean);
    }
    throw new RuntimeException("Can't access value of " + property);
  }

  @SuppressWarnings("unchecked")
  private static <V> Function<Object, V> getter(BeanPropertyDefinition property, Class<V> valueType) {
    AnnotatedMethod getter = property.getGetter();
    if (getter != null) {
      Preconditions.checkArgument(valueType.isAssignableFrom(getter.getAnnotated().getReturnType()));
      return o -> (V) getter.getValue(o);
    }
    AnnotatedField field = property.getField();
    Preconditions.checkArgument(ClassUtils.isAssignable(field.getAnnotated().getType(), valueType, true),
        "Can't get a %s from %s %s", valueType.getTypeName(), field.getAnnotated().getType(), field.getFullName());
    return o -> (V) getValue(field, o);
  }

  @SneakyThrows({IllegalArgumentException.class, IllegalAccessException.class})
  private static Object getValue(AnnotatedField field, Object bean) {
    Field f = findField(bean.getClass(), field.getName());
    f.setAccessible(true);
    return f.get(bean);
  }

  private static Field findField(Class<?> clazz, String internalName) {
    for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
      for (Field f : c.getDeclaredFields()) {
        if (f.getName().equals(internalName)) {
          return f;
        }
      }
    }
    return null;
  }

  private static BeanPropertyDefinition findProperty(BeanDescription beanDescription, String field) {
    for (BeanPropertyDefinition property : beanDescription.findProperties()) {
      if (property.hasField() && property.getField().hasAnnotation(JsonUnwrapped.class)) {
        BeanDescription unwrappedBeanDescription = ModelSerializer.MONGO_MAPPER.getSerializationConfig()
            .introspect(ModelSerializer.MONGO_MAPPER.constructType(property.getField().getRawType()));
        try {
          log.debug("Searching for {} in {}: {}", field, unwrappedBeanDescription.getType(),
              unwrappedBeanDescription.findProperties());
          BeanPropertyDefinition unwrappedProperty = findProperty(unwrappedBeanDescription, field);
          log.debug("Found unwrapped property {}", unwrappedProperty);
          return delegatePropertyDescription(beanDescription, property, unwrappedProperty);
        } catch (IllegalArgumentException e) { }
      }
      if (property.getName().equals(field)) {
        return property;
      }
    }
    throw new IllegalArgumentException("Field not found: " + field);
  }

  /**
   * Find matching delegate property getter for unwrapped property chain.
   * NB(kevin): assumes there is a delegate getter for the unwrapped property, such as easily created by lombok Delegate annotation.
   */
  @SneakyThrows(IntrospectionException.class) @SuppressWarnings("deprecation")
  private static BeanPropertyDefinition delegatePropertyDescription(BeanDescription beanDescription,
      BeanPropertyDefinition property1, BeanPropertyDefinition property2) {
    for (PropertyDescriptor prop : Introspector.getBeanInfo(beanDescription.getBeanClass()).getPropertyDescriptors()) {
      if (prop.getName().equals(property2.getName())) {
        POJOPropertyBuilder delegatePropertyBuilder =
            new POJOPropertyBuilder(PropertyName.construct(prop.getName(), null), null, true);
        delegatePropertyBuilder.addGetter(new AnnotatedMethod(beanDescription.getClassInfo(), prop.getReadMethod(), null, null),
            prop.getName(), true, false);
        return delegatePropertyBuilder;
      }
    }
    throw new IllegalArgumentException("Delegate not found for: " + beanDescription.getBeanClass()
        + "." + property1.getName() + "." + property2.getName());
  }

  protected static <T extends Collection<?>> T checkNotEmpty(T collection) {
    Preconditions.checkArgument(!collection.isEmpty());
    return collection;
  }

  /** Filter out nulls (without modifying input collection) */
  protected static <E> Collection<E> nonNulls(Collection<E> collection) {
    return Collections2.filter(collection, Predicates.notNull());
  }

  private static boolean isEmpty(Object object) {
    if (object instanceof String) {
      if ("".equals(object)) {
        return true;
      }
    } else if (object instanceof Collection) {
      if (((Collection<?>)object).isEmpty()) {
        return true;
      }
    } else if (object instanceof Map) {
      if (((Map<?,?>)object).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  protected static Query matches(String field, Collection<?> values) {
    return values.size() == 1 ? DBQuery.is(field, values.iterator().next()) : DBQuery.in(field, values);
  }

  /** Work-around for DBQuery.in not working with collection-valued properties. */
  protected static Query matchesAny(String field, Collection<?> values) {
    return values.size() == 1 ? DBQuery.all(field, values.iterator().next()) : DBQuery.or(values.stream()
        .map(v -> DBQuery.all(field, v)).toArray(Query[]::new));
  }

  public OldBaseDao<T, K> withReadPreference(ReadPreference preference) {
    this.collection.setReadPreference(preference);
    return this;
  }

  @VisibleForTesting
  public DBObject convertToDbObject(T entity) {
    return collection.convertToDbObject(entity);
  }
}
