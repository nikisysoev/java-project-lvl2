package hexlet.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Sorter {
    public static void sortList(List<Map<String, Object>> resultList) {
        resultList.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                String value1 = (String) map1.get("key");
                String value2 = (String) map2.get("key");

                return value1.compareTo(value2);
            }
        });
    }

    public static List<Map<String, Object>> sortMapInList(List<Map<String, Object>> resultList) {
        List<Map<String, Object>> listWithSortedMap = new ArrayList<>();

        for (Map<String, Object> map: resultList) {
            Map<String, Object> sortedMap = new TreeMap<>(Sorter::compare);
            sortedMap.putAll(map);

            listWithSortedMap.add(sortedMap);
        }
        return listWithSortedMap;
    }

    private static int compare(String key1, String key2) {
        if ("oldvalue".equals(key1) && "newvalue".equals(key2)) {
            return -1;
        } else if ("oldvalue".equals(key2) && "newvalue".equals(key1)) {
            return 1;
        }
        return key1.compareTo(key2);
    }
}
