package general.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZipUtil.java
 * <p>
 * Utility class dependent on commons-compress and commons-io to
 * handle my zip compression needs.
 *
 * @author Jason Ferguson
 */
public class ZipUtil {

  /**
   * This method zips the directory
   */
  public static File zipDirectory(String zippedFileName, File dir) {
    File zippedFile = null;
    try {
      List<File> fileList = populateFilesList(dir);
      //now zip files one by one
      //create ZipOutputStream to write to the zip file
      zippedFile = new File(zippedFileName);
      FileOutputStream fos = new FileOutputStream(zippedFile);
      ZipOutputStream zos = new ZipOutputStream(fos);
      for (File file : fileList) {
        String filePath = file.getAbsolutePath();
        //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
        ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
        zos.putNextEntry(ze);
        //read the file and write to ZipOutputStream
        FileInputStream fis = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) > 0) {
          zos.write(buffer, 0, len);
        }
        zos.closeEntry();
        fis.close();
      }
      zos.close();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return zippedFile;
  }

  /**
   * This method populates all the files in a directory to a List
   */
  private static List<File> populateFilesList(File dir) throws IOException {
    List<File> filesListInDir = new ArrayList<>();
    populateFilesList(dir, filesListInDir);
    return filesListInDir;
  }

  private static void populateFilesList(File dir, List<File> fileList) throws IOException {
    File[] files = dir.listFiles();
    if (files == null) {
      return;
    }
    for (File file : files) {
      if (file.isFile())
        fileList.add(file);
      else
        populateFilesList(file, fileList);
    }
  }

  /**
   * This method compresses the single file to zip format
   */
  public static File zipFile(String zippedFileName, File file) {
    File zippedFile = null;
    try {
      zippedFile = new File(zippedFileName);
      //create ZipOutputStream to write to the zip file
      FileOutputStream fos = new FileOutputStream(zippedFile);
      ZipOutputStream zos = new ZipOutputStream(fos);
      //add a new Zip Entry to the ZipOutputStream
      ZipEntry ze = new ZipEntry(file.getName());
      zos.putNextEntry(ze);
      //read the file and write to ZipOutputStream
      FileInputStream fis = new FileInputStream(file);
      byte[] buffer = new byte[1024];
      int len;
      while ((len = fis.read(buffer)) > 0) {
        zos.write(buffer, 0, len);
      }

      //Close the zip entry to write to zip file
      zos.closeEntry();
      //Close resources
      zos.close();
      fis.close();
      fos.close();
      System.out.println(file.getCanonicalPath() + " is zipped to " + zippedFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return zippedFile;
  }
}
