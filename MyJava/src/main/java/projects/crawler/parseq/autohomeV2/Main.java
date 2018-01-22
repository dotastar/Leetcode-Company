package projects.crawler.parseq.autohomeV2;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import projects.crawler.data.model.City;
import projects.crawler.parseq.autohomeV2.crawl.CityReader;
import projects.crawler.parseq.autohomeV2.crawl.CrawlByCity;

@Slf4j
public class Main {

  public static void main(String[] args) throws IOException {
    AutohomeV2Module autohomeV2Module = new AutohomeV2Module();
    CrawlByCity crawlByCity = autohomeV2Module.getInstance(CrawlByCity.class);
    List<City> cityList = new CityReader().getAllCity();
    Collections.reverse(cityList);
    crawlByCity.crawlByCity(cityList);
  }
}
