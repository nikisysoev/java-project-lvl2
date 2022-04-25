package hexlet.code.formatter;

import hexlet.code.Sorter;
import java.util.List;
import java.util.Map;

public class Plain  {
    private static StringBuilder sb;

    public static String toString(List<Map<String, Object>> resultList) {
        sb = new StringBuilder();
        Sorter.sortList(resultList);

        for (Map<String, Object> map: resultList) {
            setUpString(map);

            switch ((String) map.get("status")) {
                case "updated" -> setUpUpdated(map);
                case "removed" -> setUpRemoved(map);
                case "added" -> setUpAdded(map);
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static void setUpString(Map<String, Object> map) {
        String status = (String) map.get("status");

        if (!"unchanged".equals(status)) {
            sb.append("Property '")
                    .append(map.get("key"))
                    .append("' was ")
                    .append(status);
        }
    }

    private static void setUpUpdated(Map<String, Object> map) {
        sb.append(". ")
                .append("From ")
                .append(hideComplexValue(map.get("oldvalue")))
                .append(" to ")
                .append(hideComplexValue(map.get("newvalue")))
                .append("\n");
    }

    private static void setUpRemoved(Map<String, Object> map) {
        sb.append("\n");
    }

    private static void setUpAdded(Map<String, Object> map) {
        sb.append(" with value: ")
                .append(hideComplexValue(map.get("newvalue")))
                .append("\n");
    }

    private static Object hideComplexValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        } else if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        }
        return value;
    }
}
