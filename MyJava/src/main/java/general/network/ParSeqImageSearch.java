package general.network;

import com.google.common.base.Preconditions;
import com.linkedin.parseq.Engine;
import com.linkedin.parseq.EngineBuilder;
import com.linkedin.parseq.ParTask;
import com.linkedin.parseq.Task;
import com.linkedin.parseq.Tasks;
import com.linkedin.parseq.Tuple3Task;
import com.linkedin.parseq.httpclient.HttpClient;
import com.ning.http.client.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ParSeqImageSearch {

  static {
    final int numCores = Runtime.getRuntime().availableProcessors();
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(numCores + 1);
    final EngineBuilder builder = new EngineBuilder().setTaskExecutor(scheduler).setTimerScheduler(scheduler);
    ENGINE = builder.build();
  }

  private static final Engine ENGINE;

  public static void main(String[] args)
      throws InterruptedException {
    // Test sequential model: Task.par().andThen()
    Task<String> cageImg = firstImageUrl("nicholas cage");
    Task<String> yazhouImg = firstImageUrl("yazhou cao");
    Task<String> asiaImg = firstImageUrl("asia cao");

    final String host = "www.google.com";

    Tuple3Task<String, String, String> fanInTask = Task.par(cageImg, yazhouImg, asiaImg)
        .andThen((a, b, c) -> System.out.println(host + a + "\n" + host + b + "\n" + host + c));
    ENGINE.run(fanInTask);

    // Blocking until promise resolved
    fanInTask.await();

    // Test search()
    String[] queries = {"tom hardy", "steve jobs", "super mario"};
    ParTask<String> imageList = search(queries);
    imageList.await();
    System.out.println("Search results:\n" + imageList.getSuccessful());
  }

  public static ParTask<String> search(String[] queries) {
    List<Task<String>> queryTasks = new ArrayList<>();
    for (String query : queries) {
      queryTasks.add(firstImageUrl(query));
    }
    ParTask<String> par = Tasks.par(queryTasks);
    ENGINE.run(par);
    return par;
  }

  public static Task<String> firstImageUrl(String query) {
    return searchImage(query).map("get image link", ParSeqImageSearch::parseFirstImageUrl);
  }

  private static String parseFirstImageUrl(String html) {
    Document page = Jsoup.parse(html);
    Element imgDiv = page.getElementById("isr_mc");
    Preconditions.checkNotNull(imgDiv);
    Elements imgLinks = imgDiv.getElementsByTag("a");
    if (imgLinks.size() == 0) {
      System.out.println("No image results!");
      return null;
    }
    Element link = imgLinks.get(0);
    return link.attr("href");
  }

  public static Task<String> searchImage(String query) {
    Preconditions.checkNotNull(query);
    try {
      String url =
          "https://www.google.com/search?hl=en&site=imghp&tbm=isch&source=hp&q=" + URLEncoder.encode(query, "UTF-8");
      return fetchBody(url);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public static Task<String> fetchBody(String url) {
    return HttpClient.get(url)
        .addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
        .task()
        .map("getBody", Response::getResponseBody);
  }
}
