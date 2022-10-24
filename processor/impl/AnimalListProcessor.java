package processor.impl;

import data.BinaryTreeDao;
import model.Node;
import processor.IGameProcessor;
import utils.Constants;
import utils.LanguageRules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AnimalListProcessor implements IGameProcessor {
    private final BinaryTreeDao binaryTreeDao;

    public AnimalListProcessor(BinaryTreeDao binaryTreeDao) {
        this.binaryTreeDao = binaryTreeDao;
    }

    @Override
    public void start() {
        Node root = binaryTreeDao.getRoot();
        if (root == null) {
            throw new RuntimeException("Tree is null");
        }
        List<Node> leaves = new ArrayList<>();
        getAllLeaves(root, leaves);
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

    private void getAllLeaves(Node root, List<Node> leaves) {
        if (root.isLeaf()) {
            leaves.add(root);
            return;
        }
        getAllLeaves(root.getPositiveResultNode(), leaves);
        getAllLeaves(root.getNegativeResultNode(), leaves);
    }
}
