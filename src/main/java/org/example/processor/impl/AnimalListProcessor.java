package org.example.processor.impl;


import org.example.data.BinaryTree;
import org.example.model.Node;
import org.example.processor.IGameProcessor;
import org.example.utils.Constants;
import org.example.utils.LanguageRules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AnimalListProcessor implements IGameProcessor {
    private final BinaryTree binaryTree;

    public AnimalListProcessor(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }

    @Override
    public void start() {
        Node root = binaryTree.getRoot();
        if (root == null) {
            throw new RuntimeException("Tree is null");
        }
        List<Node> leaves = new ArrayList<>();
        fillLeavesList(root, leaves);
        System.out.println(LanguageRules.getMessage("tree.list.animals"));
        leaves.sort(Comparator.comparing(Node::getValue));
        for (Node leaf : leaves) {
            System.out.printf(" - %s%s", leaf.getValue(), Constants.LINE_SEPARATOR);
        }
    }

    @Override
    public int getNumber() {
        return 2;
    }

    @Override
    public String getDescription() {
        return getNumber() + ". " + LanguageRules.getMessage("menu.entry.list");
    }

    private void fillLeavesList(Node root, List<Node> leaves) {
        if (root.isLeaf()) {
            leaves.add(root);
            return;
        }
        fillLeavesList(root.getPositiveResultNode(), leaves);
        fillLeavesList(root.getNegativeResultNode(), leaves);
    }
}
