package formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Json {
    public static String toString(Map<String, Object> resultMap) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> resultList = new LinkedList<>();
        List<Map.Entry<String, Object>> utilityList = new LinkedList<>(resultMap.entrySet());
        utilityList.add(null);

        for (int i = 0; i < utilityList.size() - 1; i++) {
            var pair1 = utilityList.get(i);
            var pair2 = utilityList.get(i + 1);

            String key1 = Formats.getKey(pair1);
            String key2 = Formats.getKey(pair2);

            Object value1 = Formats.getValue(pair1);
            Object value2 = Formats.getValue(pair2);

            Map<String, Object> map = new LinkedHashMap<>();

            if (key1.equals(key2)) {
                map.put("key", key1);
                map.put("oldvalue", value1);
                map.put("newvalue", value2);
                map.put("status", "updated");
                i++;
            } else if (pair1.getKey().contains("-")) {
                map.put("key", key1);
                map.put("oldvalue", value1);
                map.put("status", "removed");
            } else if (pair1.getKey().contains("+")) {
                map.put("key", key1);
                map.put("newvalue", value1);
                map.put("status", "added");
            } else {
                map.put("key", key1);
                map.put("oldvalue", value1);
                map.put("newvalue", value1);
                map.put("status", "unchanged");
            }
            resultList.add(map);
        }

        String result = objectMapper.writeValueAsString(resultList);

        return toJsonFormat(result);
    }

    private static String toJsonFormat(String result) {
        return result.replace(":", ": ")
                .replace(",", ", ")
                .replace("}, {", "},\n{")
                .replace("[{", "[\n{")
                .replace("}]", "}\n]");
    }
}
