package projects.autoplay;

import general.misc.ZipUtil;
import org.apache.commons.compress.archivers.ArchiveException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

/**
 * Auto search and play selected movie from specified folder and StreamCast it on TV
 * <p>
 * Need two Chrome Extensions: Google Cast and Videostream
 * <p>
 * Created by yazhoucao on 4/12/16.
 */
public class SeleniumAutoPlay {

  private static final String APP_URL = "http://getvideostream.com/launch-app";
  private static final String EXTENSION_DIR = "/Users/yazhoucao/Library/Application Support/Google/Chrome/Default/Extensions/";
  private static final String CHOOSE_VIDEO_BTN_ID = "button-select-video";

  private static final String EXTENSION_GOOGLECAST_ID = "boadgeojelhgndaghljhdicfkmllpafd";
  private static final String EXTENSION_VIDEOSTREAM_ID = "cnciopoikihiagdjbjpnocolokfelagl";

  private static final String ZIP_FILE_DIR = "/Users/yazhoucao/Downloads/Win7VM";
  private static final File EXTENSION_GOOGLECAST_ADDR = Paths.get(
      "/Users/yazhoucao/Library/Application Support/Google/Chrome/Default/Extensions/boadgeojelhgndaghljhdicfkmllpafd/15.1120.0.4_0").toFile();
  private static final File EXTENSION_VIDEOSTREAM_ADDR = Paths
      .get("/Users/yazhoucao/Library/Application Support/Google/Chrome/Default/Extensions/cnciopoikihiagdjbjpnocolokfelagl/2.16.330.0_0").toFile();

  private static final String EXTENSION_GOOGLECAST_ZIP = "/Users/yazhoucao/Downloads/GoogleCast.zip";
  private static final String EXTENSION_VIDEOSTREAM_ZIP = "/Users/yazhoucao/Downloads/VideoStream.zip";

  private static ChromeDriverService service;

  static {
    service = new ChromeDriverService.Builder()
        .usingDriverExecutable(new File("path/to/my/chromedriver"))
        .usingAnyFreePort()
        .build();
    try {
      service.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws InterruptedException, IOException, ArchiveException {
    initChromeDriver();
  }

  public static void autoplay(String remoteAddr) {
    WebDriver driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());

  }

  public static ChromeDriver initChromeDriver() throws InterruptedException, IOException, ArchiveException {
    File googlecast = Files.exists(Paths.get(EXTENSION_GOOGLECAST_ZIP), LinkOption.NOFOLLOW_LINKS) ?
        Paths.get(EXTENSION_GOOGLECAST_ZIP).toFile() :
        ZipUtil.zipDirectory(EXTENSION_GOOGLECAST_ZIP, EXTENSION_GOOGLECAST_ADDR);
    File videostream = Files.exists(Paths.get(EXTENSION_VIDEOSTREAM_ZIP), LinkOption.NOFOLLOW_LINKS) ?
        Paths.get(EXTENSION_VIDEOSTREAM_ZIP).toFile() :
        ZipUtil.zipDirectory(EXTENSION_VIDEOSTREAM_ZIP, EXTENSION_VIDEOSTREAM_ADDR);

    ChromeOptions options = new ChromeOptions();
    options.addExtensions(googlecast, videostream);
    ChromeDriver driver = new ChromeDriver(options);
    System.out.println("ChromeDiver starts...");
    return driver;
  }

}
