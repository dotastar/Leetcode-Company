package projects.crawler.parseq.autohomeV2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.mongodb.DB;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.mongojack.Id;
import projects.crawler.data.model.Model;
import projects.crawler.taskframework.autohome.AutohomeModule;


@Data
@AllArgsConstructor
public class DealerPost implements Model<ObjectId> {
  @Id protected ObjectId id;
  @JsonProperty protected String title;
  @JsonProperty protected String brand;
  @JsonProperty protected String dealerType;
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
    static final String COLLECTION_NAME = "dealerPost";

    @Inject
    public Dao(DB db) {
      super(db.getCollection(COLLECTION_NAME), DealerPost.class, ObjectId.class);
    }
  }
}
