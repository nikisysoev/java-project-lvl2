package formatter;

import hexlet.code.Utility;
import java.util.Map;

public class Formats {
    public static Object getValue(Map.Entry<String, Object> pair) {
        return (pair != null) ? pair.getValue() : null;
    }

    public static String getKey(Map.Entry<String, Object> pair) {
        return (pair != null) ? Utility.removeSpaces(pair.getKey()) : null;
    }
}
