package projects.crawler.parseq.autohomeV2.crawl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Verify;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import projects.crawler.data.model.City;


public class CityReader {

  private static final String CITY_FILE_NAME = "cities.json";
  private static final String BASE_URL = "https://dealer.autohome.com.cn/";

  public List<City> getAllCity() throws IOException {
    CityFileMapping cityFileMapping = deserilizeCityFile();
    List<AreaInfoGroups> areaInfoGroups = cityFileMapping.getAreaInfoGroups();

    List<City> cities = new ArrayList<>();
    areaInfoGroups.stream().map(CityReader::toCities).forEach(cities::addAll);
    return cities;
  }

  private static CityFileMapping deserilizeCityFile() throws IOException {
    URL citiesFile = Verify.verifyNotNull(CityReader.class.getClassLoader().getResource(CITY_FILE_NAME));
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(citiesFile, CityFileMapping.class);
//    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cityFileMapping));
  }

  private static List<City> toCities(AreaInfoGroups areaInfoGroups) {
    List<City> cities = new ArrayList<>();
    List<ProvinceJson> provinceJsons = areaInfoGroups.getValues();
    for (ProvinceJson provinceJson : provinceJsons) {
      String provinceName = provinceJson.getName();
      provinceJson.Cities.stream().map(c -> toCity(c, provinceName)).forEach(cities::add);
    }
    return cities;
  }

  private static City toCity(CityJson cityJson, String provinceName) {
    String name = cityJson.Name;
    String url = BASE_URL + cityJson.Pinyin;
    return new City(name, provinceName, url);
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    CityReader cityJsonParser = new CityReader();
    System.out.println(cityJsonParser.getAllCity());
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class CityFileMapping {
    public List<CityJson> HotCites;
    public List<AreaInfoGroups> AreaInfoGroups;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class AreaInfoGroups {
    public String Key; // province name, 直辖市为""
    public List<ProvinceJson> Values; // province
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class ProvinceJson {
    public String Id;
    public String Name;
    public String Pinyin;
    public int Count;
    public String FirstChar;
    public List<CityJson> Cities;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class CityJson {
    public String Id;
    public String Name;
    public String Pinyin;
    public int Count;
  }
}
