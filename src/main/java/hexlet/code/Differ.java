package hexlet.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Differ {
    private static final int ONLY_KEY = 4;

    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        Map<String, Object> fileMap1 = Parser.getData(filePath1);
        Map<String, Object> fileMap2 = Parser.getData(filePath2);
        Map<String, Object> resultMap = compareData(fileMap1, fileMap2);
        return Formatter.toString(resultMap, format);
    }

    private static Map<String, Object> compareData(Map<String, Object> fileMap1, Map<String, Object> fileMap2) {
        Map<String, Object> utilityMap = new HashMap<>(fileMap2);
        Map<String, Object> resultMap = new TreeMap<>(Differ::sort);

        for (Map.Entry<String, Object> pair : fileMap1.entrySet()) {
            String difference = "  - ";
            if (fileMap2.entrySet().contains(pair)) {
                utilityMap.remove(pair.getKey());
                difference = "    ";
            }
            resultMap.put(difference + pair.getKey(), pair.getValue());
        }

        for (Map.Entry<String, Object> pair : utilityMap.entrySet()) {
            resultMap.put("  + " + pair.getKey(), pair.getValue());
        }
        return resultMap;
    }

    private static int sort(String key1, String key2) {
        String keyWithoutSpaces1 = key1.substring(ONLY_KEY);
        String keyWithoutSpaces2 = key2.substring(ONLY_KEY);

        if (keyWithoutSpaces1.equals(keyWithoutSpaces2)) {
            return 1;
        }
        return keyWithoutSpaces1.compareTo(keyWithoutSpaces2);
    }
}
