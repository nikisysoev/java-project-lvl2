package hexlet.code;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DifferTest {
    static Stream<Arguments> pathFileAndFormatProvider() {
        return Stream.of(
                arguments("src/test/resources/file1.json", "src/test/resources/file2.json",
                        "src/test/resources/expected1", "stylish"),
                arguments("src/test/resources/file1.yml", "src/test/resources/file2.yml",
                        "src/test/resources/expected2", "plain"),
                arguments("src/test/resources/file1.json", "src/test/resources/file2.json",
                        "src/test/resources/expected3", "json")
        );
    }

    @ParameterizedTest
    @MethodSource("pathFileAndFormatProvider")
    void whenFilesAreNotEmpty(String pathFile1, String pathFile2, String resultPath, String format) throws IOException {
        Path path = Paths.get(resultPath);

        String expected = Files.readString(path);
        String actual = Differ.generate(pathFile1, pathFile2, format);

        assertEquals(expected, actual);
    }
}
