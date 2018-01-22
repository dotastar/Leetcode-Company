package projects.crawler.network;

import com.linkedin.parseq.Task;
import com.linkedin.parseq.httpclient.HttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import projects.crawler.utils.RecoveryHelper;


@Slf4j
@Singleton
public class SimpleHttpClient {
  public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
  private static final int TIMEOUT_SECOND = 5;
  private static final int MAX_ATTEMPTS = 3;
  private static final int BACKOFF_TIME_MS = 1000;
  // AsyncHttpClientConfig
//  private static final int MAX_IO_EXCEPTION_RETRY = 2;

  private static AsyncHttpClientConfig getConfig() {
    AsyncHttpClientConfig.Builder configBuilder = new AsyncHttpClientConfig.Builder();
    configBuilder.setUserAgent(USER_AGENT);
//    configBuilder.setMaxRequestRetry(MAX_IO_EXCEPTION_RETRY);
//    configBuilder.setConnectTimeout();
//    configBuilder.setReadTimeout();
//    configBuilder.setRequestTimeout();
//    configBuilder.setMaxConnections();
    return configBuilder.build();
  }

  static {
    HttpClient.initialize(getConfig());
  }

  public static void close() {
    HttpClient.close();
  }

  public static Optional<Document> get(String url) {
    Document page = null;
    int attempts = 0;
    while (page == null && ++attempts <= MAX_ATTEMPTS) {
      try {
        page = Jsoup.connect(url).userAgent(USER_AGENT).timeout(TIMEOUT_SECOND * 1000).get();
      } catch (Throwable e) {
        log.error("Failed to get url {}", url, e);
        try {
          Thread.sleep(BACKOFF_TIME_MS);
        } catch (InterruptedException e1) {
          log.error("", e1);
        }
      }
    }
    return Optional.ofNullable(page);
  }

  public static Task<Optional<Document>> asyncGet(String url) {
    Task<Response> requestTask = HttpClient.get(url).task("Get " + url);
    return requestTask
        .map("getResponseBody", Response::getResponseBody)
        .map("Jsoup::parse", Jsoup::parse)
        .map("Optional::of", Optional::of)
        .recover(t -> RecoveryHelper.errorLoggingOptional(log, "Failed to asyncGet " + url, t))
        .withTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS);
  }
}
