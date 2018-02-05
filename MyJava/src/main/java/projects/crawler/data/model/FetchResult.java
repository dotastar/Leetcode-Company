package projects.crawler.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DB;
import java.time.ZonedDateTime;
import javax.annotation.Nullable;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.jsoup.nodes.Document;
import org.mongojack.Id;
import projects.crawler.data.BaseDao;

import static projects.crawler.data.model.FetchResult.Result.FAILED;


/**
 * FetchResult represents the result of a fetch action happened at some time
 */
@Data
@AllArgsConstructor
public class FetchResult implements Model<ObjectId> {

  public static FetchResult failed(String url) {
    return new FetchResult(url, null, FAILED);
  }

  public static FetchResult noResult(@NonNull String url, @NonNull Document page) {
    return new FetchResult(url, page, Result.NO_RESULT);
  }

  public static FetchResult success(@NonNull String url, @NonNull Document page) {
    return new FetchResult(url, page, Result.SUCCESS);
  }

  public enum Result {
    SUCCESS,    // normal successful fetch
    NO_RESULT,  // a special success, which has no results to parse, deemed as an end page
    FAILED
  }

  @Id @NonNull private ObjectId id;
  @JsonProperty @NonNull private String url;
  @JsonProperty @NonNull private Result result;
  @JsonProperty @NonNull private ZonedDateTime fetchedDate;

  @JsonIgnore @Nullable private Document page;

  private FetchResult(String url, Document page, Result result) {
    this(new ObjectId(), url, result, ZonedDateTime.now(), page);
  }

  public @Nullable String getHtml() {
    return page == null ? null : page.html();
  }

  public static class Dao extends BaseDao<FetchResult, ObjectId> {
    static final String COLLECTION_NAME = "fetchResult";

    @Inject
    public Dao(DB db) {
      super(db.getCollection(COLLECTION_NAME), FetchResult.class, ObjectId.class);
    }
  }
}
