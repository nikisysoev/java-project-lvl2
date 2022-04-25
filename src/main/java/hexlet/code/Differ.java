package hexlet.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Differ {
    private static final String STYLISH = "stylish";
    private static Map<String, Object> dataMap1;
    private static Map<String, Object> dataMap2;

    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        String data1 = FileReader.getData(filePath1);
        String data2 = FileReader.getData(filePath2);

        String fileType1 = FileReader.getFileType(filePath1);
        String fileType2 = FileReader.getFileType(filePath2);

        dataMap1 = Parser.parse(data1, fileType1);
        dataMap2 = Parser.parse(data2, fileType2);

        List<Map<String, Object>> resultList = compareDataMaps();

        return Formatter.toString(resultList, format);
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, STYLISH);
    }

    private static List<Map<String, Object>> compareDataMaps() {
        List<Map<String, Object>> resultList = new ArrayList<>();

        iterateFirstMap(resultList);
        iterateSecondMap(resultList);

        return resultList;
    }

    private static void iterateFirstMap(List<Map<String, Object>> resultList) {
        for (Map.Entry<String, Object> pair: dataMap1.entrySet()) {
            if (!dataMap2.containsKey(pair.getKey())) {
                resultList.add(setUpFirstMap(pair, "removed"));
            } else if (dataMap2.entrySet().contains(pair)) {
                resultList.add(setUpFirstMap(pair, "unchanged"));
            } else {
                resultList.add(setUpFirstMap(pair, "updated"));
            }
        }
    }

    private static void iterateSecondMap(List<Map<String, Object>> resultList) {
        for (Map.Entry<String, Object> pair: dataMap2.entrySet()) {
            resultList.add(setUpSecondMap(pair));
        }
    }

    private static Map<String, Object> setUpFirstMap(Map.Entry<String, Object> pair, String status) {
        Map<String, Object> map = new HashMap<>();

        if ("removed".equals(status)) {
            map.put("key", pair.getKey());
            map.put("oldvalue", pair.getValue());
            map.put("status", status);
            return map;
        }

        map.put("key", pair.getKey());
        map.put("oldvalue", pair.getValue());
        map.put("newvalue", dataMap2.get(pair.getKey()));
        map.put("status", status);
        dataMap2.remove(pair.getKey());

        return map;
    }

    private static Map<String, Object> setUpSecondMap(Map.Entry<String, Object> pair) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", pair.getKey());
        map.put("newvalue", pair.getValue());
        map.put("status", "added");

        return map;
    }
}
