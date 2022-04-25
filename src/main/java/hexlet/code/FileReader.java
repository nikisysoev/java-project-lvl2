package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    public static String getData(String filePath) throws IOException {
        Path path = Paths.get(getLocalFilePath(filePath));

        return Files.readString(path);
    }

    private static String getLocalFilePath(String filePath) {
        return filePath.substring(filePath.indexOf("src"));
    }

    public static String getFileType(String filePath) {
        return filePath.substring(filePath.indexOf(".") + 1);
    }
}


