package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Differ {
    private static final int ONLY_KEY = 3;

    public static String generate(String filePath1, String filePath2) throws IOException {
        Path path1 = Paths.get(filePath1);
        Path path2 = Paths.get(filePath2);

        ObjectMapper objectmapper = new ObjectMapper();
        Map<String, Object> fileMap1 = objectmapper.readValue(path1.toFile(), new TypeReference<>() { });
        Map<String, Object> fileMap2 = objectmapper.readValue(path2.toFile(), new TypeReference<>() { });
        Map<String, Object> utilityMap = new HashMap<>(fileMap2);

        Map<String, Object> resultMap = new TreeMap<>((s, t1) -> {
            String key1 = s.substring(ONLY_KEY);
            String key2 = t1.substring(ONLY_KEY);

            if (key1.equals(key2)) {
                return 1;
            }

            return key1.compareTo(key2);
        });

        for (Map.Entry<String, Object> elem : fileMap1.entrySet()) {
            String difference = " - ";

            if (fileMap2.entrySet().contains(elem)) {
                utilityMap.remove(elem.getKey());
                difference = "   ";
            }
            resultMap.put(difference + elem.getKey(), elem.getValue());
        }

        for (Map.Entry<String, Object> elem : utilityMap.entrySet()) {
            resultMap.put(" + " + elem.getKey(), elem.getValue());
        }

        if (resultMap.isEmpty()) {
            return resultMap.toString()
                    .replace("{", "\n{")
                    .replace("}", "\n}");
        }

        return resultMap.toString()
               .replace("=", ": ")
               .replace(",", "\n")
               .replace("{", "\n{\n ")
               .replace("}", "\n}");
    }
}
