package formatter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Plain {
    private static final int ONLY_KEY = 4;

    public static String toString(Map<String, Object> resultMap) {
        StringBuilder sb = new StringBuilder();
        LinkedList<Map.Entry<String, Object>> list = new LinkedList<>(resultMap.entrySet());
        list.add(null);

        for (int i = 0; i < list.size() - 1; i++) {
            var pair1 = list.get(i);
            var pair2 = list.get(i + 1);

            String key1 = getKey(pair1);
            String key2 = getKey(pair2);

            Object value1 = hideComplexValue(getValue(pair1));
            Object value2 = hideComplexValue(getValue(pair2));

            if (key1.equals(key2)) {
                sb.append("Property '" + key1 + "' was updated. From " + value1 + " to " + value2 + "\n");
                i++;
            } else if (pair1.getKey().contains("-")) {
                sb.append("Property '" + key1 + "' was removed\n");
            } else if (pair1.getKey().contains("+")) {
                sb.append("Property '" + key1 + "' was added with value: " + value1 + "\n");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static Object hideComplexValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        } else if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        }
        return value;
    }

    private static Object getValue(Map.Entry<String, Object> pair) {
        return (pair != null) ? pair.getValue() : null;
    }

    private static String getKey(Map.Entry<String, Object> pair) {
        return (pair != null) ? pair.getKey().substring(ONLY_KEY) : null;
    }
}
