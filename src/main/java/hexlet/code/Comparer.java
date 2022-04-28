package hexlet.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Comparer {
    public static List<Map<String, Object>> compareData(Map<String, Object> dataMap1, Map<String, Object> dataMap2) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        Set<String> keySet = new TreeSet<>(dataMap1.keySet());
        keySet.addAll(dataMap2.keySet());

        for (String key: keySet) {
            Map<String, Object> map = new LinkedHashMap<>();

            if (dataMap1.containsKey(key) && !dataMap2.containsKey(key)) {
                map.put("key", key);
                map.put("oldvalue", dataMap1.get(key));
                map.put("status", "removed");
            } else if (dataMap2.containsKey(key) && !dataMap1.containsKey(key)) {
                map.put("key", key);
                map.put("newvalue", dataMap2.get(key));
                map.put("status", "added");
            } else if (Objects.equals(dataMap1.get(key), (dataMap2.get(key)))) {
                map.put("key", key);
                map.put("oldvalue", dataMap1.get(key));
                map.put("newvalue", dataMap2.get(key));
                map.put("status", "unchanged");
            } else {
                map.put("key", key);
                map.put("oldvalue", dataMap1.get(key));
                map.put("newvalue", dataMap2.get(key));
                map.put("status", "updated");
            }
            resultList.add(map);
        }
        return resultList;
    }
}
