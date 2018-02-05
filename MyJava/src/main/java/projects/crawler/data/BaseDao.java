package projects.crawler.data;

import com.mongodb.DBCollection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.DBUpdate;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import projects.crawler.data.db.ModelMapper;
import projects.crawler.data.model.Model;

import static com.google.common.base.Preconditions.checkArgument;

public class BaseDao<T extends Model<K>, K> {
  private static Set<String> indexedCollections = ConcurrentHashMap.newKeySet();

  protected final JacksonDBCollection<T, K> coll;

  public BaseDao(DBCollection mongoColl, Class<T> clazz, Class<K> idClazz) {
    this(JacksonDBCollection.wrap(mongoColl, clazz, idClazz, ModelMapper.MONGO_MAPPER), null);
  }

  public BaseDao(DBCollection mongoColl, Class<T> clazz, Class<K> idClazz, Consumer<DBCollection> indexCreator) {
    this(JacksonDBCollection.wrap(mongoColl, clazz, idClazz, ModelMapper.MONGO_MAPPER), indexCreator);
  }

  private BaseDao(JacksonDBCollection<T, K> collection, @Nullable Consumer<DBCollection> indexCreator) {
    this.coll = collection;
    if (indexCreator != null && indexedCollections.add(collection.getFullName())) {
      indexCreator.accept(collection.getDbCollection());
    }
  }

  public WriteResult<T, K> insert(T doc) {
    return coll.insert(doc);
  }

  public WriteResult<T, K> insertMany(List<T> docs) {
    return coll.insert(docs);
  }

  public long count() {
    return coll.count();
  }

  public T findOne() {
    return coll.find().iterator().next();
  }

  public DBCursor<T> find() {
    return coll.find();
  }

  public DBCursor<T> find(DBQuery.Query query) {
    return coll.find(query);
  }

  public T findById(K id) {
    return coll.findOneById(id);
  }

  public DBCursor<T> findByIds(Set<ObjectId> ids) {
    return coll.find(DBQuery.in("_id", ids));
  }

  public DBCursor<T> findByIds(Set<ObjectId> ids, DBQuery.Query query) {
    return coll.find(DBQuery.and(DBQuery.in("_id", ids), query));
  }

  public WriteResult<T, K> deleteById(K id) {
    return coll.removeById(id);
  }

  public WriteResult<T, K> deleteOne(DBQuery.Query query) {
    return coll.remove(query);
  }

  public WriteResult deleteMany(DBQuery.Query query) {
    return coll.remove(query);
  }

  public void updateById(K id, Map<String, ?> fields) {
    checkArgument(!fields.isEmpty());
    for (String field : fields.keySet()) {
      checkArgument(!StringUtils.isBlank(field));
    }

    DBUpdate.Builder update = new DBUpdate.Builder();
    fields.forEach(update::set);
    coll.updateById(id, update);
  }
}
