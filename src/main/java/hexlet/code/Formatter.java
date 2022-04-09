package hexlet.code;

import java.util.Map;

public class Formatter {
    public static String toString(Map<String, Object> resultMap) {
        String result = resultMap.toString()
                .replace("}", "\n}");

        if (resultMap.isEmpty()) {
            return result.replace("{", "\n{");
        }
        return result.replace("=", ": ")
                .replace(",", "\n")
                .replace("{", "\n{\n ");
    }
}
