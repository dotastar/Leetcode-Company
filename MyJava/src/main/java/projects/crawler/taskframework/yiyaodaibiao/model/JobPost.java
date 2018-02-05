package projects.crawler.taskframework.yiyaodaibiao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DB;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.mongojack.DBQuery;
import org.mongojack.Id;
import projects.crawler.data.model.Model;
import projects.crawler.taskframework.yiyaodaibiao.YiyaodaibiaoModule;

@Data
@Builder
@AllArgsConstructor
public class JobPost implements Model<ObjectId> {
  @Id private ObjectId _id;
  @JsonProperty private ObjectId id; // redundant
  @JsonProperty private String title;
  @JsonProperty private String companyName;
  @JsonProperty private String district;
  @JsonProperty private String postDate;
  @JsonProperty private String url;
  @JsonProperty private String parentUrl;
  @JsonProperty private String city;
  @JsonProperty private String province;

  public JobPost(ObjectId id, String title, String companyName, String district, String postDate, String url, String parentUrl, String city, String province) {
    this(id, id, title, companyName, district, postDate, url, parentUrl, city, province);
  }

  public static class Dao extends projects.crawler.data.BaseDao<JobPost, ObjectId> {
    static final String COLLECTION_NAME = "jobPost";

    @Inject
    public Dao(DB db) {
      super(db.getCollection(COLLECTION_NAME), JobPost.class, ObjectId.class);
    }
  }

  public static void main(String[] args) {
    JobPost.Dao dao = new YiyaodaibiaoModule().getInstance(JobPost.Dao.class);
    JobPost post = JobPost.builder().title("医药代表高薪招聘").district("白云").companyName("广州天河制药").build();
    dao.insert(post);
    JobPost saved = dao.findOne();
    System.out.println(post);
    Assert.assertNotNull(saved);
    dao.deleteOne(DBQuery.is("companyName", "广州天河制药"));
    Assert.assertFalse(dao.find(DBQuery.is("jobTitle", "医药代表高薪招聘")).iterator().hasNext());
  }
}
