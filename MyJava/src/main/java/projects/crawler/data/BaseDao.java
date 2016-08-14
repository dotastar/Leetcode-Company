package projects.crawler.data;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import projects.crawler.data.model.Model;

import java.util.List;
import java.util.Set;

public class BaseDao<T extends Model<K>, K> {

  protected final JacksonDBCollection<T, K> coll;

  public BaseDao(DBCollection mongoColl, Class<T> clazz, Class<K> idClazz) {
    coll = JacksonDBCollection.wrap(mongoColl, clazz, idClazz);
  }

  public BaseDao(DB db, String collName, Class<T> clazz, Class<K> idClazz) {
    coll = JacksonDBCollection.wrap(db.getCollection(collName), clazz, idClazz);
  }

  public void insert(T doc) {
    coll.insert(doc);
  }

  public void insertMany(List<T> docs) {
    coll.insert(docs);
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
}
