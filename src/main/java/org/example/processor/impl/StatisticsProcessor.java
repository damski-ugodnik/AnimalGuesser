package org.example.processor.impl;

import org.example.data.BinaryTreeDao;
import org.example.model.BinaryTreeStatistic;
import org.example.model.Node;
import org.example.processor.IGameProcessor;
import org.example.utils.LanguageRules;

public class StatisticsProcessor implements IGameProcessor {
    private final BinaryTreeDao binaryTreeDao;

    public StatisticsProcessor(BinaryTreeDao binaryTreeDao) {
        this.binaryTreeDao = binaryTreeDao;
    }

    @Override
    public void start() {
        Node root = binaryTreeDao.getRoot();
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
