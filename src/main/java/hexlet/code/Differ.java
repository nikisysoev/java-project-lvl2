package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Differ {
    public static String generate(String filePath1, String filePath2) throws IOException  {
        Path path1 = Paths.get(filePath1);
        Path path2 = Paths.get(filePath2);

        ObjectMapper objectmapper = new ObjectMapper();
        Map<String, Object> fileMap1 = objectmapper.readValue(path1.toFile(), new TypeReference<>() {});
        Map<String, Object> fileMap2 = objectmapper.readValue(path2.toFile(), new TypeReference<>() {});
        Map<String, Object> utilityMap = new HashMap<>(fileMap2);

        Map<String, Object> resultMap = new TreeMap<>((s, t1) -> {
            String key1 = s.substring(2);
            String key2 = t1.substring(2);

            if (key1.equals(key2)) return 1;

            return key1.compareTo(key2);
        });

        for (Map.Entry<String, Object> elem : fileMap1.entrySet()) {
            String difference = "- ";
            
            if (fileMap2.entrySet().contains(elem)) {
                utilityMap.remove(elem.getKey());
                difference = "  ";
            }   
            resultMap.put(difference + elem.getKey(), elem.getValue());
        }

        for (Map.Entry<String, Object> elem : utilityMap.entrySet()) {
            resultMap.put("+ " + elem.getKey(), elem.getValue());
        }

       return resultMap.toString()
               .replace("=", ": ")
               .replace(",", "\n")
               .replace("{", "{\n ")
               .replace("}", "\n}");

    }
}
