package alararestaurant.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtilImpl implements FileUtil {

    @Override
    public String readFile(String path) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader(path));
        StringBuilder output = new StringBuilder();
        String line = bfr.readLine();
        while (line != null) {
            output.append(line).append(System.lineSeparator());

            line = bfr.readLine();
        }

        return output.toString().trim();
    }

    @Override
    public boolean isFileEmpty(String path) {
        File file = new File(path);

        return file.length() == 0L;
    }
}
