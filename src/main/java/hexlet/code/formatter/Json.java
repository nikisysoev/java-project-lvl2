package hexlet.code.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;

public class Json {
    public static String toString(List<Map<String, Object>> resultList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(resultList);

        return toJsonFormat(result);
    }

    private static String toJsonFormat(String result) {
        return result.replace(":", ": ")
                .replace(",", ", ")
                .replace("}, {", "},\n{")
                .replace("[{", "[\n{")
                .replace("}]", "}\n]");
    }
}
