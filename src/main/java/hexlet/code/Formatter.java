package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatter.Json;
import hexlet.code.formatter.Plain;
import hexlet.code.formatter.Stylish;
import java.util.List;
import java.util.Map;

public class Formatter {
    public static String toString(List<Map<String, Object>> resultList, String format) throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> Stylish.toString(resultList);
            case "plain" -> Plain.toString(resultList);
            default -> Json.toString(resultList);
        };
    }
}
