package hexlet.code;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Differ {
    private static final String STYLISH = "stylish";

    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        String data1 = FileReader.getData(filePath1);
        String data2 = FileReader.getData(filePath2);

        String fileType1 = FileReader.getFileType(filePath1);
        String fileType2 = FileReader.getFileType(filePath2);

        Map<String, Object> dataMap1 = Parser.parse(data1, fileType1);
        Map<String, Object> dataMap2 = Parser.parse(data2, fileType2);

        List<Map<String, Object>> resultList = Comparer.compareData(dataMap1, dataMap2);

        return Formatter.toString(resultList, format);
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, STYLISH);
    }
}
