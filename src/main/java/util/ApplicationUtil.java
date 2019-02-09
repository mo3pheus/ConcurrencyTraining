package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ApplicationUtil {
    public static String getMessageFilePath(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--messageFile")) {
                return args[i + 1];
            }
        }
        return null;
    }

    public static String getMessage(String[] args) throws IOException {
        String       filePath = getMessageFilePath(args);
        List<String> messages = Files.readAllLines(Paths.get(filePath));
        String       message  = "";
        for (String line : messages) {
            message += line;
        }
        return message;
    }

    public static int getEncodingBound(boolean lower, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (lower && args[i].equals("--offsetLowerBound")) {
                return Integer.parseInt(args[i + 1]);
            } else if (!lower && args[i].equals("--offsetUpperBound")) {
                return Integer.parseInt(args[i + 1]);
            }
        }
        throw new IllegalArgumentException("Invalid bounds / bounds not passed in");
    }
}
