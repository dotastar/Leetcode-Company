package projects.crawler.parseq.engine;

import lombok.Data;

@Data
public class EngineConfig {
  private int availableCores = Runtime.getRuntime().availableProcessors();

  private final int engineThreads = availableCores == 0 ? 1 : availableCores;
  private final int timeoutSecond = 8;
  private final int retryAttempt = 4;
  private final int backoffTimeMs = 100;
  private final int engineShutdownTimeoutSecond = 10;
}
