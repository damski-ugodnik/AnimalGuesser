package org.example.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class LanguageRules {
    private static final Random random = new Random();
    private static Map<String, Pattern> patterns;
    private static Map<String, String> replacements;
    private static ResourceBundle messages;
    private static Set<String> farewells;
    private static Set<String> excuses;
    private static Map<String, Pattern> checkingPatterns;

    static {
        init("");
    }

    public static void setLanguage(String languageCode) {
        if (!languageCode.equals("")) {
            languageCode = "_" + languageCode;
        }
        init(languageCode);
    }

    private static void init(String languageCode) {
        messages = ResourceBundle.getBundle("messages" + languageCode);
        ResourceBundle patternsBundle = ResourceBundle.getBundle("patterns");
        patterns = patternsBundle.keySet().stream()
                .filter(key -> key.endsWith(".pattern"))
                .collect(toUnmodifiableMap(key -> key.replace(".pattern", ""),
                        key -> Pattern.compile(patternsBundle.getString(key), Pattern.CASE_INSENSITIVE)));
        replacements = patternsBundle.keySet().stream()
                .filter(key -> key.endsWith(".replace"))
                .collect(Collectors.toUnmodifiableMap(key -> key.replace(".replace", ""), patternsBundle::getString));
        farewells = new HashSet<>(messages.keySet().stream()
                .filter(key -> key.startsWith("farewell"))
                .collect(Collectors.toMap(Function.identity(), messages::getString)).values());
        excuses = new HashSet<>(messages.keySet().stream()
                .filter(key -> key.startsWith("ask.again"))
                .collect(Collectors.toMap(Function.identity(), messages::getString)).values());
        checkingPatterns = patternsBundle.keySet()
                .stream().filter(key -> key.endsWith(".isCorrect"))
                .collect(Collectors.toUnmodifiableMap(key -> key.replace(".isCorrect", ""),
                        key -> Pattern.compile(patternsBundle.getString(key), Pattern.CASE_INSENSITIVE)));
    }

    public static String getMessage(String key) {
        try {
            return messages.getString(key);
        } catch (NullPointerException | ClassCastException | MissingResourceException e) {
            throw new RuntimeException("No such key");
        }
    }

    public static String getMessage(String key, Object value) {
        return MessageFormat.format(getMessage(key), value);
    }

    public static String getMessage(String key, Object value1, Object value2) {
        return MessageFormat.format(getMessage(key), value1, value2);
    }

    public static String applyRules(String input, String patternName) {
        String key;
        Pattern pattern;
        Matcher matcher;
        int i = 0;
        while (true) {
            i++;
            key = patternName + "." + i;
            pattern = patterns.get(key);

            if (isNull(pattern)) {
                return input;
            }
            matcher = pattern.matcher(input);
            if (matcher.matches()) {
                return matcher.replaceFirst(replacements.get(key));
            }
        }
    }

    public static boolean matchesRules(String input, String patternName) {
        Pattern pattern = checkingPatterns.get(patternName);
        if (pattern == null) {
            throw new RuntimeException("no such pattern");
        }
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static String getRandomFarewell() {
        return getRandomItemFromSet(farewells);
    }

    public static String getRandomConfuse() {
        return getRandomItemFromSet(excuses);
    }

    private static <T> T getRandomItemFromSet(Set<T> set) {
        return new ArrayList<>(set).get(random.nextInt(set.size()));
    }
}
