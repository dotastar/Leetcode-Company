package projects.crawler.yiyaodaibiao.model;

import com.mongodb.client.result.DeleteResult;
import lombok.*;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import projects.crawler.data.Model;
import projects.crawler.data.MongoConn;
import projects.crawler.yiyaodaibiao.YiyaodaibiaoModule;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


/**
 * Created by yazhoucao on 10/25/15.
 */
@Data
@NoArgsConstructor
public class FailedRecord<K> implements Bson, Model<ObjectId> {

  public static void main(String[] args) {
    FailedRecord<ObjectId> failedRecord = new FailedRecord<>(new ObjectId(), "test");
    FailedRecord.Dao dao = new YiyaodaibiaoModule().getInstance(FailedRecord.Dao.class);
    @SuppressWarnings("unchecked")
    Set<ObjectId> failedIds = dao.getFailedRecordIds(JobPost.class.getSimpleName());
    System.out.println(failedIds);
  }

  @Getter(AccessLevel.PRIVATE)
  @Setter(AccessLevel.PRIVATE)
  protected ObjectId _id;
  protected K recordId;
  protected String collectionName;

  public FailedRecord(K recordId, String recordType) {
    this.collectionName = recordType;
    this.recordId = recordId;
  }

  @Override
  public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
    return new BsonDocumentWrapper<>(this, codecRegistry.get(FailedRecord.class));
  }

  @Override
  public ObjectId getId() {
    return _id;
  }

  @Override
  public void setId(ObjectId key) {
    this._id = key;
  }

  /**
   * Dao
   */
  public static class Dao extends projects.crawler.data.BaseDao<FailedRecord> {

    public static final String COLLECTION_NAME = "failedJobPost";

    @Inject
    public Dao(MongoConn conn) {
      super(COLLECTION_NAME, conn, FailedRecord.class, new FailedRecordCodec());
    }

    public Set getFailedRecordIds(String collecName) {
      return StreamSupport.stream(find(eq("collectionName", collecName)).spliterator(), true)
          .map(FailedRecord::getRecordId)
          .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public boolean deleteRecord(Object recordId, String collecName) {
      DeleteResult result = deleteMany(and(eq("recordId", recordId), eq("collectionName", collecName)));
      return result.getDeletedCount() > 0;
    }
  }

  /**
   * Codec
   */
  public static class FailedRecordCodec implements CollectibleCodec<FailedRecord> {
    private Codec<Document> documentCodec;

    public FailedRecordCodec() {
      this.documentCodec = new DocumentCodec();
    }

    @Override
    public FailedRecord generateIdIfAbsentFromDocument(FailedRecord document) {
      if (!documentHasId(document)) {
        document.setId(new ObjectId());
      }
      return document;
    }

    @Override
    public boolean documentHasId(FailedRecord document) {
      return document.getId() != null;
    }

    @Override
    public BsonValue getDocumentId(FailedRecord document) {
      return new BsonString(document.getId().toHexString());
    }

    @Override
    public FailedRecord decode(BsonReader reader, DecoderContext decoderContext) {
      Document document = documentCodec.decode(reader, decoderContext);
      FailedRecord failedRecord = new FailedRecord();
      failedRecord.setId(document.getObjectId("_id"));
      Field[] fields = FailedRecord.class.getDeclaredFields();
      for (Field f : fields) {
        try {
          if (!Modifier.isStatic(f.getModifiers()) && document.get(f.getName()) != null) {
            f.set(failedRecord, document.get(f.getName()));
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return failedRecord;
    }

    @Override
    public void encode(BsonWriter writer, FailedRecord value, EncoderContext encoderContext) {
      Document document = new Document();
      if (value.getId() == null) {
        value.setId(new ObjectId());
      }
      Field[] fields = FailedRecord.class.getDeclaredFields();
      for (Field f : fields) {
        try {
          if (!Modifier.isStatic(f.getModifiers()) && f.get(value) != null) {
            document.put(f.getName(), f.get(value));
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      documentCodec.encode(writer, document, encoderContext);
    }

    @Override
    public Class<FailedRecord> getEncoderClass() {
      return FailedRecord.class;
    }
  }
}
