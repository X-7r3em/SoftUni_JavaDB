package softuni.exam.util;

import java.io.*;

public class FileUtilImpl implements FileUtil {

    @Override
    public String readFile(String filePath) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader(filePath));
        StringBuilder output = new StringBuilder();
        String line = bfr.readLine();
        while (line != null) {
            output.append(line).append(System.lineSeparator());

            line = bfr.readLine();
        }

        return output.toString().trim();
    }
}
