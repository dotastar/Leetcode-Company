package projects.crawler.subproject.yiyaodaibiao.model;

import lombok.extern.slf4j.Slf4j;
import projects.crawler.data.model.City;
import projects.crawler.subproject.yiyaodaibiao.crawl.WuBaCrawler;
import projects.crawler.subproject.yiyaodaibiao.YiyaodaibiaoModule;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

@Slf4j
public class Exporter {

    public static void main(String[] args) throws IOException {
        exportAllCities("全国医药代表");
    }

    public static void exportAllCities(String jobTitle) throws IOException {
        String rootDir = "./";
        String jobTitleDir = rootDir + jobTitle + "/";
        Files.createDirectories(Paths.get(jobTitleDir));
        WuBaCrawler crawler = new WuBaCrawler();
        Map<String, Optional<City>> cityMap = crawler.crawlAllCities().stream().collect(groupingBy(City::getName, reducing((a, b) -> ((a != null) ? a : b))));
        cityMap.forEach((K, V) -> {
            try {
                Path path = Paths.get(jobTitleDir + V.get().getProvinceName());
                if (Files.notExists(path)) {
                    Files.createDirectories(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Job.Dao jobDao = new YiyaodaibiaoModule().getInstance(Job.Dao.class);
        // Aggregate jobs by city
        Map<String, List<Job>> jobsByCity = StreamSupport.stream(jobDao.find().spliterator(), true).collect(groupingBy(Job::getCity, toList()));
        // Export jobsByCity to HTML files, and create a map of city to path
        Map<City, Path> filesByCity = new HashMap<>();
        jobsByCity.forEach((K, V) -> filesByCity.put(cityMap.get(K).get(), exportByCity(cityMap.get(K).get(), jobTitle, jobTitleDir, Job.class, V)));
        // Aggregate HTML files by province
        Map<String, List<Map.Entry<City, Path>>> filesByProvince = filesByCity.entrySet().stream().collect(groupingBy(entry -> entry.getKey().getProvinceName(), toList()));
        // Generate index page
        Path indexPage = Paths.get(rootDir + "index.html");
        HTML.generateIndexPage(indexPage, jobTitle, filesByProvince);
    }

    public static Path exportByCity(City city, String jobTitle, String rootDir, Class<?> clazz, Iterable<Job> iterable) {
        HTML<Job> exporter = new HTML<>();
        String htmlTitle = city.getName() + "|" + jobTitle;
        String filePath = rootDir + city.getProvinceName() + "/" + city.getName() + "-" + jobTitle + ".html";
        try {
            return exporter.toHTML(htmlTitle, filePath, clazz, iterable);
        } catch (IOException e) {
            log.error("Export {} in city {} failed: {}", jobTitle, city.getName(), e);
        }
        return null;
    }

    public static class HTML<T> {
        private static final String HTML_TEMPLATE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"> <html> "
                + "<head> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\"> "
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> "
                + "<meta http-equiv=\"Content-Style-Type\" content=\"text/css\"> <title>%s</title> </head> "
                + "<body> <div class=\"container-fluid table-responsive\">  "
                + "<table class=\"table table-striped table-hover table-condensed table-responsive\"> <thead> <tr> %s  </tr> </thead> <tbody>  %s </tbody> </table> "
                + "</div> </body> </html>";

        private static final String RAW_TABLE_TEMPLATE = "<table> <tbody> %s </tbody> </table>";
        //        private static final String TABLE_TEMPLATE = "<table class=\"table table-striped table-hover table-condensed table-responsive\"> <tbody> %s </tbody> </table>";
        private static final String TABLE_ROW_TEMPLATE = "<tr> %s </tr>";
        private static final String TABLE_CELL_TEMPLATE = "<td> %s </td>";
        private static final String IMAGE_TEMPLATE = "<img src=\"%s\" />";
        private static final String LINK_TEMPLATE = "<a href=\"%s\"> %s </a>";

        public static void generateIndexPage(Path indexFilePath, String jobTitle, Map<String, List<Map.Entry<City, Path>>> filesByProvince) throws IOException {
            // Generate the table has the format: province | city1 | city2 | ...
            StringBuilder cityRows = new StringBuilder();
            StringBuilder currentRow = new StringBuilder();
            filesByProvince.entrySet()
                    .stream()
//                    .sorted((a, b) -> a.getValue().get(0).getKey().getAbbreviatedName().compareTo(b.getValue().get(0).getKey().getAbbreviatedName()))
                    .sorted((a, b) -> Integer.min(a.getValue().size(), b.getValue().size()))
                    .forEach(entry -> {
                        String provinceName = entry.getKey();
                        List<Map.Entry<City, Path>> cities = entry.getValue();
                        currentRow.append(toTableCell("<strong>" + provinceName + "</strong>"));
                        cities.sort(Map.Entry.comparingByKey((c1, c2) -> c1.getName().compareTo(c2.getName())));
                        cities.forEach(cityPath -> currentRow.append(toLinkTableCell(cityPath.getKey().getName(), cityPath.getValue().toString())));
                        cityRows.append(toTableRow(currentRow.toString()));
                        currentRow.setLength(0);
                    });

            String tableHead = toTableCell("<h3> " + jobTitle + " </h3>");
            String html = String.format(HTML_TEMPLATE, "index | " + jobTitle, tableHead, cityRows);
            Files.write(indexFilePath, html.getBytes("UTF-8"), CREATE, WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        }

        public Path toHTML(String htmlTitle, String filePath, Class<?> clazz, Iterable<T> iterable) throws IOException {
            // compose table head
            Field[] fields = clazz.getDeclaredFields();
            StringBuilder tableHead = new StringBuilder();
            Arrays.stream(fields)
                    .filter(f -> !Modifier.isStatic(f.getModifiers()) && !f.getName().equals("id"))
                    .forEach(f -> tableHead.append("<th>").append(f.getName()).append("</th>"));
            // compose table body
            StringBuilder tableBody = new StringBuilder();
            iterable.forEach(t -> tableBody.append(toTableRow(fields, t)));
            String html = String.format(HTML_TEMPLATE, htmlTitle, tableHead, tableBody);
            // System.out.println(html);
            return Files.write(Paths.get(filePath), html.getBytes("UTF-8"), CREATE, WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        }

      @SuppressWarnings("unchecked")
        private String toTableRow(Field[] fields, T obj) {
            StringBuilder row = new StringBuilder("<tr>");
            for (Field f : fields) {
                if (Modifier.isStatic(f.getModifiers()) || f.getName().equals("id")) {
                    continue;
                }
                row.append("<td>");
                try {
                    if (f.getName().toLowerCase().equals("phoneurl")) {
                        if (f.get(obj) == null) {
                            row.append("未填写");
                        } else {
                            List<String> phoneUrls = (List<String>) f.get(obj);
                            StringBuilder phoneRows = new StringBuilder();
                            phoneUrls.forEach(phoneUrl -> phoneRows.append(toPhoneUrlTableRow(phoneUrl)));
                            row.append(String.format(RAW_TABLE_TEMPLATE, phoneRows));
                        }
                    } else if (f.getName().toLowerCase().contains("url")) {
                        row.append(String.format(LINK_TEMPLATE, f.get(obj), f.getName()));
                    } else {
                        row.append(f.get(obj) == null ? "无" : f.get(obj));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                row.append("</td>");
            }
            row.append("</tr>");
            return row.toString();
        }

        private static String toTableCell(String cellContent) {
            return String.format(TABLE_CELL_TEMPLATE, cellContent);
        }

        private static String toLinkTableCell(String linkText, String linkUrl) {
            return toTableCell(String.format(LINK_TEMPLATE, linkUrl, linkText));
        }

        private static String toImageTableCell(String imgUrl) {
            return toTableCell(String.format(IMAGE_TEMPLATE, imgUrl));
        }

        private static String toPhoneUrlTableRow(String phoneUrl) {
            return toTableRow(toImageTableCell(phoneUrl));
        }

        private static String toTableRow(String tdContend) {
            return String.format(TABLE_ROW_TEMPLATE, tdContend);
        }

        public static void test1() {
            HTML<Job> exporter = new HTML<>();
            Job job = Job.builder().city("LA").companyName("connectifier")
                    .postTitle("hiring").jobTitle("医药代表").build();
            System.out.println(exporter.toTableRow(Job.class.getDeclaredFields(), job));
            assertEquals(true, true);
        }
    }

}
