package hexlet.code;

import formatter.Plain;
import formatter.Stylish;
import java.util.Map;

public class Formatter {
    public static String toString(Map<String, Object> resultMap, String format) {
        if (format.equals("stylish")) {
            return Stylish.toString(resultMap);
        }
        return Plain.toString(resultMap);
    }
}
