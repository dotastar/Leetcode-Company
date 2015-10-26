package general.webcrawler.yiyaodaibiao.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.function.Consumer;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertEquals;

public class Exporter {

    public static void main(String[] args) {
        HTML<Job> exporter = new HTML<Job>();
        String filePath = "/Users/yazhoucao/Downloads/jobList.html";
        try {
            exporter.toHTML("Job List", filePath, Job.class, new Job.Dao());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class HTML<T> {
        private static final String HTML_TEMPLATE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"> <html> "
                + "<head> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\"> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <meta http-equiv=\"Content-Style-Type\" content=\"text/css\"> <title>%s</title> </head> "
                + "<body> <div class=\"container-fluid table-responsive\">  "
                + "<table class=\"table table-striped\"> <thead> <tr> %s  </tr> </thead> <tbody>  %s </tbody> </table> "
                + "</div> </body> </html>";
        private static final String IMAGE_TEMPLATE = "<img src=\"%s\" />";
        private static final String LINK_TEMPLATE = "<a href=\"%s\"> %s </a>";

        public void toHTML(String htmlTitle, String filePath, Class<?> clazz, BaseDao<T> dao) throws UnsupportedEncodingException, IOException {
            // compose table head
            Field[] fields = clazz.getDeclaredFields();
            StringBuilder tableHead = new StringBuilder();
            Arrays.stream(fields)
                    .filter(f -> !Modifier.isStatic(f.getModifiers()) && !f.getName().equals("id"))
                    .forEach(f -> tableHead.append("<th>" + f.getName() + "</th>"));
            // compose table body
            StringBuilder tableBody = new StringBuilder();
            dao.find().forEach((Consumer<T>) t -> tableBody.append(toTableRow(fields, t)));
            String html = String.format(HTML_TEMPLATE, htmlTitle, tableHead, tableBody);
            // System.out.println(html);
            Files.write(Paths.get(filePath), html.toString().getBytes("UTF-8"), CREATE, WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        }

        private String toTableRow(Field[] fields, T obj) {
            StringBuilder row = new StringBuilder("<tr>");
            for (Field f : fields) {
                if (Modifier.isStatic(f.getModifiers()) || f.getName().equals("id")) {
                    continue;
                }
                row.append("<td>");
                try {
                    if (f.getName().toLowerCase().equals("phoneurl")) {
                        row.append(f.get(obj) == null ? "未填写" : String.format(IMAGE_TEMPLATE, f.get(obj)));
                    } else if (f.getName().toLowerCase().contains("url")) {
                        row.append(String.format(LINK_TEMPLATE, f.get(obj), f.getName()));
                    } else {
                        row.append(f.get(obj) == null ? "无" : f.get(obj));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                row.append("</td>");
            }
            row.append("</tr>");
            return row.toString();
        }

        public static void test1() {
            HTML<Job> exporter = new HTML<Job>();
            Job job = Job.builder().city("LA").companyName("connectifier")
                    .postTitle("hiring").jobTitle("医药代表").build();
            System.out.println(exporter.toTableRow(Job.class.getDeclaredFields(), job));
            assertEquals(true, true);
        }
    }

}
