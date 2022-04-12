package hexlet.code;

import java.util.Map;

public class Formatter {
    public static String toString(Map<String, Object> resultMap) {
        StringBuilder sb = new StringBuilder("\n{\n");

        for (Map.Entry<String, Object> pair: resultMap.entrySet()) {
            sb.append(pair.getKey())
                    .append(": ")
                    .append(pair.getValue())
                    .append("\n");
        }
        sb.append("}");

        return sb.toString();
    }
}
