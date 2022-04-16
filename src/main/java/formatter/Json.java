package formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Json {
    private static final int ONLY_KEY = 4;

    public static String toString(Map<String, Object> resultMap) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> resultList = new LinkedList<>();
        List<Map.Entry<String, Object>> list = new LinkedList<>(resultMap.entrySet());
        list.add(null);

        for (int i = 0; i < list.size() - 1; i++) {
            var pair1 = list.get(i);
            var pair2 = list.get(i + 1);

            String key1 = getKey(pair1);
            String key2 = getKey(pair2);

            Object value1 = getValue(pair1);
            Object value2 = getValue(pair2);

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

        String json = objectMapper.writeValueAsString(resultList);

        return json;
    }

    private static Object getValue(Map.Entry<String, Object> pair) {
        return (pair != null) ? pair.getValue() : null;
    }

    private static String getKey(Map.Entry<String, Object> pair) {
        return (pair != null) ? pair.getKey().substring(ONLY_KEY) : null;
    }
}
