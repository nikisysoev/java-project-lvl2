package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable {

    @Option(names = { "-f", "--format" }, paramLabel = "format", description = "output format [default: stylish]")
    private String format = "stylish";

    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private String filePath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private String filePath2;

    @Override
    public final Object call() throws Exception {
        String difference = Differ.generate(filePath1, filePath2);
        System.out.println(difference);
        return null;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
