package org.example.processor.impl;


import org.example.data.BinaryTree;
import org.example.model.Node;
import org.example.processor.IGameProcessor;
import org.example.utils.ConsoleReader;
import org.example.utils.Constants;
import org.example.utils.LanguageRules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimalSearcher implements IGameProcessor {
    private final BinaryTree binaryTree;

    public AnimalSearcher(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }

    @Override
    public void start() {
        String animalName = ConsoleReader.readConsole(LanguageRules.getMessage("tree.search.prompt"));
        animalName = LanguageRules.applyRules(animalName, "animalName");
        Node root = binaryTree.getRoot();
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
