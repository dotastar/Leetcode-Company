package projects.crawler.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Verify;
import com.mongodb.DB;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.jsoup.nodes.Document;
import org.mongojack.Id;
import projects.crawler.data.BaseDao;
import projects.crawler.data.db.MongoIndexCreator;

import static projects.crawler.utils.RawPageUtils.parseDoc;


/**
 * RawPage represents a raw HTML page and its meta data of a given url
 * Url is an unique key.
 * Url and RawPage have a 1:1 mapping.
 * Url and corresponding Html content have a many:1 mapping.
 */
@Data
@ToString(exclude = "parsedPage")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RawPage implements Model<ObjectId> {

  @Id private ObjectId id;
  @JsonProperty @NonNull private String fromUrl;
  @JsonProperty @NonNull private ZonedDateTime lastUpdateDate;
  @JsonProperty private String html;

  // TODO: Add domain as a field if we have crawled more than one site

  @JsonIgnore transient volatile private Document parsedPage;

  public RawPage(@NonNull String fromUrl, String html) {
    this.id = new ObjectId();
    this.lastUpdateDate = ZonedDateTime.now();
    this.fromUrl = fromUrl;
    this.html = html;
  }

  public Document getParsedPage() {
    if (parsedPage == null) {
      synchronized (this) {
        if (parsedPage == null) {
          parsedPage = parseDoc(html, fromUrl);
        }
      }
    }
    return Verify.verifyNotNull(parsedPage);
  }

  public static class Dao extends BaseDao<RawPage, ObjectId> {
    static final String COLLECTION_NAME = "rawPage";

    @Inject
    public Dao(DB db) {
      super(db.getCollection(COLLECTION_NAME), RawPage.class, ObjectId.class, INDEXER);
    }

    private static final MongoIndexCreator INDEXER = MongoIndexCreator.builder()
        .createUniqueIndex("fromUrl")
        .createSparseIndex("lastUpdateDate")
        .build();
  }
}
