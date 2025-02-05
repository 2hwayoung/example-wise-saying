
import standard.TestUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;


class AppTest {
    private static ByteArrayOutputStream out;
    private static final Path tempDbPath = Paths.get("db/testWiseSaying");;

    public static void clear() {
        if (out != null) TestUtil.clearSetOutToByteArray(out);
    }

    public static void close() {
        try (Stream<Path> paths = Files.walk(tempDbPath)) {
           paths.map(Path::toFile)
             .forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException("Failed to close temp file: " + e.getMessage());
        }
    }

    public static String run(String input) {
        Scanner scanner = TestUtil.genScanner(input + "종료\n");
        out = TestUtil.setOutToByteArray();

        App app = new App(scanner);
        app.run();

        return out.toString();
    }

    public static void makeSample(int cnt) {
        App app = new App(null);
        app.makeSampleData(cnt);
    }

}