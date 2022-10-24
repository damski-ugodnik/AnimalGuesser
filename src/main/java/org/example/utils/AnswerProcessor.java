package org.example.utils;

public class AnswerProcessor {
    public static boolean getAnswer(String inputMsg) {
        String answer;
        while (true) {
            answer = ConsoleReader.readConsole(inputMsg.trim()).toLowerCase().trim();
            if (LanguageRules.matchesRules(answer, "positiveAnswer")) {
                return true;
            }
            if (LanguageRules.matchesRules(answer, "negativeAnswer")) {
                return false;
            }
            System.out.println(LanguageRules.getRandomConfuse());
        }
    }
}
