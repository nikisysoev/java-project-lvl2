package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {
    public static Map<String, Object> getData(String filePath) throws IOException {
        ObjectMapper objectmapper = chooseParserFormat(filePath);

        Path path = Paths.get(getLocalFilePath(filePath));
        File file = path.toFile();

        return objectmapper.readValue(file, new TypeReference<>() { });
    }

    private static String getLocalFilePath(String filePath) {
        return filePath.substring(filePath.indexOf("src"));
    }

    private static ObjectMapper chooseParserFormat(String filePath) {
        if (filePath.endsWith("json")) {
            return new ObjectMapper();
        }
        return new ObjectMapper(new YAMLFactory());
    }
}
