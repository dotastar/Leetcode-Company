package projects.crawler.subproject.autohome.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.*;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Assert;
import projects.crawler.subproject.autohome.AutohomeModule;
import projects.crawler.data.Model;
import projects.crawler.data.MongoConn;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class DealerPost implements Bson, Model<ObjectId> {
    protected ObjectId _id;
    protected String title;
    protected String brand;
    protected String address;
    protected String promotion;
    protected String phone;
    protected String city;
    protected String province;

    protected Date crawlDate;
    protected String linkToUrl;
    protected String whereAtUrl;

    public DealerPost() {
        this._id = new ObjectId();
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<>(this, codecRegistry.get(DealerPost.class));
    }

    @Override
    public ObjectId getId() {
        return _id;
    }

    @Override
    public void setId(ObjectId key) {
        this._id = key;
    }

    public static class Dao extends projects.crawler.data.BaseDao<DealerPost> {
        public static final String COLLECTION_NAME = "dealerPost";
        @Inject
        public Dao(MongoConn conn) {
            super(COLLECTION_NAME, conn, DealerPost.class, new Codec());
        }
    }

    // TODO: replace with MongoJack
    public static class Codec implements CollectibleCodec<DealerPost> {

        private org.bson.codecs.Codec<Document> documentCodec;

        public Codec() {
            this.documentCodec = new DocumentCodec();
        }

        @Override
        public void encode(BsonWriter writer, DealerPost value, EncoderContext encoderContext) {
            Document document = new Document();
            if (value.getId() == null) {
                value.setId(new ObjectId());
            }
            Field[] fields = DealerPost.class.getDeclaredFields();
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
        public Class<DealerPost> getEncoderClass() {
            return DealerPost.class;
        }

        @Override
        public DealerPost decode(BsonReader reader, DecoderContext decoderContext) {
            Document document = documentCodec.decode(reader, decoderContext);
            DealerPost post = new DealerPost();
            post.setId(document.getObjectId("_id"));
            Field[] fields = DealerPost.class.getDeclaredFields();
            for (Field f : fields) {
                try {
                    if (!Modifier.isStatic(f.getModifiers()) && document.get(f.getName()) != null) {
                        f.set(post, document.get(f.getName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return post;
        }

        @Override
        public DealerPost generateIdIfAbsentFromDocument(DealerPost document) {
            if (!documentHasId(document)) {
                document.setId(new ObjectId());
            }
            return document;
        }

        @Override
        public boolean documentHasId(DealerPost document) {
            return document.getId() != null;
        }

        @Override
        public BsonValue getDocumentId(DealerPost document) {
            return new BsonString(document.getId().toHexString());
        }

    }

    public static void main(String[] args) {
        DealerPost.Dao dao = new AutohomeModule().getInstance(DealerPost.Dao.class);
        DealerPost post = DealerPost.builder().title("医药代表高薪招聘").build();
        dao.insert(post);
        DealerPost saved = dao.findOne();
        System.out.println(post);
        Assert.assertNotNull(saved);
        dao.deleteById(post.getId());
        Assert.assertFalse(dao.findById(post.getId()).iterator().hasNext());
    }
}
