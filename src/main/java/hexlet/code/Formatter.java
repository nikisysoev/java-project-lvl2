package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import formatter.Json;
import formatter.Plain;
import formatter.Stylish;
import java.util.Map;

public class Formatter {
    public static String toString(Map<String, Object> resultMap, String format) throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> Stylish.toString(resultMap);
            case "plain" -> Plain.toString(resultMap);
            default -> Json.toString(resultMap);
        };
    }
}
