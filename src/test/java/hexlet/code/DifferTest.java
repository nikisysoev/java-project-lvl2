package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    private static final List<Path> PATHS = new ArrayList<>();
    private static final Map<String, String> FILE_PATHS = new HashMap<>();
    static {
        FILE_PATHS.put("src/test/resources/file1.json", "src/test/resources/file2.json");
        FILE_PATHS.put("src/test/resources/file1.yml",  "src/test/resources/file2.yml");
    }

    @BeforeAll
    static void getPath() {
        for (Map.Entry<String, String> pair: FILE_PATHS.entrySet()) {
            PATHS.add(Paths.get(pair.getKey()));
            PATHS.add(Paths.get(pair.getValue()));
        }
    }

    @BeforeEach
    void makeFile() {
        for (Map.Entry<String, String> pair: FILE_PATHS.entrySet()) {
            new File(pair.getKey());
            new File(pair.getValue());
        }
    }

    @Test
    void whenFilesAreNotEmpty() throws IOException {
        String[] data = {"""
                  {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                  }""", """
                  {
                  "timeout": 20,
                  "verbose": true,
                  "host": "hexlet.io"
                  }""", """
                  host: 'hexlet.io'
                  timeout: 50
                  proxy: '123.234.53.22'
                  follow: false""", """
                  timeout: 20
                  verbose: true
                  host: 'hexlet.io'"""
        };
        writeDataToFiles(data);
        String expected = """

                    {
                      - follow: false
                        host: hexlet.io
                      - proxy: 123.234.53.22
                      - timeout: 50
                      + timeout: 20
                      + verbose: true
                    }""";
        for (Map.Entry<String, String> pair: FILE_PATHS.entrySet()) {
            String actual = Differ.generate(pair.getKey(), pair.getValue());
            assertEquals(expected, actual);
        }
    }

    @Test
    void whenFilesAreEmpty() throws IOException {
        String[] data = {"""
                {
                }""", """
                {
                }""", "", ""
        };
        writeDataToFiles(data);
        String expected = """

                {
                }""";
        for (Map.Entry<String, String> pair: FILE_PATHS.entrySet()) {
            String actual = Differ.generate(pair.getKey(), pair.getValue());
            assertEquals(expected, actual);
        }
    }

    @Test
    void whenOneFileIsEmpty() throws IOException {
        String[] data = {"""
                  {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                  }""", """
                {
                }""", """
                  host: 'hexlet.io'
                  timeout: 50
                  proxy: '123.234.53.22'
                  follow: false""", ""
        };
        writeDataToFiles(data);
        String expected = """

                  {
                    - follow: false
                    - host: hexlet.io
                    - proxy: 123.234.53.22
                    - timeout: 50
                  }""";
        for (Map.Entry<String, String> pair: FILE_PATHS.entrySet()) {
            String actual = Differ.generate(pair.getKey(), pair.getValue());
            assertEquals(expected, actual);
        }
    }

    void writeDataToFiles(String[] data) throws IOException {
        int i = 0;
        for (Path elem: PATHS) {
            Files.writeString(elem, data[i]);
            i++;
        }
    }
}
