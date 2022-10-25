package org.example.utils;

import java.util.Scanner;

public class ConsoleReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readConsole(String inputMessage) {
        System.out.println(inputMessage);
        return scanner.nextLine();
    }
}
