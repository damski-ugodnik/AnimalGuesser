package org.example.processor.impl;

import org.example.data.BinaryTree;
import org.example.model.BinaryTreeStatistic;
import org.example.model.Node;
import org.example.processor.IGameProcessor;
import org.example.utils.LanguageRules;

public class StatisticsProcessor implements IGameProcessor {
    private final BinaryTree binaryTree;

    public StatisticsProcessor(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }

    @Override
    public void start() {
        Node root = binaryTree.getRoot();
        if (root == null) {
            throw new RuntimeException("Tree is null");
        }
        BinaryTreeStatistic binaryTreeStatistic = BinaryTreeStatistic.of(root);
        System.out.println(binaryTreeStatistic);
    }

    @Override
    public int getNumber() {
        return 4;
    }

    @Override
    public String getDescription() {
        return getNumber() + ". " + LanguageRules.getMessage("menu.entry.statistics");
    }
}
