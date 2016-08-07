package projects.crawler.data;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

public class BaseDao<T> {
    private static final String DB_NAME = "yiyaodaibiao";
    protected static MongoClient client = new MongoClient("localhost", 27017);
    protected static MongoDatabase conn = client.getDatabase(DB_NAME);

    private MongoCollection<T> coll;

    public BaseDao(String collectionName, Class<T> clazz, Codec<T> codec) {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(codec), MongoClient.getDefaultCodecRegistry());
        coll = conn.getCollection(collectionName, clazz).withCodecRegistry(codecRegistry);
    }

    public BaseDao(String collectionName, Class<T> clazz, CodecRegistry codecRegistry) {
        codecRegistry = CodecRegistries.fromRegistries(codecRegistry, MongoClient.getDefaultCodecRegistry());
        coll = conn.getCollection(collectionName, clazz).withCodecRegistry(codecRegistry);
    }

    public void insert(T doc) {
        coll.insertOne(doc);
    }

    public void insertMany(List<T> docs) {
        coll.insertMany(docs);
    }

    public long count() {
        return coll.count();
    }

    public T findOne() {
        return coll.find().iterator().next();
    }

    public FindIterable<T> find() {
        return coll.find();
    }

    public FindIterable<T> find(Bson filter) {
        return coll.find(filter);
    }

    public FindIterable<T> findByIds(Set<ObjectId> ids) {
        return coll.find(Filters.in("_id", ids));
    }

    public FindIterable<T> findByIds(Set<ObjectId> ids, Bson filter) {
        return coll.find(Filters.and(Filters.in("_id", ids), filter));
    }

    public DeleteResult deleteOne(Bson filter) {
        return coll.deleteOne(filter);
    }

    public DeleteResult deleteMany(Bson filter) {
        return coll.deleteMany(filter);
    }

    public UpdateResult updateOne(Bson filter, Bson update) {
        return coll.updateOne(filter, update);
    }
}
