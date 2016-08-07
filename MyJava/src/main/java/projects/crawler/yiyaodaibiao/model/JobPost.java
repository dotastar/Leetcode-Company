package projects.crawler.yiyaodaibiao.model;

import com.mongodb.client.model.Filters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Assert;
import projects.crawler.data.Model;
import projects.crawler.data.MongoConn;
import projects.crawler.yiyaodaibiao.YiyaodaibiaoModule;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Data
@Builder
@AllArgsConstructor
public class JobPost implements Bson, Model<ObjectId> {
    protected ObjectId _id;
    protected String title;
    protected String companyName;
    protected String district;
    protected String postDate;
    protected String url;
    protected String parentUrl;
    protected String city;
    protected String province;

    public JobPost() {
        this._id = new ObjectId();
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<>(this, codecRegistry.get(JobPost.class));
    }

    @Override
    public ObjectId getId() {
        return _id;
    }

    @Override
    public void setId(ObjectId key) {
        this._id = key;
    }

    public static class Dao extends projects.crawler.data.BaseDao<JobPost> {
        public static final String COLLECTION_NAME = "jobPost";

        @Inject
        public Dao(MongoConn conn) {
            super(COLLECTION_NAME, conn, JobPost.class, new JobPostCodec());
        }
    }

    public static class JobPostCodec implements CollectibleCodec<JobPost> {

        private Codec<Document> documentCodec;

        public JobPostCodec() {
            this.documentCodec = new DocumentCodec();
        }

        @Override
        public void encode(BsonWriter writer, JobPost value, EncoderContext encoderContext) {
            Document document = new Document();
            if (value.getId() == null) {
                value.setId(new ObjectId());
            }
            Field[] fields = JobPost.class.getDeclaredFields();
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
        public Class<JobPost> getEncoderClass() {
            return JobPost.class;
        }

        @Override
        public JobPost decode(BsonReader reader, DecoderContext decoderContext) {
            Document document = documentCodec.decode(reader, decoderContext);
            JobPost post = new JobPost();
            post.setId(document.getObjectId("_id"));
            Field[] fields = JobPost.class.getDeclaredFields();
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
        public JobPost generateIdIfAbsentFromDocument(JobPost document) {
            if (!documentHasId(document)) {
                document.setId(new ObjectId());
            }
            return document;
        }

        @Override
        public boolean documentHasId(JobPost document) {
            return document.getId() != null;
        }

        @Override
        public BsonValue getDocumentId(JobPost document) {
            return new BsonString(document.getId().toHexString());
        }

    }

    public static void main(String[] args) {
        JobPost.Dao dao = new YiyaodaibiaoModule().getInstance(JobPost.Dao.class);
        JobPost post = JobPost.builder().title("医药代表高薪招聘").district("白云").companyName("广州天河制药").build();
        dao.insert(post);
        JobPost saved = dao.findOne();
        System.out.println(post);
        Assert.assertNotNull(saved);
        dao.deleteOne(Filters.eq("companyName", "广州天河制药"));
        Assert.assertFalse(dao.find(Filters.eq("jobTitle", "医药代表高薪招聘")).iterator().hasNext());
    }
}
