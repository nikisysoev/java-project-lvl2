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
    private static final String[] FORMAT = {"stylish", "plain"};

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
                  "setting1": "Some value",
                  "setting2": 200,
                  "setting3": true,
                  "key1": "value1",
                  "numbers1": [1, 2, 3, 4],
                  "numbers2": [2, 3, 4, 5],
                  "id": 45,
                  "default": null,
                  "checked": false,
                  "numbers3": [3, 4, 5],
                  "chars1": ["a", "b", "c"],
                  "chars2": ["d", "e", "f"]
                }""", """
                {
                  "setting1": "Another value",
                  "setting2": 300,
                  "setting3": "none",
                  "key2": "value2",
                  "numbers1": [1, 2, 3, 4],
                  "numbers2": [22, 33, 44, 55],
                  "id": null,
                  "default": ["value1", "value2"],
                  "checked": true,
                  "numbers4": [4, 5, 6],
                  "chars1": ["a", "b", "c"],
                  "chars2": false,
                  "obj1": {
                    "nestedKey": "value",
                    "isNested": true
                  }
                }""", """
                   setting1: Some value
                   setting2: 200
                   setting3: true
                   key1: value1
                   numbers1: [1, 2, 3, 4]
                   numbers2: [2, 3, 4, 5]
                   id: 45
                   default: null
                   checked: false
                   numbers3: [3, 4, 5]
                   chars1: [a, b, c]
                   chars2: [d, e, f]
                   """, """
                   setting1: Another value
                   setting2: 300
                   setting3: none
                   key2: value2
                   numbers1: [1, 2, 3, 4]
                   numbers2: [22, 33, 44, 55]
                   id: null
                   default: [value1, value2]
                   checked: true
                   numbers4: [4, 5, 6]
                   chars1: [a, b, c]
                   chars2: false
                   obj1:
                     nestedKey: value
                     isNested: true
                """
        };
        writeDataToFiles(data);
        String[] expected = {"""

                 {
                     chars1: [a, b, c]
                   - chars2: [d, e, f]
                   + chars2: false
                   - checked: false
                   + checked: true
                   - default: null
                   + default: [value1, value2]
                   - id: 45
                   + id: null
                   - key1: value1
                   + key2: value2
                     numbers1: [1, 2, 3, 4]
                   - numbers2: [2, 3, 4, 5]
                   + numbers2: [22, 33, 44, 55]
                   - numbers3: [3, 4, 5]
                   + numbers4: [4, 5, 6]
                   + obj1: {nestedKey=value, isNested=true}
                   - setting1: Some value
                   + setting1: Another value
                   - setting2: 200
                   + setting2: 300
                   - setting3: true
                   + setting3: none
                 }""", """

                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'"""};

        for (Map.Entry<String, String> pair: FILE_PATHS.entrySet()) {
            for (int i = 0; i < FORMAT.length; i++) {
                String actual = Differ.generate(pair.getKey(), pair.getValue(), FORMAT[i]);
                assertEquals(expected[i], actual);
            }
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
