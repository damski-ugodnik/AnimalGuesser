package org.example.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class Constants {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String WORKING_DIRECTORY_PATH_WITHOUT_EXTENSION =
            Paths.get("")
                    .toAbsolutePath() + File.separator + "animals.";
    public static final Pattern OPTION_INPUT_PATTERN = Pattern.compile("^\\d+$");
}
