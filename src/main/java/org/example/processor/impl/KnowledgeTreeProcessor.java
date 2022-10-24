package org.example.processor.impl;

import org.example.data.BinaryTreeDao;
import org.example.model.Node;
import org.example.processor.IGameProcessor;
import org.example.utils.LanguageRules;

public class KnowledgeTreeProcessor implements IGameProcessor {
    private final BinaryTreeDao binaryTreeDao;

    public KnowledgeTreeProcessor(BinaryTreeDao binaryTreeDao) {
        this.binaryTreeDao = binaryTreeDao;
    }

    @Override
    public void start() {
        Node root = binaryTreeDao.getRoot();
        if (root == null) {
            throw new RuntimeException("Tree is null");
        }
        traverseThroughTree(root, 0);
    }

    private void traverseThroughTree(Node root, int spacesCount) {
        System.out.println(getNSpaces(spacesCount) + root.getValue());
        if (root.isLeaf()) {
            return;
        }
        spacesCount++;
        traverseThroughTree(root.getPositiveResultNode(), spacesCount);
        traverseThroughTree(root.getNegativeResultNode(), spacesCount);
    }

    @Override
    public int getNumber() {
        return 5;
    }

    private static String getNSpaces(int n) {
        return " ".repeat(n);
    }

    @Override
    public String getDescription() {
        return getNumber() + ". " + LanguageRules.getMessage("menu.entry.print");
    }
}
