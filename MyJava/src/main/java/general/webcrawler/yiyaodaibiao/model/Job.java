package general.webcrawler.yiyaodaibiao.model;

import com.mongodb.client.model.Filters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job implements Bson {
    protected ObjectId id;
    protected String jobTitle;
    protected String companyName;
    protected String contactName;
    protected List<String> phoneURL = new ArrayList<>();
    protected String jobAddress;
    protected String industry;
    protected String companyType;
    protected String companySize;
    protected String salaryRange;
    protected String district;
    protected String city;
    protected String province;

    // Meta data
    protected String postTitle;
    protected String postDate;
    protected String sourceURL;

    public static void main(String[] args) {
        Job.Dao dao = new Job.Dao();
        Job job = Job.builder().jobTitle("jobTitle").city("广州")
                .companyName("companyName").postDate(new Date().toString()).build();
        dao.insert(job);
        Job saved = dao.findOne();
        System.out.println(job);
        Assert.assertNotNull(saved);
        dao.deleteOne(Filters.eq("jobTitle", "广州"));
        Assert.assertFalse(dao.find(Filters.eq("jobTitle", "广州")).iterator().hasNext());
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<>(this, codecRegistry.get(Job.class));
    }

    public static class Dao extends BaseDao<Job> {
        private static final String COLLECTION_NAME = "job";

        public Dao() {
            super(COLLECTION_NAME, Job.class, new JobCodec());
        }
    }

    public static class JobCodec implements CollectibleCodec<Job> {

        private Codec<Document> documentCodec;

        public JobCodec() {
            this.documentCodec = new DocumentCodec();
        }

//        public JobCodec(Codec<Document> codec) {
//            this.documentCodec = codec;
//        }

        @Override
        public void encode(BsonWriter writer, Job value, EncoderContext encoderContext) {
            Document document = new Document();
            if (value.getId() == null) {
                value.setId(new ObjectId());
            }
            Field[] fields = Job.class.getDeclaredFields();
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
        public Class<Job> getEncoderClass() {
            return Job.class;
        }

        @Override
        public Job decode(BsonReader reader, DecoderContext decoderContext) {
            Document document = documentCodec.decode(reader, decoderContext);
            Job job = new Job();
            job.setId(document.getObjectId("_id"));
            Field[] fields = Job.class.getDeclaredFields();
            for (Field f : fields) {
                try {
                    if (!Modifier.isStatic(f.getModifiers()) && document.get(f.getName()) != null) {
                        f.set(job, document.get(f.getName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return job;
        }

        @Override
        public Job generateIdIfAbsentFromDocument(Job document) {
            if (!documentHasId(document)) {
                document.setId(new ObjectId());
            }
            return document;
        }

        @Override
        public boolean documentHasId(Job document) {
            return document.getId() != null;
        }

        @Override
        public BsonValue getDocumentId(Job document) {
            return new BsonString(document.getId().toHexString());
        }

    }

}
