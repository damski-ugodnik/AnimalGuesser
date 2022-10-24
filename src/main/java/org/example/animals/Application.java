package org.example.animals;

import org.example.data.BinaryTreeDao;
import org.example.model.Node;
import org.example.model.PeriodOfDay;
import org.example.processor.GameProcessorFactory;
import org.example.processor.IGameProcessor;
import org.example.utils.ConsoleReader;
import org.example.utils.Constants;
import org.example.utils.LanguageRules;

import java.util.regex.Matcher;

public class Application {
    private final GameProcessorFactory factory;
    private final BinaryTreeDao dao;

    public Application(GameProcessorFactory factory, BinaryTreeDao binaryTreeDao) {
        this.factory = factory;
        this.dao = binaryTreeDao;
    }

    public void run() {
        System.out.println(PeriodOfDay.defineCurrentPeriod().getGreeting());
        if (dao.getRoot() == null) {
            learnIfTreeIsEmpty(dao);
        }
        System.out.println(LanguageRules.getMessage("welcome") + Constants.LINE_SEPARATOR);
        int option;
        String input;
        Matcher matcher;
        IGameProcessor processor;
        while (true) {
            printMenu();
            input = ConsoleReader.readConsole("");
            matcher = Constants.OPTION_INPUT_PATTERN.matcher(input);
            if (!matcher.matches()) {
                System.out.println(LanguageRules.getMessage("menu.property.error", factory.getDescriptions().size()));
                continue;
            }
            option = Integer.parseInt(input);
            if (option == 0) {
                break;
            }
            processor = factory.getProcessor(option);
            processor.start();
        }
        System.out.println(LanguageRules.getRandomFarewell());
    }

    private void printMenu() {
        System.out.println(LanguageRules.getMessage("menu.property.title"));
        for (String description : factory.getDescriptions()) {
            System.out.println(description);
        }
        System.out.println("0. " + LanguageRules.getMessage("menu.property.exit"));
    }

    private void learnIfTreeIsEmpty(BinaryTreeDao dao) {
        String input;
        while (true) {
            input = ConsoleReader.readConsole(LanguageRules.getMessage("animal.wantLearn") + Constants.LINE_SEPARATOR +
                    LanguageRules.getMessage("animal.askFavorite"));
            if (LanguageRules.matchesRules(input, "animal")) {
                break;
            }
            System.out.println(LanguageRules.getMessage("animal.error"));
        }
        String animalName = LanguageRules.applyRules(input, "animal");
        animalName = LanguageRules.applyRules(animalName, "animal");
        Node newNode = new Node(animalName);
        dao.setRoot(newNode);
        System.out.println(LanguageRules.getMessage("animal.nice") + Constants.LINE_SEPARATOR +
                LanguageRules.getMessage("animal.learnedMuch") + Constants.LINE_SEPARATOR);
    }
}
