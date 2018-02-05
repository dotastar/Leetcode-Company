package projects.crawler.parseq.autohomeV2;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Main {
  private static AutohomeV2Module autohomeV2Module = new AutohomeV2Module();

  public static void main(String[] args) throws IOException {
    JobCenter jobCenter = autohomeV2Module.getInstance(JobCenter.class);
    jobCenter.parseAllRawPageTask();
  }
}
