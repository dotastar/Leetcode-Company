package general.webcrawler.yiyaodaibiao.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by yazhoucao on 10/25/15.
 */
@Data
@NoArgsConstructor
public class FailedRecord implements Bson {
    protected ObjectId id;
    protected String pageUrl;

    public FailedRecord(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public static class Dao extends BaseDao<FailedRecord> {
        private static final String COLLECTION_NAME = "failedRecord";

        public Dao() {
            super(COLLECTION_NAME, FailedRecord.class, new FailedRecordCodec());
        }
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<>(this, codecRegistry.get(FailedRecord.class));
    }

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
