package alararestaurant.util;

import java.io.IOException;

public interface FileUtil {

    String readFile(String filePath) throws IOException;

    boolean isFileEmpty(String path) throws IOException;
}
