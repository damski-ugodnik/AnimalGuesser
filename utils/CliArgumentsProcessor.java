package utils;

import java.util.Arrays;

public class CliArgumentsProcessor {
    public static String getFileExtension(String[] args) {
        int index = Arrays.stream(args).toList().indexOf("-type");
        if (index == -1 || index == args.length - 1) {
            return "json";
        }
        return args[index + 1];
    }

    public static String getLanguageCode() {
        String languageCode = System.getProperty("user.language");
        if (languageCode.equals("en")) {
            languageCode = "";
        }
        return languageCode;
    }
}
