package hexlet.code.formatter;

import hexlet.code.Sorter;
import java.util.List;
import java.util.Map;

public class Stylish {
    private static final String[] DIFFERENCE = {"    ", "  - ", "  + "};
    private static StringBuilder sb;

    public static String toString(List<Map<String, Object>> resultList) {
        sb = new StringBuilder("{\n");
        Sorter.sortList(resultList);

        for (Map<String, Object> map: resultList) {
            switch ((String) map.get("status")) {
                case "unchanged" -> setUpString(map, "oldvalue", DIFFERENCE[0]);
                case "updated" -> {
                    setUpString(map, "oldvalue", DIFFERENCE[1]);
                    setUpString(map, "newvalue", DIFFERENCE[2]);
                }
                case "removed" -> setUpString(map, "oldvalue", DIFFERENCE[1]);
                case "added" -> setUpString(map, "newvalue", DIFFERENCE[2]);
                default -> { }
            }
        }
        return sb.append("}").toString();
    }

    private static void setUpString(Map<String, Object> map, String value, String difference) {
        sb.append(difference)
                .append(map.get("key"))
                .append(": ")
                .append(map.get(value))
                .append("\n");
    }
}
