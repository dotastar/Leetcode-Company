package projects.crawler.subproject.yiyaodaibiao.model;

import projects.crawler.data.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import projects.crawler.data.MongoConn;
import projects.crawler.subproject.yiyaodaibiao.YiyaodaibiaoModule;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job implements Bson, Model<ObjectId> {
    protected ObjectId _id;
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
        Job.Dao dao = new YiyaodaibiaoModule().getInstance(Job.Dao.class);
        Stream<Job> stream = StreamSupport.stream(dao.find().spliterator(), true);
        Map<String, Long> urlCnt = stream.collect(groupingBy(Job::getSourceURL, counting()));
        long total = urlCnt.values().stream().mapToLong(V -> V.longValue()).sum();
        System.out.println(total + "\t" + urlCnt);
        System.out.println(urlCnt);
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<>(this, codecRegistry.get(Job.class));
    }

    @Override
    public ObjectId getId() {
        return _id;
    }

    @Override
    public void setId(ObjectId key) {
        this._id = key;
    }

    public static class Dao extends projects.crawler.data.BaseDao<Job> {
        public static final String COLLECTION_NAME = "job";

        @Inject
        public Dao(MongoConn conn) {
            super(COLLECTION_NAME, conn, Job.class, new JobCodec());
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
