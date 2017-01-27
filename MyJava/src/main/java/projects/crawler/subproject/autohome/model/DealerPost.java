package projects.crawler.subproject.autohome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.mongodb.DB;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.mongojack.Id;
import projects.crawler.data.model.Model;
import projects.crawler.subproject.autohome.AutohomeModule;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class DealerPost implements Model<ObjectId> {
  @Id protected ObjectId id;
  @JsonProperty protected String title;
  @JsonProperty protected String brand;
  @JsonProperty protected String address;
  @JsonProperty protected String promotion;
  @JsonProperty protected String phone;
  @JsonProperty protected String city;
  @JsonProperty protected String province;

  @JsonProperty protected Date crawlDate;
  @JsonProperty protected String linkToUrl;
  @JsonProperty protected String whereAtUrl;

  @JsonProperty protected List<BrandDetail> brandDetailList = new ArrayList<>();

  public DealerPost() {
    this.id = new ObjectId();
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  @Override
  public void setId(ObjectId key) {
    this.id = key;
  }

  public static class Dao extends projects.crawler.data.BaseDao<DealerPost, ObjectId> {
    public static final String COLLECTION_NAME = "dealerPost";

    @Inject
    public Dao(DB db) {
      super(db.getCollection(COLLECTION_NAME), DealerPost.class, ObjectId.class);
    }
  }

  public static void main(String[] args) {
    DealerPost.Dao dao = new AutohomeModule().getInstance(DealerPost.Dao.class);
    DealerPost post = new DealerPost();
    post.setTitle("北京广本日银");
    post.setBrand("大众");
    post.getBrandDetailList().add(new BrandDetail("一汽大众", ImmutableList.of("宝来", "高尔夫", "高尔夫·嘉旅", "捷达", "迈腾", "速腾")));
    System.out.println("Inserting " + post);
    dao.insert(post);
    DealerPost saved = dao.findOne();
    System.out.println(post);
    Assert.assertNotNull(saved);
    dao.deleteById(post.getId());
    Assert.assertTrue(dao.findById(post.getId()) == null);
  }
}
