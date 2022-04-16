package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import formatter.Json;
import formatter.Plain;
import formatter.Stylish;
import java.util.Map;

public class Formatter {
    public static String toString(Map<String, Object> resultMap, String format) throws JsonProcessingException {
        if (format.equals("stylish")) {
            return Stylish.toString(resultMap);
        } else if (format.equals("plain")) {
            return Plain.toString(resultMap);
        }
        return Json.toString(resultMap);
    }
}
