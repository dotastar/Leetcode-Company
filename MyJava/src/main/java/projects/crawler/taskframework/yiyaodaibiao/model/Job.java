package projects.crawler.taskframework.yiyaodaibiao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DB;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongojack.Id;
import projects.crawler.data.model.Model;
import projects.crawler.taskframework.yiyaodaibiao.YiyaodaibiaoModule;

import javax.inject.Inject;
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
public class Job implements Model<ObjectId> {
    @Id private ObjectId id;
    @JsonProperty private String jobTitle;
    @JsonProperty private String companyName;
    @JsonProperty private String contactName;
    @JsonProperty private List<String> phoneURL = new ArrayList<>();
    @JsonProperty private String jobAddress;
    @JsonProperty private String industry;
    @JsonProperty private String companyType;
    @JsonProperty private String companySize;
    @JsonProperty private String salaryRange;
    @JsonProperty private String district;
    @JsonProperty private String city;
    @JsonProperty private String province;

    // Meta data
    @JsonProperty private String postTitle;
    @JsonProperty private String postDate;
    @JsonProperty private String sourceURL;

    public static void main(String[] args) {
        Job.Dao dao = new YiyaodaibiaoModule().getInstance(Job.Dao.class);
        Stream<Job> stream = StreamSupport.stream(dao.find().spliterator(), true);
        Map<String, Long> urlCnt = stream.collect(groupingBy(Job::getSourceURL, counting()));
        long total = urlCnt.values().stream().mapToLong(V -> V).sum();
        System.out.println(total + "\t" + urlCnt);
        System.out.println(urlCnt);
    }

    public static class Dao extends projects.crawler.data.BaseDao<Job, ObjectId> {
        public static final String COLLECTION_NAME = "job";

        @Inject
        public Dao(DB db) {
            super(db, COLLECTION_NAME, Job.class, ObjectId.class);
        }
    }
}
