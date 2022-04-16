package hexlet.code;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    private static final Map<String, String> SOURCE_FILES = new HashMap<>();
    static {
        SOURCE_FILES.put("src/test/resources/file1.json", "src/test/resources/file2.json");
        SOURCE_FILES.put("src/test/resources/file1.yml",  "src/test/resources/file2.yml");
    }

    private static final Map<String, String> EXPECTED_RESULTS = new HashMap<>();
    static {
        EXPECTED_RESULTS.put("src/test/resources/expected1", "stylish");
        EXPECTED_RESULTS.put("src/test/resources/expected2",  "plain");
        EXPECTED_RESULTS.put("src/test/resources/expected3",  "json");
    }

    @Test
    void whenFilesAreNotEmpty() throws IOException {
        for (Map.Entry<String, String> pairPaths: SOURCE_FILES.entrySet()) {

            for (Map.Entry<String, String> pathsAndFormats: EXPECTED_RESULTS.entrySet()) {

                Path path = Paths.get(pathsAndFormats.getKey());
                String expected = Files.readString(path);

                String actual = Differ.generate(pairPaths.getKey(), pairPaths.getValue(), pathsAndFormats.getValue());

                assertEquals(expected, actual);
            }
        }
    }
}
