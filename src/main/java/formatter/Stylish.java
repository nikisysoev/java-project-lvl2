package formatter;

import java.util.Map;

public class Stylish {
    public static String toString(Map<String, Object> resultMap) {
        StringBuilder sb = new StringBuilder("{\n");

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
