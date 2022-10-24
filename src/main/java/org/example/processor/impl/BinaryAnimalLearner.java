package org.example.processor.impl;

import org.example.data.BinaryTreeDao;
import org.example.model.Node;
import org.example.processor.IGameProcessor;
import org.example.utils.AnswerProcessor;
import org.example.utils.ConsoleReader;
import org.example.utils.Constants;
import org.example.utils.LanguageRules;

public class BinaryAnimalLearner implements IGameProcessor {
    private final BinaryTreeDao dao;

    public BinaryAnimalLearner(BinaryTreeDao dao) {
        this.dao = dao;
    }

    @Override
    public void start() {
        if (dao.getRoot() == null) {
            throw new RuntimeException("Tree not initialized");
        }
        System.out.println(LanguageRules.getMessage("animal.learnedMuch") + Constants.LINE_SEPARATOR +
                LanguageRules.getMessage("game.letsPlay"));
        boolean answer;
        do {
            ConsoleReader.readConsole(LanguageRules.getMessage("game.think") + Constants.LINE_SEPARATOR +
                    LanguageRules.getMessage("game.enter"));
            if (guessAnimal(dao.getRoot())) {
                System.out.println(LanguageRules.getMessage("game.win"));
            }
            answer = AnswerProcessor.getAnswer(LanguageRules.getMessage("game.again"));
        } while (answer);
    }

    private boolean guessAnimal(Node currentNode) {
        boolean answer = AnswerProcessor.getAnswer(currentNode.getQuestion());
        if (currentNode.getPositiveResultNode() == null || currentNode.getNegativeResultNode() == null) {
            if (!answer) {
                Node node = learnNewAnimal(currentNode);
                currentNode.setPositiveResultNode(node.getPositiveResultNode());
                currentNode.setNegativeResultNode(node.getNegativeResultNode());
                currentNode.setValue(node.getValue());
            }
            return answer;
        }
        if (answer) {
            return guessAnimal(currentNode.getPositiveResultNode());
        } else {
            return guessAnimal(currentNode.getNegativeResultNode());
        }
    }

    private Node learnNewAnimal(Node currentNode) {
        boolean answer;
        String statement;
        String animalName;
        while (true) {
            animalName = ConsoleReader.readConsole(LanguageRules.getMessage("game.giveUp"));
            if (!LanguageRules.matchesRules(animalName, "animal")) {
                System.out.println(LanguageRules.getMessage("animal.error"));
                continue;
            }
            animalName = LanguageRules.applyRules(animalName, "animal");

            statement = ConsoleReader.readConsole(String.format(LanguageRules.getMessage("statement.prompt", animalName,
                    LanguageRules.applyRules(currentNode.getValue(), "animal"))));
            if (LanguageRules.matchesRules(statement, "statement")) {
                statement = LanguageRules.applyRules(statement, "statement");
                break;
            }
            System.out.println(LanguageRules.getMessage("statement.error"));
        }
        answer = AnswerProcessor.getAnswer(LanguageRules.getMessage("game.isCorrect", animalName));
        currentNode = Node.addNewNode(currentNode.getValue(), statement, animalName, answer);
        System.out.printf(LanguageRules.getMessage("game.learned") + Constants.LINE_SEPARATOR +
                        " - %s." + Constants.LINE_SEPARATOR +
                        " - %s." + Constants.LINE_SEPARATOR +
                        LanguageRules.getMessage("game.distinguish") + Constants.LINE_SEPARATOR +
                        " - %s" + Constants.LINE_SEPARATOR +
                        LanguageRules.getMessage("animal.nice") +
                        LanguageRules.getMessage("animal.learnedMuch") + Constants.LINE_SEPARATOR,
                currentNode.getStatement(true), currentNode.getStatement(false), currentNode.getQuestion());
        return currentNode;
    }

    @Override
    public int getNumber() {
        return 1;
    }

    @Override
    public String getDescription() {
        return getNumber() + ". " + LanguageRules.getMessage("menu.entry.play");
    }
}
