package projects.crawler.subproject.autohome.crawl;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import projects.crawler.data.model.City;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toCollection;

/**
 * CityCrawler
 * <p>
 * Created by yazhoucao on 8/12/16.
 */
@Slf4j
public class CityCrawler {

  public static final String CITY_URL = "http://dealer.autohome.com.cn/dealerlist/Dealer/GetCityPanel";

  //  private static final String PROVINCE = ".citypop-scity dt";
  private static final String CITY_IN_A_PROVINCE = ".citypop-scity dd";

  public List<City> convert(Map<String, Set<City>> cityByProvince) {
    Preconditions.checkNotNull(cityByProvince);
    return cityByProvince.entrySet()
        .stream()
        .map(Map.Entry::getValue)
        .flatMap(Collection::stream)
        .collect(toCollection(ArrayList::new));
  }

  public Map<String, Set<City>> extractCities(Document page) {
    Map<String, Set<City>> cityByProvince = new HashMap<>();
    Elements citiesInProvinces = page.body().select(CITY_IN_A_PROVINCE);
    for (Element citiesDiv : citiesInProvinces) {
      String province = getProvinceName(citiesDiv);
      Elements citiesElem = citiesDiv.select("a");
      if (citiesElem.isEmpty()) {
        log.info("Ignore province {} since there is no cities available...", province);
        continue; // No cities available
      }

      // Extract cities
      Set<City> cities = citiesElem.select("a")
          .stream()
          .map(elem -> new City(province, checkNotNull(elem.text()), checkNotNull(elem.absUrl("href"))))
          .collect(Collectors.toSet());
      cityByProvince.put(province, cities);
    }
    return cityByProvince;
  }

  private String getProvinceName(Element citiesDiv) {
    Element provinceDiv = citiesDiv.previousElementSibling();
    Preconditions.checkState(provinceDiv.tag().getName().equals("dt"), "provinceDiv:" + provinceDiv.tagName());
    Element provinceLink = Iterables.getOnlyElement(provinceDiv.select("a"), null);
    String province = provinceLink != null ? provinceLink.text() : provinceDiv.text();
    return checkNotNull(province);
  }

  public static void main(String[] args) throws IOException {
    CityCrawler parser = new CityCrawler();
    Document doc = Jsoup.connect(CITY_URL).timeout(10_000).userAgent(AutohomeParser.USER_AGENT).get();
    Map<String, Set<City>> cities = parser.extractCities(doc);
    // Print full city info
    cities.forEach((k, v) -> System.out.println(k + ":\t" + v));
    // Print city name only
    cities.forEach((k, v) -> System.out.println(k + ":\t" + Joiner.on(',').join(v.stream().map(City::getName).collect(Collectors.toList()))));
  }
}
