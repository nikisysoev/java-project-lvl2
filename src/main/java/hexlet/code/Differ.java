package hexlet.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Differ {
    private static final String STYLISH = "stylish";
    private static final String[] DIFFERENCE = {"  - ", "    ", "  + "};

    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        Map<String, Object> fileMap1 = Parser.getData(filePath1);
        Map<String, Object> fileMap2 = Parser.getData(filePath2);

        Map<String, Object> resultMap = compareData(fileMap1, fileMap2);

        return Formatter.toString(resultMap, format);
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, STYLISH);
    }

    private static Map<String, Object> compareData(Map<String, Object> fileMap1, Map<String, Object> fileMap2) {
        Map<String, Object> utilityMap = new HashMap<>(fileMap2);
        Map<String, Object> resultMap = new TreeMap<>(Differ::sort);

        for (Map.Entry<String, Object> pair : fileMap1.entrySet()) {
            String difference = DIFFERENCE[0];
            if (fileMap2.entrySet().contains(pair)) {
                utilityMap.remove(pair.getKey());
                difference = DIFFERENCE[1];
            }
            resultMap.put(difference + pair.getKey(), pair.getValue());
        }

        for (Map.Entry<String, Object> pair : utilityMap.entrySet()) {
            resultMap.put(DIFFERENCE[2] + pair.getKey(), pair.getValue());
        }

        return resultMap;
    }

    private static int sort(String key1, String key2) {
        String elem1 = Utility.removeSpaces(key1);
        String elem2 = Utility.removeSpaces(key2);

        if (elem1.equals(elem2)) {
            return 1;
        }
        return elem1.compareTo(elem2);
    }
}
