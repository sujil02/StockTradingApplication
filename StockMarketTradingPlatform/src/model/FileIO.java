package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * This class is responsible to do all the File IO operations for this application.
 */
public class FileIO {
  /**
   * Create a file of given type and the specified location.
   *
   * @param serializedContents Contents to be published.
   * @param path               location where it is to be published.
   * @param fileName           name with which the file is to be created.
   * @param append             appendable or no.
   * @param type               file type.
   */
  public void createFile(String serializedContents, String path,
                         String fileName, boolean append, String type) {
    BufferedWriter bw = null;
    FileWriter fw = null;
    File file = new File(path + "\\" + fileName + type);
    try {
      // if file doesnt exists, then create it
      if (!file.exists()) {
        file.createNewFile();
      }
      // if file preference is set to non-appendable clear the old file.
      if (!append) {
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
      }
      fw = new FileWriter(file.getAbsoluteFile(), false);
      bw = new BufferedWriter(fw);
      bw.write(serializedContents);
    } catch (IOException e) {
      //Do nothing
    } finally {
      try {
        if (bw != null && fw != null) {
          bw.close();
          fw.close();
        }
      } catch (IOException ex) {
        //Do Nothing
      }
    }
  }

  /**
   * This Method reads the contents of the given file.
   *
   * @param path path of the file
   * @return String representation of the contents of the given file.
   * @throws IOException If error in reading file.
   */

  public String readFile(String path) throws IOException {
    try {
      return new String(Files.readAllBytes(Paths.get(path)));
    } catch (NoSuchFileException e) {
      throw new IOException("File Not Found");
    }
  }
}
