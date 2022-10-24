package processor.impl;

import data.BinaryTreeDao;
import model.Node;
import processor.IGameProcessor;
import utils.ConsoleReader;
import utils.Constants;
import utils.LanguageRules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimalSearcher implements IGameProcessor {
    private final BinaryTreeDao binaryTreeDao;

    public AnimalSearcher(BinaryTreeDao binaryTreeDao) {
        this.binaryTreeDao = binaryTreeDao;
    }

    @Override
    public void start() {
        String animalName = ConsoleReader.readConsole(LanguageRules.getMessage("tree.search.prompt"));
        animalName = LanguageRules.applyRules(animalName, "animalName");
        Node root = binaryTreeDao.getRoot();
        if (root == null) {
            throw new RuntimeException("Tree is null");
        }
        List<String> facts = new ArrayList<>();
        if (!getAllFactsAboutAnimal(animalName, root, facts) || facts.size() == 0) {
            System.out.println(LanguageRules.getMessage("tree.search.noFacts", animalName));
            return;
        }
        Collections.reverse(facts);
        System.out.println(LanguageRules.getMessage("tree.search.facts", animalName));
        for (String fact : facts) {
            System.out.printf(" - %s.%s", fact, Constants.LINE_SEPARATOR);
        }
    }

    private boolean getAllFactsAboutAnimal(String animalName, Node root, List<String> facts) {
        if (root.isLeaf()) {
            String nodeAnimalName = LanguageRules.applyRules(root.getValue(), "animalName");
            return animalName.equalsIgnoreCase(nodeAnimalName);
        }
        if (getAllFactsAboutAnimal(animalName, root.getPositiveResultNode(), facts)) {
            facts.add(root.getIndefiniteStatement(true));
            return true;
        }
        if (getAllFactsAboutAnimal(animalName, root.getNegativeResultNode(), facts)) {
            facts.add(root.getIndefiniteStatement(false));
            return true;
        }
        return false;
    }

    @Override
    public int getNumber() {
        return 3;
    }

    @Override
    public String getDescription() {
        return getNumber() + ". " + LanguageRules.getMessage("menu.entry.search");
    }
}
