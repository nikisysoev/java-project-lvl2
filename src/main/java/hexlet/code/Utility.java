package hexlet.code;

public class Utility {
    private static final int NUMBER_OF_SPACES = 4;

    public static String removeSpaces(String key) {
        return key.substring(NUMBER_OF_SPACES);
    }
}
