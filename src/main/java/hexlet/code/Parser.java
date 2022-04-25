package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String data, String fileType) throws IOException {
        ObjectMapper objectmapper = chooseParserFormat(fileType);

        return objectmapper.readValue(data, new TypeReference<>() { });
    }

    private static ObjectMapper chooseParserFormat(String fileType) {
        return "json".equals(fileType) ? new ObjectMapper() : new ObjectMapper(new YAMLFactory());
    }
}
