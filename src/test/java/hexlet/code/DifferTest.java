package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    private static final String FILE_PATH_1 = "/home/nikita/java-project-lvl2/src/test/resources/file1.json";
    private static final String FILE_PATH_2 = "src/test/resources/file2.json";
    private static final Path PATH_1 = Paths.get(FILE_PATH_1);
    private static final Path PATH_2 = Paths.get(FILE_PATH_2);

    @BeforeEach
    void makeFile() {
        new File(FILE_PATH_1);
        new File(FILE_PATH_2);
    }

    @Test
    void whenFilesAreNotEmpty() throws IOException {
        String json1 = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }""";
        String json2 = """
                {
                  "timeout": 20,
                  "verbose": true,
                  "host": "hexlet.io"
                }""";

        Files.writeString(PATH_1, json1);
        Files.writeString(PATH_2, json2);

        String actual = Differ.generate(FILE_PATH_1, FILE_PATH_2);
        String expected = """

                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        assertEquals(expected, actual);
    }

    @Test
    void whenFilesAreEmpty() throws IOException {
        String json1 = """
                {
                }
                """;
        String json2 = """
                {
                }""";

        Files.writeString(PATH_1, json1);
        Files.writeString(PATH_2, json2);

        String actual = Differ.generate(FILE_PATH_1, FILE_PATH_2);
        String expected = """

                {
                }""";
        assertEquals(expected, actual);
    }

    @Test
    void whenOneFileIsEmpty() throws IOException {
        String json1 = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }""";
        String json2 = """
                {
                }""";

        Files.writeString(PATH_1, json1);
        Files.writeString(PATH_2, json2);

        String actual = Differ.generate(FILE_PATH_1, FILE_PATH_2);
        String expected = """

                {
                  - follow: false
                  - host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                }""";
        assertEquals(expected, actual);
    }
}
